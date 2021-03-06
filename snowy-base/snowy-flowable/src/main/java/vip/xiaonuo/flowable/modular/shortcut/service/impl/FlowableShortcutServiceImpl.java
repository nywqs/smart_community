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
package vip.xiaonuo.flowable.modular.shortcut.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xiaonuo.core.enums.CommonStatusEnum;
import vip.xiaonuo.core.exception.ServiceException;
import vip.xiaonuo.core.factory.PageFactory;
import vip.xiaonuo.core.pojo.page.PageResult;
import vip.xiaonuo.flowable.modular.shortcut.entity.FlowableShortcut;
import vip.xiaonuo.flowable.modular.shortcut.enums.FlowableShortcutExceptionEnum;
import vip.xiaonuo.flowable.modular.shortcut.mapper.FlowableShortcutMapper;
import vip.xiaonuo.flowable.modular.shortcut.node.FlowableShortcutTreeNode;
import vip.xiaonuo.flowable.modular.shortcut.param.FlowableShortcutParam;
import vip.xiaonuo.flowable.modular.shortcut.service.FlowableShortcutService;

import java.util.List;

/**
 * 流程申请入口service接口实现类
 *
 * @author xuyuxiang
 * @date 2020/6/30 10:50
 */
@Service
public class FlowableShortcutServiceImpl extends ServiceImpl<FlowableShortcutMapper, FlowableShortcut> implements FlowableShortcutService {

    @Override
    public PageResult<FlowableShortcut> page(FlowableShortcutParam flowableShortcutParam) {
        LambdaQueryWrapper<FlowableShortcut> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(flowableShortcutParam)) {
            //根据名称查询
            if (ObjectUtil.isNotEmpty(flowableShortcutParam.getName())) {
                queryWrapper.like(FlowableShortcut::getName, flowableShortcutParam.getName());
            }
            //根据分类查询
            if (ObjectUtil.isNotEmpty(flowableShortcutParam.getCategory())) {
                queryWrapper.like(FlowableShortcut::getCategory, flowableShortcutParam.getCategory());
            }
        }
        queryWrapper.ne(FlowableShortcut::getStatus, CommonStatusEnum.DELETED.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(FlowableShortcut::getSort);
        return new PageResult<>(this.page(PageFactory.defaultPage(), queryWrapper));
    }

