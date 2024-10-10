<template>
  <!--  <Error @error-click="push('/')" />-->
  <div id="app" v-hasPermi="['system:monitor:serviceInfo']">
    <MonitoringPanel :data="monitoringData"/>
  </div>
</template>
<script lang="ts" setup>
import {useRouter} from 'vue-router';
import MonitoringPanel from './MonitoringPanel.vue';
import * as ServiceApi from '@/api/system/monitor/index'
// 设置组件名称
defineOptions({name: 'MonitorServiceInfo'});

// 使用路由钩子
const {push} = useRouter();
// 基本信息
const serviceInfo = async () => {
  const data = await ServiceApi.getServiceInfo()
  monitoringData.value = data
}
// 定义监控数据
const monitoringData = ref({
  status: "",
  components: {
    db: {
      status: "DOWN",
      details: {
        database: "",
        validationQuery: ""
      }
    },
    diskSpace: {
      status: "DOWN",
      details: {
        total: 0,
        free: 0,
        threshold: 0,
        exists: true
      }
    },
    ping: {
      status: "DOWN"
    },
    redis: {
      status: "DOWN",
      details: {
        version: ""
      }
    }
  }
});


/** 初始化 **/
onMounted(() => {
  serviceInfo()
})
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
