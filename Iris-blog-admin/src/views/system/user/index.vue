<template>
  <!--  <doc-alert title="用户体系" url="https://doc.iocoder.cn/user-center/" />-->
  <!--  <doc-alert title="三方登陆" url="https://doc.iocoder.cn/social-user/" />-->
  <!--  <doc-alert title="Excel 导入导出" url="https://doc.iocoder.cn/excel-import-and-export/" />-->

  <el-row :gutter="24" v-hasPermi="['system:user:list']">
    <el-col :span="24" :xs="24">
      <!-- 搜索 -->
      <ContentWrap>
        <el-form
          class="-mb-15px"
          :model="queryParams"
          ref="queryFormRef"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="用户名称" prop="username">
            <el-input
              v-model="queryParams.action.username"
              placeholder="请输入用户名称"
              clearable
              @keyup.enter="handleQuery"
              class="!w-240px"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="phone">
            <el-input
              v-model="queryParams.action.phone"
              placeholder="请输入手机号码"
              clearable
              @keyup.enter="handleQuery"
              class="!w-240px"
            />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.action.status"
              placeholder="用户状态"
              clearable
              class="!w-240px"
            >
              <el-option
                v-for="status in getBoolDictOptions(DICT_TYPE.SYSTEM_BOOLEAN_STATUS)"
                :key="status.value"
                :label="status.label"
                :value="status.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间" prop="createTime">
            <el-date-picker
              v-model="queryParams.action.createTime"
              value-format="YYYY-MM-DD HH:mm:ss"
              type="datetimerange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              class="!w-240px"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" plain @click="handleQuery">
              <Icon icon="ep:search" />
              搜索
            </el-button>
            <el-button type="primary" plain @click="resetQuery">
              <Icon icon="ep:refresh" />
              重置
            </el-button>
            <el-button type="primary" @click="openForm('create')" v-hasPermi="['system:user:save']">
              <Icon icon="ep:plus" />
              新增
            </el-button>
            <!--            <el-button-->
            <!--              type="warning"-->
            <!--              plain-->
            <!--              @click="handleImport"-->
            <!--              v-hasPermi="['system:user:import']"-->
            <!--            >-->
            <!--              <Icon icon="ep:upload" /> 导入-->
            <!--            </el-button>-->
            <!--            <el-button-->
            <!--              type="success"-->
            <!--              plain-->
            <!--              @click="handleExport"-->
            <!--              :loading="exportLoading"-->
            <!--              v-hasPermi="['system:user:export']"-->
            <!--            >-->
            <!--              <Icon icon="ep:download" />导出-->
            <!--            </el-button>-->
          </el-form-item>
        </el-form>

        <el-table v-loading="loading" :data="list">
          <el-table-column type="index" label="编号" width="100" align="center" />
          <el-table-column
            label="用户名称"
            align="center"
            prop="username"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="用户昵称"
            align="center"
            prop="nickname"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="手机号码" align="center" prop="phone" />
          <el-table-column label="QQ" align="center" prop="qq" />
          <el-table-column label="微信" align="center" prop="wx" />
          <el-table-column label="邮箱" align="center" prop="email" />
          <el-table-column label="头像" align="center" prop="avatar">
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
          <el-table-column label="性别" align="center" prop="sex">
            <template #default="scope">
              <span v-if="scope.row.sex === 0">女</span>
              <span v-if="scope.row.sex === 1">男</span>
              <span v-if="scope.row.sex === 2 || scope.row.sex === null">未知</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" key="status">
            <template #default="scope">
              <el-switch v-model="scope.row.status" @change="handleStatusChange(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="登录类型" align="center" prop="loginType">
            <template #default="scope">
              {{ scope.row.loginType == 1 ? 'PC' : '安卓' }}
            </template>
          </el-table-column>
          <el-table-column label="IP" align="center" prop="ip" />
          <el-table-column label="IP地址" align="center" prop="ipAddress" />
          <el-table-column label="客户端" align="center" prop="os" />
          <el-table-column
            label="最后登录时间"
            align="center"
            prop="lastLoginTime"
            :formatter="dateFormatter"
            width="120"
          />
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            :formatter="dateFormatter"
            width="120"
          />
          <el-table-column label="操作" align="center" width="160">
            <template #default="scope">
              <div class="flex items-center justify-center">
                <el-button
                  type="primary"
                  link
                  @click="openForm('update', scope.row.id)"
                  v-hasPermi="['system:user:update']"
                >
                  <Icon icon="ep:edit" />
                  修改
                </el-button>
                <el-dropdown
                  @command="(command) => handleCommand(command, scope.row)"
                  v-hasPermi="[
                    'system:user:delete',
                    'system:user:update-password',
                    'system:permission:assign-user-role'
                  ]"
                >
                  <el-button type="primary" link>
                    <Icon icon="ep:more-filled" />
                    更多
                  </el-button>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item
                        command="handleDelete"
                        v-if="checkPermi(['system:user:delete'])"
                      >
                        <Icon icon="ep:delete" />
                        删除
                      </el-dropdown-item>
                      <el-dropdown-item
                        command="handleResetPwd"
                        v-if="checkPermi(['system:user:update-password'])"
                      >
                        <Icon icon="ep:key" />
                        重置密码
                      </el-dropdown-item>
                      <el-dropdown-item
                        command="handleRole"
                        v-if="checkPermi(['system:permission:assign-user-role'])"
                      >
                        <Icon icon="ep:circle-check" />
                        分配角色
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <Pagination
          :total="total"
          v-model:page="queryParams.pageNo"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
        />
      </ContentWrap>
    </el-col>
  </el-row>

  <!-- 添加或修改用户对话框 -->
  <UserForm ref="formRef" @success="getList" />
  <!-- 用户导入对话框 -->
  <UserImportForm ref="importFormRef" @success="getList" />
  <!-- 分配角色 -->
  <UserAssignRoleForm ref="assignRoleFormRef" @success="getList" />
