<template>
	<div class="page-header">
		<h1 class="page-title">
			<div class="icon-and-text">
				<svg-icon icon-class="friend" size="2rem"></svg-icon>
				友情链接 ({{ friendList.length }})
			</div>
		</h1>
		<img class="page-cover" src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641" alt=""/>
		<!-- 波浪 -->
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container">
			<h2>
				<svg-icon class="flower" icon-class="sun" size="1.25rem"></svg-icon>
				小伙伴们
			</h2>
			<div class="friends">
				<div class="friend-item" v-animate="['slideUpBigIn']" v-for="friend in friendList" :key="friend.id">
					<a target="_blank" :href="friend.url">
						<img class="image" v-lazy="friend.image"/>
					</a>
					<div class="info">
						<a class="name" target="_blank" :href="friend.url" :style="{ 'color': getRandomColor() }">{{
								friend.name
							}}</a>
						<p class="desc">{{ friend.info }}</p>
					</div>
				</div>
			</div>
			<h2>
				<svg-icon class="flower" icon-class="sun" size="1.25rem"></svg-icon>
				本站信息
			</h2>
			<blockquote class="block">
				<p>名称：{{ blog.blogInfo.siteName }}</p>
				<p>简介：{{ blog.blogInfo.siteIntro }}</p>
				<p>头像：{{ blog.blogInfo.authorAvatar }}</p>
			</blockquote>
			<div style="display: flex; position: relative">
				<h2>
					<svg-icon class="flower" icon-class="sun" size="1.25rem"></svg-icon>
					申请方法
				</h2>
				<div class="btn-box" @click="openDialog">
					<svg-icon class="flower" icon-class="add" size="1.25rem"></svg-icon>
					加入友链
				</div>
			</div>
			<div class="welcome">需要交换友链的可在本页留言,或点击添加友链 (｡･∀･)ﾉﾞ</div>
			<blockquote class="block">
				<p>出于信息需要,大佬你的信息格式要包含：网站名称、网站链接、头像链接、网站介绍、名称颜色</p>
			</blockquote>
			<CommentList :comment-type="commentType"></CommentList>
			<n-modal
				class="dialog"
				type="info"
				:title="!showTips ? '申请友链' : '友情提示'"
				v-model:show="dialogFormVisible"
				preset="dialog"
				:mask-closable="false"
			>
				<div v-if="showTips">
					申请友链之前请确保贵站点已经添加了本站点的友链，否则申请之后会被删除！
				</div>

				<n-form v-else :model="form" :rules="rules" ref="formRef">
					<n-form-item
						label="网站名称"
						:label-width="formLabelWidth"
						path="name"
					>
						<n-input v-model:value="form.name" placeholder="请输入网站名称"></n-input>
					</n-form-item>
					<n-form-item
						label="网站简介"
						:label-width="formLabelWidth"
						path="info"
					>
						<n-input v-model:value="form.info" placeholder="请输入网站简介"></n-input>
					</n-form-item>
					<n-form-item label="网站地址" :label-width="formLabelWidth" path="url">
						<n-input v-model:value="form.url" placeholder="请输入网站地址"></n-input>
					</n-form-item>
					<n-form-item label="网站头像" :label-width="formLabelWidth" path="image">
						<n-input v-model:value="form.image" placeholder="请输入网站头像"></n-input>
					</n-form-item>
					<n-form-item label="邮箱地址" :label-width="formLabelWidth" path="email">
						<n-input v-model:value="form.email" placeholder="请输入邮箱地址"></n-input>
					</n-form-item>
				</n-form>
				<template #action>
					<div class="dialog-footer">
						<n-button @click="dialogFormVisible = false">取 消</n-button>
						<n-button style="margin-left: 0.5rem;" v-if="showTips" type="info" @click="showTips = false">确 定
						</n-button>
						<n-button style="margin-left: 0.5rem;" v-else type="info" @click="handleAddLink">确 定</n-button>
					</div>
				</template>
			</n-modal>
		</div>
	</div>
</template>

