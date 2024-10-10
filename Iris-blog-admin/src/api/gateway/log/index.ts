import request from '@/config/axios'

export interface GatewayLogVO {
  id: number
  ip: string
  requestEndTime: Date
  statusCode: number
  requestMethod: string
  serverProtocol: string
  serverPort: string
  uri: string
  args: string
  bytesSent: number
  requestTime: number
  httpUserAgent: string
  latitude: string
  longitude: string
  createTime: Date
  updateTime: Date
}

// 网关日志列表
export const getGatewayLogPage = async (data: PageParam) => {
  return await request.post({ url: '/system/gateway/log/list', data })
}

// 网关日志详情
export const getGatewayLog = async (id: number) => {
  return await request.get({ url: '/system/gateway/log/info', params: { id } })
}

// 新增网关日志
export const createGatewayLog = async (data: GatewayLogVO) => {
  return await request.post({ url: '/system/gateway/log/save', data })
}

// 删除网关日志
export const deleteGatewayLog = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/gateway/log/delete', data })
}

// 导出网关日志
export const exportGatewayLogExcel = async (data: any) => {
  return await request.download({ url: '/system/gateway/log/export', data })
}

// 网关访问IP
export const selectGatewayIPList = async () => {
  return await request.get({ url: '/system/gateway/log/ip'})
}
