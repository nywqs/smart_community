<template>
  <div>
    <a-card :bordered="false" :bodyStyle="tstyle">
      <div class="table-page-search-wrapper" v-if="hasPerm('activity:page')">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="活动主题">
                <a-input v-model="queryParam.title" allow-clear placeholder="请输入活动主题"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="活动介绍">
                <a-input v-model="queryParam.content" allow-clear placeholder="请输入活动介绍"/>
              </a-form-item>
            </a-col>
            <template v-if="advanced">
              <a-col :md="8" :sm="24">
                <a-form-item label="活动封面">
                  <a-input v-model="queryParam.faceImage" allow-clear placeholder="请输入活动封面"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="报名开始时间">
                  <a-date-picker style="width: 100%" placeholder="请选择报名开始时间" v-model="queryParam.preStarttimeDate" @change="onChangepreStarttime"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="报名截至时间">
                  <a-date-picker style="width: 100%" placeholder="请选择报名截至时间" v-model="queryParam.preEndtimeDate" @change="onChangepreEndtime"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="活动开始时间">
                  <a-date-picker style="width: 100%" placeholder="请选择活动开始时间" v-model="queryParam.starttimeDate" @change="onChangestarttime"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="活动结束时间">
                  <a-date-picker style="width: 100%" placeholder="请选择活动结束时间" v-model="queryParam.endtimeDate" @change="onChangeendtime"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="是否收费">
                  <a-select style="width: 100%" v-model="queryParam.chargeStatus" placeholder="请选择是否收费">
                    <a-select-option v-for="(item,index) in chargeStatusData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="报名费">
                  <a-input v-model="queryParam.price" allow-clear placeholder="请输入报名费"/>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="是否捐助">
                  <a-select style="width: 100%" v-model="queryParam.welfareStatus" placeholder="请选择是否捐助">
                    <a-select-option v-for="(item,index) in welfareStatusData" :key="index" :value="item.code">{{ item.name }}</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
              <a-col :md="8" :sm="24">
                <a-form-item label="捐助金额">
                  <a-input v-model="queryParam.welfareMoney" allow-clear placeholder="请输入捐助金额"/>
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
        <template class="table-operator" slot="operator" v-if="hasPerm('activity:add')" >
          <a-button type="primary" v-if="hasPerm('activity:add')" icon="plus" @click="$refs.addForm.add()">新增活动</a-button>
          <a-button type="danger" :disabled="selectedRowKeys.length < 1" v-if="hasPerm('activity:delete')" @click="batchDelete"><a-icon type="delete"/>批量删除</a-button>
          <x-down
            v-if="hasPerm('activity:export')"
            ref="batchExport"
            @batchExport="batchExport"
          />
        </template>
        <span slot="chargeStatusscopedSlots" slot-scope="text">
          {{ 'yes_or_no' | dictType(text) }}
        </span>
        <span slot="welfareStatusscopedSlots" slot-scope="text">
          {{ 'yes_or_no' | dictType(text) }}
        </span>
        <span slot="statusscopedSlots" slot-scope="text">
          {{ 'sys_activiti_status' | dictType(text) }}
        </span>
        <span slot="action" slot-scope="text, record">
          <a v-if="hasPerm('activity:edit')" @click="$refs.editForm.edit(record)">编辑</a>
          <a-divider type="vertical" v-if="hasPerm('activity:edit') & hasPerm('activity:delete')"/>
          <a-popconfirm v-if="hasPerm('activity:delete')" placement="topRight" title="确认删除？" @confirm="() => singleDelete(record)">
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
  import { activityPage, activityDelete, activityExport } from '@/api/modular/main/activity/activityManage'
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
            title: '活动主题',
            align: 'center',
            dataIndex: 'title'
          },
          {
            title: '活动介绍',
            align: 'center',
            dataIndex: 'content'
          },
          {
            title: '活动封面',
            align: 'center',
            dataIndex: 'faceImage'
          },
          {
            title: '报名开始时间',
            align: 'center',
            dataIndex: 'preStarttime'
          },
          {
            title: '报名截至时间',
            align: 'center',
            dataIndex: 'preEndtime'
          },
          {
            title: '活动开始时间',
            align: 'center',
            dataIndex: 'starttime'
          },
          {
            title: '活动结束时间',
            align: 'center',
            dataIndex: 'endtime'
          },
          {
            title: '是否收费',
            align: 'center',
            dataIndex: 'chargeStatus',
            scopedSlots: { customRender: 'chargeStatusscopedSlots' }
          },
          {
            title: '报名费',
            align: 'center',
            dataIndex: 'price'
          },
          {
            title: '是否捐助',
            align: 'center',
            dataIndex: 'welfareStatus',
            scopedSlots: { customRender: 'welfareStatusscopedSlots' }
          },
          {
            title: '捐助金额',
            align: 'center',
            dataIndex: 'welfareMoney'
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
          return activityPage(Object.assign(parameter, this.switchingDate())).then((res) => {
            return res.data
          })
        },
        chargeStatusData: [],
        welfareStatusData: [],
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
      if (this.hasPerm('activity:edit') || this.hasPerm('activity:delete')) {
        this.columns.push({
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        })
      }
      const chargeStatusOption = this.$options
      this.chargeStatusData = chargeStatusOption.filters['dictData']('yes_or_no')
      const welfareStatusOption = this.$options
      this.welfareStatusData = welfareStatusOption.filters['dictData']('yes_or_no')
      const statusOption = this.$options
      this.statusData = statusOption.filters['dictData']('sys_activiti_status')
    },
    methods: {
      moment,
      /**
       * 查询参数组装
       */
      switchingDate () {
        const queryParampreStarttime = this.queryParam.preStarttimeDate
        if (queryParampreStarttime != null) {
            this.queryParam.preStarttime = moment(queryParampreStarttime).format('YYYY-MM-DD')
            if (queryParampreStarttime.length < 1) {
                delete this.queryParam.preStarttime
            }
        }
        const queryParampreEndtime = this.queryParam.preEndtimeDate
        if (queryParampreEndtime != null) {
            this.queryParam.preEndtime = moment(queryParampreEndtime).format('YYYY-MM-DD')
            if (queryParampreEndtime.length < 1) {
                delete this.queryParam.preEndtime
            }
        }
        const queryParamstarttime = this.queryParam.starttimeDate
        if (queryParamstarttime != null) {
            this.queryParam.starttime = moment(queryParamstarttime).format('YYYY-MM-DD')
            if (queryParamstarttime.length < 1) {
                delete this.queryParam.starttime
            }
        }
        const queryParamendtime = this.queryParam.endtimeDate
        if (queryParamendtime != null) {
            this.queryParam.endtime = moment(queryParamendtime).format('YYYY-MM-DD')
            if (queryParamendtime.length < 1) {
                delete this.queryParam.endtime
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
        this.activityDelete(param)
      },
      /**
       * 批量删除
       */
      batchDelete () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        this.activityDelete(paramIds)
      },
      activityDelete (record) {
        activityDelete(record).then((res) => {
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
      onChangepreStarttime(date, dateString) {
        this.preStarttimeDateString = dateString
      },
      onChangepreEndtime(date, dateString) {
        this.preEndtimeDateString = dateString
      },
      onChangestarttime(date, dateString) {
        this.starttimeDateString = dateString
      },
      onChangeendtime(date, dateString) {
        this.endtimeDateString = dateString
      },
      /**
       * 批量导出
       */
      batchExport () {
        const paramIds = this.selectedRowKeys.map((d) => {
            return { 'id': d }
        })
        activityExport(paramIds).then((res) => {
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
