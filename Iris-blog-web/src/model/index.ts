/**
 * 结果返回接口
 */
export interface Result<T> {
	/**
	 * 返回状态
	 */
	flag: boolean;
	/**
	 * 状态码
	 */
	code: number;
	/**
	 * 返回信息
	 */
	msg: string;
	/**
	 * 返回数据
	 */
	data: T;
}

/**
 * 分页参数
 */
export interface PageQuery {
	/**
	 * 当前页
	 */
	pageSize?: number
	/**
	 * 每页大小
	 */
	pageNo?: number
	/**
	 * 请求参数
	 */
	action?: any
}

/**
 * 用户信息
 */
export interface UserForm {
	/**
	 * 用户名
	 */
	username: string;
	/**
	 * 密码
	 */
	password: string;
	/**
	 * 验证码
	 */
	code: string;
}
