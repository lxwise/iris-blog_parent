<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="网站名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入网站名称" />
      </el-form-item>
      <el-form-item label="网站地址" prop="url">
        <el-input v-model="formData.url" placeholder="请输入网站地址" />
      </el-form-item>
      <el-form-item label="网站logo" prop="image">
        <el-input v-model="formData.image" placeholder="请输入网站logo" />
      </el-form-item>
      <el-form-item label="网站描述" prop="info">
        <el-input v-model="formData.info" placeholder="请输入网站描述" />
      </el-form-item>
      <el-form-item label="联系邮箱" prop="email">
        <el-input v-model="formData.email" placeholder="请输入联系邮箱" />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as FriendLinkApi from '@/api/website/friendlink/index'

/** 友情链接表表单 */
defineOptions({ name: 'FriendLinkForm' })
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  url: undefined,
  image: undefined,
  info: undefined,
  email: undefined,
  sort: undefined
})
const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
const formRules = reactive({
  name: [{ required: true, message: '请输入网站名称', trigger: 'blur' }],
  url: [{ required: true, message: '请输入网站地址', trigger: 'blur' }],
  image: [{ required: true, message: '请输入网站logo', trigger: 'blur' }],
  info: [{ required: true, message: '请输入网站描述', trigger: 'blur' }],
  sort: [{ required: true, message: '请输入排序', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入联系邮箱', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (!emailRegex.test(value)) {
          callback(new Error('邮箱格式不正确'))
        } else {
          callback()
        }
      }
    }
  ]
})
const formRef = ref() // 表单 Ref
/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await FriendLinkApi.getFriendLink(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as FriendLinkApi.FriendLinkVO
    if (formType.value === 'create') {
      await FriendLinkApi.createFriendLink(data)
      message.success(t('common.createSuccess'))
    } else {
      await FriendLinkApi.updateFriendLink(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: undefined,
    url: undefined,
    image: undefined,
    info: undefined,
    email: undefined,
    sort: undefined
  }
  formRef.value?.resetFields()
}
</script>
