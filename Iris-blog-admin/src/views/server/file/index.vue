<template>
  <!-- 搜索 -->
  <ContentWrap>
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item width="100">
        <el-button type="success" @click="openFileForm('update')"
          ><Icon icon="ep:setting" class="mr-5px" /> 云存储配置</el-button
        >
      </el-form-item>
      <el-form-item label="文件路径" prop="url">
        <el-input
          v-model="queryParams.action.url"
          placeholder="请输入文件路径"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件名" prop="name">
        <el-input
          v-model="queryParams.action.name"
          placeholder="请输入文件名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件类型" prop="type" width="80">
        <el-input
          v-model="queryParams.action.type"
          placeholder="请输入文件类型"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="handleQuery"
          ><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button
        >
        <el-button type="primary" plain @click="resetQuery"
          ><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button
        >
        <el-button type="success" plain @click="openForm" v-hasPermi="['server:file:upload']">
          <Icon icon="ep:upload" class="mr-5px" /> 上传文件
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column type="index" label="编号" width="100" align="center" />
      <el-table-column label="文件内容" align="center" prop="url" width="110px">
        <template #default="{ row }">
          <el-image
            v-if="checkImageType(row)"
            class="h-80px w-80px"
            lazy
            :src="row.url"
            :preview-src-list="[row.url]"
            preview-teleported
            fit="cover"
          />
          <el-link
            v-else-if="row.type.includes('pdf')"
            type="primary"
            :href="row.url"
            :underline="false"
            target="_blank"
            >预览</el-link
          >
          <el-link v-else type="primary" download :href="row.url" :underline="false" target="_blank"
            >下载</el-link
          >
        </template>
      </el-table-column>
      <el-table-column label="文件名" align="center" prop="name" :show-overflow-tooltip="true" />
      <el-table-column
        label="文件大小"
        align="center"
        prop="size"
        width="120"
        :formatter="fileSizeFormatter"
      />
      <el-table-column label="文件类型" align="center" prop="type" width="180px">
        <template #default="scope">
          <span v-if="scope.row.type === 1">七牛云</span>
          <span v-if="scope.row.type === 2">阿里云</span>
          <span v-if="scope.row.type === 3">腾讯云</span>
          <span v-if="scope.row.type === 4">本地云</span>
        </template>
      </el-table-column>
      <el-table-column label="URL" align="center" prop="url" :show-overflow-tooltip="true" />
      <el-table-column label="文件路径" align="center" prop="filePath" :show-overflow-tooltip="true" />
      <el-table-column
        label="上传时间"
        align="center"
        prop="createTime"
        width="180"
        :formatter="dateFormatter"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['server:file:delete']"
          >
            <Icon icon="ep:delete" />
            删除
          </el-button>
          <el-button
              link
              type="primary"
              @click="handleDownload(scope.row.url)">
            <Icon icon="ep:download" />
            下载
          </el-button>
          <el-button
              link
              type="warning"
              @click="handlePreview(scope.row.url)"
          >
            <Icon icon="ep:view" />
            预览
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <FileForm ref="formRef" @success="getList" />
  <FileConfigForm ref="openFileFormRef" @success="getList" />
</template>
<script lang="ts" setup>
import { fileSizeFormatter } from '@/utils'
import { dateFormatter } from '@/utils/formatTime'
import * as FileApi from '@/api/system/file'
import FileForm from './FileForm.vue'
import FileConfigForm from './FileConfigForm.vue'

defineOptions({ name: 'ServerFileConfig' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
let createTime = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    name: undefined,
    type: undefined,
    url: undefined,
    createTimeStart: null,
    createTimeEnd: null
  }
})
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    if (createTime.value) {
      queryParams.action.createTimeStart = createTime.value[0]
      queryParams.action.createTimeEnd = createTime.value[1]
    }
    const data = await FileApi.getFilePage(queryParams)
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

const checkImageType = (row: any) => {
  return ['jpg', 'png', 'gif', 'jpeg'].some((ext) => row.type.endsWith(ext))
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryParams.action = {}
  createTime.value = []
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = () => {
  formRef.value.open()
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await FileApi.deleteFile(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
/**
 * 下载
 * @param id
 */
const handleDownload = async (url: string) => {
  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const blob = await response.blob();
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = url.split('/').pop() || 'download'; // 使用文件名进行下载
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(link.href);
  } catch {}
}

/*
预览
 */
const handlePreview = async (url: string) => {
  try {
    // 假设你的预览链接是通过文件ID生成的
    const previewUrl = url;
    window.open(previewUrl, '_blank');
  } catch {}
}

//云存储配置表单
/** 添加/修改操作 */
const openFileFormRef = ref()
const openFileForm = (type: string) => {
  openFileFormRef.value.open(type)
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
