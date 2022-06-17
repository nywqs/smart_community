<template>
  <div>
    <a-card :bordered="false" :bodyStyle="tstyle">
      <div class="table-page-search-wrapper" v-if="hasPerm('merchant:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="经营地址">
                <a-input v-model="queryParam.address" allow-clear placeholder="请输入经营地址"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="经营类型">
                <a-select style="width: 100%" v-model="queryParam.bizType" placeholder="请选择经营类型">
                  <a-select-option v-for="(item,index) in bizTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="楼宇ID">
                  <a-select style="width: 100%" v-model="queryParam.buildingId" placeholder="请选择楼宇ID">
                    <a-select-option v-for="(item,index) in buildingIdData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="社区ID">
                  <a-select style="width: 100%" v-model="queryParam.communityId" placeholder="请选择社区ID">
                    <a-select-option v-for="(item,index) in communityIdData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="店招照片">
                  <a-input v-model="queryParam.image" allow-clear placeholder="请输入店招照片"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="开业时间">
                  <a-date-picker style="width: 100%" placeholder="请选择开业时间" v-model="queryParam.openDateDate" @change="onChangeopenDate"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="状态">
                  <a-select style="width: 100%" v-model="queryParam.status" placeholder="请选择状态">
                    <a-select-option v-for="(item,index) in statusData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="联系电话">
                  <a-input v-model="queryParam.tel" allow-clear placeholder="请输入联系电话"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="商户名称">
                  <a-input v-model="queryParam.title" allow-clear placeholder="请输入商户名称"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="经营面积">
                  <a-input-number v-model="queryParam.useArea" style="width: 100%" allow-clear placeholder="请输入经营面积"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="小区ID">
                  <a-select style="width: 100%" v-model="queryParam.villageId" placeholder="请选择小区ID">
                    <a-select-option v-for="(item,index) in villageIdData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
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
        <template class="table-operator" slot="operator" v-if="hasPerm('merchant:add')" >
          <a-button type="primary" v-if="hasPerm('merchant:add')" icon="plus" @click="$refs.addForm.add()">新增商户</a-button>
          <a-button type="danger" :disabled="selectedRowKeys.length < 1" v-if="hasPerm('merchant:delete')" @click="batchDelete"><a-icon type="delete"/>批量删除</a-button>
          <x-down
            v-if="hasPerm('merchant:export')"
            ref="batchExport"
            @batchExport="batchExport"
          />
        </template>
        <span slot="bizTypescopedSlots" slot-scope="text">
          {{ 'merchant_biz_type' | dictType(text) }}
        </span>
        <span slot="buildingIdscopedSlots" slot-scope="text">
          {{ '${column.dictTypeCode}' | dictType(text) }}
        </span>
        <span slot="communityIdscopedSlots" slot-scope="text">
          {{ '${column.dictTypeCode}' | dictType(text) }}
        </span>
        <span slot="statusscopedSlots" slot-scope="text">
          {{ 'common_status' | dictType(text) }}
        </span>
        <span slot="villageIdscopedSlots" slot-scope="text">
          {{ '${column.dictTypeCode}' | dictType(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('merchant:edit')" @click="$refs.editForm.edit(record)">编辑</a>
          <a-divider type="vertical" v-if="hasPerm('merchant:edit') & hasPerm('merchant:delete')"/>
          <a-popconfirm v-if="hasPerm('merchant:delete')" placement="topRight" title="确认删除？" @confirm="() => singleDelete(record)">
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
  import moment from 'moment'
  import { merchantPage, merchantDelete, merchantExport } from '@/api/modular/main/merchant/merchantManage'
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
            title: '经营地址',
            align: 'center',
            dataIndex: 'address'
          },
          {
            title: '经营类型',
            align: 'center',
            dataIndex: 'bizType',
            scopedSlots: { customRender: 'bizTypescopedSlots' }
          },
          {
            title: '楼宇ID',
            align: 'center',
            dataIndex: 'buildingId',
            scopedSlots: { customRender: 'buildingIdscopedSlots' }
          },
          {
            title: '社区ID',
            align: 'center',
            dataIndex: 'communityId',
            scopedSlots: { customRender: 'communityIdscopedSlots' }
          },
          {
            title: '店招照片',
            align: 'center',
            dataIndex: 'image'
          },
          {
            title: '开业时间',
            align: 'center',
            dataIndex: 'openDate'
          },
          {
            title: '状态',
            align: 'center',
            dataIndex: 'status',
            scopedSlots: { customRender: 'statusscopedSlots' }
          },
          {
            title: '联系电话',
            align: 'center',
            dataIndex: 'tel'
          },
          {
            title: '商户名称',
            align: 'center',
            dataIndex: 'title'
          },
          {
            title: '经营面积',
            align: 'center',
            dataIndex: 'useArea'
          },
          {
            title: '小区ID',
            align: 'center',
            dataIndex: 'villageId',
            scopedSlots: { customRender: 'villageIdscopedSlots' }
          }
        ],
        tstyle: { 'padding-bottom': '0px', 'margin-bottom': '10px' },
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          return merchantPage(Object.assign(parameter, this.switchingDate())).then((res) => {
            return res.data
          })
        },
        bizTypeData: [],
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
      if (this.hasPerm('merchant:edit') || this.hasPerm('merchant:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
      const bizTypeOption = this.$options
      this.bizTypeData = bizTypeOption.filters['dictData']('merchant_biz_type')
      const statusOption = this.$options
      this.statusData = statusOption.filters['dictData']('common_status')
    },
    methods: {
      moment,
      /**
       * 查询参数组装
       */
      switchingDate () {
        const queryParamopenDate = this.queryParam.openDateDate
        if (queryParamopenDate != null) {
            this.queryParam.openDate = moment(queryParamopenDate).format('YYYY-MM-DD')
            if (queryParamopenDate.length < 1) {
                delete this.queryParam.openDate
            }
        }
        const obj = JSON.parse(JSON.stringify(this.queryParam))
        return obj
      },
      /**
       * 单个删除
       */
      singleDelete (record) {
        const param = [{ 'id': record.id }]
        this.merchantDelete(param)
      },
      /**
       * 批量删除
       */
      batchDelete () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        this.merchantDelete(paramIds)
      },
      merchantDelete (record) {
        merchantDelete(record).then((res) => {
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
      onChangeopenDate(date, dateString) {
        this.openDateDateString = dateString
      },
      /**
       * 批量导出
       */
      batchExport () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        merchantExport(paramIds).then((res) => {
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
