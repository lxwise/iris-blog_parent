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
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.action.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.action.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="status in getIntDictOptions(DICT_TYPE.MESSAGE_COMMENT_AUDIT_STATUS)"
            :key="status.value"
            :label="status.label"
            :value="status.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="评论类型" prop="commentType">
        <el-select
          v-model="queryParams.action.commentType"
          placeholder="请选择评论类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="status in getIntDictOptions(DICT_TYPE.COMMENT_TYPE)"
            :key="status.value"
            :label="status.label"
            :value="status.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
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
  <div class="mb-15px">
    <el-button type="success" plain @click="handleStatus(1)" v-hasPermi="['system:comment:update']">
      <Icon icon="ep:finished" class="mr-5px" />
      批量通过
    </el-button>
    <el-button type="warning" plain @click="handleStatus(2)" v-hasPermi="['system:comment:update']">
      <Icon icon="ep:finished" class="mr-5px" />
      批量驳回
    </el-button>
    <el-button type="danger" plain @click="handleDelete()" v-hasPermi="['system:comment:delete']">
      <Icon icon="ep:finished" class="mr-5px" />
      批量删除
    </el-button>
  </div>
  <!-- 列表 -->
  <ContentWrap>
    <el-table
      v-loading="loading"
      :data="list"
      highlight-current-row
      ref="multipleTableRef"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column type="index" label="编号" width="100" align="center" />
      <el-table-column label="评论人" align="center" prop="nickname" />
      <el-table-column label="评论人头像" align="center" prop="avatar">
        <template #default="{ row }">
          <el-image
            class="h-80px w-80px"
            lazy
            :src="row.avatar"
            :preview-src-list="[row.avatar]"
            preview-teleported
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="评论类型" align="center" prop="commentType">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.commentType === 1">{{ '文章' }}</el-tag>
          <el-tag type="warning" v-if="scope.row.commentType === 2">{{ '说说' }}</el-tag>
          <el-tag type="primary" v-if="scope.row.commentType === 3">{{ '友链' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="内容" align="center" prop="content" />
      <el-table-column label="回复人" align="center" prop="replyNickname" />
      <el-table-column label="ip" align="center" prop="ip" />
      <el-table-column label="ip地址" align="center" prop="ipAddress" />
      <el-table-column label="操作系统" align="center" prop="os" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.status === 0">{{ '未审核' }}</el-tag>
          <el-tag type="success" v-if="scope.row.status === 1">{{ '通过' }}</el-tag>
          <el-tag type="warning" v-if="scope.row.status === 2">{{ '驳回' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" width="240px">
        <template #default="scope">
          <el-button
            link
            type="success"
            @click="handleStatus(1, scope.row.id)"
            v-hasPermi="['system:comment:update']"
          >
            <Icon icon="ep:circle-check" />
            通过
          </el-button>
          <el-button
            link
            type="warning"
            @click="handleStatus(2, scope.row.id)"
            v-hasPermi="['system:comment:update']"
          >
            <Icon icon="ep:refresh-right" />
            驳回
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['system:comment:delete']"
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
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import * as CommentApi from '@/api/message/comment/index'
import { ElMessage, ElTable } from 'element-plus'
import { CommonStatusEnum } from '@/utils/constants'

const createTime = ref([])
/** 文章评论表 列表 */
defineOptions({ name: 'MessageComment' })
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    userName: undefined,
    status: undefined,
    commentType: undefined,
    createTimeStart: null,
    createTimeEnd: null
  }
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const commentIds = ref<number[]>([])

/** 行checkbox 选中事件 */
function handleSelectionChange(selection: CommentApi.CommentVO[]) {
  commentIds.value = selection.map((item: any) => item.id)
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    if (createTime.value) {
      queryParams.action.createTimeStart = createTime.value[0]
      queryParams.action.createTimeEnd = createTime.value[1]
    }
    const data = await CommentApi.getCommentPage(queryParams)
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
  createTime.value = []
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 删除按钮操作 */
const handleDelete = async (id?: number) => {
  try {
    let ids: number[] = [] // 初始化为一个空数组
    // 如果有直接传入的id，则添加到数组中
    if (id) {
      ids.push(id)
    }
    // 如果commentIds.value存在且有长度，覆盖当前ids
    if (commentIds.value.length) {
      ids = commentIds.value
    }
    // 检查ids是否为空，若为空则提示并返回
    if (!ids.length) {
      ElMessage.warning('请勾选删除项')
      return
    }
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await CommentApi.deleteComment(ids)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
/** 状态按钮操作 */
const handleStatus = async (status: number, id?: number) => {
  try {
    let ids: number[] = [] // 初始化为一个空数组
    // 如果有直接传入的id，则添加到数组中
    if (id) {
      ids.push(id)
    }
    // 如果commentIds.value存在且有长度，覆盖当前ids
    if (commentIds.value.length) {
      ids = commentIds.value
    }
    // 检查ids是否为空，若为空则提示并返回
    if (!ids.length) {
      ElMessage.warning('请勾选修改项')
      return
    }
    // 修改状态的二次确认
    const text = status === CommonStatusEnum.ENABLE ? '通过' : '驳回'
    await message.confirm('确认要"' + text + '"评论吗?')

    // 确保在这里ids是一个number数组，然后发起修改状态请求
    await CommentApi.updateCommentStatus({ status, ids })
    message.success(t('common.updateSuccess'))
    // 刷新列表
    await getList()
  } catch (error) {
    // 异常处理，比如记录日志或显示错误信息
    console.error('更新评论状态时发生错误:', error)
  }
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
