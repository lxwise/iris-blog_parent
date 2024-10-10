import * as FileApi from '@/api/system/file'
import { UploadRequestOptions } from 'element-plus/es/components/upload/src/upload'
export const useUpload = () => {
  // 后端上传地址
  const uploadUrl = import.meta.env.VITE_UPLOAD_URL
  // 重写ElUpload上传方法
  const httpRequest = async (options: UploadRequestOptions) => {
    // 模式二：后端上传
    // 重写 el-upload httpRequest 文件上传成功会走成功的钩子，失败走失败的钩子
    return new Promise((resolve, reject) => {
      FileApi.updateFile({ file: options.file })
        .then((res) => {
          if (res.code === 200) {
            resolve(res)
          } else {
            reject(res)
          }
        })
        .catch((res) => {
          reject(res)
        })
    })
  }
  return {
    uploadUrl,
    httpRequest
  }
}