</template>
<script lang="ts" setup>
import { DICT_TYPE, getBoolDictOptions } from '@/utils/dict'
import { checkPermi } from '@/utils/permission'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { CommonStatusBooleanEnum } from '@/utils/constants'
import * as UserApi from '@/api/system/user'
import UserForm from './UserForm.vue'
import UserImportForm from './UserImportForm.vue'
import UserAssignRoleForm from './UserAssignRoleForm.vue'

defineOptions({ name: 'SystemUser' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    username: undefined,
    phone: undefined,
    status: undefined,
    createTime: []
  }
})
const queryFormRef = ref() // 搜索的表单
/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await UserApi.getUserPage(queryParams)
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
  queryParams['action'] = {}
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 用户导入 */
const importFormRef = ref()
const handleImport = () => {
  importFormRef.value.open()
}

/** 修改用户状态 */
const handleStatusChange = async (row: UserApi.UserVO) => {
  try {
    // 修改状态的二次确认
    const text = row.status === CommonStatusBooleanEnum.ENABLE ? '启用' : '停用'
    await message.confirm('确认要"' + text + '""' + row.username + '"用户吗?')
    // 发起修改状态
    await UserApi.updateUserStatus(row.id)
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
    const data = await UserApi.exportUser(queryParams)
    download.excel(data, '用户数据.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 操作分发 */
const handleCommand = (command: string, row: UserApi.UserVO) => {
  switch (command) {
    case 'handleDelete':
      handleDelete(row.id)
      break
    case 'handleResetPwd':
      handleResetPwd(row)
      break
    case 'handleRole':
      handleRole(row)
      break
    default:
      break
  }
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await UserApi.deleteUser(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 重置密码 */
const handleResetPwd = async (row: UserApi.UserVO) => {
  try {
    // 重置的二次确认
    const result = await message.prompt(
      '请输入"' + row.username + '"的新密码',
      t('common.reminder')
    )
    const password = result.value
    // 发起重置
    await UserApi.resetUserPwd(row.id, password)
    message.success('密码修改成功')
  } catch {}
}

/** 分配角色 */
const assignRoleFormRef = ref()
const handleRole = (row: UserApi.UserVO) => {
  assignRoleFormRef.value.open(row)
}

/** 初始化 */
onMounted(() => {
  getList()
})
</script>
