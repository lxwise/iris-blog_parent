<template>
	<n-modal
		class="dialog-wrapper bg"
		v-model:show="dialogVisible"
		preset="dialog"
		:show-icon="false"
		transform-origin="center"
		:block-scroll="false"
	>
		<template #header>
			<span class="title">文章搜索</span>
		</template>
		<!-- 输入框 -->
		<div class="search-input-wrapper">
			<label for="search">
				<svg-icon icon-class="search"></svg-icon>
			</label>
			<input id="search" placeholder="输入文章标题或内容..." v-model="keyword"/>
		</div>
		<!-- 标签搜索 -->
		<hr class="divider"/>
		<div v-if="tagList.length" class="tagBox">
			<div class="tag-title">标签搜索</div>
			<div>
				<span
					@click="handleToTag(tag.id)"
					:style="{ backgroundColor: `${getRandomColor()}` }"
					class="tag-item-list"
					v-for="(tag, index) in tagList"
					:key="index"
				>
					{{ tag.tagName }}
				</span>
			</div>
		</div>
		<!-- 搜索结果 -->
		<div class="search-result-wrapper">
			<ul v-if="articleList.length">
				<li class="search-result" v-for="article in articleList" :key="article.id">
					<!-- 文章标题 -->
					<router-link class="search-title" :to="`/article/${article.id}`">
						<span @click="dialogVisible = false" v-html="article.articleTitle"></span>
					</router-link>
					<!-- 文章内容 -->
					<p class="search-content" v-html="article.articleContent"></p>
				</li>
			</ul>
			<!-- 搜索结果不存在提示 -->
			<div v-else-if="keyword" class="colorFlag" style="font-size: 0.875rem; margin-top: 1rem;">
				找不到您查询的内容：{{ keyword }}
			</div>
		</div>
	</n-modal>
</template>

<script setup lang="ts">
import {searchArticle} from "@/api/article";
import {ArticleSearch} from "@/api/article/types";
import {useAppStore} from "@/store";
import {debouncedWatch} from "@vueuse/core";
import {getTagList} from "@/api/tag";
import {computed, onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";

const router = useRouter();
const route = useRoute();
const app = useAppStore();

const dialogVisible = computed({
	get: () => app.searchFlag,
	set: (value) => (app.searchFlag = value),
});

const keyword = ref("");
const articleList = ref<ArticleSearch[]>([]);
const tagList = ref([]);

debouncedWatch(
	keyword,
	() => (keyword.value ? handleSearch() : (articleList.value = [])),
	{debounce: 300}
);

const handleSearch = async () => {
	try {
		const data = {
			categoryId: null,
			tags: null,
			keyword: keyword.value
		};
		articleList.value = await searchArticle(data);
	} catch (error) {
		console.error("搜索文章失败:", error);
	}
};

const handleToTag = (id: number) => {
	app.searchFlag = false;
	router.push({path: `/tag/${id}`});
};

const getRandomColor = () => {
	return "rgb(" + Math.round(Math.random() * 255) + "," + Math.round(Math.random() * 255) + "," + Math.round(Math.random() * 255) + ")";
};

const fetchTagList = async () => {
	try {
		tagList.value = await getTagList();
	} catch (error) {
		console.error("获取标签列表失败:", error);
	}
};

// 使用 onMounted 确保组件挂载后才执行获取标签列表的操作
onMounted(() => {
	fetchTagList();
});
</script>

<style scoped>
.title {
	color: var(--color-blue);
	font-size: 1rem;
}

.search-input-wrapper {
	display: flex;
	align-items: center;
	padding: 5px;
	height: 35px;
	width: 100%;
	border: 2px solid #8e8cd8;
	color: var(--text-color);
	border-radius: 2rem;
}

.search-input-wrapper #search {
	width: 100%;
	font-size: 1rem;
	margin-left: 5px;
	color: var(--text-color);
	outline: none;
}

@media (min-width: 960px) {
	.search-result-wrapper {
		padding: 0 3px;
		height: 390px;
		overflow: auto;
	}
}

@media (max-width: 959px) {
	.search-result-wrapper {
		height: calc(100vh - 190px);
		overflow: auto;
	}
}

.search-result {
	margin-top: 1rem;
	font-size: 1rem;
	color: var(--text-color);
}

.search-title {
	font-weight: 700;
	border-bottom: 1px solid #999;
	text-decoration: none;
}

.search-content {
	cursor: pointer;
	border-bottom: 1px dashed #ccc;
	padding: 5px 0;
	line-height: 2;
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-line-clamp: 3;
	-webkit-box-orient: vertical;
}

.divider {
	margin-top: 1.2rem;
	border: 2px dashed #d2ebfd;
}

.tag-item-list {
	display: inline-block;
	border-radius: 5px;
	padding: 5px;
	margin-right: 5px;
	margin-bottom: 5px;
	color: #fff;

	&:hover {
		transform: scale(1.1);
	}
}

.tagBox {
	margin-top: 15px;

	.tag-title {
		margin-bottom: 10px;
		position: relative;
		padding-left: 10px;

		&::before {
			content: "";
			width: 5px;
			height: 100%;
			background-color: #d2ebfd;
			position: absolute;
			left: 0;
			bottom: 0;
		}
	}
}
</style>
