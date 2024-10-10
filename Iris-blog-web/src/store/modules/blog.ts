import {BlogInfo, SiteConfig} from "@/api/blogInfo/types";

/**
 * 博客
 */
interface BlogState {
	/**
	 * 博客信息
	 */
	blogInfo: BlogInfo;
}

export const useBlogStore = defineStore("useBlogStore", {
	state: (): BlogState => ({
		blogInfo: {
			siteConfig: {} as SiteConfig,
		} as BlogInfo,
	}),
	actions: {
		setBlogInfo(blogInfo: BlogInfo) {
			this.blogInfo = blogInfo;
			// // 打印对象中的所有字段和值
			// 	for (const key in this.blogInfo) {
			// 		if (this.blogInfo.hasOwnProperty(key)) {
			// 			console.log(`Field: ${key}, Value: ${this.blogInfo[key]}`);
			// 		}
			// 	}
		},
	},
	getters: {},
	persist: {
		key: "blog",
		storage: sessionStorage,
	},
});
