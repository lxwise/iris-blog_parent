<template>
	<div>
		<!-- 作者信息 -->
		<div class="author-container">
			<!-- 作者头像 -->
			<img class="author-avatar" :src="blog.blogInfo.authorAvatar"/>
			<!-- 作者名称 -->
			<p class="author-name">{{ blog.blogInfo.siteAuthor }}</p>
			<!-- 网站简介 -->
			<div class="site-desc desc">{{ blog.blogInfo.siteIntro }}</div>
		</div>
		<!-- 博客信息 -->
		<blog-info></blog-info>
		<!-- 社交 -->
		<social-list></social-list>
	</div>
</template>

<script setup lang="ts">
import {useBlogStore} from "@/store";

const blog = useBlogStore();

</script>

<style scoped>

.desc {
	font-size: 14px;
	color: var(--text-color);
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	width: 100%;
	text-align: center;
	background: radial-gradient(
		circle at 49.86% 48.37%,
		#0090ff 0,
		#0089ff 3.33%,
		#3a82ff 6.67%,
		#717aff 10%,
		#9371fb 13.33%,
		#ae67ef 16.67%,
		#c45de1 20%,
		#d652d2 23.33%,
		#e448c2 26.67%,
		#ef3eb0 30%,
		#f7369e 33.33%,
		#fd318c 36.67%,
		#ff317a 40%,
		#ff3569 43.33%,
		#fd3d57 46.67%,
		#f94646 50%,
		#f35035 53.33%,
		#ea5a22 56.67%,
		#e16308 60%,
		#d56d00 63.33%,
		#c97500 66.67%,
		#bb7d00 70%,
		#ac8300 73.33%,
		#9d8900 76.67%,
		#8c8f00 80%,
		#7a9300 83.33%,
		#669700 86.67%,
		#4f9b00 90%,
		#309e0e 93.33%,
		#00a029 96.67%,
		#00a23d 100%
	);
	-webkit-background-clip: text;
	background-clip: text;
	-webkit-text-fill-color: transparent;
}
</style>
