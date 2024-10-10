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
      <el-form-item>
        <el-button
          type="primary"
          @click="openForm('create')"
          v-hasPermi="['system:talk:save']"
        >
          <Icon icon="ep:plus" class="mr-5px"/>
          新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true">
      <el-table-column type="index" label="编号" width="100" align="center"/>
      <el-table-column label="说说内容" align="center" prop="talkContent"/>
      <el-table-column label="是否置顶" align="center" prop="isTop">
        <template #default="scope">
          <el-tag :type="scope.row.isTop == 0 ? 'primary' : 'danger'">{{
              scope.row.isTop == 0  ? '否' : '是'
            }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-tag :type="scope.row.status == 1 ? 'success' : 'danger'">{{
              scope.row.status == 1  ? '公开' : '私密'
            }}</el-tag>
        </template>
      </el-table-column>
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
            v-hasPermi="['system:talk:update']"
          >
            <Icon icon="ep:edit"/>
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['system:talk:delete']"
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
  <TalkForm ref="formRef" @success="getList"/>
</template>
<script setup lang="ts">
import {dateFormatter} from '@/utils/formatTime'
import * as TalkApi from '@/api/message/talk'
import TalkForm from './TalkForm.vue'
/** 说说 列表 */
defineOptions({name: 'MessageTalk'})
const message = useMessage() // 消息弹窗
const {t} = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
  }
})
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await TalkApi.getTalkPage(queryParams)
    list.value = data.records
    total.value = data.total
  } finally {
    loading.value = false
  }
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
    await TalkApi.deleteTalk(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {
  }
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
