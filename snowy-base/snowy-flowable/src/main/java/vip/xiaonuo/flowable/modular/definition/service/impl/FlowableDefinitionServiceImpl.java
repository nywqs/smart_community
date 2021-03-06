/*
Copyright [2020] [https://www.xiaonuo.vip]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Snowy源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/xiaonuobase/snowy
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/xiaonuobase/snowy
6.若您的项目无法满足以上几点，可申请商业授权，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.flowable.modular.definition.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.flowable.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.NotFoundException;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.service.BpmnDisplayJsonConverter;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.core.consts.SymbolConstant;
import vip.xiaonuo.core.context.constant.ConstantContextHolder;
import vip.xiaonuo.core.enums.CommonStatusEnum;
import vip.xiaonuo.core.exception.DemoException;
import vip.xiaonuo.core.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.flowable.modular.button.service.FlowableButtonService;
import vip.xiaonuo.flowable.modular.definition.enums.FlowableDefinitionExceptionEnum;
import vip.xiaonuo.flowable.modular.definition.factory.FlowableDefinitionFactory;
import vip.xiaonuo.flowable.modular.definition.param.FlowableDefinitionParam;
import vip.xiaonuo.flowable.modular.definition.result.FlowableDefinitionResult;
import vip.xiaonuo.flowable.modular.definition.result.FlowableUserTaskResult;
import vip.xiaonuo.flowable.modular.definition.service.FlowableDefinitionService;
import vip.xiaonuo.flowable.modular.event.service.FlowableEventService;
import vip.xiaonuo.flowable.modular.form.service.FlowableFormService;
import vip.xiaonuo.flowable.modular.option.service.FlowableOptionService;
import vip.xiaonuo.flowable.modular.shortcut.service.FlowableShortcutService;
import vip.xiaonuo.flowable.modular.user.factory.FlowableUserFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 流程定义service接口实现类
 *
 * @author xuyuxiang
 * @date 2020/4/14 19:26
 */
@Service
public class FlowableDefinitionServiceImpl implements FlowableDefinitionService {

    /**
     * 模型资源后缀
     */
    private static final String MODEL_BPMN_SUFFIX = ".bpmn20.xml";

    /**
     * 启动节点默认启动人标志
     */
    private static final String INITIATOR = "INITIATOR";

    /**
     * 用户任务节点流程发起人默认标志
     */
    private static final String SYMBOL_INITIATOR = "$INITIATOR";

    /**
     * 部署时需替换的用户任务节点流程发起人变量标志
     */
    private static final String DOUBLE_SYMBOL_INITIATOR = "${INITIATOR}";

    private static final Log log = Log.get();

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ModelService modelService;

    @Resource
    private BpmnDisplayJsonConverter bpmnDisplayJsonConverter;

    @Resource
    private FlowableOptionService flowableOptionService;

    @Resource
    private FlowableButtonService flowableButtonService;

    @Resource
    private FlowableEventService flowableEventService;

    @Resource
    private FlowableShortcutService flowableShortcutService;

    @Resource(name = "snowyFlowableFormService")
    private FlowableFormService flowableFormService;

