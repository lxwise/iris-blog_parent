import request from '@/config/axios'

export interface ArticleVO {
  id: number
  authorId: number
  authorName: string
  categoryId: number
  categoryName: string
  tagNameList: string[]
  title: string
  coverImage: string
  intro: string
  content: string
  readType: number
  isTop: number
  status: number
  isForward: number
  forwardUrl: string
  isRecommend: number
  likes: number
  collection: number
  comment: number
  views: number
  createTime: Date
  updateTime: Date
  imageDetails: string[]
}

// 文章表列表
export const getArticlePage = async (data: PageParam) => {
  return await request.post({ url: '/system/article/list', data })
}

// 文章表详情
export const getArticle = async (id: number) => {
  return await request.get({ url: '/system/article/info', params: { id } })
}

// 新增文章表
export const createArticle = async (data: ArticleVO) => {
  return await request.post({ url: '/system/article/save', data })
}

// 修改文章表
export const updateArticle = async (data: ArticleVO) => {
  return await request.post({ url: '/system/article/update', data })
}

// 删除文章表
export const deleteArticle = async (id: number) => {
  const data: number[] = [id]
  return await request.post({ url: '/system/article/delete', data })
}

// 文章表状态修改
export const updateArticleStatus = async (id: number) => {
  return await request.get({ url: '/system/article/update/status', params: { id: id } })
}
// 文章置顶
export const topArticle = async (id: number) => {
  return await request.get({ url: '/system/article/top', params: { id: id } })
}

// 导出文章表
export const exportArticleExcel = async (id: number) => {
  const data: number[] = [id]
  return await request.download({ url: '/system/article/export', data })
}

//文章分类下拉
export const getSelectArticleCategoryList = async () => {
  return await request.get({ url: '/system/article/category/select' })
}
//文章标签下拉
export const getSelectArticleTagList = async () => {
  return await request.get({ url: '/system/article/tag/select' })
}
// 导入文章
export const importArticle = (data: any) => {
  return request.upload({ url: '/system/article/import', data })
}
