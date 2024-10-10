/**
 * 反馈
 */
export interface FeedBackVO {
	id: number;
	userId: number;
	title: string;
	content: string;
	imgUrl: string;
	status: boolean;
	backType: boolean;
	createTime: Date;
}


/**
 * 添加反馈
 */
export interface AppFeedBackDTO {
	title: string;
	content: string;
	imgUrl: any;
	backType: boolean;
}

