<template>
  <a-modal
    title="编辑房屋"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
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
          label="门牌号"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入门牌号" v-decorator="['code', {rules: [{required: true, message: '请输入门牌号！'}]}]" />
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
          label="楼层"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择楼层" v-decorator="['floor', {rules: [{ required: true, message: '请选择楼层！' }]}]">
            <a-select-option v-for="(item,index) in floorData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="户型(例如一房一厅)"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择户型(例如一房一厅)" v-decorator="['houseType', {rules: [{ required: true, message: '请选择户型(例如一房一厅)！' }]}]">
            <a-select-option v-for="(item,index) in houseTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item v-show="false"><a-input v-decorator="['id']" /></a-form-item>
        <a-form-item
          label="是否出租"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择是否出租" v-decorator="['leaseStatus', {rules: [{ required: true, message: '请选择是否出租！' }]}]">
            <a-select-option v-for="(item,index) in leaseStatusData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
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
          label="名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入名称" v-decorator="['title', {rules: [{required: true, message: '请输入名称！'}]}]" />
        </a-form-item>
        <a-form-item
          label="房屋性质(0:居家，1:办公)"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择房屋性质(0:居家，1:办公)" v-decorator="['useType', {rules: [{ required: true, message: '请选择房屋性质(0:居家，1:办公)！' }]}]">
            <a-select-option v-for="(item,index) in useTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
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
  import { houseEdit } from '@/api/modular/main/house/houseManage'
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
        houseTypeData: [],
        leaseStatusData: [],
        statusData: [],
        useTypeData: [],
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this)
      }
    },
    methods: {
      // 初始化方法
      edit (record) {
        this.visible = true
        const houseTypeOption = this.$options
        this.houseTypeData = houseTypeOption.filters['dictData']('house_type')
        const leaseStatusOption = this.$options
        this.leaseStatusData = leaseStatusOption.filters['dictData']('yes_or_no')
        const statusOption = this.$options
        this.statusData = statusOption.filters['dictData']('common_status')
        const useTypeOption = this.$options
        this.useTypeData = useTypeOption.filters['dictData']('house_use_type')
        setTimeout(() => {
          this.form.setFieldsValue(
            {
              buildingId: record.buildingId,
              code: record.code,
              communityId: record.communityId,
              floor: record.floor,
              houseType: record.houseType,
              id: record.id,
              leaseStatus: record.leaseStatus,
              status: record.status,
              title: record.title,
              useType: record.useType,
              villageId: record.villageId
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
            houseEdit(values).then((res) => {
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