    @Override
    public List<FlowableShortcutTreeNode> list(FlowableShortcutParam flowableShortcutParam) {
        QueryWrapper<FlowableShortcutTreeNode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("b.status", CommonStatusEnum.ENABLE.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc("b.sort");
        return this.baseMapper.list(queryWrapper);
    }

    @Override
    public void add(FlowableShortcutParam flowableShortcutParam) {
        //校验参数，检查是否存在相同的名称和流程定义key
        checkParam(flowableShortcutParam, false);
        FlowableShortcut flowableShortcut = new FlowableShortcut();
        BeanUtil.copyProperties(flowableShortcutParam, flowableShortcut);
        flowableShortcut.setStatus(CommonStatusEnum.ENABLE.getCode());
        this.save(flowableShortcut);
    }

    @Override
    public void delete(FlowableShortcutParam flowableShortcutParam) {
        FlowableShortcut flowableShortcut = this.queryFlowableShortcut(flowableShortcutParam);
        flowableShortcut.setStatus(CommonStatusEnum.DELETED.getCode());
        this.updateById(flowableShortcut);
    }

    @Override
    public void delete(String processDefinitionId) {
        LambdaQueryWrapper<FlowableShortcut> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowableShortcut::getProcessDefinitionId, processDefinitionId);
        FlowableShortcut flowableShortcut = this.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(flowableShortcut)) {
            flowableShortcut.setStatus(CommonStatusEnum.DELETED.getCode());
            this.updateById(flowableShortcut);
        }
    }

    @Override
    public void edit(FlowableShortcutParam flowableShortcutParam) {
        FlowableShortcut flowableShortcut = this.queryFlowableShortcut(flowableShortcutParam);
        //校验参数，检查是否存在相同的名称和流程定义key
        checkParam(flowableShortcutParam, true);
        BeanUtil.copyProperties(flowableShortcutParam, flowableShortcut);
        //不能修改状态，用修改状态接口修改状态
        flowableShortcut.setStatus(null);
        this.updateById(flowableShortcut);
    }

    @Override
    public FlowableShortcut detail(FlowableShortcutParam flowableShortcutParam) {
        return this.queryFlowableShortcut(flowableShortcutParam);
    }

    @Override
    public void changeStatus(String processDefinitionId, Integer status) {
        LambdaQueryWrapper<FlowableShortcut> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowableShortcut::getProcessDefinitionId, processDefinitionId);
        FlowableShortcut flowableShortcut = this.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(flowableShortcut)) {
            flowableShortcut.setStatus(status);
            this.updateById(flowableShortcut);
        }
    }

    @Override
    public void applyLastVersionIntoShortcut(String lastVersionProcessDefinitionId, String newVersionProcessDefinitionId) {
        FlowableShortcut flowableShortcut = this.getOne(new LambdaQueryWrapper<FlowableShortcut>()
                .eq(FlowableShortcut::getProcessDefinitionId, lastVersionProcessDefinitionId));
        if(ObjectUtil.isNotEmpty(flowableShortcut)) {
            //移除主键id
            flowableShortcut.setId(null);
            //设置为最新版定义版本
            flowableShortcut.setVersion(flowableShortcut.getVersion() + 1);
            String[] nameArr = flowableShortcut.getName().split("V");
            int newVersion = flowableShortcut.getVersion() + 1;
            if(ObjectUtil.isNotEmpty(nameArr)) {
                flowableShortcut.setName(nameArr[0] + "V" + newVersion);
            } else {
                flowableShortcut.setName(flowableShortcut.getName() + "V" + newVersion);
            }
            //设置为最新版定义id
            flowableShortcut.setProcessDefinitionId(newVersionProcessDefinitionId);
            //新增一条
            this.save(flowableShortcut);
        }
    }

    /**
     * 校验参数，检查是否存在相同的名称和流程定义key
     *
     * @author xuyuxiang
     * @date 2020/6/30 12:08
     */
    private void checkParam(FlowableShortcutParam flowableShortcutParam, boolean isExcludeSelf) {
        Long id = flowableShortcutParam.getId();
        String name = flowableShortcutParam.getName();
        String processDefinitionId = flowableShortcutParam.getProcessDefinitionId();

        LambdaQueryWrapper<FlowableShortcut> queryWrapperByName = new LambdaQueryWrapper<>();
        queryWrapperByName.eq(FlowableShortcut::getName, name)
                .ne(FlowableShortcut::getStatus, CommonStatusEnum.DELETED.getCode());

        LambdaQueryWrapper<FlowableShortcut> queryWrapperByProcessInstanceId = new LambdaQueryWrapper<>();
        queryWrapperByProcessInstanceId.eq(FlowableShortcut::getProcessDefinitionId, processDefinitionId)
                .ne(FlowableShortcut::getStatus, CommonStatusEnum.DELETED.getCode());

        if (isExcludeSelf) {
            queryWrapperByName.ne(FlowableShortcut::getId, id);
            queryWrapperByProcessInstanceId.ne(FlowableShortcut::getId, id);
        }
        int countByName = this.count(queryWrapperByName);
        int countByKey = this.count(queryWrapperByProcessInstanceId);

        if (countByName >= 1) {
            throw new ServiceException(FlowableShortcutExceptionEnum.SHORTCUT_NAME_REPEAT);
        }
        if (countByKey >= 1) {
            throw new ServiceException(FlowableShortcutExceptionEnum.SHORTCUT_CODE_REPEAT);
        }
    }

    /**
     * 获取流程申请入口
     *
     * @author xuyuxiang
     * @date 2020/6/30 12:03
     */
    private FlowableShortcut queryFlowableShortcut(FlowableShortcutParam flowableShortcutParam) {
        FlowableShortcut flowableShortcut = this.getById(flowableShortcutParam.getId());
        if (ObjectUtil.isNull(flowableShortcut)) {
            throw new ServiceException(FlowableShortcutExceptionEnum.SHORTCUT_NOT_EXIST);
        }
        return flowableShortcut;
    }
}
