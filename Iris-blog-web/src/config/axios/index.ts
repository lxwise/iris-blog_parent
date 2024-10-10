import {service} from './service'

import {config} from './config'

const {default_headers} = config

const request = (option: any) => {
	const {url, method, params, data, headersType, responseType, ...config} = option
	return service({
		url: url,
		method,
		params,
		data,
		...config,
		responseType: responseType,
		headers: {
			'Content-Type': headersType || default_headers
		}
	})
}
export default {
	get: async <T = any>(option: any) => {
		const res = await request({method: 'GET', ...option})
		return res.data as unknown as T
	},
	post: async <T = any>(option: any) => {
		const res = await request({method: 'POST', ...option})
		return res.data as unknown as T
	},
	postOriginal: async (option: any) => {
		const res = await request({method: 'POST', ...option})
		return res
	},
	delete: async <T = any>(option: any) => {
		const res = await request({method: 'DELETE', ...option})
		return res.data as unknown as T
	},
	put: async <T = any>(option: any) => {
		const res = await request({method: 'PUT', ...option})
		return res.data as unknown as T
	},
	download: async <T = any>(option: any) => {
		// 确保option中包含了发送POST请求所需的data
		const postData = option.data || {} // 假设如果data未提供，则为空对象
		delete option.data // 从option中移除data，因为我们将在请求配置中直接使用

		const config = {
			method: 'POST',
			responseType: 'blob', // 保持响应类型为blob，以便处理文件下载
			headers: {
				// 如果有特定的header要求，比如Content-Type，请在这里设置
				// 'Content-Type': 'application/json'
			},
			...option, // 扩展其他可能存在的配置项，但已删除了data
			data: postData // 明确指定请求体数据
		}

		const res = await request(config)
		return res as unknown as Promise<T>
	},
	upload: async <T = any>(option: any) => {
		option.headersType = 'multipart/form-data'
		const res = await request({method: 'POST', ...option})
		return res as unknown as Promise<T>
	}
}
