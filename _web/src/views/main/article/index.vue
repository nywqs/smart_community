<template>
  <div>
    <a-card :bordered="false" :bodyStyle="tstyle">
      <div class="table-page-search-wrapper" v-if="hasPerm('article:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="标题">
                <a-input v-model="queryParam.title" allow-clear placeholder="请输入标题"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="类型">
                  <a-select style="width: 100%" v-model="queryParam.type" placeholder="请选择类型">
                    <a-select-option v-for="(item,index) in typeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="置顶">
                  <a-select style="width: 100%" v-model="queryParam.top" placeholder="请选择置顶">
                    <a-select-option v-for="(item,index) in topData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="热门">
                  <a-select style="width: 100%" v-model="queryParam.hot" placeholder="请选择热门">
                    <a-select-option v-for="(item,index) in hotData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="发布人姓名">
                  <a-input v-model="queryParam.publicUserName" allow-clear placeholder="请输入发布人姓名"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="发布机构名称">
                  <a-input v-model="queryParam.publicOrgName" allow-clear placeholder="请输入发布机构名称"/>
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
        <template class="table-operator" slot="operator" v-if="hasPerm('article:add')" >
          <a-button type="primary" v-if="hasPerm('article:add')" icon="plus" @click="$refs.addForm.add()">新增文章</a-button>
          <a-button type="danger" :disabled="selectedRowKeys.length < 1" v-if="hasPerm('article:delete')" @click="batchDelete"><a-icon type="delete"/>批量删除</a-button>
          <x-down
            v-if="hasPerm('article:export')"
            ref="batchExport"
            @batchExport="batchExport"
          />
        </template>
        <span slot="typescopedSlots" slot-scope="text">
          {{ 'sys_article_type' | dictType(text) }}
        </span>
        <span slot="topscopedSlots" slot-scope="text">
          {{ 'yes_or_no' | dictType(text) }}
        </span>
        <span slot="hotscopedSlots" slot-scope="text">
          {{ 'yes_or_no' | dictType(text) }}
        </span>
        <span slot="statusscopedSlots" slot-scope="text">
          {{ 'notice_status' | dictType(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('article:edit')" @click="$refs.editForm.edit(record)">编辑</a>
          <a-divider type="vertical" v-if="hasPerm('article:edit') & hasPerm('article:delete')"/>
          <a-popconfirm v-if="hasPerm('article:delete')" placement="topRight" title="确认删除？" @confirm="() => singleDelete(record)">
            <a>删除</a>
          </a-popconfirm>
          <a-divider type="vertical"/>
          <a @click="$refs.detail.show(record)">查看</a>
        </span>
      </s-table>
      <add-form ref="addForm" @ok="handleOk" />
      <edit-form ref="editForm" @ok="handleOk" />
      <detail ref="detail" />
    </a-card>
  </div>
</template>
<script>
  import { STable, XDown } from '@/components'
  import { articlePage, articleDelete, articleExport } from '@/api/modular/main/article/articleManage'
  import addForm from './addForm.vue'
  import editForm from './editForm.vue'
  import Detail from './component/detail'
  export default {
    components: {
      STable,
      addForm,
      editForm,
      XDown,
      Detail
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
            align: 'left',
            dataIndex: 'title'
          },
          {
            title: '类型',
            align: 'center',
            dataIndex: 'type',
            width: 150,
            scopedSlots: { customRender: 'typescopedSlots' }
          },
          {
            title: '置顶',
            align: 'center',
            dataIndex: 'top',
            width: 150,
            scopedSlots: { customRender: 'topscopedSlots' }
          },
          {
            title: '热门',
            align: 'center',
            dataIndex: 'hot',
            width: 150,
            scopedSlots: { customRender: 'hotscopedSlots' }
          },
          {
            title: '发布人姓名',
            align: 'center',
            width: 150,
            dataIndex: 'publicUserName'
          },
          {
            title: '发布机构名称',
            align: 'left',
            width: 260,
            dataIndex: 'publicOrgName'
          },
          {
            title: '发布时间',
            align: 'center',
            width: 260,
            dataIndex: 'publicTime'
          },
          {
            title: '状态',
            align: 'center',
            dataIndex: 'status',
            width: 150,
            scopedSlots: { customRender: 'statusscopedSlots' }
          }
        ],
        tstyle: { 'padding-bottom': '0px', 'margin-bottom': '10px' },
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          return articlePage(Object.assign(parameter, this.queryParam)).then((res) => {
            return res.data
          })
        },
        typeData: [],
        topData: [],
        hotData: [],
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
      if (this.hasPerm('article:edit') || this.hasPerm('article:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
      const typeOption = this.$options
      this.typeData = typeOption.filters['dictData']('sys_article_type')
      const topOption = this.$options
      this.topData = topOption.filters['dictData']('yes_or_no')
      const hotOption = this.$options
      this.hotData = hotOption.filters['dictData']('yes_or_no')
    },
    methods: {
      /**
       * 单个删除
       */
      singleDelete (record) {
        const param = [{ 'id': record.id }]
        this.articleDelete(param)
      },
      /**
       * 批量删除
       */
      batchDelete () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        this.articleDelete(paramIds)
      },
      articleDelete (record) {
        articleDelete(record).then((res) => {
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
        articleExport(paramIds).then((res) => {
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
