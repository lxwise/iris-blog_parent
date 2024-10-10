<template>
  <el-drawer v-model="dialogVisible" :max-height="800" :scroll="true" title="详情" width="1000">
    <el-descriptions :column="1" border>
      <el-descriptions-item label="日志主键" min-width="120">
        {{ detailData.id }}
      </el-descriptions-item>
      <el-descriptions-item label="操作用户id">
        {{ detailData.userId }}
      </el-descriptions-item>
      <el-descriptions-item label="操作用户名">
        {{ detailData.userName }}
      </el-descriptions-item>
      <el-descriptions-item label="请求的接口">
        {{ detailData.requestUrl }}
      </el-descriptions-item>
      <el-descriptions-item label="请求方式">
        {{ detailData.requestType }}
      </el-descriptions-item>
      <el-descriptions-item label="请求类名">
        {{ detailData.requestClassName }}
      </el-descriptions-item>
      <el-descriptions-item label="请求方法">
        {{ detailData.requestMethod }}
      </el-descriptions-item>
      <el-descriptions-item label="请求参数">
        {{ detailData.requestParams }}
      </el-descriptions-item>
      <el-descriptions-item label="请求耗时">
        {{ detailData.requestTime + ' ms' }}
      </el-descriptions-item>
      <el-descriptions-item label="请求ip">
        {{ detailData.requestIp }}
      </el-descriptions-item>
      <el-descriptions-item label="请求地址">
        {{ detailData.requestAddress }}
      </el-descriptions-item>
      <el-descriptions-item label="操作描述">
        {{ detailData.operateDesc }}
      </el-descriptions-item>
      <el-descriptions-item label="操作系统">
        {{ detailData.operateOs }}
      </el-descriptions-item>
      <el-descriptions-item v-if="detailData.logType" label="异常信息的message">
        {{ detailData.errorMessage }}
      </el-descriptions-item>
      <el-descriptions-item v-if="detailData.logType" label="异常信息json格式">
        {{ detailData.errorInfo }}
      </el-descriptions-item>
      <el-descriptions-item label="日志类型">
        {{ detailData.logType ? '异常日志' : '操作日志' }}
      </el-descriptions-item>
      <el-descriptions-item label="操作时间">
        {{ formatDate(detailData.createTime) }}
      </el-descriptions-item>
    </el-descriptions>
  </el-drawer>
</template>
<script lang="ts" setup>
import { formatDate } from '@/utils/formatTime'
import * as OperateLogApi from '@/api/system/operatelog'

defineOptions({ name: 'SystemOperateLogDetail' })

const dialogVisible = ref(false) // 弹窗的是否展示
const detailLoading = ref(false) // 表单的加载中
const detailData = ref({} as OperateLogApi.OperateLogVO) // 详情数据

/** 打开弹窗 */
const open = async (data: OperateLogApi.OperateLogVO) => {
  dialogVisible.value = true
  // 设置数据
  detailLoading.value = true
  try {
    detailData.value = data
  } finally {
    detailLoading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
