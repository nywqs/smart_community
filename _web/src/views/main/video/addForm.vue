<template>
  <a-modal
    title="新增视频列表"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="简要描述"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-textarea placeholder="请输入简要描述" v-decorator="['content', {rules: [{required: true, message: '请输入简要描述！'}]}]" :auto-size="{ minRows: 3, maxRows: 6 }"/>
        </a-form-item>
        <a-form-item
          label="满意度"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择满意度" v-decorator="['playTimes', {rules: [{ required: true, message: '请选择满意度！' }]}]">
            <a-select-option v-for="(item,index) in playTimesData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="状态"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-radio-group placeholder="请选择状态" v-decorator="['status',{rules: [{ required: true, message: '请选择状态！' }]}]" >
            <a-radio v-for="(item,index) in statusData" :key="index" :value="item.code">{{ item.name }}</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item
          label="标题"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入标题" v-decorator="['title', {rules: [{required: true, message: '请输入标题！'}]}]" />
        </a-form-item>
        <a-form-item
          label="类型（字典 1建议 2反馈）"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入类型（字典 1建议 2反馈）" v-decorator="['type', {rules: [{required: true, message: '请输入类型（字典 1建议 2反馈）！'}]}]" />
        </a-form-item>
        <a-form-item
          label="视频地址"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入视频地址" v-decorator="['url', {rules: [{required: true, message: '请输入视频地址！'}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { videoAdd } from '@/api/modular/main/video/videoManage'
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
        playTimesData: [],
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
        const playTimesOption = this.$options
        this.playTimesData = playTimesOption.filters['dictData']('sys_star')
        const statusOption = this.$options
        this.statusData = statusOption.filters['dictData']('show_or_hidden')
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
            videoAdd(values).then((res) => {
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
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      }
    }
  }
</script>
