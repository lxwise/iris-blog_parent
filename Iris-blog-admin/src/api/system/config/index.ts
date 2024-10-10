import request from '@/config/axios'

export interface SystemConfigVO {
  paramType: boolean
  paramCode: string
  createTime: Date
  remark: string
  updateTime: Date
  id: number
  paramValue: string
  status: boolean
}

// 系统参数列表
export const getSysConfigPage = async (data: PageParam) => {
  return await request.post({ url: '/system/config/list', data })
}

// 系统参数详情
export const getSysConfig = async (id: number) => {
  return await request.get({ url: '/system/config/info', params: { id } })
}

// 新增系统参数
export const createSysConfig = async (data: SystemConfigVO) => {
  return await request.post({ url: '/system/config/save', data })
}

// 修改系统参数
export const updateSysConfig = async (data: SystemConfigVO) => {
  return await request.post({ url: '/system/config/update', data })
}

// 删除系统参数
export const deleteSysConfig = async (id: number) => {
  return await request.get({ url: '/system/config/delete', params: { id } })
}

// 系统参数状态修改
export const updateSysConfigStatus = (id: number) => {
  return request.get({
    url: '/system/config/update/status',
    params: {
      id: id
    }
  })
}

// 导出系统参数
export const exportSysConfig = (data: any) => {
  return request.download({ url: '/system/config/export', data })
}
