/**
 * 网站配置
 */
export interface SiteConfig {
	/**
	 * 主键
	 */
	id: number;
	/**
	 * 用户头像
	 */
	userAvatar: string;
	/**
	 * 游客头像
	 */
	touristAvatar: string;
	/**
	 * 网站名称
	 */
	siteName: string;
	/**
	 * 网站地址
	 */
	siteAddress: string;
	/**
	 * 网站简介
	 */
	siteIntro: string;
	/**
	 * 网站公告
	 */
	siteNotice: string;
	/**
	 * 建站日期
	 */
	createSiteTime: Date;
	/**
	 * 备案号
	 */
	recordNumber: string;
	/**
	 * 作者头像
	 */
	authorAvatar: string;
	/**
	 * 网站作者
	 */
	siteAuthor: string;

	/**
	 * 文章默认封面
	 */
	articleCover: string;

	/**
	 * 关于我
	 */
	aboutMe: string;
	/**
	 * email
	 */
	email: string;
	/**
	 * Github
	 */
	github: string;
	/**
	 * Gitee
	 */
	gitee: string;
	/**
	 * QQ群
	 */
	qqGroup: string;
	/**
	 * QQ
	 */
	qq: string;
	/**
	 * 是否评论审核 (0否 1是)
	 */
	commentCheck: number;
	/**
	 * 是否留言审核 (0否 1是)
	 */
	messageCheck: number;
	/**
	 * 是否开启打赏 (0否 1是)
	 */
	isReward: number;
	/**
	 * 微信二维码
	 */
	wechatCode: string;
	/**
	 * 支付宝二维码
	 */
	alipayCode: string;
	/**
	 * 是否邮箱通知 (0否 1是)
	 */
	emailNotice: number;
	/**
	 * 社交列表
	 */
	socialList: string;
	/**
	 * 登录方式
	 */
	loginList: string;
	/**
	 * 是否开启音乐播放器 (0否 1是)
	 */
	isMusic: number;
	/**
	 * 网易云歌单id
	 */
	musicId: string;
}

/**
 * 博客信息
 */
export interface BlogInfo {
	/**
	 * 文章数量
	 */
	articleCount: number;
	/**
	 * 分类数量
	 */
	categoryCount: number;
	/**
	 * 标签数量
	 */
	tagCount: number;
	/**
	 * 网站访问量
	 */
	viewCount: number;
	/**
	 * 网站配置
	 */
	siteConfig: SiteConfig;
}


/**
 * 天气信息
 */
export interface WeatherVO {
	date: string;
	time: string;
	message: string;
	status: number;
	cityInfo:CityInfoVO;
	data: WeatherInfoVO;
}
export interface CityInfoVO {
	parent: string;
	city: string;
	citykey: string;
	updateTime: string;
}
export interface WeatherInfoVO {
	shidu: string;
	pm25: number;
	pm10: number;
	ganmao: string;
	wendu: string;
	quality: string;
	forecast:ForecastVO[]
	forecastDay:ForecastVO
}

export interface ForecastVO {
	date: string;
	ymd: string;
	high: string;
	sunrise: string;
	fx: string;
	week: string;
	low: string;
	fl: string;
	sunset: string;
	aqi: number;
	type: string;
	notice: string;
}

/**
 * IP地址信息
 */
export interface IPAddressVO {
	ipAddress: string;
	device: string;
	country: string;
	province: string;
	city: string;
	fullLocation: string;
	latitude: string;
	longitude: string;
}
