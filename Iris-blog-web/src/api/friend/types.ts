/**
 * 友链
 */
export interface Friend {
	/**
	 * 友链id
	 */
	id: number;
	/**
	 * 友链名称
	 */
	name: string;
	/**
	 *友链头像
	 */
	image: string;
	/**
	 * 友链地址
	 */
	url: string;
	/**
	 * 友链介绍
	 */
	info: string;
}

/**
 * 申请友链
 */
export interface ApplyFriendLink {
	/**
	 * 友链名称
	 */
	name: string;
	/**
	 *友链头像
	 */
	image: string;
	/**
	 * 友链地址
	 */
	url: string;
	/**
	 * 友链介绍
	 */
	info: string;
	/**
	 * 邮箱
	 */
	email: string;
}
