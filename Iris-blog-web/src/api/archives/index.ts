import request from '@/config/axios'

/**
 * 查看文章归档
 * @returns 文章归档
 * @param data
 */
export const getArchivesList = async (data: PageParam) => {
	return await request.post({url: '/v1/article/archives/list', data})
}
