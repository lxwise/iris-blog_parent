import request from '@/config/axios'

/**
 * 查看文章标签
 * @returns 文章标签
 */

export const getTagList = async () => {
	return await request.get({url: '/v1/tag/list'})
}
