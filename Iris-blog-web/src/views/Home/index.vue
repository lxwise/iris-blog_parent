<template>
	<!--背景轮播-->
	<Images></Images>
	<!-- 品牌 -->
	<Brand></Brand>
	<div class="bg">
		<div class="main-container mt">
			<div :class="app.sideFlag ? 'test' : ''" class="left-container">
				<!-- 说说 -->
				<TalkSwiper></TalkSwiper>
				<!-- 推荐文章 -->
				<Recommend></Recommend>
				<!-- 文章列表 -->
				<ArticleItem></ArticleItem>
			</div>
			<SideBar :class="app.sideFlag ? 'temp' : ''" class="right-container"></SideBar>
		</div>
	</div>
</template>

<script setup lang="ts">
import {getBlogInfo} from "@/api/blogInfo";
import {useAppStore, useBlogStore} from "@/store";
import ArticleItem from "@/views/Article/ArticleItem.vue";
import Brand from "./Brand/index.vue";
import Images from "./Swiper/Images.vue";
import Recommend from "./Swiper/Recommend.vue";
import TalkSwiper from "./Swiper/TalkSwiper.vue";


const app = useAppStore();
const blog = useBlogStore();
onMounted(async () => {
	const data = await getBlogInfo();
	blog.setBlogInfo(data);
})
</script>

<style lang="scss" scoped>
.mt {
	margin-top: 1rem;
	padding-bottom: 1.75rem;
}
</style>
