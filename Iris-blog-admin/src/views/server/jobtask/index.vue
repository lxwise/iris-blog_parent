<template>
  <el-row :gutter="24">
    <el-col :span="24" :xs="24">
      <!-- 搜索 -->
      <ContentWrap>
        <el-form
          class="-mb-15px"
          :model="queryParams"
          ref="queryFormRef"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="任务名称" prop="jobName">
            <el-input
              v-model="queryParams.action.jobName"
              placeholder="请输入任务名称"
              clearable
              @keyup.enter="handleQuery"
              class="!w-240px"
            />
          </el-form-item>
          <el-form-item label="任务组名" prop="jobGroup">
            <el-input
              v-model="queryParams.action.jobGroup"
              placeholder="请输入任务组名"
              clearable
              @keyup.enter="handleQuery"
              class="!w-240px"
            />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.action.status"
              placeholder="任务状态"
              clearable
              class="!w-240px"
            >
              <el-option
                v-for="status in getIntDictOptions(DICT_TYPE.SYSTEM_NUMBER_STATUS)"
                :key="status.value"
                :label="status.label"
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
            <el-button
              type="primary"
              @click="openForm('create')"
              v-hasPermi="['system:jobtask:save']"
            >
              <Icon icon="ep:plus" />
              新增
            </el-button>
          </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="list">
          <el-table-column type="index" label="编号" width="100" align="center" />
          <el-table-column
            label="任务名称"
            align="center"
            prop="jobName"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="任务组名" align="center" prop="jobGroup" />
          <el-table-column
            label="调用目标字符串"
            align="center"
            prop="invokeTarget"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="cron执行表达式"
            align="center"
            prop="cronExpression"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="状态" key="status" width="200">
            <template #default="scope">
              <el-switch
                v-model="scope.row.status"
                :active-value="1"
                :inactive-value="0"
                @change="handleStatusChange(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            :formatter="dateFormatter"
          />
          <el-table-column label="操作" align="center" width="300">
            <template #default="scope">
              <div class="flex items-center justify-center">
                <el-button
                  type="primary"
                  link
                  @click="openForm('update', scope.row.id)"
                  v-hasPermi="['system:jobtask:update']"
                >
                  <Icon icon="ep:edit" />
                  修改
                </el-button>
                <el-button
                  v-hasPermi="['system:jobtask:delete']"
                  link
                  type="danger"
                  @click="handleDelete(scope.row.id)"
                >
                  <Icon icon="ep:delete" />
                  删除
                </el-button>
                <el-dropdown
                  @command="(command) => handleCommand(command, scope.row)"
                  v-hasPermi="['system:jobtask:query', 'system:jobtask:run']"
                >
                  <el-button type="primary" link>
                    <Icon icon="ep:more-filled" />
                    更多
                  </el-button>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item
                        command="handleDetail"
                        v-if="checkPermi(['system:jobtask:query'])"
                      >
                        <Icon icon="ep:delete" />
                        任务详情
                      </el-dropdown-item>
                      <el-dropdown-item
                        command="handleRun"
                        v-if="checkPermi(['system:jobtask:run'])"
                      >
                        <Icon icon="ep:d-arrow-right" />
                        执行一次
                      </el-dropdown-item>
                      <el-dropdown-item
                        command="handleLog"
                        v-if="checkPermi(['system:jobtask:query'])"
                      >
                        <Icon icon="ep:circle-check" />
                        调度日志
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </ContentWrap>
    </el-col>
  </el-row>

  <!-- 添加或修改对话框 -->
  <JobTaskForm ref="formRef" @success="getList" />
  <!-- 表单弹窗：详情 -->
  <JobTaskLogDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import { checkPermi } from '@/utils/permission'
import { dateFormatter } from '@/utils/formatTime'
import { CommonStatusEnum } from '@/utils/constants'
import * as JobApi from '@/api/system/job'
import JobTaskLogDetail from './JobTaskLogDetail.vue'
import JobTaskForm from './JobTaskForm.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'

defineOptions({ name: 'MonitorJobTask' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const { push } = useRouter()
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    jobName: undefined,
    jobGroup: undefined,
    status: undefined
  }
})
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await JobApi.getJobPage(queryParams)
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

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 修改任务状态 */
const handleStatusChange = async (row: JobApi.JobVO) => {
  try {
    // 修改状态的二次确认
    const text = row.status === CommonStatusEnum.ENABLE ? '启用' : '停用'
    await message.confirm('确认要"' + text + '""' + row.jobName + '"任务吗?')
    // 发起修改状态
    await JobApi.updateJobStatus(row.id)
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status =
      row.status === CommonStatusEnum.ENABLE ? CommonStatusEnum.DISABLE : CommonStatusEnum.ENABLE
  }
}

/** 操作分发 */
const handleCommand = (command: string, row: JobApi.JobVO) => {
  switch (command) {
    case 'handleDetail':
      handleDetail(row)
      break
    case 'handleRun':
      handleRun(row)
      break
    case 'handleLog':
      handleLog(row)
      break
    default:
      break
  }
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await JobApi.deleteJob(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 详情操作 */
const detailRef = ref()
const handleDetail = (data: JobApi.JobVO) => {
  detailRef.value.open(data.id)
}
/** 执行一次 */
const handleRun = async (row: JobApi.JobVO) => {
  try {
    //执行的二次确认
    await message.confirm('确认要立即执行一次"' + row.jobName + '"任务吗？', t('common.reminder'))
    // 发起执行
    await JobApi.runJob(row.id)
    message.success('任务执行成功')
  } catch {}
}

const handleLog = (row: JobApi.JobVO) => {
  /** 跳转执行日志 */
  if (row.id && row.id > 0) {
    push({ name: 'MonitorJobLog', query: { id: row.id } })
  } else {
    push('/job/job-log')
  }
  // if (row.id && row.id > 0) {
  //   push('/job/job-log?id=' + row.id)
  // } else {
  //   push('/job/job-log')
  // }
}

/** 初始化 */
onMounted(() => {
  getList()
})
</script>
