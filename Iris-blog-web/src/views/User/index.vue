<template>
	<div class="page-header">
		<h1 class="page-title">
			<div class="icon-and-text">
				<svg-icon icon-class="user" size="2rem"></svg-icon>
				个人中心
			</div>
		</h1>
		<img alt="" class="page-cover"
				 src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641">
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container">
			<div class="title">基本信息</div>
			<div class="info-container">
				<user-avatar class="avatar"></user-avatar>
				<div class="info mt-4">
					<n-form ref="formInstRef" :label-width="80" :model="userForm" :rules="rules" label-align="left">
						<n-form-item label="昵称" label-style="color: var(--text-color);" path="nickname">
							<n-input v-model:value="userForm.nickname" placeholder="输入您的昵称"/>
						</n-form-item>
						<n-form-item label="简介" label-style="color: var(--text-color);" path="info">
							<n-input v-model:value="userForm.info" placeholder="介绍一下自己吧"/>
						</n-form-item>
						<n-form-item label="联系电话" label-style="color: var(--text-color);" path="phone">
							<n-input v-model:value="userForm.phone" placeholder="输入您的联系电话"/>
						</n-form-item>
						<n-form-item label="联系QQ" label-style="color: var(--text-color);" path="qq">
							<n-input v-model:value="userForm.qq" placeholder="输入您的联系QQ"/>
						</n-form-item>
						<n-form-item label="联系微信" label-style="color: var(--text-color);" path="wx">
							<n-input v-model:value="userForm.wx" placeholder="输入您的联系微信"/>
						</n-form-item>
						<n-form-item label="性别" label-style="color: var(--text-color);" path="sex">
							<n-space>
								<n-radio-group v-model:value="userForm.sex" :default-value="userForm.sex">
									<n-radio :value="1">男</n-radio>
									<n-radio :value="0">女</n-radio>
								</n-radio-group>
							</n-space>
						</n-form-item>
						<n-form-item label="邮箱" label-style="color: var(--text-color);" path="email">
							<n-input-group>
								<n-input v-model:value="user.email" disabled placeholder="请输入邮箱"></n-input>
								<n-button v-if="user.email" color="#49b1f5" @click="app.emailFlag = true">
									修改邮箱
								</n-button>
								<n-button v-else color="#49b1f5" @click="app.emailFlag = true">
									绑定邮箱
								</n-button>
							</n-input-group>
						</n-form-item>
					</n-form>
					<n-button color="#3e999f" @click="openDialog">
						修改
					</n-button>
					<n-modal
						v-model:show="showModal"
						preset="dialog"
						type="warning"
						title="修改信息"
						content="确定要修改个人信息吗?"
						positive-text="确认"
						negative-text="取消"
						@positive-click="handleUpdate"
					/>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import {updateUserInfo} from "@/api/user";
import {UserInfo} from "@/api/user/types";
import {useAppStore, useUserStore} from "@/store";
import {FormInst} from 'naive-ui';
import {getUserInfo} from "@/api/login";

const user = useUserStore();
const app = useAppStore();
const formInstRef = ref<FormInst | null>(null)
const router = useRouter();
const rules = {
	nickname: {
		required: true,
		message: "昵称不能为空",
	},
	sex: {
		required: true,
		message: "性别不能为空",
	},
};
const userForm = ref<UserInfo>({
	nickname: '',
	info: '',
	webSite: '',
	phone: '',
	qq: '',
	wx: '',
	email: '',
	sex: 1,
});

/**打开模态框 */
const showModal = ref(false);
const openDialog = () => {
	showModal.value = true;
}


const handleUpdate = async () => {
	formInstRef.value?.validate(async (errors) => {
		if (!errors) {
			await updateUserInfo(userForm.value);
			window.$message?.success("修改成功");
		}
	})
};
onMounted(async () => {
	if (!user.id) {
		router.push("/");
	}
	userForm.value = await getUserInfo();
})
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.title {
	font-size: 1.25rem;
	font-weight: 700;
}

.info-container {
	@include flex;
	flex-wrap: wrap;
	margin-top: 1rem;

	.avatar {
		display: inline-flex;
		width: 230px;
		height: 140px;
	}

	.info {
		width: 530px;
	}
}

@media (max-width: 850px) {
	.avatar {
		justify-content: center;
	}
}
</style>
