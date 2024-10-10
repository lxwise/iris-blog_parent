import request from '@/config/axios'

export interface CommentVO {
  id: number
  userId: number
  commentType: number
  typeId: number
  nickname: string
  content: string
  replyUserId: number
  replyNickname: string
  parentId: number
  ip: string
  ipAddress: string
  os: string
  status: number
  createTime: Date
  updateTime: Date
}
export interface UpdateCommentStatusVO {
  status: number
  ids: number[]
}

// 文章评论表列表
export const getCommentPage = async (data: PageParam) => {
  return await request.post({ url: 'system/comment/list', data })
}

// 文章评论表详情
export const getCommentInfo = async (id: number) => {
  return await request.get({ url: 'system/comment/info', params: { id } })
}

// 删除文章评论表
export const deleteComment = async (data: number[]) => {
  return await request.post({ url: '/system/comment/delete', data })
}

// 文章评论表状态修改
export const updateCommentStatus = async (data: UpdateCommentStatusVO) => {
  return await request.post({ url: '/system/comment/status', data })
}

// 导出文章评论表
export const exportArticleCommentExcel = async (data: any) => {
  return await request.download({ url: '/system/comment/export', data })
}

// 开启链接
export const sseConnect = async () => {
  return await request.get({ url: '/system/message/sse/connect' })
}
//关闭链接
export const sseClose = async () => {
  return await request.get({ url: '/system/message/sse/close' })
}
//关闭链接
export const sendMessage = async (msg: string) => {
  return await request.get({ url: '/system/message/sse/msg', params: { msg } })
}
