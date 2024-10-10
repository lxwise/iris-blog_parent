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
      <el-form-item label="网站名称" prop="name">
        <el-input
          v-model="queryParams.action.name"
          placeholder="请输入网站名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="是否展示" prop="status">
        <el-select
          v-model="queryParams.action.status"
          placeholder="请选择是否展示"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="status in getIntDictOptions(DICT_TYPE.FRIEND_LINK_SHOW_STATUS)"
            :key="status.value"
            :label="status.label"
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
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['system:friendlink:save']"
        >
          <Icon icon="ep:plus" class="mr-5px" />
          新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true">
      <el-table-column type="index" label="编号" width="100" align="center" />
      <el-table-column label="网站名称" align="center" prop="name" />
      <el-table-column label="网站地址" align="center" prop="url">
        <template #default="scope">
          <el-link :underline="false" :href="scope.row.url" target="_blank"
            >{{ scope.row.url }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="网站logo" align="center" prop="image">
        <template #default="{ row }">
          <el-image
            class="h-80px w-80px"
            lazy
            :src="row.image"
            :preview-src-list="[row.image]"
            preview-teleported
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="网站描述" align="center" prop="info" />
      <el-table-column label="联系邮箱" align="center" prop="email" />
      <el-table-column label="是否展示" align="center" prop="status">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.status === 0">{{ '申请' }}</el-tag>
          <el-tag type="success" v-if="scope.row.status === 1">{{ '展示' }}</el-tag>
          <el-tag type="warning" v-if="scope.row.status === 2">{{ '不展示' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="更新时间"
        align="center"
        prop="updateTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" width="160">
        <template #default="scope">
          <div class="flex items-center justify-center">
            <el-button
              link
              type="primary"
              @click="openForm('update', scope.row.id)"
              v-hasPermi="['system:friendlink:update']"
            >
              <Icon icon="ep:edit" />
              编辑
            </el-button>
            <el-dropdown
              @command="(command) => handleCommand(command, scope.row)"
              v-hasPermi="['system:friendlink:delete', 'system:friendlink:update']"
            >
              <el-button type="primary" link>
                <Icon icon="ep:more-filled" />
                更多
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                    command="handleDelete"
                    v-if="checkPermi(['system:friendlink:delete'])"
                  >
                    <Icon icon="ep:delete" />
                    删除
                  </el-dropdown-item>
                  <el-dropdown-item
                    command="handleShow"
                    v-if="checkPermi(['system:friendlink:update'])"
                  >
                    <Icon icon="ep:view" />
                    展示
                  </el-dropdown-item>
                  <el-dropdown-item
                    command="handleNoShow"
                    v-if="checkPermi(['system:friendlink:update'])"
                  >
                    <Icon icon="ep:hide" />
                    不展示
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
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
  <FriendLinkForm ref="formRef" @success="getList" />
</template>
<script setup lang="ts">
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import * as FriendLinkApi from '@/api/website/friendlink/index'
import FriendLinkForm from './FriendLinkForm.vue'
import { checkPermi } from '@/utils/permission'

/** 友情链接表 列表 */
defineOptions({ name: 'WebFriendLink' })
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    name: undefined,
    status: undefined
  }
})
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await FriendLinkApi.getFriendLinkPage(queryParams)
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
    await FriendLinkApi.deleteFriendLink(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
/** 状态按钮操作 */
const handleShowStatus = async (id: number, status: number) => {
  try {
    // 修改状态的二次确认
    const text = status === 1 ? '展示' : '不展示'
    await message.confirm('确认要"' + text + '"友链吗?')
    await FriendLinkApi.updateFriendLinkStatus(id, status)
    message.success(t('common.updateSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 操作分发 */
const handleCommand = (command: string, row: UserApi.UserVO) => {
  switch (command) {
    case 'handleDelete':
      handleDelete(row.id)
      break
    case 'handleShow':
      handleShowStatus(row.id, 1)
      break
    case 'handleNoShow':
      handleShowStatus(row.id, 2)
      break
    default:
      break
  }
}
/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
