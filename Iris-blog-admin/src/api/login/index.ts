import request from '@/config/axios'
import type { UserLoginVO } from './types'
// 登录
export const login = (data: UserLoginVO) => {
  return request.post({ url: '/system/login/login', data })
}

// 登出
export const loginOut = () => {
  return request.get({ url: '/system/login/logout' })
}
// 获取用户权限信息
export const getInfo = () => {
  return request.get({ url: '/system/permission/get-permission-info' })
}

// 社交快捷登录，使用 code 授权码
export function socialLogin(type: string, code: string, state: string) {
  return request.post({
    url: '/system/login/social-login',
    data: {
      type,
      code,
      state
    }
  })
}

// 社交授权的跳转
export const socialAuthRedirect = (type: number, redirectUri: string) => {
  return request.get({
    url: '/system/login/social-auth-redirect?type=' + type + '&redirectUri=' + redirectUri
  })
}
// 获取验证图片以及 token
export const getCode = (data) => {
  return request.postOriginal({ url: 'system/captcha/get', data })
}

// 滑动或者点选验证
export const reqCheck = (data) => {
  return request.postOriginal({ url: 'system/captcha/check', data })
}
