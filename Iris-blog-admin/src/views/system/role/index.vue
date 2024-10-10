<template>
  <!--  <doc-alert title="功能权限" url="https://doc.iocoder.cn/resource-permission" />-->
  <!--  <doc-alert title="数据权限" url="https://doc.iocoder.cn/data-permission" />-->

  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form :inline="true" :model="queryParams" class="-mb-15px" label-width="68px">
      <el-form-item label="角色名称" prop="name">
        <el-input
          v-model="queryParams.action.name"
          class="!w-240px"
          clearable
          placeholder="请输入角色名称"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="角色标识" prop="code">
        <el-input
          v-model="queryParams.action.code"
          class="!w-240px"
          clearable
          placeholder="请输入角色标识"
          @keyup.enter="handleQuery"
        />
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
        <el-button v-hasPermi="['system:role:save']" type="primary" @click="openForm('create')">
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column type="index" label="编号" width="100" align="center" />
      <el-table-column align="center" label="角色名称" prop="name" />
      <el-table-column align="center" label="角色标识" prop="code" />
      <el-table-column align="center" label="备注" prop="remarks" />
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
            v-hasPermi="['system:role:update']"
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
          >
            <Icon icon="ep:edit" />
            编辑
          </el-button>
          <el-button
            v-hasPermi="['system:permission:assign-role-menu']"
            link
            preIcon="ep:basketball"
            title="菜单权限"
            type="primary"
            @click="openAssignMenuForm(scope.row)"
          >
            <Icon icon="ep:pointer" />
            菜单权限
          </el-button>
          <el-button
            v-hasPermi="['system:role:delete']"
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
  <RoleForm ref="formRef" @success="getList" />
  <!-- 表单弹窗：菜单权限 -->
  <RoleAssignMenuForm ref="assignMenuFormRef" @success="getList" />
</template>
<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import * as RoleApi from '@/api/system/role'
import RoleForm from './RoleForm.vue'
import RoleAssignMenuForm from './RoleAssignMenuForm.vue'

defineOptions({ name: 'SystemRole' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    code: '',
    name: ''
  }
})
/** 查询角色列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await RoleApi.getRolePage(queryParams)
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
    code: '',
    name: ''
  }
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 菜单权限操作 */
const assignMenuFormRef = ref()
const openAssignMenuForm = async (row: RoleApi.RoleVO) => {
  assignMenuFormRef.value.open(row)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await RoleApi.deleteRole(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
