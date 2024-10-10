import {getUserInfo, logout} from "@/api/login";
import {UserInfo} from "@/api/user/types";
import {removeToken} from "@/utils/token";

/**
 * 用户
 */
interface UserState {
	/**
	 * 用户id
	 */
	id?: number;
	/**
	 * 头像
	 */
	avatar: string;
	/**
	 * 昵称
	 */
	nickname: string;
	/**
	 * 用户名
	 */
	username: string;
	/**
	 * 邮箱
	 */
	email: string;
	/**
	 * 个人网站
	 */
	webSite: string;
	/**
	 * 个人简介
	 */
	info: string;
	/**
	 * 登录方式
	 */
	loginType?: number;
	/**
	 * 第三方登录之前的path
	 */
	path: string;
	/**
	 * 文章点赞集合
	 */
	articleLikeSet: number[];
	/**
	 * 评论点赞集合
	 */
	commentLikeSet: number[];
	/**
	 * 说说点赞集合
	 */
	talkLikeSet: number[];
}


export const useUserStore = defineStore("useUserStore", {
	state: (): UserState => ({
		id: undefined,
		avatar: "",
		nickname: "",
		email: "",
		username: "",
		webSite: "",
		info: "",
		loginType: undefined,
		path: "",
		articleLikeSet: [],
		commentLikeSet: [],
		talkLikeSet: [],
	}
	),
	actions: {
		// 使用 async/await 改写 GetUserInfo
		async GetUserInfo() {
			try {
				const data = await getUserInfo();
				if (data) {
					this.id = data.id;
					this.avatar = data.avatar;
					this.nickname = data.nickname;
					this.email = data.email;
					this.username = data.username;
					this.webSite = data.webSite;
					this.info = data.info;
					this.loginType = data.loginType;
					this.articleLikeSet = data.articleLikeSet;
					this.commentLikeSet = data.commentLikeSet;
					this.talkLikeSet = data.talkLikeSet;
				}
				return data;
			} catch (error) {
				console.error("获取用户信息失败：", error);
				throw error;
			}
		},

		// 使用 async/await 改写 LogOut
		async LogOut() {
			try {
				await logout();
				this.$reset();
				removeToken();
			} catch (error) {
				console.error("登出失败：", error);
				throw error;
			}
		},

		forceLogOut() {
			this.$reset();
			removeToken();
		},

		savePath(path: string) {
			this.path = path;
		},

		articleLike(articleId: number) {
			let index = this.articleLikeSet.indexOf(articleId);
			if (index != -1) {
				this.articleLikeSet.splice(index, 1);
			} else {
				this.articleLikeSet.push(articleId);
			}
		},

		commentLike(commentId: number) {
			let index = this.commentLikeSet.indexOf(commentId);
			if (index != -1) {
				this.commentLikeSet.splice(index, 1);
			} else {
				this.commentLikeSet.push(commentId);
			}
		},

		talkLike(talkId: number) {
			let index = this.talkLikeSet.indexOf(talkId);
			if (index != -1) {
				this.talkLikeSet.splice(index, 1);
			} else {
				this.talkLikeSet.push(talkId);
			}
		},

		updateUserInfo(user: UserInfo) {
			this.nickname = user.nickname;
			this.webSite = user.webSite;
			this.info = user.info;
		},
	},
	getters: {},
	persist: {
		key: "user",
		storage: sessionStorage,
	},
});
