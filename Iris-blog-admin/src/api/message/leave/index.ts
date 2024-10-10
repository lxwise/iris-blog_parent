import request from '@/config/axios'

export interface LeaveMessageVO {
  id: number
  content: string
  nickname: string
  avatar: string
  ip: string
  ipAddress: string
  os: string
  status: number
  createTime: Date
  updateTime: Date
}

export interface UpdateLeaveStatusVO {
  status: number
  ids: number[]
}

// 用户留言表列表
export const getLeaveMessagePage = async (data: PageParam) => {
  return await request.post({ url: '/system/leave/message/list', data })
}

// 用户留言表详情
export const getLeaveMessage = async (id: number) => {
  return await request.get({ url: '/system/leave/message/info', params: { id } })
}

// 导出用户留言表
export const exportLeaveMessageExcel = async (data: any) => {
  return await request.download({ url: '/system/leave/message/export', data })
}

// 删除用户留言表
export const deleteLeaveMessage = async (data: number[]) => {
  return await request.post({ url: '/system/leave/message/delete', data })
}

// 用户留言表状态修改
export const updateLeaveMessageStatus = async (data: UpdateLeaveStatusVO) => {
  return await request.post({ url: '/system/leave/message/status', data })
}
