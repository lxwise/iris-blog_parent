/**
 * 邮箱
 */
export interface EmailForm {
	/**
	 * 邮箱号
	 */
	email: string;
	/**
	 * 验证码
	 */
	code: string;
}

/**
 * 用户信息
 */
export interface UserInfo {
	/**
	 * 昵称
	 */
	nickname: string;
	/**
	 * 个人网站
	 */
	webSite: string;
	/**
	 * 个人简介
	 */
	info: string;
	/**
	 * 联系电话
	 */
	phone: string;
	/**
	 * 联系qq
	 */
	qq: string;
	/**
	 * 联系wx
	 */
	wx: string;
	/**
	 * 联系邮箱
	 */
	email: string;
	/**
	 * 性别
	 */
	sex: number;
}
