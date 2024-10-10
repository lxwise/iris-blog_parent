import request from '@/config/axios'
export interface OperateLogVO {
  logType: boolean
  requestType: string
  requestMethod: string
  errorMessage: string
  requestParams: string
  errorInfo: string
  requestIp: string
  updateTime: Date
  userName: string
  userId: string
  requestTime: number
  requestAddress: string
  operateOs: string
  createTime: Date
  requestUrl: string
  requestClassName: string
  operateDesc: string
  id: string
}

// 查询操作日志列表
export const getOperateLogPage = (data: PageParam) => {
  return request.post({ url: '/system/log/list', data })
}
