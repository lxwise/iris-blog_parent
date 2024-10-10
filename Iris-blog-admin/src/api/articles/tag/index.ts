import request from '@/config/axios'

export interface ArticleTagVO {
  id: number
  name: string
  sort: number
  createTime: Date
  updateTime: Date
}

// 文章标签表列表
export const getArticleTagPage = async (data: PageParam) => {
  return await request.post({ url: '/system/article/tag/list', data })
}

// 文章标签表详情
export const getArticleTag = async (id: number) => {
  return await request.get({ url: '/system/article/tag/info', params: { id } })
}

// 新增文章标签表
export const createArticleTag = async (data: ArticleTagVO) => {
  return await request.post({ url: '/system/article/tag/save', data })
}

// 修改文章标签表
export const updateArticleTag = async (data: ArticleTagVO) => {
  return await request.post({ url: '/system/article/tag/update', data })
}

// 删除文章标签表
export const deleteArticleTag = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/article/tag/delete', data })
}

// 文章标签表状态修改
export const updateArticleTagStatus = async (id: number) => {
  return await request.get({ url: '/system/article/tag/update/status', params: { id: id } })
}
// 文章分类下拉
export const getArticleTagSelectList = async (name: string) => {
  return await request.get({ url: '/system/article/tag/select', params: { name: name } })
}

// 导出文章标签表
export const exportArticleTagExcel = async (data: any) => {
  return await request.download({ url: '/system/article/tag/export', data })
}
