<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <!-- 是否置顶 -->
      <el-form-item label="是否置顶" prop="isTop">
        <el-radio-group v-model="formData.isTop">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 状态 -->
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio :label="1">公开</el-radio>
          <el-radio :label="2">私密</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="说说内容" prop="talkContent">
        <Editor v-model="formData.talkContent"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as TalkApi from '@/api/message/talk'

/** 说说表单 */
defineOptions({name: 'TalkForm'})
const {t} = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  talkContent: undefined,
  isTop: undefined,
  status: undefined
})
const formRules = reactive({
  talkContent: [{required: true, message: '说说内容+不能为空', trigger: 'blur'}],
  isTop: [{required: true, message: '是否置顶 (0否 1是)+不能为空', trigger: 'blur'}],
  status: [{required: true, message: '状态 (1公开  2私密)+不能为空', trigger: 'blur'}]
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
      formData.value = await TalkApi.getTalk(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({open}) // 提供 open 方法，用于打开弹窗

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
    const data = formData.value as unknown as TalkApi.TalkVO
    if (formType.value === 'create') {
      await TalkApi.createTalk(data)
      message.success(t('common.createSuccess'))
    } else {
      await TalkApi.updateTalk(data)
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
    talkContent: undefined,
    isTop: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
}
</script>
