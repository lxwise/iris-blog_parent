import request from '@/config/axios'
import {MessageForm} from "@/api/message/types";

/**
 * 查看留言列表
 * @returns 文章分类
 */
export const getMessageList = async () => {
	return await request.get({url: '/v1/message/list'})
}

/**
 * 添加留言
 * @param data 留言
 */
export const addMessage = async (data: MessageForm) => {
	return await request.post({url: '/v1/message/add', data})
}
