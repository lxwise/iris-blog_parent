import {RouteRecordRaw} from "vue-router";

export const routes: RouteRecordRaw[] = [
	{
		path: "/",
		component: () => import("@/views/Home/index.vue"),
		meta: {
			title: "首页",
		},
	},
	{
		path: "/message",
		component: () => import("@/views/Message/index.vue"),
		meta: {
			title: "留言",
		},
	},
	{
		path: "/about",
		component: () => import("@/views/About/index.vue"),
		meta: {
			title: "关于",
		},
	},
	{
		name: "friend",
		path: "/friend",
		component: () => import("@/views/Friend/index.vue"),
		meta: {
			title: "友链",
		},
	},
	{
		path: "/archive",
		component: () => import("@/views/Archive/index.vue"),
		meta: {
			title: "归档",
		},
	},
	{
		path: "/user",
		component: () => import("@/views/User/index.vue"),
		meta: {
			title: "个人中心",
		},
	},
	{
		path: "/category",
		component: () => import("@/views/Category/index.vue"),
		meta: {
			title: "分类",
		},
	},
	{
		path: "/tag",
		component: () => import("@/views/Tag/index.vue"),
		meta: {
			title: "标签",
		},
	},
	{
		path: "/software",
		component: () => import("@/views/Software/index.vue"),
		meta: {
			title: "开源软件"
		}
	},
	{
		name: "talk",
		path: "/talk",
		component: () => import("@/views/Talk/TalkList.vue"),
		meta: {
			title: "说说",
		},
	},
	{
		name: "talkInfo",
		path: "/talk/:id",
		component: () => import("@/views/Talk/Talk.vue"),
		meta: {
			title: "说说",
		},
	},
	{
		name: "article",
		path: "/article/:id",
		component: () => import("@/views/Article/Article.vue"),
		meta: {
			title: "文章",
		},
	},
	{
		path: "/404",
		component: () => import("@/views/404/index.vue"),
		meta: {
			title: "404",
		},
	},
	{
		name: "notice",
		path: "/notice/:id",
		component: () => import("@/views/Notice/TalkList.vue"),
		meta: {
			title: "通知",
		},
	},
	{
		name: "feedback",
		path: "/feedback",
		component: () => import("@/views/Feedback/Feedback.vue"),
		meta: {
			title: "反馈",
		},
	},
	{path: "/:catchAll(.*)", redirect: "/404"},
];
