<template>
	<Provider>
		<div class="app-wrapper">
			<Header></Header>
			<main class="main-wrapper">
				<router-view v-slot="{ Component, route }">
					<keep-alive>
						<component :is="Component" :key="route.path"/>
					</keep-alive>
				</router-view>
			</main>
			<Footer></Footer>
			<Tool></Tool>
			<Search></Search>
			<Login></Login>
			<Register></Register>
			<Forget></Forget>
			<Email></Email>
			<Drawer></Drawer>
		</div>
	</Provider>
</template>

<script setup lang="ts">
import {useAppStore, useUserStore} from "@/store";
import {getToken, setToken} from "@/utils/token";
const user = useUserStore();
const app = useAppStore();
const route = useRoute();
const router = useRouter();
onMounted(() => {
	document.addEventListener('copy', handleCopy);
	console.log(
		"%c Hello World %c By 鸢尾 %c",
		"background:#e9546b ; padding: 1px; border-radius: 3px 0 0 3px;  color: #fff; padding:5px 0;",
		"background:#ec8c69 ; padding: 1px; border-radius: 0 3px 3px 0;  color: #000; padding:5px 0;",
		"background:transparent"
	);
})
onUnmounted(() => {
	document.removeEventListener('copy', handleCopy);
});

//监听整个页面的 copy 事件
const handleCopy = (e: ClipboardEvent) => {
	const clipboardData = e.clipboardData || (window as any).clipboardData;
	if (!clipboardData) return;

	const text = window.getSelection()?.toString();
	if (text) {
		e.preventDefault();
		clipboardData.setData('text/plain', text);
		window.$message?.success('复制成功, 转载请务必保留原文链接!');
	}
};

(async () => {
	try {
		app.setLoginFlag(false);
		// 检查 URL 中是否有 token
		let token: string | undefined = '';
		const flag = window.location.href.indexOf("token") !== -1;
		if (flag) {
			token = window.location.href.split("token=")[1];
			// 如果有 token，则获取用户信息
			if (token) {
				setToken(token);
				await user.GetUserInfo();
				window.$message?.success("登录成功");
				app.setLoginFlag(false);
				await router.push('/');
			}
		}
	} catch (error) {
		console.error("登录失败：", error);
	}
})();


</script>

<style scoped>
.app-wrapper {
	position: relative;
	min-height: 100vh;

}

.main-wrapper {
	display: flex;
	flex-direction: column;
	width: 100%;
	padding: 0 0 8rem;
}
</style>
