<template>
	<footer class="footer-wrapper" v-if="!isMessage">
		<p>
			© {{ formatDate(blog.blogInfo.createSiteTime, "YYYY") }} -
			{{ new Date().getFullYear() }} By
			{{ blog.blogInfo.siteAuthor }}
		</p>
		<a href="https://beian.miit.gov.cn/" target="_blank">
			{{ blog.blogInfo.recordNumber }}
		</a>
		<div class="footer-color mt-[5px] flex justify-center flex-wrap">
			<a
				class="p-[3px]"
				href="https://www.aliyun.com/?spm=5176.28055625.J_3207526240.1.6a27154aw7v5VK"
				target="_blank"
			>
				<img
					src="https://img.shields.io/badge/%E9%98%BF%E9%87%8C%E4%BA%91-ECS%E6%9C%8D%E5%8A%A1%E5%99%A8-orange"
					alt=""
				/></a>
			<a class="p-[3px]" href="https://imzbf.github.io/md-editor-v3/docs/index" target="_blank">
				<img
					src="https://img.shields.io/badge/MdEditorV3-MD%E7%BC%96%E8%BE%91%E5%99%A8-159957"
					alt=""
				/></a>
			<a class="p-[3px]" href="https://butterfly.js.org/" target="_blank">
				<img src="https://img.shields.io/badge/HEXO-BUTTERFLY-blue" alt=""/>
			</a>
			<a class="p-[3px]" href="https://min.io/" target="_blank">
				<img
					src="https://img.shields.io/badge/minio-%E5%AF%B9%E8%B1%A1%E5%AD%98%E5%82%A8%E7%B3%BB%E7%BB%9F-purple
"
					alt=""
				/>
			</a>
		</div>
	</footer>
</template>

<script setup lang="ts">
import {useBlogStore} from "@/store";
import {formatDate} from "@/utils/date";

const route = useRoute();
const blog = useBlogStore();
const isMessage = computed(() => route.path == "/message");
</script>

<style lang="scss" scoped>
.footer-wrapper {
	position: absolute;
	left: 0;
	right: 0;
	bottom: 0;
	width: 100%;
	height: 8rem;
	padding: 2.5rem 0;
	font-size: 0.975rem;
	text-align: center;
	//color: var(--grey-0);
	//background: var(--footer-bg);
	background-size: 300% 300%;
	-webkit-animation: gradientBG 10s ease infinite;
	animation: gradientBG 10s ease infinite;
}

@keyframes gradientBG {
	0% {
		background-position: 0 50%;
	}

	50% {
		background-position: 100% 50%;
	}

	100% {
		background-position: 0 50%;
	}
}
</style>
