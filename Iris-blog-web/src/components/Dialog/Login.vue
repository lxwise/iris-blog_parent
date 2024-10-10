<template>
	<n-modal class="bg" v-model:show="dialogVisible" preset="dialog" :show-icon="false" transform-origin="center"
					 :block-scroll="false">
		<n-input class="mt-11" v-model:value="loginForm.username" placeholder="邮箱号" @keyup.enter="handlelogin"></n-input>
		<n-input-group class="mt-11">
			<n-input placeholder="验证码" v-model:value="loginForm.captchaCode"></n-input>
			<n-button color="#49b1f5" :disabled="flag" @click="sendCode">
				{{ timer == 0 ? '发送' : `${timer}s` }}
			</n-button>
		</n-input-group>
		<n-input class="mt-11" v-model:value="loginForm.password" type="password" show-password-on="click"
						 placeholder="密码"
						 @keyup.enter="handlelogin"></n-input>
		<n-button class="mt-11" color="#70B6FF" style="width: 100%" :loading="loading" @click="handlelogin">
			登 录
		</n-button>
		<div class="mt-10 login-tip">
			<span class="colorFlag" @click="handleRegister">立即注册</span>
			<span class="colorFlag" @click="handleForget">忘记密码?</span>
		</div>
		<div>
			<div class="social-login-title">社交账号登录</div>
			<div class="social-login-wrapper">
				<svg-icon class="icon" icon-class="qq" size="2rem" v-if="showLogin('qq')"
									@click="openAuthLogin('qq')"></svg-icon>
				<svg-icon class="icon" icon-class="gitee" size="2rem" v-if="showLogin('gitee')"
									@click="openAuthLogin('gitee')"></svg-icon>
				<svg-icon class="icon" icon-class="github" size="2rem" v-if="showLogin('github')"
									@click="openAuthLogin('github')"></svg-icon>
				<svg-icon class="icon" icon-class="weibo" size="2rem" v-if="showLogin('weibo')"
									@click="openAuthLogin('weibo')"></svg-icon>
			</div>
		</div>
	</n-modal>
</template>

<script setup lang="ts">
import {getCode, login, openAuthUrl} from "@/api/login";
import {LoginForm} from "@/api/login/types";
import {useAppStore, useBlogStore, useUserStore} from "@/store";
import {setToken} from "@/utils/token";
import {useIntervalFn} from "@vueuse/core";

const app = useAppStore();
const user = useUserStore();
const blog = useBlogStore();
const route = useRoute();
const loading = ref(false);
const flag = ref(false);
const timer = ref(0);
const loginForm = ref<LoginForm>({
	username: "",
	password: "",
	captchaCode: ""
});

//停止定时器
const {pause, resume} = useIntervalFn(() => {
	timer.value--;
	if (timer.value <= 0) {
		// 停止定时器
		pause();
		flag.value = false;
	}
}, 1000, {immediate: false});

// 开始定时器
const start = (time: number) => {
	flag.value = true;
	timer.value = time;
	// 启动定时器
	resume();
};

// 发送验证码
const sendCode = async () => {
	let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	if (!reg.test(loginForm.value.username)) {
		window.$message?.warning("邮箱格式不正确");
		return;
	}
	start(60);
	try {
		await getCode(loginForm.value.username);
		window.$message?.success("发送成功");
	} finally {
	}

};

const dialogVisible = computed({
	get: () => app.loginFlag,
	set: (value) => (app.loginFlag = value),
});
const showLogin = computed(
	() => (type: string) => blog.blogInfo.loginList.includes(type)
);
const handleRegister = () => {
	app.setLoginFlag(false);
	app.setRegisterFlag(true);
};
const handleForget = () => {
	app.setLoginFlag(false);
	app.setForgetFlag(true);
};
const handlelogin = async () => {
	let reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;

	if (loginForm.value.captchaCode.trim().length != 6) {
		window.$message?.warning("请输入6位验证码");
		return;
	}
	if (!reg.test(loginForm.value.username)) {
		window.$message?.warning("邮箱格式不正确");
		return;
	}

	if (loginForm.value.password.trim().length == 0) {
		window.$message?.warning("密码不能为空");
		return;
	}

	loading.value = true;

	try {
		const data = await login(loginForm.value);
		if (data) {
			setToken(data);
			await user.GetUserInfo();
			window.$message?.success("登录成功");

			loginForm.value = {
				username: "",
				password: "",
				captchaCode: "",
			};

			app.setLoginFlag(false);
		}
	} catch (error) {
		console.error("登录失败：", error);
	} finally {
		loading.value = false;
	}
};

const openAuthLogin = async (source: string) => {
	//保留当前路径
	// this.settingUrl();
	const res = await openAuthUrl(source);
	window.open(res, "_self");
}
</script>

<style scoped>
.login-tip {
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.social-login-title {
	margin-top: 1rem;
	color: #b5b5b5;
	font-size: 0.75rem;
	text-align: center;
}

.social-login-title::before {
	content: "";
	display: inline-block;
	background-color: #d8d8d8;
	width: 60px;
	height: 1px;
	margin: 0 12px;
	vertical-align: middle;
}

.social-login-title::after {
	content: "";
	display: inline-block;
	background-color: #d8d8d8;
	width: 60px;
	height: 1px;
	margin: 0 12px;
	vertical-align: middle;
}

.social-login-wrapper {
	text-align: center;
	margin-top: 1.4rem;
}

.icon {
	margin: 0 0.3rem;
}
</style>
