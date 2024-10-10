import {CommentForm} from "./types";

import request from '@/config/axios'

/**
 * 查看最新评论
 * @returns 最新评论
 */
export const getRecentComment = async () => {
	return await request.get({url: '/v1/comment/latest/list'})
}

/**
 * 添加评论
 */
export const addComment = async (data: CommentForm) => {
	return await request.post({url: '/v1/comment/comment/add', data})
}

/**
 * 查看评论列表
 * @returns 评论列表
 */
export const getCommentList = async (data: PageParam) => {
	return await request.post({url: '/v1/comment/comment/list', data})
}

/**
 * 查看回复评论
 * @returns 回复评论列表
 * @param data
 */
export const getReplyList = async (data: PageParam) => {
	return await request.post({url: '/v1/comment/comment/reply/list', data})
}

/**
 * 点赞评论
 * @param commentId 评论id
 */
export const likeComment = async (commentId: number) => {
	return await request.post({url: `/v1/comment/comment/${commentId}/like`})
}
