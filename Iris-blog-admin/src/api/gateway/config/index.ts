import request from '@/config/axios'

export interface GatewayConfigVO {
  id: number
  name: string
  path: string
  fileName: string
  position: number
  createTime: Date
  updateTime: Date
}

// 网关配置列表
export const getGatewayConfigPage = async (data: PageParam) => {
  return await request.post({ url: '/system/gateway/config/list', data })
}

// 网关配置详情
export const getGatewayConfig = async (id: number) => {
  return await request.get({ url: '/system/gateway/config/info', params: { id } })
}

// 新增网关配置
export const createGatewayConfig = async (data: GatewayConfigVO) => {
  return await request.post({ url: '/system/gateway/config/save', data })
}

// 修改网关配置
export const updateGatewayConfig = async (data: GatewayConfigVO) => {
  return await request.post({ url: '/system/gateway/config/update', data })
}

// 删除网关配置
export const deleteGatewayConfig = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/gateway/config/delete', data })
}

// 导出网关配置
export const exportGatewayConfigExcel = async (data: any) => {
  return await request.download({ url: '/system/gateway/config/export', data })
}
