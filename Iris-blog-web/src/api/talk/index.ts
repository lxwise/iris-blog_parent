import request from '@/config/axios'

/**
 * 查看说说列表
 * @returns 说说列表
 * @param data
 */
export const getTalkList = async (data: PageParam) => {
	return await request.post({url: '/v1/talk/talk/list', data})
}
/**
 * 查看首页说说
 * @returns 首页说说
 */
export const getTalkHomeList = async () => {
	return await request.get({url: '/v1/talk/home/talk'})
}

/**
 * 查看说说
 * @returns 说说
 */
export const getTalk = async (talkId: number) => {
	return await request.get({url: `/v1/talk/talk/${talkId}`})
}
/**
 * 点赞说说
 * @param talkId 说说id
 */
export const likeTalk = async (talkId: number) => {
	return await request.post({url: `/v1/talk/talk/${talkId}/like`})
}
