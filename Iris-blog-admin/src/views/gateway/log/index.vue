<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="100px"
    >
      <el-form-item label="IP地址" prop="ip">
        <el-input
            v-model="queryParams.action.ip"
            placeholder="请输入IP地址"
            clearable
            @keyup.enter="handleQuery"
            class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="响应状态码" prop="statusCode">
        <el-input
            v-model="queryParams.action.statusCode"
            placeholder="请输入响应状态码"
            clearable
            @keyup.enter="handleQuery"
            class="!w-150px"
        />
      </el-form-item>
      <el-form-item label="请求方式" prop="requestMethod">
        <el-select
            v-model="queryParams.action.requestMethod"
            class="!w-160px"
            clearable
            placeholder="请选择请求方式"
        >
          <el-option
              v-for="type in getStrDictOptions(DICT_TYPE.SYSTEM_REQUEST_TYPE)"
              :key="type.value"
              :label="type.label"
              :value="type.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="端口号" prop="serverPort">
        <el-input
            v-model="queryParams.action.serverPort"
            placeholder="请输入端口号"
            clearable
            @keyup.enter="handleQuery"
            class="!w-160px"
        />
      </el-form-item>
      <el-form-item label="请求URI" prop="uri">
        <el-input
            v-model="queryParams.action.uri"
            placeholder="请输入请求URI"
            clearable
            @keyup.enter="handleQuery"
            class="!w-180px"
        />
      </el-form-item>
      <el-form-item label="请求完成时间" prop="requestEndTime">
        <el-date-picker
            v-model="createTime"
            value-format="YYYY-MM-DD HH:mm:ss"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
            class="!w-220px"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="handleQuery">
          <Icon icon="ep:search" class="mr-5px"/>
          搜索
        </el-button>
        <el-button type="primary" plain @click="resetQuery">
          <Icon icon="ep:refresh" class="mr-5px"/>
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true">
      <el-table-column type="index" label="编号" width="100" align="center"/>
      <el-table-column label="IP地址" align="center" prop="ip"/>
      <el-table-column label="纬度" align="center" prop="latitude"/>
      <el-table-column label="经度" align="center" prop="longitude"/>
<!--      <el-table-column-->
<!--          label="请求完成时间"-->
<!--          align="center"-->
<!--          prop="requestEndTime"-->
<!--          :formatter="dateFormatter"-->
<!--          width="180px"-->
<!--      />-->
      <el-table-column label="响应状态码" align="center" prop="statusCode" width="100px">
        <template #default="scope">
          <el-tag :type="scope.row.statusCode === 200 ? 'success' : 'warning'">
            {{ scope.row.statusCode }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="请求方式" align="center" prop="requestMethod" width="100px"/>
      <el-table-column label="端口号" align="center" prop="serverPort" width="100px"/>
      <el-table-column label="请求URI" align="center" prop="uri"/>
      <el-table-column label="响应字节大小" align="center" prop="bytesSent">
        <template #default="scope">
          {{ (scope.row.bytesSent / 1024).toFixed(2) + ' KB' }}
        </template>
      </el-table-column>
      <el-table-column label="请求花费时间(秒)" align="center" prop="requestTime"/>
      <el-table-column
          label="创建时间"
          align="center"
          prop="createTime"
          :formatter="dateFormatter"
          width="180px"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" @click="openDetail(scope.row)">
            <Icon icon="ep:view"/>
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
        v-model:limit="queryParams.pageSize"
        v-model:page="queryParams.pageNo"
        :total="total"
        @pagination="getList"
    />
  </ContentWrap>
  <!-- 表单弹窗：详情 -->
  <GatewayLogForm ref="detailRef"/>
</template>
<script setup lang="ts">
import {dateFormatter} from '@/utils/formatTime'
import download from '@/utils/download'
import * as GatewayLogApi from '@/api/gateway/log'
import GatewayLogForm from './GatewayLogDetail.vue'
import {DICT_TYPE, getStrDictOptions} from '@/utils/dict'

const createTime = ref([])
/** 网关日志 列表 */
defineOptions({name: 'GatewayLog'})
const message = useMessage() // 消息弹窗
const {t} = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    ip: undefined,
    statusCode: undefined,
    requestMethod: undefined,
    serverPort: undefined,
    uri: undefined
  }
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    if (createTime.value) {
      queryParams.action.createTimeStart = createTime.value[0]
      queryParams.action.createTimeEnd = createTime.value[1]
    }
    const data = await GatewayLogApi.getGatewayLogPage(queryParams)
    list.value = data.records
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryParams.action = {}
  createTime.value = []
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 详情操作 */
const detailRef = ref()
const openDetail = (data: GatewayLogApi.GatewayLogVO) => {
  detailRef.value.open(data)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await GatewayLogApi.deleteGatewayLog(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {
  }
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await GatewayLogApi.exportGatewayLog(queryParams)
    download.excel(data, '网关日志.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
