import request from '@/config/axios'

export interface ArticleCategoryVO {
  id: number
  name: string
  sort: number
  icon: string
  createTime: Date
  updateTime: Date
  articleCount: number
}

// 文章分类表列表
export const getArticleCategoryPage = async (data: PageParam) => {
  return await request.post({ url: '/system/article/category/list', data })
}

// 文章分类表详情
export const getArticleCategory = async (id: number) => {
  return await request.get({ url: '/system/article/category/info', params: { id } })
}

// 新增文章分类表
export const createArticleCategory = async (data: ArticleCategoryVO) => {
  return await request.post({ url: '/system/article/category/save', data })
}

// 修改文章分类表
export const updateArticleCategory = async (data: ArticleCategoryVO) => {
  return await request.post({ url: '/system/article/category/update', data })
}

// 删除文章分类表
export const deleteArticleCategory = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/article/category/delete', data })
}

// 文章分类表状态修改
export const updateArticleCategoryStatus = async (id: number) => {
  return await request.get({ url: '/system/article/category/update/status', params: { id: id } })
}
// 文章分类下拉
export const getArticleCategorySelectList = async (name: string) => {
  return await request.get({ url: '/system/article/category/select', params: { name: name } })
}

// 导出文章分类表
export const exportArticleCategoryExcel = async (data: any) => {
  return await request.download({ url: '/system/article/category/export', data })
}