<script setup lang="ts">
import {applyFriendLink, getFriendList} from "@/api/friend";
import {Friend} from "@/api/friend/types";
import {useBlogStore} from "@/store";
import {NButton, NForm, NFormItem, NInput} from 'naive-ui';

const blog = useBlogStore();
const commentType = ref(3);
const friendList = ref<Friend[]>([]);
onMounted(async () => {
	friendList.value = await getFriendList();
});

const getRandomColor = () => {
	return "rgb(" + Math.round(Math.random() * 255) + "," + Math.round(Math.random() * 255) + "," + Math.round(Math.random() * 255) + ")";
};

const dialogFormVisible = ref(false);
const showTips = ref(false);
const form = ref({
	name: '',
	info: '',
	url: '',
	image: '',
	email: ''
});

const formLabelWidth = ref('80px');
const rules = ref({
	name: [{required: true, message: '请输入网站名称', trigger: 'blur'}],
	info: [{required: true, message: '请输入网站简介', trigger: 'blur'}],
	url: [{required: true, message: '请输入网站地址', trigger: 'blur'}],
	image: [{required: true, message: '请输入网站头像', trigger: 'blur'}],
	email: [{required: true, message: '请输入邮箱地址', trigger: 'blur'}],
});
const formRef = ref();

/**打开表单 */
const openDialog = () => {
	form.value = {};
	showTips.value = true;
	dialogFormVisible.value = true;
}

/**提交 */
const handleAddLink = async () => {
	// 校验表单
	if (!formRef) return
	const valid = await formRef.value.validate()
	if (!valid) return

	try {
		await applyFriendLink(form.value);
		dialogFormVisible.value = false;
		window.$message?.success('提交成功，请等待审核');
	} catch (e) {
		console.log("提交失败:" + form.value)
		window.$message?.error('提交失败,请重试');
	}

}

</script>

<style lang="scss" scoped>
.dialog-footer {
	display: flex;
	justify-content: flex-end;
}

.block {
	line-height: 2;
	margin: 0 1.5rem;
	font-size: 15px;
	border-left: 0.2rem solid var(--color-blue);
	padding: 0.625rem 1rem;
	color: var(--grey-5);
	background: var(--color-theme-bg);
	border-radius: 4px;
	word-wrap: break-word;
}

.welcome {
	position: relative;
	margin: 0 2.5rem 0.5rem;

	&::before {
		content: "";
		position: absolute;
		width: 0.4em;
		height: 0.4em;
		background: var(--primary-color);
		border-radius: 50%;
		top: 0.85em;
		left: -1em;
	}
}

.friends {
	display: flex;
	flex-wrap: wrap;
	font-size: 0.9rem;
}

.friend-item {
	display: inline-flex;
	align-items: center;
	width: calc(50% - 2rem);
	margin: 1rem;
	padding: 0.5rem 1rem;
	line-height: 1.5;
	border-radius: 0.5rem;
	border: 0.0625rem solid var(--grey-2);
	box-shadow: 0 0.625rem 1.875rem -0.9375rem var(--box-bg-shadow);
	transition: all 0.2s ease-in-out 0s;
	animation-duration: 0.5s;
	visibility: hidden;
	background-color: #dfeef5;
	//&:hover {
	//  box-shadow: 0 0 2rem var(--box-bg-shadow);
	//}
	&:hover {
		transform: scale(1.1);
	}

	.image {
		display: block;
		width: 4rem;
		height: 4rem;
		border-radius: 0.9375rem;
	}

	.info {
		padding-left: 1rem;
	}

	.name {
		font-weight: 700;
	}

	.desc {
		font-size: 0.85em;
		margin: 0.5rem 0;
	}
}

.flower {
	animation: rotate 6s linear infinite;
}

.btn-box {
	margin-left: 10px;
	color: var(--color-blue);
	font-size: 16px;
	margin-top: 20px;
	position: absolute;
	right: 0;
	line-height: 24px;
}

@keyframes rotate {
	0% {
		transform: rotate(0);
	}

	100% {
		transform: rotate(360deg);
	}
}

@media (max-width: 767px) {
	.friend-item {
		width: calc(100% - 2rem);
	}
}
</style>
