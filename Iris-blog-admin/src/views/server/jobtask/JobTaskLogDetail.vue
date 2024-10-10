<template>
  <el-drawer v-model="dialogVisible" :max-height="800" :scroll="true" title="详情" width="1000">
    <el-descriptions :column="1" border>
      <el-descriptions-item label="任务ID" min-width="120">
        {{ detailData.id }}
      </el-descriptions-item>
      <el-descriptions-item label="任务名称">
        {{ detailData.jobName }}
      </el-descriptions-item>
      <el-descriptions-item label="任务组名">
        {{ detailData.jobGroup }}
      </el-descriptions-item>
      <el-descriptions-item label="调用方法">
        {{ detailData.invokeTarget }}
      </el-descriptions-item>
      <el-descriptions-item label="cron执行表达式">
        {{ detailData.cronExpression }}
      </el-descriptions-item>
      <el-descriptions-item label="计划执行错误策略">
        {{ JobApi.misfirePolicyTypeToText(detailData.misfirePolicy) }}
      </el-descriptions-item>
      <el-descriptions-item label="状态">
        {{ detailData.status === 1 ? '正常' : '暂停' }}
      </el-descriptions-item>
      <el-descriptions-item label="创建者">
        {{ detailData.createBy }}
      </el-descriptions-item>
      <el-descriptions-item label="创建时间">
        {{ formatDate(detailData.createTime) }}
      </el-descriptions-item>
      <el-descriptions-item label="更新者">
        {{ detailData.updateBy }}
      </el-descriptions-item>
      <el-descriptions-item label="更新时间">
        {{ formatDate(detailData.updateTime) }}
      </el-descriptions-item>
      <el-descriptions-item label="备注信息">
        {{ detailData.remark }}
      </el-descriptions-item>
      <el-descriptions-item label="后续执行时间">
        <el-timeline>
          <el-timeline-item
            v-for="(nextTime, index) in detailData.nextValidTime"
            :key="index"
            :timestamp="formatDate(nextTime)"
          >
            第 {{ index + 1 }} 次
          </el-timeline-item>
        </el-timeline>
      </el-descriptions-item>
    </el-descriptions>
  </el-drawer>
</template>
<script lang="ts" setup>
import { formatDate } from '@/utils/formatTime'
import * as JobApi from '@/api/system/job'

defineOptions({ name: 'MonitorJobTaskLogDetail' })

const dialogVisible = ref(false) // 弹窗的是否展示
const detailLoading = ref(false) // 表单的加载中
const detailData = ref({} as JobApi.JobVO) // 详情数据

// /** 打开弹窗 */
// const open = async (data: JobApi.JobVO) => {
//   dialogVisible.value = true
//   // 设置数据
//   detailLoading.value = true
//   try {
//     detailData.value = data
//   } finally {
//     detailLoading.value = false
//   }
// }
/** 打开弹窗 */
const open = async (id: number) => {
  dialogVisible.value = true
  // 查看，设置数据
  if (id) {
    detailLoading.value = true
    try {
      detailData.value = await JobApi.getJob(id)
    } finally {
      detailLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
