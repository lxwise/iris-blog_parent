import request from '@/config/axios'

/**
 * 获取博客信息
 */
export const getBlogInfo = async () => {
	return await request.get({url: '/v1/blog'})
}

/**
 * 上传访客信息
 */
export const report = async () => {
	return await request.get({url: '/v1/report'})
}

//获取天气数据
export const getWeatherInfo = (cityCode:string) => {
	return request.get({ url: '/system/common/get/weather', params: {cityCode} })
}
