<template>
	<div class="menu">
		<div class="menu-item title">
			<router-link to="/" class="menu-btn">
				{{ blog.blogInfo.siteName }}
			</router-link>
		</div>
		<template v-for="menu of menuList" :key="menu.name">
			<div v-if="!menu.children" class="menu-item" :class="{ active: route.meta.title === menu.name }">
				<router-link :to="menu.path" class="menu-btn">
					<svg-icon :icon-class="menu.icon"></svg-icon>
					{{ menu.name }}
				</router-link>
			</div>
			<div v-else class="menu-item dropdown">
				<a class="menu-btn drop">
					<svg-icon :icon-class="menu.icon"></svg-icon>
					{{ menu.name }} </a>
				<ul class="submenu">
					<li class="subitem" v-for="submenu of menu.children" :key="submenu.name"
							:class="{ active: route.meta.title === submenu.name }">
						<template v-if="submenu.path === '/code'">
							<a class="link" href="https://gitee.com/lxwise" target="_blank">
								<svg-icon :icon-class="submenu.icon"></svg-icon>
								{{ submenu.name }}
							</a>
						</template>
						<template v-else>
							<router-link class="link" :to="submenu.path">
								<svg-icon :icon-class="submenu.icon"></svg-icon>
								{{ submenu.name }}
							</router-link>
						</template>
					</li>
				</ul>
			</div>
		</template>
	</div>
</template>

<script setup lang="ts">
import {useAppStore, useBlogStore, useUserStore} from "@/store";

const user = useUserStore();
const app = useAppStore();
const blog = useBlogStore();
const router = useRouter();
const route = useRoute();
const menuList = [
	{
		name: "首页",
		icon: "home",
		path: "/"
	},
	{
		name: "文章",
		icon: "article",
		children: [
			{
				name: "归档",
				icon: "archives",
				path: "/archive"
			},
			{
				name: "分类",
				icon: "category",
				path: "/category"
			},
			{
				name: "标签",
				icon: "tag",
				path: "/tag"
			},
		]
	},
	{
		name: "说说",
		icon: "talk",
		path: "/talk"
	},
	{
		name: "友链",
		icon: "friend",
		path: "/friend"
	},
	{
		name: "留言板",
		icon: "leave",
		path: "/message"
	},
	{
		name: "开源软件",
		icon: "plane",
		path: "/software"
	},
	{
		name: "关于",
		icon: "info",
		children: [
			{
				name: "关于本站",
				icon: "webinfo",
				path: "/about"
			},
			{
				name: "网站源码",
				icon: "code",
				path: "/code"
			},
			{
				name: "问题反馈",
				icon: "feedback",
				path: "/feedback"
			}
		]
	},
];
const logout = () => {
	if (route.path == "/user") {
		router.go(-1);
	}
	user.LogOut();
	window.$message?.success("退出成功");
};
</script>

<style lang="scss" scoped>
.user-avatar {
	display: inline-block;
	position: relative;
	top: 0.3rem;
	width: 24px;
	height: 24px;
	border-radius: 50%;
	cursor: pointer;
}

.menu {
	display: flex;
	align-items: center;
	height: 100%;
}

.menu-item {
	position: relative;
	display: inline-block;
	padding: 0 0.625rem;
	letter-spacing: 0.0625rem;
	font-size: 17px;
	text-align: center;

	&:not(.title) .menu-btn::before {
		content: "";
		position: absolute;
		width: 0;
		height: 0.1875rem;
		bottom: 0;
		border-radius: 0.125rem;
		left: 50%;
		transform: translateX(-50%);
		background-color: currentColor;
		transition: all 0.4s ease-in-out 0s;
	}

	&:hover .submenu {
		display: block;
	}
}

.menu-item.active:not(.dropdown) .menu-btn::before,
.menu-item:not(.dropdown):hover .menu-btn::before {
	width: 70%;
	color: #70B6FF;
}

.submenu {
	display: none;
	position: absolute;
	left: 7px;
	width: max-content;
	margin-top: 0.5rem;
	padding: 0;
	background: var(--grey-9-a5);
	box-shadow: 0 0.3125rem 1.25rem -0.25rem var(--grey-9-a1);
	border-radius: 0.625rem 0.625rem;
	animation: slideUpIn 0.3s;

	&::before {
		position: absolute;
		top: -1.25rem;
		left: 0;
		width: 100%;
		height: 2.5rem;
		content: "";
	}
}

.subitem {
	display: block;
	font-size: 1rem;

	&:first-child {
		border-radius: 0.625rem 0 0 0;
	}

	&:last-child {
		border-radius: 0 0 0.625rem 0;
	}

	.link {
		display: inline-block;
		padding: 0.3rem 0.7rem;
		width: 100%;
		text-shadow: none;
	}

	&:hover .link {
		transform: translateX(0.3rem);
	}
}

.submenu .subitem.active,
.submenu .subitem:hover {
	color: #70B6FF;
	//background-image: linear-gradient(to right, #B4D9FF, #70B6FF);
	//box-shadow: 0 0 0.75rem #B4D9FF;
}

.sub.menu .submenu {
	background-color: var(--grey-1);
}

.drop::after {
	content: "";
	display: inline-block;
	vertical-align: middle;
	border: 0.3rem solid transparent;
	border-top-color: currentColor;
	border-bottom: 0;
}

@media (max-width: 865px) {
	.menu {
		justify-content: center;
	}

	.menu .menu-item {
		display: none;
	}

	.menu .title {
		display: block;
	}
}
</style>
