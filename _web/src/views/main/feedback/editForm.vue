<template>
  <a-modal
    title="编辑建议反馈"
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
          label="标题"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入标题" v-decorator="['title', {rules: [{required: true, message: '请输入标题！'}]}]" />
        </a-form-item>
        <a-form-item
          label="内容"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择内容" v-decorator="['content', {rules: [{ required: true, message: '请选择内容！' }]}]">
            <a-select-option v-for="(item,index) in contentData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="类型（字典 1建议 2反馈）"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择类型（字典 1建议 2反馈）" v-decorator="['type', {rules: [{ required: true, message: '请选择类型（字典 1建议 2反馈）！' }]}]">
            <a-select-option v-for="(item,index) in typeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="满意度"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入满意度" v-decorator="['star', {rules: [{required: true, message: '请输入满意度！'}]}]" />
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
  import { feedbackEdit } from '@/api/modular/main/feedback/feedbackManage'
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
        typeData: [],
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
        const typeOption = this.$options
        this.typeData = typeOption.filters['dictData']('feedback_type')
        const statusOption = this.$options
        this.statusData = statusOption.filters['dictData']('sys_star')
        setTimeout(() => {
          this.form.setFieldsValue(
            {
              id: record.id,
              title: record.title,
              content: record.content,
              type: record.type,
              star: record.star,
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
            feedbackEdit(values).then((res) => {
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