    @Override
    public PageResult<FlowableDefinitionResult> page(FlowableDefinitionParam flowableDefinitionParam) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (ObjectUtil.isNotNull(flowableDefinitionParam)) {
            //根据key模糊查询
            if (ObjectUtil.isNotEmpty(flowableDefinitionParam.getKey())) {
                processDefinitionQuery.processDefinitionKeyLike(SymbolConstant.PERCENT + flowableDefinitionParam.getKey() + SymbolConstant.PERCENT);
            }
            //根据名称模糊查询
            if (ObjectUtil.isNotEmpty(flowableDefinitionParam.getName())) {
                processDefinitionQuery.processDefinitionNameLike(SymbolConstant.PERCENT + flowableDefinitionParam.getName() + SymbolConstant.PERCENT);
            }
            //根据分类查询
            if (ObjectUtil.isNotEmpty(flowableDefinitionParam.getCategory())) {
                processDefinitionQuery.processDefinitionCategory(flowableDefinitionParam.getCategory());
            }
            //根据挂起状态查询
            if (flowableDefinitionParam.getSuspended()) {
                processDefinitionQuery.suspended();
            }
            //默认查询最新版本
            if (flowableDefinitionParam.getLastedVersion()) {
                processDefinitionQuery.latestVersion();
            }
        }
        Page<FlowableDefinitionResult> defaultPage = PageFactory.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery
                .listPage(Convert.toInt((current - 1) * size), Convert.toInt(size));
        defaultPage.setTotal(processDefinitionQuery.count());
        return FlowableDefinitionFactory.pageResult(processDefinitionList, defaultPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deploy(FlowableDefinitionParam flowableDefinitionParam) {

        Boolean demoEnvFlag = ConstantContextHolder.getDemoEnvFlag();
        if (demoEnvFlag) {
            throw new DemoException();
        }

        //此model为设计器model，非act_re_model
        String modelId = flowableDefinitionParam.getModelId();

        //流程分类
        String category = flowableDefinitionParam.getCategory();
        //流程模型
        org.flowable.ui.modeler.domain.Model designModel;
        try {
            //获取流程模型
            designModel = modelService.getModel(modelId);
        } catch (NotFoundException e) {
            //捕获异常该流程模型不存在
            throw new ServiceException(FlowableDefinitionExceptionEnum.MODEL_NOT_EXIST);
        }

        //获取bpmnModel
        BpmnModel bpmnModel = modelService.getBpmnModel(designModel);

        //下面这段代码是用于修复流程发起人参数，关于这个问题详见https://github.com/flowable/flowable-engine/issues/534
        //关于initiator作用以及使用见http://www.shareniu.com/article/191.htm
        bpmnModel.getMainProcess().getFlowElements().forEach(flowElement -> {
            //遍历节点，若为开始事件，则设置INITIATOR
            if (flowElement instanceof StartEvent) {
                StartEvent startEvent = (StartEvent) flowElement;
                startEvent.setInitiator(INITIATOR);
            }
            //遍历节点，若任务节点的待办人为$INITIATOR（流程引擎默认的标识，不能用于设置流程启动人参数，需替换为${INITIATOR}）
            if (flowElement instanceof UserTask) {
                UserTask userTask = (UserTask) flowElement;
                String assignee = userTask.getAssignee();
                if (ObjectUtil.isNotEmpty(assignee)) {
                    //若任务节点的待办人为$INITIATOR则替换为${INITIATOR}
                    if (SYMBOL_INITIATOR.equals(assignee)) {
                        userTask.setAssignee(DOUBLE_SYMBOL_INITIATOR);
                    }
                }
            }
        });

        if (ObjectUtil.isEmpty(bpmnModel)) {
            //模型资源为空，则抛异常该流程模型无资源
            throw new ServiceException(FlowableDefinitionExceptionEnum.MODEL_HAS_NO_RESOURCE);
        }

        //定义一个部署构造器
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        //流程命名与模型相同
        deploymentBuilder.name(designModel.getName());
        //构造部署的流程资源名称
        String deploymentName = designModel.getName() + MODEL_BPMN_SUFFIX;
        //设置流程资源
        deploymentBuilder.addBpmnModel(deploymentName, bpmnModel);
        //设置流程分类
        deploymentBuilder.category(category);
        //部署
        try {
            Deployment deployment = deploymentBuilder.deploy();
            String deploymentId = deployment.getId();
            //部署后根据deploymentId查询到processDefinition，设置分类
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deploymentId).singleResult();
            repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);

            //获取当前版本号
            int version = processDefinition.getVersion();
            //如果不是第一版本，则获取上一版本的配置，并拷贝
            if(version != 1) {
                //最新版定义id
                String newVersionProcessDefinitionId = processDefinition.getId();
                //获取上一版本流程定义
                ProcessDefinition lastVersionProcessDefinition = repositoryService.createProcessDefinitionQuery()
                        .processDefinitionKey(processDefinition.getKey())
                        .processDefinitionVersion(version - 1).singleResult();
                if(ObjectUtil.isNotEmpty(lastVersionProcessDefinition)) {
                    //上一版本定义id
                    String lastVersionProcessDefinitionId = lastVersionProcessDefinition.getId();
                    //拷贝上一版本的选项配置
                    flowableOptionService.copyLastVersionOptionConfig(lastVersionProcessDefinitionId, newVersionProcessDefinitionId);
                    //拷贝上一版本的流程按钮配置
                    flowableButtonService.copyLastVersionButtonConfig(lastVersionProcessDefinitionId, newVersionProcessDefinitionId);
                    //拷贝上一版本的流程事件配置
                    flowableEventService.copyLastVersionEventConfig(lastVersionProcessDefinitionId, newVersionProcessDefinitionId);
                    //拷贝上一版本的流程表单配置
                    flowableFormService.copyLastVersionFormConfig(lastVersionProcessDefinitionId, newVersionProcessDefinitionId);
                    //重新应用最新版本的定义到入口重新命名为【原入口名称+版本号】格式，如【请假管理V2】，
                    //原版本的入口保留以备不时之需，若不需要可以手动在入口管理删除
                    flowableShortcutService.applyLastVersionIntoShortcut(lastVersionProcessDefinitionId, newVersionProcessDefinitionId);
                }
            } else {
                //为第一版本，则部署后自动插入一条流程选项设置
                flowableOptionService.addByProcessDefinition(processDefinition);
            }
        } catch (Exception e) {
            log.error(">>> 流程部署失败，具体信息为：{}", e.getMessage());
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_ERROR);
        }

    }

    @Override
    public void export(FlowableDefinitionParam flowableDefinitionParam, HttpServletResponse response) {
        //根据id获取流程定义
        String id = flowableDefinitionParam.getId();

        //获取流程定义
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        //根据流程定义获取deployment
        String deploymentId = processDefinition.getDeploymentId();
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId(deploymentId).singleResult();
        if (ObjectUtil.isEmpty(deployment)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_NOT_EXIST);
        }

        //获取资源名称
        String resourceName = processDefinition.getResourceName();
        List<String> resourceList = repositoryService.getDeploymentResourceNames(deploymentId);

        //遍历资源名称集合，匹配到则导出
        if (resourceList.contains(resourceName)) {
            try {
                final InputStream resourceStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
                byte[] bytes = IoUtil.readBytes(resourceStream);
                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(resourceName, "UTF-8") + "\"");
                response.addHeader("Content-Length", "" + bytes.length);
                response.setContentType("application/octet-stream;charset=UTF-8");
                IoUtil.write(response.getOutputStream(), true, bytes);
            } catch (Exception e) {
                log.error(">>> 流程导出失败，流读取错误：{}", e.getMessage());
                throw new ServiceException(FlowableDefinitionExceptionEnum.RESOURCE_EXPORT_ERROR);
            }
        } else {
            log.error(">>> 流程导出失败，无该资源文件名称");
            throw new ServiceException(FlowableDefinitionExceptionEnum.RESOURCE_EXPORT_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void mapping(FlowableDefinitionParam flowableDefinitionParam) {
        //给flowable设置当前用户
        FlowableUserFactory.assumeUser();

        //根据id获取流程定义
        String id = flowableDefinitionParam.getId();

        //获取流程定义
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        //根据流程定义获取deployment
        String deploymentId = processDefinition.getDeploymentId();
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (ObjectUtil.isEmpty(deployment)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_NOT_EXIST);
        }

        //获取bpmnModel并转为modelNode
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);

        ObjectNode modelNode = new BpmnJsonConverter().convertToJson(bpmnModel);

        //构造模型
        ModelRepresentation model = new ModelRepresentation();
        model.setKey(processDefinition.getKey());
        model.setName(processDefinition.getName());
        model.setDescription(processDefinition.getDescription());
        model.setModelType(AbstractModel.MODEL_TYPE_BPMN);
        //创建模型
        modelService.createModel(model, modelNode.toString(), SecurityUtils.getCurrentUserObject());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void activeOrSuspend(FlowableDefinitionParam flowableDefinitionParam, boolean isSuspend) {
        //根据id获取流程定义
        String id = flowableDefinitionParam.getId();
        //获取流程定义
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        boolean suspended = processDefinition.isSuspended();

        //如果要挂起且已经是挂起状态
        if (suspended && isSuspend) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEFINITION_SUSPEND);
        }

        //如果要激活且已经是激活状态
        if (!suspended && !isSuspend) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEFINITION_ACTIVE);
        }
        if (isSuspend) {
            //挂起
            repositoryService.suspendProcessDefinitionById(id);
            //修改对应的入口状态
            flowableShortcutService.changeStatus(id, CommonStatusEnum.DISABLE.getCode());
        } else {
            //激活
            repositoryService.activateProcessDefinitionById(id);
            //修改对应的入口状态
            flowableShortcutService.changeStatus(id, CommonStatusEnum.ENABLE.getCode());
        }
    }

    @Override
    public JsonNode trace(FlowableDefinitionParam flowableDefinitionParam) {
        //根据id获取流程定义
        String id = flowableDefinitionParam.getId();
        //获取流程定义
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        //根据流程定义获取deployment
        String deploymentId = processDefinition.getDeploymentId();
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (ObjectUtil.isEmpty(deployment)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_NOT_EXIST);
        }

        //获取bpmnModel并转为modelNode
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);
        ObjectNode modelNode = new BpmnJsonConverter().convertToJson(bpmnModel);

        //构建model并设置流程设计资源
        Model model = new Model();
        model.setModelEditorJson(modelNode.toString());

        //获取流程图显示节点数据
        ObjectNode displayNode = new ObjectMapper().createObjectNode();
        bpmnDisplayJsonConverter.processProcessElements(model, displayNode, new GraphicInfo());

        return displayNode;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(FlowableDefinitionParam flowableDefinitionParam) {
        //根据id获取流程定义
        String id = flowableDefinitionParam.getId();
        //获取流程定义
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);
        String deploymentId = processDefinition.getDeploymentId();
        repositoryService.deleteDeployment(deploymentId, true);
        //删除该定义对应的表单配置信息
        flowableFormService.delete(id);
        //同时删除该流程定义的选项配置
        flowableOptionService.delete(id);
        //同时删除该流程定义的流程事件
        flowableEventService.delete(id);
        //同时删除该流程定义的流程按钮
        flowableButtonService.delete(id);
        //同时删除该流程定义对应的申请入口
        flowableShortcutService.delete(id);
    }

    @Override
    public List<FlowableUserTaskResult> userTasks(FlowableDefinitionParam flowableDefinitionParam) {
        List<FlowableUserTaskResult> resultList = CollectionUtil.newArrayList();
        //根据id获取流程定义
        String id = flowableDefinitionParam.getId();

        //获取流程定义
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        //根据流程定义获取deployment
        String deploymentId = processDefinition.getDeploymentId();
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (ObjectUtil.isEmpty(deployment)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_NOT_EXIST);
        }

        //获取bpmnModel并转为modelNode
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);
        //获取主流程
        Process mainProcess = bpmnModel.getMainProcess();
        //获取用户任务节点类型，深入子流程
        mainProcess.findFlowElementsOfType(UserTask.class, true).forEach(userTask -> {
            FlowableUserTaskResult flowableUserTaskResult = new FlowableUserTaskResult();
            flowableUserTaskResult.setId(userTask.getId());
            flowableUserTaskResult.setProcessDefinitionId(processDefinition.getId());
            flowableUserTaskResult.setName(userTask.getName());
            resultList.add(flowableUserTaskResult);
        });
        return resultList;
    }

    @Override
    public FlowableDefinitionResult detail(String processDefinitionId) {
        ProcessDefinition processDefinition = this.queryProcessDefinition(processDefinitionId);
        return FlowableDefinitionFactory.convertToFlowableDefinitionResult(processDefinition);
    }

    @Override
    public ProcessDefinition queryProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        if (ObjectUtil.isNull(processDefinition)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEFINITION_NOT_EXIST);
        }
        return processDefinition;
    }

    @Override
    public ProcessDefinition queryProcessDefinitionWithValidStatus(String processDefinitionId) {
        ProcessDefinition processDefinition = this.queryProcessDefinition(processDefinitionId);
        boolean suspended = processDefinition.isSuspended();
        if (suspended) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEFINITION_SUSPEND);
        }
        return processDefinition;
    }

    @Override
    public String getMultiInstanceActAssigneeParam(String processDefinitionId, String actId) {
        AtomicReference<String> resultParam = new AtomicReference<>();
        ProcessDefinition processDefinition = this.queryProcessDefinition(processDefinitionId);
        //获取bpmnModel并转为modelNode
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        //获取主流程
        Process mainProcess = bpmnModel.getMainProcess();
        //获取用户任务节点类型，深入子流程
        mainProcess.findFlowElementsOfType(UserTask.class, true).forEach(userTask -> {
            String userTaskId = userTask.getId();
            if (userTaskId.equals(actId)) {
                Object behavior = userTask.getBehavior();
                if (ObjectUtil.isNotNull(behavior)) {
                    //并行多实例节点
                    if (behavior instanceof ParallelMultiInstanceBehavior) {
                        ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
                                (ParallelMultiInstanceBehavior) behavior;
                        String collectionElementVariable = parallelMultiInstanceBehavior
                                .getCollectionElementVariable();
                        if (ObjectUtil.isNotEmpty(collectionElementVariable)) {
                            resultParam.set(collectionElementVariable);
                        }
                    }
                    //串行多实例节点
                    if (behavior instanceof SequentialMultiInstanceBehavior) {
                        SequentialMultiInstanceBehavior sequentialMultiInstanceBehavior =
                                (SequentialMultiInstanceBehavior) behavior;
                        String collectionElementVariable = sequentialMultiInstanceBehavior
                                .getCollectionElementVariable();
                        if (ObjectUtil.isNotEmpty(collectionElementVariable)) {
                            resultParam.set(collectionElementVariable);
                        }
                    }
                }
            }
        });
        return resultParam.get();
    }

    @Override
    public boolean hasDefinition(String category) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.processDefinitionCategory(category);
        return processDefinitionQuery.list().size() > 0;
    }
}
