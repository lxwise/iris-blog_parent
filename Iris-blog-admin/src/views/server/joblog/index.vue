<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form class="-mb-15px" :model="queryParams" :inline="true" label-width="100px">
      <el-form-item label="任务ID" prop="jobId">
        <el-input
          v-model="queryParams.action.jobId"
          placeholder="请输入任务ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-180px"
        />
      </el-form-item>
      <el-form-item label="任务名称" prop="jobName">
        <el-input
          v-model="queryParams.action.jobName"
          placeholder="请输入任务名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-180px"
        />
      </el-form-item>
      <el-form-item label="任务组名" prop="jobGroup">
        <el-input
          v-model="queryParams.action.jobGroup"
          placeholder="请输入任务组名"
          clearable
          @keyup.enter="handleQuery"
          class="!w-180px"
        />
      </el-form-item>
      <el-form-item label="开始执行时间" prop="startDateTime">
        <el-date-picker
          v-model="queryParams.action.startDateTime"
          type="date"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择开始执行时间"
          clearable
          class="!w-180px"
        />
      </el-form-item>
      <el-form-item label="结束执行时间" prop="stopDateTime">
        <el-date-picker
          v-model="queryParams.action.stopDateTime"
          type="date"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择结束执行时间"
          clearable
          :default-time="new Date('1 23:59:59')"
          class="!w-180px"
        />
      </el-form-item>
      <el-form-item label="任务状态" prop="status">
        <el-select
          v-model="queryParams.action.status"
          placeholder="请选择任务状态"
          clearable
          class="!w-180px"
        >
          <el-option
            v-for="status in getIntDictOptions(DICT_TYPE.SYSTEM_NUMBER_STATUS)"
            :key="status.value"
            :label="status.value === 0 ? '正常' : '失败'"
            :value="status.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="handleQuery">
          <Icon icon="ep:search" />
          搜索
        </el-button>
        <el-button type="primary" plain @click="resetQuery">
          <Icon icon="ep:refresh" />
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="日志编号" align="center" prop="id" />
      <el-table-column label="任务编号" align="center" prop="jobId" />
      <el-table-column label="任务名称" align="center" prop="jobName" />
      <el-table-column label="任务组名" align="center" prop="jobGroup">
        <template #default="scope">
          <el-tag type="primary">{{ scope.row.jobGroup === 'DEFAULT' ? '默认' : '系统' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        :show-overflow-tooltip="true"
        label="执行方法"
        align="center"
        prop="invokeTarget"
      />
      <el-table-column label="执行时间" align="center">
        <template #default="scope">
          <span>{{
            formatDate(scope.row.startTime) + ' ~ ' + formatDate(scope.row.stopTime)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="执行时长" align="center" prop="duration">
        <template #default="scope">
          <el-tag :type="scope.row.duration <= 200 ? 'success' : 'danger'">{{
            scope.row.duration + ' 毫秒'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="任务状态" align="center" prop="status">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'"
            >{{ scope.row.status === 0 ? '正常' : '失败' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            type="primary"
            link
            @click="openDetail(scope.row.id)"
            v-hasPermi="['system:jobtask:query']"
          >
            <Icon icon="ep:view" />
            详细
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：查看 -->
  <JobTaskLogDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import { formatDate } from '@/utils/formatTime'
import JobTaskLogDetail from './JobTaskLogDetail.vue'
import * as JobLogApi from '@/api/system/job'
import { CommonStatusEnum } from '@/utils/constants'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'

defineOptions({ name: 'MonitorJobLog' })
const route = useRoute()
const { query } = useRoute() // 查询参数
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    jobId: query.id,
    jobName: undefined,
    jobGroup: undefined,
    startDateTime: undefined,
    stopDateTime: undefined,
    status: undefined
  }
})
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await JobLogApi.getJobLogPage(queryParams)
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
  queryParams['action'] = {}
  handleQuery()
}

/** 查看操作 */
const detailRef = ref()
const openDetail = (rowId?: number) => {
  detailRef.value.open(rowId)
}
/** 初始化 **/
onMounted(() => {
  // jobId.value = route.query.id;
  // if (jobId.value){
  //   detailRef.value.open(jobId.value)
  // }
  getList()
})
</script>
