import request from '@/config/axios'
import {AppFeedBackDTO} from "@/api/freedback/types";

/**
 * 反馈列表
 * @returns 反馈列表
 */

export const getFeedBackList = async (data: PageParam) => {
	return await request.post({url: '/v1/feedback/list', data})
}

/**
 * 添加反馈
 * @returns 添加反馈
 */
export const saveFeedback = async (data: AppFeedBackDTO) => {
	return await request.post({url: '/v1/feedback/save', data})
}

