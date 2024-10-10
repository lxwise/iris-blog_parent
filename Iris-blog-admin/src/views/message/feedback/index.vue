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
      <el-form-item label="反馈类型" prop="backType">
        <el-select
          v-model="queryParams.action.backType"
          placeholder="请选择反馈类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="status in getBoolDictOptions(DICT_TYPE.SYSTEM_BOOLEAN_STATUS)"
            :key="status.value"
            :label="status.value ? '缺陷' : '需求'"
            :value="status.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.action.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="status in getBoolDictOptions(DICT_TYPE.SYSTEM_BOOLEAN_STATUS)"
            :key="status.value"
            :label="status.value ? '解决' : '未解决'"
            :value="status.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain @click="handleQuery">
          <Icon icon="ep:search" class="mr-5px" />
          搜索
        </el-button>
        <el-button type="primary" plain @click="resetQuery">
          <Icon icon="ep:refresh" class="mr-5px" />
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column type="index" label="编号" width="100" align="center" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="详细内容" align="center" prop="content" />
      <el-table-column label="图片地址" align="center" prop="imgUrl">
        <template #default="{ row }">
          <el-image
            class="h-80px w-80px"
            lazy
            :src="row.imgUrl"
            :preview-src-list="[row.imgUrl]"
            preview-teleported
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="反馈类型" align="center" prop="backType">
        <template #default="scope">
          <el-tag :type="scope.row.backType ? 'danger' : 'success'">{{
            scope.row.backType ? '缺陷' : '需求'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-tag :type="scope.row.status ? 'success' : 'warning'">{{
            scope.row.status ? '解决' : '未解决'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="ip" align="center" prop="ip" />
      <el-table-column label="ip地址" align="center" prop="ipAddress" />
      <el-table-column label="操作系统" align="center" prop="os" />
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
            :type="scope.row.status ? 'warning' : 'success'"
            @click="handleStatus(scope.row.id, scope.row.status)"
            v-hasPermi="['system:feedback:update']"
          >
            <Icon icon="ep:circle-check" />
            {{ scope.row.status ? '未解决' : '解决' }}
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['system:feedback:delete']"
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
</template>
<script setup lang="ts">
import { DICT_TYPE, getBoolDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as FeedBackApi from '@/api/message/feedback/index'

/** 用户反馈表 列表 */
defineOptions({ name: 'MessageFeedback' })
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    backType: undefined,
    status: undefined
  }
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await FeedBackApi.getFeedBackPage(queryParams)
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

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await FeedBackApi.deleteFeedBack(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
/** 解决按钮操作 */
const handleStatus = async (id: number, status: boolean) => {
  try {
    // 二次确认
    const text = status ? '未解决' : '解决'
    await message.confirm('确认要修改状态为' + text + '吗?')
    // 发起修改
    await FeedBackApi.updateFeedBackStatus(id)
    message.success(t('common.updateSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await FeedBackApi.exportFeedBackExcel(queryParams)
    download.excel(data, '用户反馈表.xls')
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
