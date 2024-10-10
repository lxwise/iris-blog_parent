import request from '@/config/axios'

// 文件预签名地址 Response VO
export interface FileRespVO {
  id: number
  name: string
  url: string
  filePath: string
  type: string
  path: string
  createTime: Date
  size: number
  configType: number
}

export interface FileConfigVO {
  qiniuDomain: string
  aliyunDomain: string
  type: number
  directoryList: string
  qiniuAccessKey: string
  qcloudRegion: string
  qiniuBucketName: string
  aliyunAccessKeyId: string
  qcloudBucketName: string
  qcloudSecretKey: string
  aliyunEndPoint: string
  localPath: string
  localDomain: string
  qcloudAppId: number
  aliyunAccessKeySecret: string
  qcloudSecretId: string
  qcloudDomain: string
  aliyunBucketName: string
  qiniuSecretKey: string
  minioEndPoint: string
  minioAccessKey: string
  minioSecretKey: string
  minioBucketName: string
}

// 查询文件列表
export const getFilePage = (data: PageParam) => {
  return request.post({ url: '/oss/file/list', data })
}

// 删除文件
export const deleteFile = (id: number) => {
  // const data:number[] = [id]
  return request.get({ url: '/oss/file/delete', params: { id } })
}

// 获取云存储配置
export const getFileConfig = () => {
  const paramCode: string = 'SYS_OSS_CONFIG_KEY'
  return request.get({ url: '/system/config/get/code/value', params: { paramCode } })
}

// 添加云存储配置
export const updateFileConfig = async (paramValue: string) => {
  const paramCode: string = 'SYS_OSS_CONFIG_KEY'
  return await request.get({
    url: '/system/config/update/code/value',
    params: { paramCode, paramValue }
  })
}
// 上传文件
export const updateFile = (data: any) => {
  return request.upload({ url: '/oss/file/upload', data })
}
