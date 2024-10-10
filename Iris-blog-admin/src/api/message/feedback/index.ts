import request from '@/config/axios'

export interface FeedBackVO {
  id: number
  userId: number
  title: string
  content: string
  imgUrl: string
  backType: boolean
  status: boolean
  ip: string
  ipAddress: string
  os: string
  createTime: Date
  updateTime: Date
}

// 用户反馈表列表
export const getFeedBackPage = async (data: PageParam) => {
  return await request.post({ url: '/system/feed/back/list', data })
}

// 用户反馈表详情
export const getFeedBack = async (id: number) => {
  return await request.get({ url: '/system/feed/back/info', params: { id } })
}

// 删除用户反馈表
export const deleteFeedBack = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/feed/back/delete', data })
}

// 用户反馈表状态修改
export const updateFeedBackStatus = async (id: number) => {
  return await request.get({ url: '/system/feed/back/status', params: { id: id } })
}

// 导出用户反馈表
export const exportFeedBackExcel = async (data: any) => {
  return await request.download({ url: '/system/feed/back/export', data })
}
