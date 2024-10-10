<template>
  <!--  <doc-alert title="系统日志" url="https://doc.iocoder.cn/system-log/" />-->

  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="操作人" prop="userName">
        <el-input
          v-model="queryParams.action.userName"
          placeholder="请输入操作人"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="请求接口" prop="requestUrl">
        <el-input
          v-model="queryParams.action.requestUrl"
          placeholder="请输入请求接口"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="操作描述" prop="operateDesc">
        <el-input
          v-model="queryParams.action.operateDesc"
          placeholder="请输入操作描述"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="请求方式" prop="requestType">
        <el-select
          v-model="queryParams.action.requestType"
          class="!w-240px"
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
      <el-form-item label="请求方法" prop="requestMethod">
        <el-input
          v-model="queryParams.action.requestMethod"
          placeholder="请输入请求方法"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="操作时间" prop="createTime">
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
        <el-button type="primary" plain @click="handleQuery"
          ><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button
        >
        <el-button type="primary" plain @click="resetQuery"
          ><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button
        >
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column type="index" label="编号" width="100" align="center" />
      <el-table-column label="操作人" align="center" prop="userName" width="100" />
      <el-table-column
        :show-overflow-tooltip="true"
        label="请求类名"
        align="center"
        prop="requestClassName"
      />
      <el-table-column label="请求的接口" align="center" prop="requestUrl" />
      <el-table-column label="请求方式" align="center" prop="requestType" />
      <el-table-column label="操作描述" align="center" prop="operateDesc" />
      <el-table-column label="请求方法" align="center" prop="requestMethod" />
      <el-table-column label="请求耗时" align="center" sortable prop="requestTime" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.requestTime <= 200 ? 'success' : 'danger'">{{
            scope.row.requestTime + ' ms'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="请求ip" align="center" prop="requestIp" />
      <el-table-column label="请求地址" align="center" prop="requestAddress" />
      <el-table-column
        label="操作时间"
        align="center"
        prop="createTime"
        width="180"
        :formatter="dateFormatter"
      />
      <el-table-column label="操作" align="center" fixed="right" width="60">
        <template #default="scope">
          <el-button link type="primary" @click="openDetail(scope.row)">
            <Icon icon="ep:view" />
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：详情 -->
  <OperateLogDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import * as OperateLogApi from '@/api/system/operatelog'
import OperateLogDetail from './OperateLogDetail.vue'
import { DICT_TYPE, getStrDictOptions } from '@/utils/dict'

defineOptions({ name: 'SystemOperateLog' })
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
let createTime = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    userName: null,
    requestUrl: null,
    requestType: null,
    requestMethod: null,
    logType: undefined,
    createTimeStart: null,
    createTimeEnd: null,
    operateDesc: null,
    logType: false
  }
})
const queryFormRef = ref() // 搜索的表单
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    if (createTime.value) {
      queryParams.action.createTimeStart = createTime.value[0]
      queryParams.action.createTimeEnd = createTime.value[1]
    }
    const data = await OperateLogApi.getOperateLogPage(queryParams)
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
  // queryFormRef.value.resetFields()
  handleQuery()
}

/** 详情操作 */
const detailRef = ref()
const openDetail = (data: OperateLogApi.OperateLogVO) => {
  detailRef.value.open(data)
}

/** 初始化 **/
onMounted(async () => {
  await getList()
})
</script>
