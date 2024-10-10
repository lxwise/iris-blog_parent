<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
        class="-mb-15px"
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="68px"
    >
      <el-form-item label="配置名" prop="name">
        <el-input
            v-model="queryParams.action.name"
            placeholder="请输入配置路径"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="配置路径" prop="path">
        <el-input
            v-model="queryParams.action.path"
            placeholder="请输入配置路径"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="文件名" prop="fileName">
        <el-input
            v-model="queryParams.action.fileName"
            placeholder="请输入文件名"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="handleQuery">
          <Icon icon="ep:search" class="mr-5px"/>
          搜索
        </el-button>
        <el-button type="primary" plain @click="resetQuery">
          <Icon icon="ep:refresh" class="mr-5px"/>
          重置
        </el-button>
        <el-button type="primary" @click="openForm('create')" v-hasPermi="['gateway:config:save']">
          <Icon icon="ep:plus" class="mr-5px"/>
          新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true">
      <el-table-column label="ID" align="center" prop="id"/>
      <el-table-column label="配置名" align="center" prop="name"/>
      <el-table-column label="配置路径" align="center" prop="path"/>
      <el-table-column label="文件名" align="center" prop="fileName"/>
      <el-table-column label="读取位置" align="center" prop="position"/>
      <el-table-column
          label="创建时间"
          align="center"
          prop="createTime"
          :formatter="dateFormatter"
          width="180px"
      />
      <el-table-column
          label="修改时间"
          align="center"
          prop="updateTime"
          :formatter="dateFormatter"
          width="180px"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
              link
              type="primary"
              @click="openForm('update', scope.row.id)"
              v-hasPermi="['gateway:config:update']"
          >
            <Icon icon="ep:edit"/>
            编辑
          </el-button>
          <el-button
              link
              type="danger"
              @click="handleDelete(scope.row.id)"
              v-hasPermi="['gateway:config:delete']"
          >
            <Icon icon="ep:delete"/>
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
        v-model:limit="queryParams.pageSize"
        v-model:page="queryParams.pageNo"
        :total="total"
        @pagination="getList"
    />
  </ContentWrap>
  <!-- 表单弹窗：添加/修改 -->
  <GatewayConfigForm ref="formRef" @success="getList"/>
</template>
<script setup lang="ts">
import {dateFormatter} from '@/utils/formatTime'
import download from '@/utils/download'
import * as GatewayConfigApi from '@/api/gateway/config'
import GatewayConfigForm from './GatewayConfigForm.vue'

/** 网关配置 列表 */
defineOptions({name: 'GatewayConfig'})
const message = useMessage() // 消息弹窗
const {t} = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    name: undefined,
    path: undefined,
    fileName: undefined
  }
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await GatewayConfigApi.getGatewayConfigPage(queryParams)
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
  queryParams.action = {}
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await GatewayConfigApi.deleteGatewayConfig(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {
  }
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await GatewayConfigApi.exportGatewayConfig(queryParams)
    download.excel(data, '网关配置.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
