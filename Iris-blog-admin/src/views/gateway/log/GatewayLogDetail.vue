<template>
  <el-drawer v-model="dialogVisible" :max-height="700" :scroll="true" title="详情" width="800">
    <el-descriptions :column="1" border>
      <el-descriptions-item label="日志主键" min-width="120">
        {{ detailData.id }}
      </el-descriptions-item>
      <el-descriptions-item label="请求ip">
        {{ detailData.ip }}
      </el-descriptions-item>
      <el-descriptions-item label="纬度">
        {{ detailData.latitude }}
      </el-descriptions-item>
      <el-descriptions-item label="经度">
        {{ detailData.longitude }}
      </el-descriptions-item>
      <el-descriptions-item label="请求完成时间">
        {{ formatDate(detailData.requestEndTime) }}
      </el-descriptions-item>
      <el-descriptions-item label="响应状态码">
        {{ detailData.statusCode }}
      </el-descriptions-item>
      <el-descriptions-item label="请求URI">
        {{ detailData.uri }}
      </el-descriptions-item>
      <el-descriptions-item label="请求方式">
        {{ detailData.requestMethod }}
      </el-descriptions-item>
      <el-descriptions-item label="协议及版本">
        {{ detailData.serverProtocol }}
      </el-descriptions-item>
      <el-descriptions-item label="端口号">
        {{ detailData.serverPort }}
      </el-descriptions-item>
      <el-descriptions-item label="请求参数">
        {{ detailData.args }}
      </el-descriptions-item>
      <el-descriptions-item label="请求耗时">
        {{ detailData.requestTime + ' 秒' }}
      </el-descriptions-item>
      <el-descriptions-item label="响应字节大小">
        {{ (detailData.bytesSent / 1024).toFixed(2) + ' KB' }}
      </el-descriptions-item>
      <el-descriptions-item label="客户端信息">
        {{ detailData.httpUserAgent }}
      </el-descriptions-item>
      <el-descriptions-item label="创建时间">
        {{ formatDate(detailData.createTime) }}
      </el-descriptions-item>
    </el-descriptions>
  </el-drawer>
</template>
<script lang="ts" setup>
import {formatDate} from '@/utils/formatTime'
import * as GatewayLogApi from '@/api/gateway/log'

defineOptions({name: 'SystemGatewayLogDetail'})

const dialogVisible = ref(false) // 弹窗的是否展示
const detailLoading = ref(false) // 表单的加载中
const detailData = ref({} as GatewayLogApi.GatewayLogVO) // 详情数据

/** 打开弹窗 */
const open = async (data: GatewayLogApi.GatewayLogVO) => {
  dialogVisible.value = true
  // 设置数据
  detailLoading.value = true
  try {
    detailData.value = data
  } finally {
    detailLoading.value = false
  }
}
defineExpose({open}) // 提供 open 方法，用于打开弹窗
</script>
