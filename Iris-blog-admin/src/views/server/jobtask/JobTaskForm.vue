<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="80px"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="任务名称" prop="jobName">
            <el-input v-model="formData.jobName" placeholder="请输入任务名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务分组" prop="jobGroup">
            <el-select v-model="formData.jobGroup" placeholder="请选择">
              <el-option
                v-for="dict in jobGroupList"
                :key="dict.index"
                :label="dict.value"
                :value="dict.index"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item prop="invokeTarget">
            <template #label>
              <span>
                调用方法
                <el-tooltip placement="top">
                  <template #content>
                    <div>
                      Bean调用示例：blogQuartz.blogParams('blog')
                      <br />Class类调用示例：com.shiyi.quartz.BlogQuartz.blogParams('blog')
                      <br />参数说明：支持字符串，布尔类型，长整型，浮点型，整型
                    </div>
                  </template>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </span>
            </template>
            <el-input v-model="formData.invokeTarget" placeholder="请输入调用方法" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="CRON 表达式" prop="cronExpression">
            <crontab v-model="formData.cronExpression" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="错误策略" prop="misfirePolicy">
            <el-radio-group v-model="formData.misfirePolicy">
              <el-radio-button
                v-for="type in getStrDictOptions(DICT_TYPE.SYSTEM_TASK_ERROR_POLICY)"
                :key="type.value"
                :label="type.label"
                :value="type.value"
              >
                {{ type.label }}
              </el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态">
            <el-radio-group v-model="formData.status">
              <el-radio
                v-for="status in getIntDictOptions(DICT_TYPE.SYSTEM_NUMBER_STATUS)"
                :key="status.value"
                :label="status.label"
                :value="status.value"
              >
                {{ status.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import { CommonStatusEnum } from '@/utils/constants'
import * as JobApi from '@/api/system/job'
import { FormRules } from 'element-plus'
import { DICT_TYPE, getIntDictOptions, getStrDictOptions } from '@/utils/dict'

defineOptions({ name: 'MonitorJobTaskForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  jobName: '',
  jobGroup: '',
  invokeTarget: '',
  cronExpression: '',
  misfirePolicy: '',
  status: CommonStatusEnum.ENABLE
})
const jobGroupList = ref([
  { index: 'DEFAULT', value: '默认' },
  { index: 'SYSTEM', value: '系统' }
])
const formRules = reactive<FormRules>({
  jobName: [{ required: true, message: '任务名称不能为空', trigger: 'blur' }],
  jobGroup: [{ required: true, message: '任务组名不能为空', trigger: 'blur' }],
  invokeTarget: [{ required: true, message: '调用方法不能为空', trigger: 'blur' }],
  misfirePolicy: [{ required: true, message: '错误策略不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
  cronExpression: [
    { required: true, message: 'cron执行表达式不能为空', trigger: ['blur', 'change'] }
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
      formData.value = await JobApi.getJob(id)
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
  try {
    const data = formData.value as unknown as JobApi.JobVO
    if (formType.value === 'create') {
      await JobApi.createJob(data)
      message.success(t('common.createSuccess'))
    } else {
      await JobApi.updateJob(data)
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
    jobName: '',
    jobGroup: '',
    invokeTarget: '',
    cronExpression: '',
    misfirePolicy: '',
    status: CommonStatusEnum.ENABLE
  }
  formRef.value?.resetFields()
}
</script>
