<template>
	<header class="header-wrapper" :class="fixedClass">
		<!-- 切换按钮 -->
		<Toggle></Toggle>
		<!-- 菜单 -->
		<NavBar :class="{ sub: y > 0 }"></NavBar>
		<!-- 右侧按钮 -->
		<ul class="right">
			<li class="item">
				<!-- 搜索框icon -->
				<svg-icon v-if="isSmallScreen" style="cursor: pointer;" icon-class="search"
									@click="app.searchFlag = true"></svg-icon>
				<!-- 搜索框 -->
				<div class="search_ico" v-else>
					<div class="hand-style" @click="app.searchFlag = true">
						<svg-icon style="cursor: pointer;" icon-class="search"></svg-icon>
						文章搜索...
					</div>
				</div>
			</li>
			<li class="item">
				<div class="menu-item dropdown">
					<div><svg-icon :icon-class="noticeList.length >0 ? 'notice_msg' : 'notice'"size="1.115rem"></svg-icon></div>
					<ul class="submenu">
						<li class="subitem">
							<router-link to="/notice/1" class="link">
								<svg-icon icon-class="notice"></svg-icon>
								通知
							</router-link>
						</li>
						<li class="subitem">
							<router-link to="/notice/2" class="link">
								<svg-icon icon-class="talk"></svg-icon>
								评论
							</router-link>
						</li>
						<li class="subitem">
							<router-link to="/notice/3" class="link">
								<svg-icon icon-class="like"></svg-icon>
								点赞
							</router-link>
						</li>
					</ul>
				</div>
			</li>
			<li class="item">
				<div class="menu-item">
					<a v-if="!user.id" @click="app.loginFlag = true" class="menu-btn">
						<n-tooltip trigger="hover">
							<template #trigger>
								<div>
									<svg-icon icon-class="user"></svg-icon>
									登录
								</div>
							</template>
							<p>登录网站，获取以下权益</p>
							<div style="margin-top: 0.75rem;">
								<span>1.参与互动评论</span>
								<span>2.阅读优质文章</span>
							</div>
						</n-tooltip>
					</a>
					<template v-else>
						<img class="user-avatar drop" :src="user.avatar"/>
						<ul class="submenu">
							<li class="subitem" :class="{ active: route.meta.title === '个人中心' }">
								<router-link to="/user" class="link">
									<svg-icon icon-class="author"></svg-icon>
									个人中心
								</router-link>
							</li>
							<li class="subitem">
								<a class="link" @click="logout">
									<svg-icon icon-class="logout"></svg-icon>
									退出 </a>
							</li>
						</ul>
					</template>
				</div>
			</li>
		</ul>
	</header>
</template>

<script setup lang="ts">
import {useAppStore, useUserStore} from "@/store";
import {useDark, useScroll, useToggle} from "@vueuse/core";
const app = useAppStore();
const user = useUserStore();
const router = useRouter();
const route = useRoute();
const {y} = useScroll(window);
const isDark = useDark({
	selector: 'html',
	attribute: 'theme',
	valueDark: 'dark',
	valueLight: 'light',
})
const toggle = useToggle(isDark);
const fixedClass = ref("");
watch(y, (newValue, oldValue) => {
	if (newValue > 0) {
		if (newValue < oldValue) {
			fixedClass.value = "show up";
		} else {
			fixedClass.value = "show down";
		}
	} else {
		fixedClass.value = "";
	}
});

const isSmallScreen = ref(window.innerWidth <= 865);

const updateScreenSize = () => {
	isSmallScreen.value = window.innerWidth <= 865;
};

onMounted(() => {
	window.addEventListener('resize', updateScreenSize);
});

onUnmounted(() => {
	window.removeEventListener('resize', updateScreenSize);
});

const logout = () => {
	if (route.path == "/user") {
		router.go(-1);
	}
	user.LogOut();
	window.$message?.success("退出成功");
};

/**
 * 通知相关
 */
const noticeList = ref([{
		label: '系统通知',
		key: 1
	},
	{
		label: '评论',
		key: 2
	},
	{
		label: '点赞',
		key: 3
	}
]);

</script>

<style lang="scss" scoped>
.header-wrapper {
	position: fixed;
	display: flex;
	flex-wrap: nowrap;
	align-items: center;
	justify-content: space-between;
	width: 100%;
	height: 3.125rem;
	padding: 0 1rem;
	text-shadow: 0 0.2rem 0.3rem rgb(0 0 0 / 50%);
	color: var(--header-text-color);
	transition: all 0.2s ease-in-out 0s;
	z-index: 9;
}

.show {
	background: var(--nav-bg);
	box-shadow: 0.1rem 0.1rem 0.2rem var(--grey-9-a1);
	text-shadow: 0 0 0.625rem var(--grey-9-a1);
	color: var(--text-color);
}

.up {
	transform: translateY(0);
}

.down {
	transform: translateY(-100%);
}

.right {
	margin-right: 4rem;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	height: 100%;

	.item {
		padding: 0.625rem 0.5rem;
	}
}

@media (max-width: 991px) {
	.header-wrapper {
		padding: 0;
	}

}

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
	background: var(--grey-9-a1);
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

.search_ico {
	padding: 0;
	margin: 0;
	line-height: 60px;

	div {
		width: 180px;
		height: 35px;
		line-height: 35px;
		font-size: 15px;
		border-radius: 5px;
		color: #82848a;
		border: 2px solid transparent;

		&:hover,
		&:focus,
		&:active {
			border: 2px solid var(--color-blue);
		}

		.iconfont {
			font-weight: 700;
			margin: 0 5px;
		}

		span {
			background: var(--docsearch-key-gradient);
			border-radius: 3px;
			box-shadow: var(--docsearch-key-shadow);
			color: #909399;
			padding: 2px 2px;
			margin-left: 0.5rem;
		}
	}
}

</style>
