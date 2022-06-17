<template>
  <div>
    <a-card :bordered="false" :bodyStyle="tstyle">
      <div class="table-page-search-wrapper" v-if="hasPerm('house:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="楼宇ID">
                <a-select style="width: 100%" v-model="queryParam.buildingId" placeholder="请选择楼宇ID">
                  <a-select-option v-for="(item,index) in buildingIdData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="门牌号">
                <a-input v-model="queryParam.code" allow-clear placeholder="请输入门牌号"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="社区ID">
                  <a-select style="width: 100%" v-model="queryParam.communityId" placeholder="请选择社区ID">
                    <a-select-option v-for="(item,index) in communityIdData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="楼层">
                  <a-select style="width: 100%" v-model="queryParam.floor" placeholder="请选择楼层">
                    <a-select-option v-for="(item,index) in floorData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="户型(例如一房一厅)">
                  <a-select style="width: 100%" v-model="queryParam.houseType" placeholder="请选择户型(例如一房一厅)">
                    <a-select-option v-for="(item,index) in houseTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="是否出租">
                  <a-select style="width: 100%" v-model="queryParam.leaseStatus" placeholder="请选择是否出租">
                    <a-select-option v-for="(item,index) in leaseStatusData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
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
                <a-form-item label="名称">
                  <a-input v-model="queryParam.title" allow-clear placeholder="请输入名称"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="房屋性质(0:居家，1:办公)">
                  <a-select style="width: 100%" v-model="queryParam.useType" placeholder="请选择房屋性质(0:居家，1:办公)">
                    <a-select-option v-for="(item,index) in useTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
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
        <template class="table-operator" slot="operator" v-if="hasPerm('house:add')" >
          <a-button type="primary" v-if="hasPerm('house:add')" icon="plus" @click="$refs.addForm.add()">新增房屋</a-button>
          <a-button type="danger" :disabled="selectedRowKeys.length < 1" v-if="hasPerm('house:delete')" @click="batchDelete"><a-icon type="delete"/>批量删除</a-button>
          <x-down
            v-if="hasPerm('house:export')"
            ref="batchExport"
            @batchExport="batchExport"
          />
        </template>
        <span slot="buildingIdscopedSlots" slot-scope="text">
          {{ '${column.dictTypeCode}' | dictType(text) }}
        </span>
        <span slot="communityIdscopedSlots" slot-scope="text">
          {{ '${column.dictTypeCode}' | dictType(text) }}
        </span>
        <span slot="floorscopedSlots" slot-scope="text">
          {{ '${column.dictTypeCode}' | dictType(text) }}
        </span>
        <span slot="houseTypescopedSlots" slot-scope="text">
          {{ 'house_type' | dictType(text) }}
        </span>
        <span slot="leaseStatusscopedSlots" slot-scope="text">
          {{ 'yes_or_no' | dictType(text) }}
        </span>
        <span slot="statusscopedSlots" slot-scope="text">
          {{ 'common_status' | dictType(text) }}
        </span>
        <span slot="useTypescopedSlots" slot-scope="text">
          {{ 'house_use_type' | dictType(text) }}
        </span>
        <span slot="villageIdscopedSlots" slot-scope="text">
          {{ '${column.dictTypeCode}' | dictType(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('house:edit')" @click="$refs.editForm.edit(record)">编辑</a>
          <a-divider type="vertical" v-if="hasPerm('house:edit') & hasPerm('house:delete')"/>
          <a-popconfirm v-if="hasPerm('house:delete')" placement="topRight" title="确认删除？" @confirm="() => singleDelete(record)">
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
  import { housePage, houseDelete, houseExport } from '@/api/modular/main/house/houseManage'
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
            title: '楼宇ID',
            align: 'center',
            dataIndex: 'buildingId',
            scopedSlots: { customRender: 'buildingIdscopedSlots' }
          },
          {
            title: '门牌号',
            align: 'center',
            dataIndex: 'code'
          },
          {
            title: '社区ID',
            align: 'center',
            dataIndex: 'communityId',
            scopedSlots: { customRender: 'communityIdscopedSlots' }
          },
          {
            title: '楼层',
            align: 'center',
            dataIndex: 'floor',
            scopedSlots: { customRender: 'floorscopedSlots' }
          },
          {
            title: '户型(例如一房一厅)',
            align: 'center',
            dataIndex: 'houseType',
            scopedSlots: { customRender: 'houseTypescopedSlots' }
          },
          {
            title: '是否出租',
            align: 'center',
            dataIndex: 'leaseStatus',
            scopedSlots: { customRender: 'leaseStatusscopedSlots' }
          },
          {
            title: '状态',
            align: 'center',
            dataIndex: 'status',
            scopedSlots: { customRender: 'statusscopedSlots' }
          },
          {
            title: '名称',
            align: 'center',
            dataIndex: 'title'
          },
          {
            title: '房屋性质(0:居家，1:办公)',
            align: 'center',
            dataIndex: 'useType',
            scopedSlots: { customRender: 'useTypescopedSlots' }
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
          return housePage(Object.assign(parameter, this.queryParam)).then((res) => {
            return res.data
          })
        },
        houseTypeData: [],
        leaseStatusData: [],
        statusData: [],
        useTypeData: [],
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
      if (this.hasPerm('house:edit') || this.hasPerm('house:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
      const houseTypeOption = this.$options
      this.houseTypeData = houseTypeOption.filters['dictData']('house_type')
      const leaseStatusOption = this.$options
      this.leaseStatusData = leaseStatusOption.filters['dictData']('yes_or_no')
      const statusOption = this.$options
      this.statusData = statusOption.filters['dictData']('common_status')
      const useTypeOption = this.$options
      this.useTypeData = useTypeOption.filters['dictData']('house_use_type')
    },
    methods: {
      /**
       * 单个删除
       */
      singleDelete (record) {
        const param = [{ 'id': record.id }]
        this.houseDelete(param)
      },
      /**
       * 批量删除
       */
      batchDelete () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        this.houseDelete(paramIds)
      },
      houseDelete (record) {
        houseDelete(record).then((res) => {
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
        houseExport(paramIds).then((res) => {
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
