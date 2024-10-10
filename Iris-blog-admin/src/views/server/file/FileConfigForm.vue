<template>
  <el-drawer v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="openFileFormRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="auto"
    >
      <el-radio-group v-model="storageType" class="mb-20px">
        <el-radio :value="2">阿里云</el-radio>
        <el-radio :value="1">七牛云</el-radio>
        <el-radio :value="3">腾讯云</el-radio>
        <el-radio :value="4">本地云</el-radio>
        <el-radio :value="5">Minio</el-radio>
      </el-radio-group>
      <!--七牛云配置-->
      <el-form-item v-if="storageType === 1" label="绑定域名" prop="config.qiniuDomain">
        <el-input v-model="formData.config.qiniuDomain" placeholder="请输入绑定域名"/>
      </el-form-item>
      <el-form-item v-if="storageType === 1" label="七牛ACCESS_KEY" prop="config.qiniuAccessKey">
        <el-input v-model="formData.config.qiniuAccessKey" placeholder="请输入七牛ACCESS_KEY"/>
      </el-form-item>
      <el-form-item v-if="storageType === 1" label="七牛SECRET_KEY" prop="config.qiniuSecretKey">
        <el-input v-model="formData.config.qiniuSecretKey" placeholder="请输入七牛SECRET_KEY"/>
      </el-form-item>
      <el-form-item v-if="storageType === 1" label="存储空间名" prop="config.qiniuBucketName">
        <el-input v-model="formData.config.qiniuBucketName" placeholder="请输入存储空间名"/>
      </el-form-item>
      <!--  阿里云配置    -->
      <el-form-item v-if="storageType === 2" label="绑定域名" prop="config.aliyunDomain">
        <el-input v-model="formData.config.aliyunDomain" placeholder="请输入绑定域名"/>
      </el-form-item>
      <el-form-item v-if="storageType === 2" label="阿里云EndPoint" prop="config.aliyunEndPoint">
        <el-input v-model="formData.config.aliyunEndPoint" placeholder="请输入阿里云EndPoint"/>
      </el-form-item>
      <el-form-item
        v-if="storageType === 2"
        label="阿里云AccessKeyId"
        prop="config.aliyunAccessKeyId"
      >
        <el-input
          v-model="formData.config.aliyunAccessKeyId"
          placeholder="请输入阿里云AccessKeyId"
        />
      </el-form-item>
      <el-form-item
        v-if="storageType === 2"
        label="阿里云AccessKeySecret"
        prop="config.aliyunAccessKeySecret"
      >
        <el-input
          v-model="formData.config.aliyunAccessKeySecret"
          placeholder="请输入阿里云AccessKeySecret"
        />
      </el-form-item>
      <el-form-item
        v-if="storageType === 2"
        label="阿里云BucketName"
        prop="config.aliyunBucketName"
      >
        <el-input v-model="formData.config.aliyunBucketName" placeholder="请输入阿里云BucketName"/>
      </el-form-item>

      <!--  腾讯云配置    -->
      <el-form-item v-if="storageType === 3" label="绑定域名" prop="config.qcloudDomain">
        <el-input v-model="formData.config.qcloudDomain" placeholder="请输入绑定域名"/>
      </el-form-item>
      <el-form-item v-if="storageType === 3" label="腾讯云AppId" prop="config.qcloudAppId">
        <el-input v-model="formData.config.qcloudAppId" placeholder="请输入腾讯云AppId"/>
      </el-form-item>
      <el-form-item v-if="storageType === 3" label="腾讯云SecretId" prop="config.qcloudSecretId">
        <el-input v-model="formData.config.qcloudSecretId" placeholder="请输入腾讯云SecretId"/>
      </el-form-item>
      <el-form-item v-if="storageType === 3" label="腾讯云SecretKey" prop="config.qcloudSecretKey">
        <el-input v-model="formData.config.qcloudSecretKey" placeholder="请输入腾讯云SecretKey"/>
      </el-form-item>
      <el-form-item
        v-if="storageType === 3"
        label="腾讯云BucketName"
        prop="config.qcloudBucketName"
      >
        <el-input v-model="formData.config.qcloudBucketName" placeholder="请输入腾讯云BucketName"/>
      </el-form-item>
      <el-form-item v-if="storageType === 3" label="腾讯云COS所属地区" prop="config.qcloudRegion">
        <el-input v-model="formData.config.qcloudRegion" placeholder="请输入腾讯云COS所属地区"/>
      </el-form-item>

      <!-- 本地云     -->
      <el-form-item v-if="storageType === 4" label="绑定域名" prop="config.localDomain">
        <el-input v-model="formData.config.localDomain" placeholder="请输入绑定域名"/>
      </el-form-item>
      <el-form-item v-if="storageType === 4" label="存储路径" prop="config.localPath">
        <el-input v-model="formData.config.localPath" placeholder="请输入存储路径"/>
      </el-form-item>
      <!-- Minio     -->
      <el-form-item v-if="storageType === 5" label="绑定域名" prop="config.minioEndPoint">
        <el-input v-model="formData.config.minioEndPoint" placeholder="请输入绑定域名"/>
      </el-form-item>
      <el-form-item v-if="storageType === 5" label="Minio AccessKey" prop="config.minioAccessKey">
        <el-input v-model="formData.config.minioAccessKey" placeholder="请输入Minio AccessKey"/>
      </el-form-item>
      <el-form-item v-if="storageType === 5" label="Minio SecretKey" prop="config.minioSecretKey">
        <el-input v-model="formData.config.minioSecretKey" placeholder="请输入Minio SecretKey"/>
      </el-form-item>
      <el-form-item v-if="storageType === 5" label="Minio BucketName" prop="config.minioBucketName">
        <el-input v-model="formData.config.minioBucketName" placeholder="请输入Minio BucketName"/>
      </el-form-item>
      <!--存放目录-->
      <el-form-item label="存放目录" prop="config.directoryList">
        <template #label>
          <Tooltip message="多个目录用英文逗号分隔，例如说：blog或者goods,order" title="存放目录"/>
        </template>
        <el-input
          v-model="formData.config.directoryList"
          clearable
          placeholder="例如说：goods,order"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </el-drawer>
