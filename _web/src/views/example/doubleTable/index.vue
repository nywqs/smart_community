<template>
  <div>
    <a-row :gutter="48">
      <a-col :md="12" :sm="24" style="padding-right: 0px;">
        <x-card>
          <div slot="content" class="table-page-search-wrapper">
            <a-form layout="inline">
              <a-row :gutter="48">
                <a-col :md="12" :sm="24">
                  <a-form-item label="列表1名称" >
                    <a-input v-model="queryParam1.name" allow-clear placeholder="请输入名称"/>
                  </a-form-item>
                </a-col>
                <a-col :md="12" :sm="24">
                  <a-button type="primary" @click="$refs.table1.refresh(true)">查询</a-button>
                  <a-button style="margin-left: 8px" @click="() => queryParam1 = {}">重置</a-button>
                </a-col>
              </a-row>
            </a-form>
          </div>
        </x-card>
      </a-col>
      <a-col :md="12" :sm="24">
        <x-card>
          <div slot="content" class="table-page-search-wrapper">
            <a-form layout="inline">
              <a-row :gutter="48">
                <a-col :md="12" :sm="24">
                  <a-form-item label="列表2名称" >
                    <a-input v-model="queryParam2.name" allow-clear placeholder="请输入名称"/>
                  </a-form-item>
                </a-col>
                <a-col :md="12" :sm="24">
                  <a-button type="primary" @click="$refs.table2.refresh(true)">查询</a-button>
                  <a-button style="margin-left: 8px" @click="() => queryParam2 = {}">重置</a-button>
                </a-col>
              </a-row>
            </a-form>
          </div>
        </x-card>
      </a-col>
    </a-row>

    <a-card>
      <!-- 第一个表格 -->
      <s-table
        ref="table1"
        :columns="columns1"
        :data="loadData1"
        :alert="true"
        :rowKey="(record) => record.id"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
      >
        <template slot="operator">
          <a-button icon="plus" type="primary">新增</a-button>
        </template>
        <span slot="action">
          <a>编辑</a>
        </span>
      </s-table>
    </a-card>
    <a-card style="margin-top: 10px">
      <!-- 第二个表格 -->
      <s-table
        ref="table2"
        :columns="columns2"
        :data="loadData2"
        :alert="true"
        :rowKey="(record) => record.id"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
      >
        <template slot="operator">
          <a-button icon="plus" type="primary">新增</a-button>
        </template>
        <span slot="action">
          <a>编辑</a>
          <a-divider type="vertical"/>
          <a-popconfirm placement="topRight" title="确认删除？">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </s-table>
    </a-card>

  </div>
</template>

<script>
  import { STable, XCard } from '@/components'

  export default {
    components: {
      XCard,
      STable
    },

    data () {
      const TableOneResule1 = { 'pageNo': 1, 'pageSize': 10, 'totalPage': 1, 'totalRows': 3, 'rows': [{ 'id': '1', 'name1': '测试1', 'name2': '测试2', 'remark': '备注' }, { 'id': '2', 'name1': '测试1', 'name2': '测试2', 'remark': '备注' }, { 'id': '3', 'name1': '测试1', 'name2': '测试2', 'remark': '备注' }] }
      const TableOneResule2 = { 'pageNo': 1, 'pageSize': 10, 'totalPage': 1, 'totalRows': 2, 'rows': [{ 'id': '1', 'name1': '2222', 'name2': '3333', 'remark': '备注' }, { 'id': '2', 'name1': 'erere', 'name2': '测试2', 'remark': '备注' }] }

      return {
        // 查询参数
        queryParam1: {},
        queryParam2: {},
        // 表头
        columns1: [
          {
            title: '名称1',
            dataIndex: 'name1'
          },
          {
            title: '名称2',
            dataIndex: 'name2'
          },
          {
            title: '备注',
            dataIndex: 'remark'
          }
        ],
        // 表头
        columns2: [
          {
            title: '名称1',
            dataIndex: 'name1'
          },
          {
            title: '名称2',
            dataIndex: 'name2'
          },
          {
            title: '备注',
            dataIndex: 'remark'
          }
        ],
        // 加载数据方法 必须为 Promise 对象
        loadData1: parameter => {
          return new Promise((resolve, reject) => {
            resolve(TableOneResule1)
          })
        },
        // 加载数据方法 必须为 Promise 对象
        loadData2: parameter => {
          return new Promise((resolve, reject) => {
            resolve(TableOneResule2)
          })
        },
        selectedRowKeys: [],
        selectedRows: []
    }
    },

    created () {
      this.columns1.push({
        title: '操作',
        width: '150px',
        dataIndex: 'action',
        scopedSlots: { customRender: 'action' }
      })
      this.columns2.push({
        title: '操作列',
        width: '150px',
        dataIndex: 'action',
        scopedSlots: { customRender: 'action' }
      })
    },

    methods: {
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
