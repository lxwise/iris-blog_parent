<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item v-if="formType === 'update'" label="字典类型" prop="dictType">
        <el-input v-model="formData.dictType" :disabled="true" placeholder="请输入字典类型" />
      </el-form-item>
      <el-form-item label="字典标签" prop="dictLabel">
        <el-input v-model="formData.dictLabel" placeholder="请输入字典标签" />
      </el-form-item>
      <el-form-item label="字典值" prop="dictValue">
        <el-input v-model="formData.dictValue" placeholder="请输入字典值" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" type="textarea" />
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
import * as DictDataApi from '@/api/system/dict/dictdata'

/** 字典数据表单 */
defineOptions({ name: 'DictDataForm' })
const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  dictType: undefined,
  dictLabel: undefined,
  dictValue: undefined,
  remark: undefined
})
const formRules = reactive({
  dictType: [{ required: true, message: '字典类型', trigger: 'blur' }],
  dictLabel: [{ required: true, message: '字典标签', trigger: 'blur' }],
  dictValue: [{ required: true, message: '字典值', trigger: 'blur' }],
  remark: [{ required: true, message: '备注', trigger: 'blur' }],
  sort: [{ required: true, message: '排序', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
/** 打开弹窗 */
const open = async (type: string, id?: number, dictType?: string) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (dictType) {
    formData.value.dictType = dictType
  }
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await DictDataApi.getDictData(id)
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
    const data = formData.value as unknown as DictDataApi.DicdataVO
    if (formType.value === 'create') {
      await DictDataApi.createDictData(data)
      message.success(t('common.createSuccess'))
    } else {
      await DictDataApi.updateDictData(data)
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
    dictLabel: undefined,
    dictValue: undefined,
    remark: undefined,
    sort: undefined
  }
  formRef.value?.resetFields()
}
</script>
