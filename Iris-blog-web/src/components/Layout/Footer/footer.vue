<template>
	<!-- 最外层 div 是占位置的 -->
	<div
		v-if="route.path !== '/message/chat'"
		class="footer_box flex flex-col items-center justify-center"
	>
		<!-- eslint-disable-next-line -->
		<div class="footer-color">&copy 鸢尾的博客 2022</div>
		<a class="footer-color change-color" href="http://beian.miit.gov.cn/" target="_blank">
			<img src="https://admin.lstar.icu:9090/file/image/20240930/beian.png" alt="备案图标" class="beian-icon">
			蜀ICP备2023007772号
		</a>
		<div class="footer-color" v-for="(item, index) in webInfo" :key="index">
			<div class="web-name">{{ item.name }} {{ item.count }}</div>
		</div>
		<div class="footer-color mt-[5px] flex justify-center flex-wrap">
			<a
				class="p-[3px]"
				href="https://cloud.tencent.com/product/cvm?cps_key=75ae4f3c1e4e051990d375abcf1bc4d5"
				target="_blank"
			>
				<img
					src="https://img.shields.io/badge/%E8%85%BE%E8%AE%AF%E4%BA%91-CVM%E6%9C%8D%E5%8A%A1%E5%99%A8-blue"
					alt=""
				/></a>
			<a class="p-[3px]" href="https://imzbf.github.io/md-editor-v3/docs/index" target="_blank">
				<img
					src="https://img.shields.io/badge/MdEditorV3-MD%E7%BC%96%E8%BE%91%E5%99%A8-159957"
					alt=""
				/></a>
			<a class="p-[3px]" href="https://spring.io/projects/spring-boot" target="_blank">
				<img src="https://img.shields.io/badge/springboot-2.7-brightgreen" alt=""/>
			</a>
			<a class="p-[3px]" href="https://min.io/" target="_blank">
				<img
					src="https://img.shields.io/badge/minio-%E5%AF%B9%E8%B1%A1%E5%AD%98%E5%82%A8%E7%B3%BB%E7%BB%9F-purple" alt=""
				/>
			</a>
		</div>
	</div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { useBlogStore } from "@/store";
import dayjs from "dayjs";
import { ref, watch } from "vue";

const route = useRoute();
const blog = useBlogStore();

const runTime = ref("");
setInterval(() => {
	const days = dayjs().diff(blog.blogInfo.createSiteTime, "days");
	const day = new Date();
	let str = "";
	str += days + "天";
	str += day.getHours() + "时";
	str += day.getMinutes() + "分";
	str += day.getSeconds() + "秒";
	runTime.value = str;
}, 1000);

const webInfo = ref([
	{ name: "风风雨雨", count: runTime },
	{ name: "总访问量", count: blog.blogInfo.viewCount },
]);

// 监听 blog.blogInfo.viewCount 的变化，并更新 webInfo
watch(
	() => blog.blogInfo.viewCount,
	(newCount) => {
		webInfo.value[1].count = newCount;
	}
);
</script>


<style lang="scss" scoped>
.footer-color {
	color: var(--font-color);
	text-align: center;
}

.change-color {
	text-decoration: none;
	transition: all 0.3s;

	&:hover {
		color: var(--global-black);
	}
}

.footer-color.change-color {
	display: flex;
	align-items: center;
}

.beian-icon {
	margin-right: 5px;
	width: 0.75rem;
	height: 0.75rem;
}
</style>
