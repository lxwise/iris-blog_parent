<template>
  <div class="monitoring-panel">
    <div class="component">
      <h3>服务状态</h3>
      <div v-if="status ==='UP'" class="container">
        <div class="status-circle status-1">
          <div class="circle rotary-circle"></div>
          <div class="circle content-circle">
            <div class="circle detail-circle">健康..</div>
          </div>
        </div>
      </div>
      <div v-else class="container">
        <div class="status-circle status-2">
          <div class="circle rotary-circle"></div>
          <div class="circle content-circle">
            <div class="circle detail-circle">异常..</div>
          </div>
        </div>
      </div>
    </div>
    <div class="component" v-for="(component, key) in components" :key="key">
      <h3>{{ key }}</h3>
      <p><el-tag :type="component.status === 'UP' ? 'success' : 'danger'">{{component.status}}</el-tag></p>
      <div v-if="component.details">
        <div v-for="(detail, detailKey) in component.details" :key="detailKey">
          <strong>{{ detailKey }}:</strong> <span class="ml-4">{{ detail }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed } from 'vue';
import { defineProps } from 'vue';

interface MonitoringData {
  status: string;
  components: {
    [key: string]: {
      status: string;
      details?: {
        [key: string]: any;
      };
    };
  };
}

const props = defineProps<{ data: MonitoringData }>();

const status = computed(() => props.data.status);
const components = computed(() => props.data.components);
</script>

<style>
.monitoring-panel {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
  text-align: center;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100px;
  height: 100px;
  margin: 20px auto;
}

@keyframes rotary {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.status-circle {
  position: relative;
  height: 100%;
  width: 100%;
}

.circle {
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 100%;
}

.rotary-circle {
  height: 100%;
  width: 100%;
  box-sizing: border-box;
  animation: rotary 2s infinite linear;
}

.content-circle {
  position: absolute;
  top: 1%;
  left: 1%;
  width: 98%;
  height: 98%;
  background: #fff;
}

.detail-circle {
  width: 90%;
  height: 90%;
  color: #fff;
}

.status-1 .detail-circle {
  background: rgb(68, 218, 111);
}

.status-1 .rotary-circle {
  background: linear-gradient(to right, rgba(68, 218, 111, 1), rgba(68, 218, 111, 0) 50%, rgba(68, 218, 111, 1));
}

.status-2 .detail-circle {
  background: rgb(255, 59, 47);
}

.status-2 .rotary-circle {
  background: linear-gradient(to right, rgba(255, 59, 47, 1), rgba(255, 59, 47, 0) 50%, rgba(255, 59, 47, 1));
}

.component {
  margin-top: 20px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #fff;
}

h3 {
  margin: 0;
}

p {
  margin: 5px 0;
}

strong {
  display: inline-block;
  width: 100px;
}
</style>
