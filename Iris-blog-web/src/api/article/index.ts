import request from '@/config/axios'

/**
 * 查看文章列表
 * @returns 文章列表
 * @param data
 */
export const getArticleList = async (data: PageParam) => {
	return await request.post({url: '/v1/article/list', data})
}

/**
 * 查看文章
 * @param articleId 文章id
 */
export const getArticle = async (articleId: number) => {
	return await request.get({
		url: `/v1/article/article/${articleId}`, // 将 articleId 作为路径参数
	})
}

/**
 * 查看推荐文章
 * @returns 推荐文章
 */
export const getArticleRecommend = async () => {
	return await request.get({url: '/v1/article/recommend'})
}

/**
 * 搜索文章
 * @returns 文章列表
 */
export const searchArticle = async (data: any) => {
	return await request.post({url: '/v1/article/article/search', data})
}

/**
 * 点赞文章
 * @param articleId 文章id
 */
export const likeArticle = async (articleId: any) => {
	return await request.post({url: `/v1/article/article/${articleId}/like`})
}
