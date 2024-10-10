import request from '@/config/axios'

// 获取系统服务监控
export const getSystemServerInfo = () => {
  return request.get({ url: '/system/monitor/systemInfo' })
}

// 获取redis服务缓存监控
export const getSystemCacheInfo = () => {
  return request.get({ url: '/system/monitor/cacheInfo' })
}

// 获取java服务健康监控
export const getServiceInfo = () => {
  return request.get({ url: '/system/monitor/serviceInfo' })
}
