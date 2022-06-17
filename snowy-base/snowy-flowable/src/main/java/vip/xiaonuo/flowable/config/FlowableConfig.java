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
package vip.xiaonuo.flowable.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventDispatcher;
import org.flowable.common.engine.impl.event.FlowableEventDispatcherImpl;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.spring.boot.FlowableProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.xiaonuo.core.consts.CommonConstant;
import vip.xiaonuo.core.context.constant.ConstantContext;
import vip.xiaonuo.flowable.core.listener.*;
import vip.xiaonuo.flowable.core.utils.BpmScriptUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * flowable配置
 *
 * @author xuyuxiang
 * @date 2020/4/13 20:40
 */
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Resource
    private BpmScriptUtil bpmScriptUtil;

    /**
     * FlowableProperties配置，关闭定时任务
     *
     * @author xuyuxiang
     * @date 2020/4/14 8:54
     */
    @Bean
    public FlowableProperties flowableProperties() {
        FlowableProperties flowableProperties = new FlowableProperties();
        //关闭自动任务
        flowableProperties.setAsyncExecutorActivate(false);
        //关闭自动创建表，第一次创建完表之后可以关闭
        // 判断当前是否需要初始化
        boolean flowableDbInitializedFlag = false;
        if(ObjectUtil.isNotEmpty(ConstantContext.me().get(CommonConstant.FLOWABLE_DB_INITIALIZED_FLAG_NAME))){
            flowableDbInitializedFlag = (boolean) ConstantContext.me().get(CommonConstant.FLOWABLE_DB_INITIALIZED_FLAG_NAME);
        }
        // 已初始化则无需生成表
        flowableProperties.setDatabaseSchemaUpdate(Convert.toStr(!flowableDbInitializedFlag));
        //关闭自动部署resource/processes中的流程文件
        flowableProperties.setCheckProcessDefinitions(false);
        return flowableProperties;
    }

    /**
     * 自定义Flowable主键生成格式
     *
     * @author xuyuxiang
     * @date 2020/4/21 17:27
     */
    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        //使用mybatis-plus的主键生成器，注意：不会影响act_de开头的表主键生成，因为这是流程设计器的，不是工作流引擎的
        springProcessEngineConfiguration.setIdGenerator(() -> Convert.toStr(new DefaultIdentifierGenerator().nextId(null)));
        //创建EventDispatcher
        FlowableEventDispatcher eventDispatcher = new FlowableEventDispatcherImpl();
        //添加全局流程启动监听器
        eventDispatcher.addEventListener(new GlobalProcessStartedListener(), FlowableEngineEventType.PROCESS_STARTED);
        //添加全局流程完成监听器
        eventDispatcher.addEventListener(new GlobalProcessCompletedListener(), FlowableEngineEventType.PROCESS_COMPLETED);
        //添加全局流程取消监听器
        eventDispatcher.addEventListener(new GlobalProcessCancelledListener(), FlowableEngineEventType.PROCESS_CANCELLED);
        //添加全局活动开始监听器
        eventDispatcher.addEventListener(new GlobalActStartedListener(), FlowableEngineEventType.ACTIVITY_STARTED);
        //添加全局活动完成监听器
        eventDispatcher.addEventListener(new GlobalActCompletedListener(), FlowableEngineEventType.ACTIVITY_COMPLETED);
        //添加全局活动取消监听器
        eventDispatcher.addEventListener(new GlobalActCancelledListener(), FlowableEngineEventType.ACTIVITY_CANCELLED);
        //添加全局任务分配监听器
        eventDispatcher.addEventListener(new GlobalTaskAssignedListener(), FlowableEngineEventType.TASK_ASSIGNED);
        //添加全局任务创建监听器
        eventDispatcher.addEventListener(new GlobalTaskCreatedListener(), FlowableEngineEventType.TASK_CREATED);
        //添加全局任务完成监听器
        eventDispatcher.addEventListener(new GlobalTaskCompletedListener(), FlowableEngineEventType.TASK_COMPLETED);
        //添加全局连接线监听器
        eventDispatcher.addEventListener(new GlobalSequenceflowTakenListener(), FlowableEngineEventType.SEQUENCEFLOW_TAKEN);
        //设置EventDispatcher
        springProcessEngineConfiguration.setEventDispatcher(eventDispatcher);
        //自定义的bean，注册到流程引擎，可用在UEL表达式中，比如，请假天数判断，可以在表达式中使用${BpmScriptUtil.getFormDataKey(execution, "days") > 3 }
        Map<Object, Object> beanMap = new HashMap<>();
        beanMap.put("BpmScriptUtil", bpmScriptUtil);
        springProcessEngineConfiguration.setBeans(beanMap);
    }
}
