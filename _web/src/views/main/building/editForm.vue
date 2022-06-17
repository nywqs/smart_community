<template>
  <a-modal
    title="编辑楼宇"
    :width="900"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="小区地址"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-textarea placeholder="请输入小区地址" v-decorator="['address', {rules: [{required: true, message: '请输入小区地址！'}]}]" :auto-size="{ minRows: 3, maxRows: 6 }"/>
        </a-form-item>
        <a-form-item
          label="建筑时间"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-date-picker style="width: 100%" placeholder="请选择建筑时间" v-decorator="['buildDate',{rules: [{ required: true, message: '请选择建筑时间！' }]}]" @change="onChangebuildDate"/>
        </a-form-item>
        <a-form-item
          label="楼宇编号"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入楼宇编号" v-decorator="['code', {rules: [{required: true, message: '请输入楼宇编号！'}]}]" />
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
          label="总层数"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input-number placeholder="请输入总层数" style="width: 100%" v-decorator="['floorCount', {rules: [{required: true, message: '请输入总层数！'}]}]" />
        </a-form-item>
        <a-form-item
          label="户型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择户型" v-decorator="['floorHouse', {rules: [{ required: true, message: '请选择户型！' }]}]">
            <a-select-option v-for="(item,index) in floorHouseData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item v-show="false"><a-input v-decorator="['id']" /></a-form-item>
        <a-form-item
          label="梯类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择梯类型" v-decorator="['ladderType', {rules: [{ required: true, message: '请选择梯类型！' }]}]">
            <a-select-option v-for="(item,index) in ladderTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
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
          label="建筑类型（0:小高层,1:高层）"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择建筑类型（0:小高层,1:高层）" v-decorator="['type', {rules: [{ required: true, message: '请选择建筑类型（0:小高层,1:高层）！' }]}]">
            <a-select-option v-for="(item,index) in typeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="建筑性质(0:住宅，1：商用房,3:商住两用)"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择建筑性质(0:住宅，1：商用房,3:商住两用)" v-decorator="['useType', {rules: [{ required: true, message: '请选择建筑性质(0:住宅，1：商用房,3:商住两用)！' }]}]">
            <a-select-option v-for="(item,index) in useTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="小区ID"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入小区ID" v-decorator="['villageId', {rules: [{required: true, message: '请输入小区ID！'}]}]" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import moment from 'moment'
  import { buildingEdit } from '@/api/modular/main/building/buildingManage'
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
        buildDateDateString: '',
        floorHouseData: [],
        ladderTypeData: [],
        statusData: [],
        typeData: [],
        useTypeData: [],
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this)
      }
    },
    methods: {
      moment,
      // 初始化方法
      edit (record) {
        this.visible = true
        const floorHouseOption = this.$options
        this.floorHouseData = floorHouseOption.filters['dictData']('building_floor_house')
        const ladderTypeOption = this.$options
        this.ladderTypeData = ladderTypeOption.filters['dictData']('building_ladder_type')
        const statusOption = this.$options
        this.statusData = statusOption.filters['dictData']('common_status')
        const typeOption = this.$options
        this.typeData = typeOption.filters['dictData']('building_type')
        const useTypeOption = this.$options
        this.useTypeData = useTypeOption.filters['dictData']('building_use_type')
        setTimeout(() => {
          this.form.setFieldsValue(
            {
              address: record.address,
              code: record.code,
              communityId: record.communityId,
              floorCount: record.floorCount,
              floorHouse: record.floorHouse,
              id: record.id,
              ladderType: record.ladderType,
              status: record.status,
              title: record.title,
              type: record.type,
              useType: record.useType,
              villageId: record.villageId
            }
          )
        }, 100)
        // 时间单独处理
        if (record.buildDate != null) {
            this.form.getFieldDecorator('buildDate', { initialValue: moment(record.buildDate, 'YYYY-MM-DD') })
        }
        this.buildDateDateString = moment(record.buildDate).format('YYYY-MM-DD')
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
            values.buildDate = this.buildDateDateString
            buildingEdit(values).then((res) => {
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
      onChangebuildDate(date, dateString) {
        this.buildDateDateString = dateString
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      }
    }
  }
</script>
