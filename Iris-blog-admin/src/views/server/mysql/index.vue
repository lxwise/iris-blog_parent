<template>
  <ContentWrap>
    <IFrame :src="url" />
  </ContentWrap>
</template>
<script lang="ts" setup>
defineOptions({ name: 'MonitorMysqlInfo' })
const url = ref(import.meta.env.VITE_BASE_URL + import.meta.env.VITE_API_URL + '/druid/index.html')
</script>
