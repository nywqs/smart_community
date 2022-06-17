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
package vip.xiaonuo.flowable.modular.task.donetask.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import vip.xiaonuo.flowable.modular.instance.result.FlowableInstanceResult;

import java.util.Date;

/**
 * 已办任务结果集
 *
 * @author xuyuxiang
 * @date 2020/4/20 9:59
 */
@Data
public class FlowableDoneTaskResult {

    /**
     * 任务id
     */
    private String id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 活动节点id
     */
    private String activityId;

    /**
     * 执行id
     */
    private String executionId;

    /**
     * 办理人
     */
    private String assignee;

    /**
     * 办理人姓名
     */
    private String assigneeName;

    /**
     * 办理人信息（自定义格式）
     */
    private String assigneeInfo;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 任务创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 格式化后的任务创建时间（如3分钟前）
     */
    private String formatCreateTime;

    /**
     * 任务签收时间（限指定多个人时，其中一个人签收后即为自己的任务）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date claimTime;

    /**
     * 任务完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 格式化后的流程开启时间（如3分钟前）
     */
    private String formatEndTime;

    /**
     * 任务历时
     */
    private String duration;

    /**
     * 任务期限
     */
    private Date dueDate;

    /**
     * 委托状态（0委托中 1委托结束）
     */
    private Integer delegationState;

    /**
     * 流程实例相关信息(包含了流程定义相关信息）
     */
    private FlowableInstanceResult procIns;
}
