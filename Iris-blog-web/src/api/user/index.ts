import {UserForm} from "@/model";
import {EmailForm, UserInfo} from "./types";
import request from "@/config/axios";

/**
 * 修改用户密码
 * @param data 用户密码
 */
export const updateUserPassword = async (data: UserForm) => {
	return await request.post({url: '/v1/user/user/password', data})
}

/**
 * 修改用户头像
 * @param data 头像
 */
export const updateUserAvatar = async (data: FormData) => {
	return await request.upload({url: '/v1/user/user/avatar', data})
}

/**
 * 修改用户邮箱
 * @param data 用户邮箱
 */
export const updateUserEmail = async (data: EmailForm) => {
	return await request.post({url: '/v1/user/user/email', data})
}

/**
 * 修改用户信息
 * @param data 用户信息
 */
export const updateUserInfo = async (data: UserInfo) => {
	return await request.post({url: '/v1/user/user/info', data})
}
