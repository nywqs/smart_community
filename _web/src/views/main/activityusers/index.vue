<template>
  <div>
    <a-card :bordered="false" :bodyStyle="tstyle">
      <div class="table-page-search-wrapper" v-if="hasPerm('activityUsers:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="活动ID">
                <a-input v-model="queryParam.activityId" allow-clear placeholder="请输入活动ID"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="参与用户ID">
                <a-input v-model="queryParam.userId" allow-clear placeholder="请输入参与用户ID"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
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
        <template class="table-operator" slot="operator" v-if="hasPerm('activityUsers:add')" >
          <a-button type="primary" v-if="hasPerm('activityUsers:add')" icon="plus" @click="$refs.addForm.add()">新增报名记录</a-button>
          <a-button type="danger" :disabled="selectedRowKeys.length < 1" v-if="hasPerm('activityUsers:delete')" @click="batchDelete"><a-icon type="delete"/>批量删除</a-button>
          <x-down
            v-if="hasPerm('activityUsers:export')"
            ref="batchExport"
            @batchExport="batchExport"
          />
        </template>
        <span slot="statusscopedSlots" slot-scope="text">
          {{ 'common_status' | dictType(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('activityUsers:edit')" @click="$refs.editForm.edit(record)">编辑</a>
          <a-divider type="vertical" v-if="hasPerm('activityUsers:edit') & hasPerm('activityUsers:delete')"/>
          <a-popconfirm v-if="hasPerm('activityUsers:delete')" placement="topRight" title="确认删除？" @confirm="() => singleDelete(record)">
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
  import { activityUsersPage, activityUsersDelete, activityUsersExport } from '@/api/modular/main/activityusers/activityUsersManage'
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
            title: '活动ID',
            align: 'center',
            dataIndex: 'activityId'
          },
          {
            title: '参与用户ID',
            align: 'center',
            dataIndex: 'userId'
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
          return activityUsersPage(Object.assign(parameter, this.queryParam)).then((res) => {
            return res.data
          })
        },
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
      if (this.hasPerm('activityUsers:edit') || this.hasPerm('activityUsers:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
      const statusOption = this.$options
      this.statusData = statusOption.filters['dictData']('common_status')
    },
    methods: {
      /**
       * 单个删除
       */
      singleDelete (record) {
        const param = [{ 'id': record.id }]
        this.activityUsersDelete(param)
      },
      /**
       * 批量删除
       */
      batchDelete () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        this.activityUsersDelete(paramIds)
      },
      activityUsersDelete (record) {
        activityUsersDelete(record).then((res) => {
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
        activityUsersExport(paramIds).then((res) => {
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
