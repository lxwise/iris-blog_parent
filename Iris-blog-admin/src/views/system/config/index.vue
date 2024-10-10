<template>
  <!--  <doc-alert title="功能权限" url="https://doc.iocoder.cn/resource-permission" />-->
  <!--  <doc-alert title="数据权限" url="https://doc.iocoder.cn/data-permission" />-->

  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form :inline="true" :model="queryParams" class="-mb-15px" label-width="68px">
      <el-form-item label="配置编码" prop="paramCode">
        <el-input
          v-model="queryParams.action.paramCode"
          class="!w-240px"
          clearable
          placeholder="请输入配置编码"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配置状态" prop="status">
        <el-select
          v-model="queryParams.action.status"
          class="!w-240px"
          clearable
          placeholder="请选择配置状态"
        >
          <el-option
            v-for="status in getBoolDictOptions(DICT_TYPE.SYSTEM_BOOLEAN_STATUS)"
            :key="status.value"
            :label="status.label"
            :value="status.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="配置类型" prop="paramType">
        <el-select
          v-model="queryParams.action.paramType"
          class="!w-240px"
          clearable
          placeholder="请选择配置类型"
        >
          <el-option
            v-for="type in getStrDictOptions(DICT_TYPE.SYSTEM_CONFIG_TYPE)"
            :key="type.value"
            :label="type.label"
            :value="type.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="handleQuery">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button type="primary" plain @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
        <el-button v-hasPermi="['system:config:save']" type="primary" @click="openForm('create')">
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['system:config:export']"
        >
          <Icon icon="ep:download" />导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column type="index" label="编号" align="center" />
      <el-table-column align="center" label="配置编码" prop="paramCode" />
      <el-table-column
        align="center"
        :show-overflow-tooltip="true"
        label="参数值"
        prop="paramValue"
      />
      <el-table-column label="配置类型" align="center" prop="paramType">
        <template #default="scope">
          <el-tag :type="scope.row.paramType ? 'danger' : 'success'">{{
            scope.row.paramType ? '非系统配置' : '系统配置'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" key="status">
        <template #default="scope">
          <el-switch v-model="scope.row.status" @change="handleStatusChange(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="备注" prop="remark" />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="创建时间"
        prop="createTime"
        width="180"
      />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="更新时间"
        prop="updateTime"
        width="180"
      />
      <el-table-column :width="300" align="center" label="操作">
        <template #default="scope">
          <el-button
            v-hasPermi="['system:config:update']"
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
          >
            <Icon icon="ep:edit" />
            编辑
          </el-button>
          <el-button
            v-hasPermi="['system:config:delete']"
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
          >
            <Icon icon="ep:delete" />
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
  <SystemConfigForm ref="formRef" @success="getList" />
</template>
<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import * as ConfigApi from '@/api/system/config'
import SystemConfigForm from './SystemConfigForm.vue'
import { CommonStatusBooleanEnum } from '@/utils/constants'
import download from '@/utils/download'
import { DICT_TYPE, getBoolDictOptions, getStrDictOptions } from '@/utils/dict'
defineOptions({ name: 'SystemConfig' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    paramCode: null,
    paramType: null,
    status: undefined
  }
})
/** 系统参数列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ConfigApi.getSysConfigPage(queryParams)
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
  queryParams.action = {
    paramCode: null,
    paramType: null,
    status: undefined
  }
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
    await ConfigApi.deleteSysConfig(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 修改配置状态 */
const handleStatusChange = async (row: ConfigApi.SystemConfigVO) => {
  try {
    // 修改状态的二次确认
    const text = row.status === CommonStatusBooleanEnum.ENABLE ? '启用' : '停用'
    await message.confirm('确认要"' + text + '""' + row.username + '"配置吗?')
    // 发起修改状态
    await ConfigApi.updateSysConfigStatus(row.id)
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status =
      row.status === CommonStatusBooleanEnum.ENABLE
        ? CommonStatusBooleanEnum.DISABLE
        : CommonStatusBooleanEnum.ENABLE
  }
}

/** 导出按钮操作 */
const exportLoading = ref(false)
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await ConfigApi.exportSysConfig(queryParams.action)
    download.excel(data, '系统参数.xls')
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
