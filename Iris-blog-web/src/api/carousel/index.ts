import request from '@/config/axios'

/**
 * 获取轮播图列表
 * @returns 轮播图列表
 */
export const getCarouselList = async () => {
	return await request.get({url: '/v1/carousel/list'})
}
