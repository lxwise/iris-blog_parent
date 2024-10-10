<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="80px"
    >
      <el-form-item label="配置编码" prop="paramCode">
        <el-input v-model="formData.paramCode" placeholder="请输入配置编码" />
      </el-form-item>
      <el-form-item label="参数值" prop="paramValue">
        <el-input v-model="formData.paramValue" placeholder="请输入参数值" type="textarea" />
      </el-form-item>
      <el-form-item label="配置类型" prop="paramType">
        <el-radio-group v-model="formData.paramType">
          <el-radio key="true" :label="true" border>非系统参数</el-radio>
          <el-radio key="false" :label="false" border>系统参数</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio key="true" :label="true" border>正常</el-radio>
          <el-radio key="false" :label="false" border>禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输备注" type="textarea" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import * as ConfigApi from '@/api/system/config'
import { CommonStatusBooleanEnum } from '@/utils/constants'

defineOptions({ name: 'SystemConfigForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  paramCode: '',
  paramValue: '',
  paramType: CommonStatusBooleanEnum.DISABLE,
  status: CommonStatusBooleanEnum.DISABLE,
  remarks: ''
})
const formRules = reactive({
  paramCode: [{ required: true, message: '配置编码不能为空', trigger: 'blur' }],
  paramValue: [{ required: true, message: '参数值不能为空', trigger: 'blur' }],
  paramType: [{ required: true, message: '配置类型不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '配置状态不能为空', trigger: 'blur' }]
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
      formData.value = await ConfigApi.getSysConfig(id)
    } finally {
      formLoading.value = false
    }
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    paramCode: '',
    paramValue: '',
    paramType: CommonStatusBooleanEnum.DISABLE,
    status: CommonStatusBooleanEnum.DISABLE,
    remarks: ''
  }
  formRef.value?.resetFields()
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
  try {
    const data = formData.value as unknown as ConfigApi.SystemConfigVO
    if (formType.value === 'create') {
      await ConfigApi.createSysConfig(data)
      message.success(t('common.createSuccess'))
    } else {
      await ConfigApi.updateSysConfig(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
</script>