</template>
<script lang="ts" setup>
import * as FileConfigApi from '@/api/system/file'
import {FormRules} from 'element-plus'

defineOptions({name: 'ServerFileConfigForm'})

const storageType = ref(0) // 日期快捷天数（单选按钮组）, 默认7天
const {t} = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  config: {} as FileConfigApi.FileConfigVO
})
const formRules = reactive<FormRules>({
  config: {
    qiniuDomain: [{required: true, message: '七牛云绑定域名不能为空', trigger: 'blur'}],
    qiniuAccessKey: [{required: true, message: '七牛云ACCESS_KEY', trigger: 'blur'}],
    qiniuSecretKey: [{required: true, message: '七牛云SECRET_KEY', trigger: 'blur'}],
    qiniuBucketName: [{required: true, message: '七牛云存储空间名', trigger: 'blur'}],
    aliyunDomain: [{required: true, message: '阿里云绑定域名不能为空', trigger: 'blur'}],
    aliyunEndPoint: [{required: true, message: '阿里云EndPoint不能为空', trigger: 'change'}],
    aliyunAccessKeyId: [{required: true, message: '阿里云AccessKeyId不能为空', trigger: 'blur'}],
    aliyunAccessKeySecret: [
      {
        required: true,
        message: '阿里云AccessKeySecret不能为空',
        trigger: 'blur'
      }
    ],
    aliyunBucketName: [{required: true, message: '阿里云BucketName不能为空', trigger: 'blur'}],
    qcloudDomain: [{required: true, message: '腾讯云绑定域名不能为空', trigger: 'blur'}],
    qcloudAppId: [{required: true, message: '腾讯云AppId不能为空', trigger: 'blur'}],
    qcloudSecretId: [{required: true, message: '腾讯云SecretId不能为空', trigger: 'blur'}],
    qcloudSecretKey: [{required: true, message: '腾讯云SecretKey不能为空', trigger: 'blur'}],
    qcloudBucketName: [{required: true, message: '腾讯云BucketName不能为空', trigger: 'blur'}],
    qcloudRegion: [{required: true, message: '腾讯云COS所属地区不能为空', trigger: 'blur'}],
    localDomain: [{required: true, message: '本地云绑定域名不能为空', trigger: 'blur'}],
    localPath: [{required: true, message: '本地云存储路径不能为空', trigger: 'blur'}]
  } as FormRules
})
const openFileFormRef = ref() // 表单 Ref
/** 打开弹窗 */
const open = async (type: string) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  // resetForm()

  formLoading.value = true
  try {
    const configStr = await FileConfigApi.getFileConfig()
    // 将JSON字符串转换为JavaScript对象
    const configObj = JSON.parse(configStr) as FileConfigApi.FileConfigVO
    // 现在可以安全地赋值给formData.value.config
    formData.value.config = configObj

    // 确保configObj.type存在且是预期的数值类型，以避免进一步的错误
    if (configObj.type !== null && configObj.type !== undefined) {
      if (configObj.type === 1) {
        storageType.value = 1
      } else if (configObj.type === 2) {
        storageType.value = 2
      } else if (configObj.type === 3) {
        storageType.value = 3
      } else if (configObj.type === 4) {
        storageType.value = 4
      }
    }
  } finally {
    formLoading.value = false
  }
}
defineExpose({open}) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!openFileFormRef) return
  const valid = await openFileFormRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value.config as unknown as FileConfigApi.FileConfigVO
    data.type = storageType.value
    const paramValue = JSON.stringify(data)
    await FileConfigApi.updateFileConfig(paramValue)
    message.success(t('common.updateSuccess'))
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
</script>
