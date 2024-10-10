import request from '@/config/axios'

export interface FriendLinkVO {
  id: number
  name: string
  url: string
  image: string
  info: string
  email: string
  status: number
  sort: number
  createTime: Date
  updateTime: Date
}

// 友情链接表列表
export const getFriendLinkPage = async (data: PageParam) => {
  return await request.post({ url: '/system/friend/link/list', data })
}

// 友情链接表详情
export const getFriendLink = async (id: number) => {
  return await request.get({ url: '/system/friend/link/info', params: { id } })
}

// 新增友情链接表
export const createFriendLink = async (data: FriendLinkVO) => {
  return await request.post({ url: '/system/friend/link/save', data })
}

// 修改友情链接表
export const updateFriendLink = async (data: FriendLinkVO) => {
  return await request.post({ url: '/system/friend/link/update', data })
}

// 删除友情链接表
export const deleteFriendLink = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/friend/link/delete', data })
}

// 友情链接表状态修改
export const updateFriendLinkStatus = async (id: number, status: number) => {
  return await request.get({
    url: '/system/friend/link/status',
    params: {
      id: id,
      status: status
    }
  })
}

// 置顶友链
export const topFriendLink = async (id: number) => {
  return await request.get({ url: '/system/friend/link/top', params: { id: id } })
}
// 导出友情链接表
export const exportFriendLinkExcel = async (data: any) => {
  return await request.download({ url: '/system/friend/link/export', data })
}
