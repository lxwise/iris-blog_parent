import request from '@/config/axios'
import {ApplyFriendLink} from "@/api/friend/types";

/**
 * 查看友链列表
 * @returns 查看友链列表
 */

export const getFriendList = async () => {
	return await request.get({url: '/v1/friend/link/list'})
}

/**
 * 申请友链
 */
export const applyFriendLink = async (data: ApplyFriendLink) => {
	return await request.post({url: '/v1/friend/link/apply', data})
}
