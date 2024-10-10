import request from '@/config/axios'
import {AppNoticeCountVO} from "@/api/notice/types";

/**
 * 查看通知
 * @returns 查看通知
 */

export const getNoticeList = async (data: PageParam) => {
	return await request.post({url: '/v1/notice/list', data})
}
/**
 * 清空通知
 * @param type
 */
export const clearAllNotice = async (type: string|number) => {
	return await request.get({url: '/v1/notice/clear',params: {type}})
}

/**
 * 未读消息数
 */
export const readAllNotice = async () : Promise<AppNoticeCountVO>	 => {
	return await request.get({url: '/v1/notice/read'})
}
