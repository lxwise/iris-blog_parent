import axios, {AxiosError, AxiosInstance, AxiosRequestHeaders, AxiosResponse, InternalAxiosRequestConfig} from 'axios'

// @ts-ignore
import qs from 'qs'
import {config} from '@/config/axios/config'
import {getToken} from "@/utils/token"
import {useUserStore} from "@/store";

const {result_code, base_url, request_timeout} = config
// 创建axios实例
const service: AxiosInstance = axios.create({
	baseURL: base_url, // api 的 base_url
	timeout: request_timeout, // 请求超时时间
	withCredentials: false // 禁用 Cookie 等信息
})
// request拦截器
service.interceptors.request.use(
	(config: InternalAxiosRequestConfig) => {
		if (getToken()) {
			config.headers["Authorization"] = getToken();
		}
		const params = config.params || {}
		const data = config.data || false
		if (
			config.method?.toUpperCase() === 'POST' &&
			(config.headers as AxiosRequestHeaders)['Content-Type'] ===
			'application/x-www-form-urlencoded'
		) {
			config.data = qs.stringify(data)
		}
		// get参数编码
		if (config.method?.toUpperCase() === 'GET' && params) {
			config.params = {}
			const paramsStr = qs.stringify(params, {allowDots: true})
			if (paramsStr) {
				config.url = config.url + '?' + paramsStr
			}
		}
		return config
	},
	(error: AxiosError) => {
		console.log(error)
		Promise.reject(error)
	}
)

// response 拦截器
service.interceptors.response.use(
	async (response: AxiosResponse<any>) => {
		const {data} = response;

		// 如果没有返回数据，抛出明确的错误信息
		if (!data) {
			throw new Error("服务器没有返回数据");
		}

		// 确保 code 存在并设置默认值
		const code = data.code || result_code; // 如果没有定义 result_code，可使用默认的 500
		const msg = data.msg || "未知错误"; // 确保 msg 有默认值

		// 处理 401 未授权错误
		if (code === 401) {
			const user = useUserStore();
			user.forceLogOut();
			window.$message?.error(msg);
			return Promise.reject(new Error(msg)); // 返回带有错误信息的错误
		}

		// 如果 code 不是 200，抛出错误
		if (code !== 200) {
			window.$message?.error(msg);
			return Promise.reject(new Error(msg)); // 返回更详细的错误信息
		}

		// 正常情况下返回数据
		return data;
	},
	(error: AxiosError) => {
		console.log('Error:', error); // Debug 输出

		// 获取错误信息，提供默认值
		const message = error.message || "网络请求发生错误";
		window.$message?.error(message);

		// 返回整个错误对象以保留上下文
		return Promise.reject(error);
	}
);

export {service}
