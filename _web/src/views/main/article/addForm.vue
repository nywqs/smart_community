<template>
  <a-modal
    title="新增文章"
    :width="1200"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          label="标题"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <a-input placeholder="请输入标题" v-decorator="['title', {rules: [{required: true, message: '请输入标题！'}]}]" />
        </a-form-item>

        <a-form-item
          label="类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select style="width: 100%" placeholder="请选择类型" v-decorator="['type', {rules: [{ required: true, message: '请选择类型！' }]}]">
            <a-select-option v-for="(item,index) in typeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item
          label="置顶"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-radio-group placeholder="请选择置顶" v-decorator="['top',{rules: [{ required: true, message: '请选择置顶！' }]}]" >
            <a-radio v-for="(item,index) in topData" :key="index" :value="item.code">{{ item.name }}</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item
          label="热门"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-radio-group placeholder="请选择热门" v-decorator="['hot',{rules: [{ required: true, message: '请选择热门！' }]}]" >
            <a-radio v-for="(item,index) in hotData" :key="index" :value="item.code">{{ item.name }}</a-radio>
          </a-radio-group>
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
          label="内容"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          has-feedback
        >
          <antd-editor :uploadConfig="editorUploadConfig" v-model="editorContent" @onchange="changeEditor" @oninit="getEditor" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import { articleAdd } from '@/api/modular/main/article/articleManage'
  import { sysFileInfoUpload } from '@/api/modular/system/fileManage'
  import { AntdEditor } from '@/components'
  export default {
    components: {
      AntdEditor
    },
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 3 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 20 }
        },
        typeData: [],
        topData: [],
        hotData: [],
        publicTimeDateString: '',
        cancelTimeDateString: '',
        statusData: [],
        visible: false,
        confirmLoading: false,
        form: this.$form.createForm(this),
        editorContent: '',
        editorContentText: '',
        editorUploadConfig: {
          method: 'http',
          callback: this.editorUploadImage
        }
      }
    },
    methods: {
      // 初始化方法
      add (record) {
        this.visible = true
        const typeOption = this.$options
        this.typeData = typeOption.filters['dictData']('sys_article_type')
        const topOption = this.$options
        this.topData = topOption.filters['dictData']('yes_or_no')
        const hotOption = this.$options
        this.hotData = hotOption.filters['dictData']('yes_or_no')
        const statusOption = this.$options
        this.statusData = statusOption.filters['dictData']('notice_status')
      },
      /**
       * 提交表单
       */
      handleSubmit () {
        const { form: { validateFields } } = this
        if (this.editorContent === '') {
          this.$message.error('请填写内容')
          return
        }
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            for (const key in values) {
              if (typeof (values[key]) === 'object') {
                values[key] = JSON.stringify(values[key])
              }
            }
            values.content = this.editorContent
            values.publicTime = this.publicTimeDateString
            values.cancelTime = this.cancelTimeDateString
            articleAdd(values).then((res) => {
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
      onChangepublicTime(date, dateString) {
        this.publicTimeDateString = dateString
      },
      onChangecancelTime(date, dateString) {
        this.cancelTimeDateString = dateString
      },
      handleCancel () {
        this.form.resetFields()
        this.visible = false
      },
      /**
       * 编辑器回调上传及回传图片url
       */
      editorUploadImage (files, insert) {
        const formData = new FormData()
        files.forEach(file => {
          formData.append('file', file)
        })
        sysFileInfoUpload(formData).then((res) => {
          if (res.success) {
            insert(process.env.VUE_APP_API_BASE_URL + '/sysFileInfo/preview?id=' + res.data)
          } else {
            this.$message.error('编辑器上传图片失败：' + res.message)
          }
        }).catch((err) => {
          this.$message.error('预览错误：' + err.message)
        })
      },
      getEditor (editor) {
        this.editor = editor
      },
      changeEditor (html, ele) {
        this.editorContent = html
        this.editorContentText = ele.text()
      }
    }
  }
</script>
