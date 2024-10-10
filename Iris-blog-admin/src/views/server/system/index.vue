<template>
  <div v-hasPermi="['system:monitor:systemInfo']" class="app-container">
    <el-row v-loading="loading">
      <el-col :span="12" class="card-box">
        <el-card style="margin-right: 10px">
          <template #header>
            <span>CPU</span>
          </template>
          <el-table :data="cpuData" style="width: 100%">
            <el-table-column prop="attribute" label="属性" />
            <el-table-column prop="value" label="值" />
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <template #header><span>内存</span></template>
          <el-table :data="memoryData" style="width: 100%">
            <el-table-column prop="attribute" label="属性" />
            <el-table-column prop="memory" label="内存" />
            <el-table-column prop="jvm" label="JVM" />
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box">
        <el-card style="margin-top: 10px">
          <template #header>
            <span>服务器信息</span>
          </template>
          <el-table :data="serverInfoData" style="width: 100%">
            <el-table-column prop="attribute" label="属性" />
            <el-table-column prop="value" label="值" />
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box">
        <el-card style="margin-top: 10px">
          <template #header>
            <span>Java虚拟机信息</span>
          </template>
          <el-table :data="jvmData" style="width: 100%">
            <el-table-column prop="attribute" label="属性" />
            <el-table-column prop="value" label="值" />
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box">
        <el-card style="margin-top: 10px">
          <template #header>
            <span>磁盘状态</span>
          </template>
          <el-table :data="server.sysFiles" style="width: 100%">
            <el-table-column prop="dirName" label="盘符路径" />
            <el-table-column prop="sysTypeName" label="文件系统" />
            <el-table-column prop="typeName" label="盘符类型" />
            <el-table-column prop="total" label="总大小" />
            <el-table-column prop="free" label="可用大小" />
            <el-table-column prop="used" label="已用大小" />
            <el-table-column prop="usage" label="已用百分比" :formatter="formatUsage" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import * as MonitorApi from '@/api/system/monitor/index'
import { ref, onMounted } from 'vue'

defineOptions({
  name: 'MonitorSystemInfo',
  inheritAttrs: false
})

const loading = ref(false)
const server = ref({})

const cpuData = ref([])
const memoryData = ref([])
const serverInfoData = ref([])
const jvmData = ref([])

/** 查询服务器信息 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MonitorApi.getSystemServerInfo()
    server.value = data

    // 设置 CPU 数据
    cpuData.value = [
      { attribute: '核心数', value: server.value.cpu.cpuNum },
      { attribute: '用户使用率', value: `${server.value.cpu.used}%` },
      { attribute: '系统使用率', value: `${server.value.cpu.sys}%` },
      { attribute: '当前空闲率', value: `${server.value.cpu.free}%` }
    ]

    // 设置内存数据
    memoryData.value = [
      {
        attribute: '总内存',
        memory: `${server.value.mem.total}G`,
        jvm: `${server.value.jvm.total}M`
      },
      {
        attribute: '已用内存',
        memory: `${server.value.mem.used}G`,
        jvm: `${server.value.jvm.used}M`
      },
      {
        attribute: '剩余内存',
        memory: `${server.value.mem.free}G`,
        jvm: `${server.value.jvm.free}M`
      },
      {
        attribute: '使用率',
        memory: `${server.value.mem.usage}%`,
        jvm: `${server.value.jvm.usage}%`
      }
    ]

    // 设置服务器信息数据
    serverInfoData.value = [
      { attribute: '服务器名称', value: server.value.sys.computerName },
      { attribute: '操作系统', value: server.value.sys.osName },
      { attribute: '服务器IP', value: server.value.sys.computerIp },
      { attribute: '系统架构', value: server.value.sys.osArch }
    ]

    // 设置 JVM 数据
    jvmData.value = [
      { attribute: 'Java名称', value: server.value.jvm.name },
      { attribute: 'Java版本', value: server.value.jvm.version },
      { attribute: '启动时间', value: server.value.jvm.startTime },
      { attribute: '运行时长', value: server.value.jvm.runTime },
      { attribute: '安装路径', value: server.value.jvm.home },
      { attribute: '项目路径', value: server.value.sys.userDir }
    ]
  } finally {
    loading.value = false
  }
}

const formatUsage = (row, column, cellValue) => {
  return `${cellValue}%`
}

onMounted(() => {
  getList()
})
</script>
