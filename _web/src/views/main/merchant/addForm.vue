<template>
  <a-modal
    title="新增商户"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="经营地址"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-textarea placeholder="请输入经营地址" v-decorator="['address', {rules: [{required: true, message: '请输入经营地址！'}]}]" :auto-size="{ minRows: 3, maxRows: 6 }"/>
        </a-form-item>
        <a-form-item
          label="经营类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择经营类型" v-decorator="['bizType', {rules: [{ required: true, message: '请选择经营类型！' }]}]">
            <a-select-option v-for="(item,index) in bizTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="楼宇ID"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择楼宇ID" v-decorator="['buildingId', {rules: [{ required: true, message: '请选择楼宇ID！' }]}]">
            <a-select-option v-for="(item,index) in buildingIdData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="社区ID"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择社区ID" v-decorator="['communityId', {rules: [{ required: true, message: '请选择社区ID！' }]}]">
            <a-select-option v-for="(item,index) in communityIdData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="店招照片"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入店招照片" v-decorator="['image', {rules: [{required: true, message: '请输入店招照片！'}]}]" />
        </a-form-item>
        <a-form-item
          label="开业时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-date-picker style="width: 100%" placeholder="请选择开业时间" v-decorator="['openDate',{rules: [{ required: true, message: '请选择开业时间！' }]}]" @change="onChangeopenDate"/>
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
        <a-form-item
          label="联系电话"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入联系电话" v-decorator="['tel', {rules: [{required: true, message: '请输入联系电话！'}]}]" />
        </a-form-item>
        <a-form-item
          label="商户名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入商户名称" v-decorator="['title', {rules: [{required: true, message: '请输入商户名称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="经营面积"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input-number placeholder="请输入经营面积" style="width: 100%" v-decorator="['useArea', {rules: [{required: true, message: '请输入经营面积！'}]}]" />
        </a-form-item>
        <a-form-item
          label="小区ID"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择小区ID" v-decorator="['villageId', {rules: [{ required: true, message: '请选择小区ID！' }]}]">
            <a-select-option v-for="(item,index) in villageIdData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { merchantAdd } from '@/api/modular/main/merchant/merchantManage'
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
        bizTypeData: [],
        openDateDateString: '',
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
        const bizTypeOption = this.$options
        this.bizTypeData = bizTypeOption.filters['dictData']('merchant_biz_type')
        const statusOption = this.$options
        this.statusData = statusOption.filters['dictData']('common_status')
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
            values.openDate = this.openDateDateString
            merchantAdd(values).then((res) => {
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
      onChangeopenDate(date, dateString) {
        this.openDateDateString = dateString
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      }
    }
  }
</script>
