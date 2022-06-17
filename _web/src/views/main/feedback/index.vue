<template>
  <div>
    <a-card :bordered="false" :bodyStyle="tstyle">
      <div class="table-page-search-wrapper" v-if="hasPerm('feedback:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="标题">
                <a-input v-model="queryParam.title" allow-clear placeholder="请输入标题"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="内容">
                <a-select style="width: 100%" v-model="queryParam.content" placeholder="请选择内容">
                  <a-select-option v-for="(item,index) in contentData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="类型（字典 1建议 2反馈）">
                  <a-select style="width: 100%" v-model="queryParam.type" placeholder="请选择类型（字典 1建议 2反馈）">
                    <a-select-option v-for="(item,index) in typeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="满意度">
                  <a-input v-model="queryParam.star" allow-clear placeholder="请输入满意度"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="状态">
                  <a-select style="width: 100%" v-model="queryParam.status" placeholder="请选择状态">
                    <a-select-option v-for="(item,index) in statusData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </template>
            <a-col :md="8" :sm="24" >
              <span class="table-page-search-submitButtons">
                <a-button type="primary" @click="$refs.table.refresh(true)" >查询</a-button>
                <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
                <a @click="toggleAdvanced" style="margin-left: 8px">
                  {{ advanced ? '收起' : '展开' }}
                  <a-icon :type="advanced ? 'up' : 'down'"/>
                </a>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
    </a-card>
    <a-card :bordered="false">
      <s-table
        ref="table"
        :columns="columns"
        :data="loadData"
        :alert="options.alert"
        :rowKey="(record) => record.id"
        :rowSelection="options.rowSelection"
      >
        <template class="table-operator" slot="operator" v-if="hasPerm('feedback:add')" >
          <a-button type="primary" v-if="hasPerm('feedback:add')" icon="plus" @click="$refs.addForm.add()">新增建议反馈</a-button>
          <a-button type="danger" :disabled="selectedRowKeys.length < 1" v-if="hasPerm('feedback:delete')" @click="batchDelete"><a-icon type="delete"/>批量删除</a-button>
          <x-down
            v-if="hasPerm('feedback:export')"
            ref="batchExport"
            @batchExport="batchExport"
          />
        </template>
        <span slot="contentscopedSlots" slot-scope="text">
          {{ '${column.dictTypeCode}' | dictType(text) }}
        </span>
        <span slot="typescopedSlots" slot-scope="text">
          {{ 'feedback_type' | dictType(text) }}
        </span>
        <span slot="statusscopedSlots" slot-scope="text">
          {{ 'sys_star' | dictType(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('feedback:edit')" @click="$refs.editForm.edit(record)">编辑</a>
          <a-divider type="vertical" v-if="hasPerm('feedback:edit') & hasPerm('feedback:delete')"/>
          <a-popconfirm v-if="hasPerm('feedback:delete')" placement="topRight" title="确认删除？" @confirm="() => singleDelete(record)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </s-table>
      <add-form ref="addForm" @ok="handleOk" />
      <edit-form ref="editForm" @ok="handleOk" />
    </a-card>
  </div>
</template>
<script>
  import { STable, XDown } from '@/components'
  import { feedbackPage, feedbackDelete, feedbackExport } from '@/api/modular/main/feedback/feedbackManage'
  import addForm from './addForm.vue'
  import editForm from './editForm.vue'
  export default {
    components: {
      STable,
      addForm,
      editForm,
      XDown
    },
    data () {
      return {
        // 高级搜索 展开/关闭
        advanced: false,
        // 查询参数
        queryParam: {},
        // 表头
        columns: [
          {
            title: '标题',
            align: 'center',
            dataIndex: 'title'
          },
          {
            title: '内容',
            align: 'center',
            dataIndex: 'content',
            scopedSlots: { customRender: 'contentscopedSlots' }
          },
          {
            title: '类型（字典 1建议 2反馈）',
            align: 'center',
            dataIndex: 'type',
            scopedSlots: { customRender: 'typescopedSlots' }
          },
          {
            title: '满意度',
            align: 'center',
            dataIndex: 'star'
          },
          {
            title: '状态',
            align: 'center',
            dataIndex: 'status',
            scopedSlots: { customRender: 'statusscopedSlots' }
          }
        ],
        tstyle: { 'padding-bottom': '0px', 'margin-bottom': '10px' },
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          return feedbackPage(Object.assign(parameter, this.queryParam)).then((res) => {
            return res.data
          })
        },
        typeData: [],
        statusData: [],
        selectedRowKeys: [],
        selectedRows: [],
        options: {
          alert: { show: true, clear: () => { this.selectedRowKeys = [] } },
          rowSelection: {
            selectedRowKeys: this.selectedRowKeys,
            onChange: this.onSelectChange
          }
        }
      }
    },
    created () {
      if (this.hasPerm('feedback:edit') || this.hasPerm('feedback:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
      const typeOption = this.$options
      this.typeData = typeOption.filters['dictData']('feedback_type')
      const statusOption = this.$options
      this.statusData = statusOption.filters['dictData']('sys_star')
    },
    methods: {
      /**
       * 单个删除
       */
      singleDelete (record) {
        const param = [{ 'id': record.id }]
        this.feedbackDelete(param)
      },
      /**
       * 批量删除
       */
      batchDelete () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        this.feedbackDelete(paramIds)
      },
      feedbackDelete (record) {
        feedbackDelete(record).then((res) => {
          if (res.success) {
            this.$message.success('删除成功')
            this.$refs.table.clearRefreshSelected()
          } else {
            this.$message.error('删除失败') // + res.message
          }
        })
      },
      toggleAdvanced () {
        this.advanced = !this.advanced
      },
      /**
       * 批量导出
       */
      batchExport () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        feedbackExport(paramIds).then((res) => {
            this.$refs.batchExport.downloadfile(res)
        })
      },
      handleOk () {
        this.$refs.table.refresh()
      },
      onSelectChange (selectedRowKeys, selectedRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectedRows = selectedRows
      }
    }
  }
</script>
<style lang="less">
  .table-operator {
    margin-bottom: 18px;
  }
  button {
    margin-right: 8px;
  }
</style>
