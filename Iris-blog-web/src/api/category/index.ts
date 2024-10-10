import request from '@/config/axios'

/**
 * 查看文章分类
 * @returns 文章分类
 */
export const getCategoryList = async () => {
	return await request.get({url: '/v1/category/list'})
}

