<template>
	<div class="social-container">
		<template v-for="(item, index) in showSocialList" :key="index">
			<a class="social-item" v-if="isShowSocial(item.type)" target="_blank" :href="item.href" @click.prevent="handleClick(item.type, item.href)">
				<svg-icon :icon-class="item.type" size="1.4rem" :color="item.color"></svg-icon>
			</a>
		</template>
	</div>
</template>

<script setup lang="ts">
import { useBlogStore } from "@/store";
import { ref, computed } from "vue";

const blog = useBlogStore();
const socialList = blog.blogInfo.socialList;

const isShowSocial = computed(() => (social: string) => {
	if (socialList) {
		return socialList.includes(social);
	}
});

const showSocialList = [
	{
		type: "github",
		href: blog.blogInfo.github,
	},
	{
		type: "gitee",
		href: blog.blogInfo.gitee,
	},
	{
		type: "qqGroup",
		href: blog.blogInfo.qqGroup,
	},
	{
		type: "qq",
		href: 'http://wpa.qq.com/msgrd?v=3&uin=' + blog.blogInfo.qq + '&site=qq&menu=yes',
		color: "#00a1d6"
	}
];

const handleClick = (type: string, href: string) => {
	if (type === "qqGroup") {
		navigator.clipboard.writeText("836922638").then(() => {
			window.$message?.success('已复制到剪贴板')
		}).catch(() => {
			window.$message?.error('复制失败')
		})
	} else {
		// 其他链接正常跳转
		window.open(href, '_blank');
	}
};
</script>

<style scoped></style>
