<template>
  <a-modal
    title="新增活动"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="活动主题"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入活动主题" v-decorator="['title', {rules: [{required: true, message: '请输入活动主题！'}]}]" />
        </a-form-item>
        <a-form-item
          label="活动介绍"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-textarea placeholder="请输入活动介绍" v-decorator="['content', {rules: [{required: true, message: '请输入活动介绍！'}]}]" :auto-size="{ minRows: 3, maxRows: 6 }"/>
        </a-form-item>
        <a-form-item
          label="活动封面"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入活动封面" v-decorator="['faceImage', {rules: [{required: true, message: '请输入活动封面！'}]}]" />
        </a-form-item>
        <a-form-item
          label="报名开始时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-date-picker style="width: 100%" placeholder="请选择报名开始时间" v-decorator="['preStarttime',{rules: [{ required: true, message: '请选择报名开始时间！' }]}]" @change="onChangepreStarttime"/>
        </a-form-item>
        <a-form-item
          label="报名截至时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-date-picker style="width: 100%" placeholder="请选择报名截至时间" v-decorator="['preEndtime',{rules: [{ required: true, message: '请选择报名截至时间！' }]}]" @change="onChangepreEndtime"/>
        </a-form-item>
        <a-form-item
          label="活动开始时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-date-picker style="width: 100%" placeholder="请选择活动开始时间" v-decorator="['starttime',{rules: [{ required: true, message: '请选择活动开始时间！' }]}]" @change="onChangestarttime"/>
        </a-form-item>
        <a-form-item
          label="活动结束时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-date-picker style="width: 100%" placeholder="请选择活动结束时间" v-decorator="['endtime',{rules: [{ required: true, message: '请选择活动结束时间！' }]}]" @change="onChangeendtime"/>
        </a-form-item>
        <a-form-item
          label="是否收费"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-radio-group placeholder="请选择是否收费" v-decorator="['chargeStatus',{rules: [{ required: true, message: '请选择是否收费！' }]}]" >
            <a-radio v-for="(item,index) in chargeStatusData" :key="index" :value="item.code">{{ item.name }}</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item
          label="报名费"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入报名费" v-decorator="['price', {rules: [{required: true, message: '请输入报名费！'}]}]" />
        </a-form-item>
        <a-form-item
          label="是否捐助"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-radio-group placeholder="请选择是否捐助" v-decorator="['welfareStatus',{rules: [{ required: true, message: '请选择是否捐助！' }]}]" >
            <a-radio v-for="(item,index) in welfareStatusData" :key="index" :value="item.code">{{ item.name }}</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item
          label="捐助金额"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入捐助金额" v-decorator="['welfareMoney', {rules: [{required: true, message: '请输入捐助金额！'}]}]" />
        </a-form-item>
        <a-form-item
          label="状态"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择状态" v-decorator="['status', {rules: [{ required: true, message: '请选择状态！' }]}]">
            <a-select-option v-for="(item,index) in statusData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { activityAdd } from '@/api/modular/main/activity/activityManage'
  export default {
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        preStarttimeDateString: '',
        preEndtimeDateString: '',
        starttimeDateString: '',
        endtimeDateString: '',
        chargeStatusData: [],
        welfareStatusData: [],
        statusData: [],
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this)
      }
    },
    methods: {
      // 初始化方法
      add (record) {
        this.visible = true
        const chargeStatusOption = this.$options
        this.chargeStatusData = chargeStatusOption.filters['dictData']('yes_or_no')
        const welfareStatusOption = this.$options
        this.welfareStatusData = welfareStatusOption.filters['dictData']('yes_or_no')
        const statusOption = this.$options
        this.statusData = statusOption.filters['dictData']('sys_activiti_status')
      },
      /**
       * 提交表单
       */
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            for (const key in values) {
              if (typeof (values[key]) === 'object') {
                values[key] = JSON.stringify(values[key])
              }
            }
            values.preStarttime = this.preStarttimeDateString
            values.preEndtime = this.preEndtimeDateString
            values.starttime = this.starttimeDateString
            values.endtime = this.endtimeDateString
            activityAdd(values).then((res) => {
              if (res.success) {
                this.$message.success('新增成功')
                this.confirmLoading = false
                this.$emit('ok', values)
                this.handleCancel()
              } else {
                this.$message.error('新增失败')// + res.message
              }
            }).finally((res) => {
              this.confirmLoading = false
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      onChangepreStarttime(date, dateString) {
        this.preStarttimeDateString = dateString
      },
      onChangepreEndtime(date, dateString) {
        this.preEndtimeDateString = dateString
      },
      onChangestarttime(date, dateString) {
        this.starttimeDateString = dateString
      },
      onChangeendtime(date, dateString) {
        this.endtimeDateString = dateString
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      }
    }
  }
</script>
