/**
 * 通知
 */
export interface NoticeVO {
	id: number;
	toUserId: number;
	toUserNickname: string;
	toUserAvatar: string;
	fromUserId: number;
	content: string;
	province: string;
	status: number;
	noticeType: number;
	noticeTypePath: string;
	typeId: number;
	createTime: Date;
}


/**
 * 未读通知数量
 */
export interface AppNoticeCountVO {
	/**
	 * 系统通知数量
	 */
	systemNoticeCount: number;
	/**
	 * 评论通知数量
	 */
	commentNoticeCount: number;
	/**
	 * 点赞通知数量
	 */
	likeNoticeCount: number;
}

