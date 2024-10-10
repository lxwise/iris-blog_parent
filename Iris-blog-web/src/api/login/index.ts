import {LoginForm} from "./types";
import request from '@/config/axios'
import {UserForm} from "@/model";

/**
 * 用户登录
 * @param data 登录信息
 * @returns Token
 */
export const login = async (data: LoginForm) => {
	return await request.post({url: '/oauth/login', data})
}

/**
 * 邮箱注册
 * @param data 注册信息
 */
export const register = async (data: UserForm) => {
	return await request.post({url: '/oauth/register', data})
}

/**
 * 获取登录用户信息
 * @returns 登录用户信息
 */
export const getUserInfo = async () => {
	return await request.get({url: '/v1/user/info'})
}

/**
 * 发送邮箱验证码
 * @param email
 */
export const getCode = async (email: string) => {
	return await request.get({url: '/oauth/code', params: {email}})
}

/**
 * 用户退出
 */
export const logout = async () => {
	return await request.get({url: '/oauth/logout'})
}

/**
 * 获取第三方登录地址
 * @param source
 */
export const openAuthUrl = async (source: string) => {
	return await request.get({
		url: `/oauth/render/${source}`, // 将 source 作为路径参数
	})
}
