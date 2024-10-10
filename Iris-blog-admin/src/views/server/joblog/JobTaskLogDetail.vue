<template>
  <el-drawer v-model="dialogVisible" :max-height="800" :scroll="true" title="任务详细" width="800">
    <el-descriptions :column="1" border>
      <el-descriptions-item label="日志编号" min-width="60">
        {{ detailData.id }}
      </el-descriptions-item>
      <el-descriptions-item label="任务编号">
        {{ detailData.jobId }}
      </el-descriptions-item>
      <el-descriptions-item label="任务名称">
        {{ detailData.jobName }}
      </el-descriptions-item>
      <el-descriptions-item label="任务组名">
        {{ detailData.jobGroup === 'DEFAULT' ? '默认' : '系统' }}
      </el-descriptions-item>
      <el-descriptions-item label="执行方法">
        {{ detailData.invokeTarget }}
      </el-descriptions-item>
      <el-descriptions-item label="日志信息">
        {{ detailData.jobMessage }}
      </el-descriptions-item>
      <el-descriptions-item label="执行状态">
        {{ detailData.status === 0 ? '成功' : '失败' }}
      </el-descriptions-item>
      <el-descriptions-item label="异常信息">
        {{ detailData.exceptionInfo }}
      </el-descriptions-item>
      <el-descriptions-item label="执行时间">
        {{ formatDate(detailData.startTime) + ' ~ ' + formatDate(detailData.stopTime) }}
      </el-descriptions-item>
      <el-descriptions-item label="执行时长">
        {{ detailData.duration + ' 毫秒' }}
      </el-descriptions-item>

      <el-descriptions-item label="创建时间">
        {{ formatDate(detailData.createTime) }}
      </el-descriptions-item>

      <el-descriptions-item label="更新时间">
        {{ formatDate(detailData.updateTime) }}
      </el-descriptions-item>
    </el-descriptions>
  </el-drawer>
</template>
<script lang="ts" setup>
import { formatDate } from '@/utils/formatTime'
import * as JobLogApi from '@/api/system/job'

defineOptions({ name: 'MonitorJobLogDetail' })

const dialogVisible = ref(false) // 弹窗的是否展示
const detailLoading = ref(false) // 表单的加载中
const detailData = ref({} as JobLogApi.JobLogVO) // 详情数据

/** 打开弹窗 */
const open = async (id: number) => {
  dialogVisible.value = true
  // 查看，设置数据
  if (id) {
    detailLoading.value = true
    try {
      detailData.value = await JobLogApi.getJobLog(id)
    } finally {
      detailLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
