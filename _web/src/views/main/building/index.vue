<template>
  <div>
    <a-card :bordered="false" :bodyStyle="tstyle">
      <div class="table-page-search-wrapper" v-if="hasPerm('building:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="小区地址">
                <a-input v-model="queryParam.address" allow-clear placeholder="请输入小区地址"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="建筑时间">
                <a-date-picker style="width: 100%" placeholder="请选择建筑时间" v-model="queryParam.buildDateDate" @change="onChangebuildDate"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="楼宇编号">
                  <a-input v-model="queryParam.code" allow-clear placeholder="请输入楼宇编号"/>
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
                <a-form-item label="总层数">
                  <a-input-number v-model="queryParam.floorCount" style="width: 100%" allow-clear placeholder="请输入总层数"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="户型">
                  <a-select style="width: 100%" v-model="queryParam.floorHouse" placeholder="请选择户型">
                    <a-select-option v-for="(item,index) in floorHouseData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="梯类型">
                  <a-select style="width: 100%" v-model="queryParam.ladderType" placeholder="请选择梯类型">
                    <a-select-option v-for="(item,index) in ladderTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
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
                <a-form-item label="建筑类型（0:小高层,1:高层）">
                  <a-select style="width: 100%" v-model="queryParam.type" placeholder="请选择建筑类型（0:小高层,1:高层）">
                    <a-select-option v-for="(item,index) in typeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="建筑性质(0:住宅，1：商用房,3:商住两用)">
                  <a-select style="width: 100%" v-model="queryParam.useType" placeholder="请选择建筑性质(0:住宅，1：商用房,3:商住两用)">
                    <a-select-option v-for="(item,index) in useTypeData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="小区ID">
                  <a-input v-model="queryParam.villageId" allow-clear placeholder="请输入小区ID"/>
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
        <template class="table-operator" slot="operator" v-if="hasPerm('building:add')" >
          <a-button type="primary" v-if="hasPerm('building:add')" icon="plus" @click="$refs.addForm.add()">新增楼宇</a-button>
          <a-button type="danger" :disabled="selectedRowKeys.length < 1" v-if="hasPerm('building:delete')" @click="batchDelete"><a-icon type="delete"/>批量删除</a-button>
          <x-down
            v-if="hasPerm('building:export')"
            ref="batchExport"
            @batchExport="batchExport"
          />
        </template>
        <span slot="communityIdscopedSlots" slot-scope="text">
          {{ '${column.dictTypeCode}' | dictType(text) }}
        </span>
        <span slot="floorHousescopedSlots" slot-scope="text">
          {{ 'building_floor_house' | dictType(text) }}
        </span>
        <span slot="ladderTypescopedSlots" slot-scope="text">
          {{ 'building_ladder_type' | dictType(text) }}
        </span>
        <span slot="statusscopedSlots" slot-scope="text">
          {{ 'common_status' | dictType(text) }}
        </span>
        <span slot="typescopedSlots" slot-scope="text">
          {{ 'building_type' | dictType(text) }}
        </span>
        <span slot="useTypescopedSlots" slot-scope="text">
          {{ 'building_use_type' | dictType(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('building:edit')" @click="$refs.editForm.edit(record)">编辑</a>
          <a-divider type="vertical" v-if="hasPerm('building:edit') & hasPerm('building:delete')"/>
          <a-popconfirm v-if="hasPerm('building:delete')" placement="topRight" title="确认删除？" @confirm="() => singleDelete(record)">
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
  import { buildingPage, buildingDelete, buildingExport } from '@/api/modular/main/building/buildingManage'
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
            title: '小区地址',
            align: 'center',
            dataIndex: 'address'
          },
          {
            title: '建筑时间',
            align: 'center',
            dataIndex: 'buildDate'
          },
          {
            title: '楼宇编号',
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
            title: '总层数',
            align: 'center',
            dataIndex: 'floorCount'
          },
          {
            title: '户型',
            align: 'center',
            dataIndex: 'floorHouse',
            scopedSlots: { customRender: 'floorHousescopedSlots' }
          },
          {
            title: '梯类型',
            align: 'center',
            dataIndex: 'ladderType',
            scopedSlots: { customRender: 'ladderTypescopedSlots' }
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
            title: '建筑类型（0:小高层,1:高层）',
            align: 'center',
            dataIndex: 'type',
            scopedSlots: { customRender: 'typescopedSlots' }
          },
          {
            title: '建筑性质(0:住宅，1：商用房,3:商住两用)',
            align: 'center',
            dataIndex: 'useType',
            scopedSlots: { customRender: 'useTypescopedSlots' }
          },
          {
            title: '小区ID',
            align: 'center',
            dataIndex: 'villageId'
          }
        ],
        tstyle: { 'padding-bottom': '0px', 'margin-bottom': '10px' },
        // 加载数据方法 必须为 Promise 对象
        loadData: parameter => {
          return buildingPage(Object.assign(parameter, this.switchingDate())).then((res) => {
            return res.data
          })
        },
        floorHouseData: [],
        ladderTypeData: [],
        statusData: [],
        typeData: [],
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
      if (this.hasPerm('building:edit') || this.hasPerm('building:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
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
    },
    methods: {
      moment,
      /**
       * 查询参数组装
       */
      switchingDate () {
        const queryParambuildDate = this.queryParam.buildDateDate
        if (queryParambuildDate != null) {
            this.queryParam.buildDate = moment(queryParambuildDate).format('YYYY-MM-DD')
            if (queryParambuildDate.length < 1) {
                delete this.queryParam.buildDate
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
        this.buildingDelete(param)
      },
      /**
       * 批量删除
       */
      batchDelete () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        this.buildingDelete(paramIds)
      },
      buildingDelete (record) {
        buildingDelete(record).then((res) => {
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
      onChangebuildDate(date, dateString) {
        this.buildDateDateString = dateString
      },
      /**
       * 批量导出
       */
      batchExport () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        buildingExport(paramIds).then((res) => {
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
