<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        v-loading="formLoading"
    >
      <el-form-item label="轮播图" prop="imgUrl">
        <el-upload
            drag
            :show-file-list="false"
            :action="uploadUrl"
            :http-request="httpRequest"
            accept="image/*"
            :before-upload="beforeUpload"
            :on-success="handleSuccess"
        >
          <img v-if="formData.imgUrl && formData.imgUrl !== ''" :src="formData.imgUrl" style="width: 100%; height: 100%; object-fit: contain;"/>
          <el-icon v-else class="el-icon--upload" style="width: 180px; height: 80px;">
            <upload-filled style="width: 100%; height: 100%;"/>
          </el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item label="是否显示" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio key="true" :label="true">是</el-radio>
          <el-radio key="false" :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import * as CarouselApi from '@/api/website/carousel'
import {useUpload} from '@/components/UploadFile/src/useUpload'
import {UploadRawFile, FormInstance} from 'element-plus'
import * as imageConversion from 'image-conversion'
import {AxiosResponse} from 'axios'
import {UploadFilled} from '@element-plus/icons-vue'

const {uploadUrl, httpRequest} = useUpload()
/** 首页轮播表单 */
defineOptions({name: 'CarouselForm'})
const {t} = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  imgUrl: undefined,
  status: false
})
const formRules = reactive({
  imgUrl: [{required: true, message: '轮播图地址+不能为空', trigger: 'blur'}],
  status: [{required: true, message: '是否显示 (0否 1是)+不能为空', trigger: 'blur'}]
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
      formData.value = await CarouselApi.getCarousel(id)
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
    const data = formData.value as unknown as CarouselApi.CarouselVO
    if (formType.value === 'create') {
      await CarouselApi.createCarousel(data)
      message.success(t('common.createSuccess'))
    } else {
      await CarouselApi.updateCarousel(data)
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
    imgUrl: undefined,
    status: undefined
  }
  formRef.value?.resetFields()
}


/***************图片上传相关方法***************************/
const handleSuccess = (response: AxiosResponse) => {
  formData.value.imgUrl = response.data.url
}
const beforeUpload = (rawFile: UploadRawFile) => {
  return new Promise((resolve) => {
    if (rawFile.size / 1024 < 200) {
      resolve(rawFile)
    }
    // 压缩到200KB,这里的200就是要压缩的大小,可自定义
    imageConversion.compressAccurately(rawFile, 200).then((res) => {
      resolve(res)
    })
  })
}
</script>
