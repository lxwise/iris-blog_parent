declare namespace App {
	namespace Service {

		interface ServiceConfig {
			/** 后端服务基 URL */
			baseURL: string;
			/** 后端服务基 URL 的代理模式 */
			proxyPattern: string;
		}
	}
}
