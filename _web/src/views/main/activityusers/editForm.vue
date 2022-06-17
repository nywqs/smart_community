<template>
  <a-modal
    title="编辑报名记录"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item v-show="false"><a-input v-decorator="['id']" /></a-form-item>
        <a-form-item
          label="活动ID"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入活动ID" v-decorator="['activityId', {rules: [{required: true, message: '请输入活动ID！'}]}]" />
        </a-form-item>
        <a-form-item
          label="参与用户ID"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入参与用户ID" v-decorator="['userId', {rules: [{required: true, message: '请输入参与用户ID！'}]}]" />
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
  import { activityUsersEdit } from '@/api/modular/main/activityusers/activityUsersManage'
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
        statusData: [],
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this)
      }
    },
    methods: {
      // 初始化方法
      edit (record) {
        this.visible = true
        const statusOption = this.$options
        this.statusData = statusOption.filters['dictData']('common_status')
        setTimeout(() => {
          this.form.setFieldsValue(
            {
              id: record.id,
              activityId: record.activityId,
              userId: record.userId,
              status: record.status
            }
          )
        }, 100)
      },
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
            activityUsersEdit(values).then((res) => {
              if (res.success) {
                this.$message.success('编辑成功')
                this.confirmLoading = false
                this.$emit('ok', values)
                this.handleCancel()
              } else {
                this.$message.error('编辑失败')//  + res.message
              }
            }).finally((res) => {
              this.confirmLoading = false
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      }
    }
  }
</script>
