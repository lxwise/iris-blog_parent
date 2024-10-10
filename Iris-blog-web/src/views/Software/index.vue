<template>
	<div class="page-header">
		<h1 class="page-title">
			<div class="icon-and-text">
				<svg-icon icon-class="plane" size="2rem"></svg-icon>
				开源软件 ({{ articleList.length }})
			</div>
		</h1>
		<img class="page-cover" src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641"
				 alt=""/>
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container">

			<!--	文章列表		-->
			<div class="article-item" v-animate="['slideUpBigIn']" v-for="article of articleList" :key="article.id">
				<!-- 文章缩略图 -->
				<div class="article-cover">
					<NCarousel autoplay
										 show-dots
										 show-arrow
										 loop
										 keyboard
					>
						<NCarouselItem v-for="(image, index) in splitCover(article.articleCover)" :key="index">
							<img class="cover" :src="image"/>
						</NCarouselItem>
					</NCarousel>
				</div>
				<!-- 文章信息 -->
				<div class="article-info">
					<div class="article-meta">
					</div>
					<!-- 文章标题 -->
					<h3 class="article-title">
						{{ article.articleTitle }}
					</h3>
					<!-- 文章内容 -->
					<div class="article-content">
						<v-md-preview class="md" :text="article.articleContent"></v-md-preview>
					</div>
					<!-- 代码仓库 -->
					<div class="article-item-tag">
						<div class="article-tag-item">
							<a class="meta-item ml" href="https://gitee.com/lxwise">
								<svg-icon icon-class="plane" size="0.9rem" style="margin-right: 0.15rem"></svg-icon>
								{{ '代码仓库' }}
							</a>
						</div>
					</div>

					<!-- 详细介绍 -->
					<router-link class="article-btn" :to="`/article/${article.id}`">
						<span>{{ '详细介绍' }}</span>
						<svg-icon icon-class="right-arrow" size="0.9rem" style="padding-left: 0.15rem;"></svg-icon>
					</router-link>
				</div>
			</div>

		</div>
	</div>
</template>

<script setup lang="ts">
import {NCarousel, NCarouselItem} from 'naive-ui';

import {useBlogStore} from "@/store";
import {getArticleList} from "@/api/article";
import {Article} from "@/api/article/types";
import {PageQuery} from "@/model";

const blog = useBlogStore();

/** 分割封面图像 */
function splitCover(param: string): string[] {
	return param.split(',').filter(s => s && s.trim());
}

const data = reactive({
	pages: 0,
	queryParams: {
		pageNo: 1,
		pageSize: 5,
		action: {
			categoryId: 100
		}
	} as PageQuery,
	articleList: [] as Article[],
});

const {pages, queryParams, articleList} = toRefs(data);

watch(
	() => queryParams.value.pageNo,
	() => {
		getList();
	}
);

onMounted(() => {
	getList();
});

/** 获取所有文章 */
const getList = async () => {
	try {
		const response = await getArticleList(queryParams.value);
		articleList.value = response.records; // 将新数据追加到现有列表
		pages.value = response.totalPages;

	} finally {
	}
};

</script>

<style scoped lang="scss">

.page-container {
	position: relative;
	width: calc(100% - 0.625rem);
	margin: 1.5rem auto;
	padding: 1.75rem 2.25rem;
	border-radius: 0.75rem;
	box-shadow: 0 0 1rem var(--box-bg-shadow);
	animation: slideUpIn 1s;
}

@media (min-width: 1200px) {
	.page-container {
		width: 110.5rem;
	}
}

@media (max-width: 767px) {
	.page-container {
		padding: 1rem 0.875rem;
	}

	.page-title {
		font-size: 2rem;
		padding: 3rem 0.5rem 0;
	}
}


.article-item {
	display: flex;
	height: 28rem;
	margin: 1.25rem 0.5rem 0;
	border-radius: 0.5rem;
	box-shadow: 0 0.625rem 1.875rem -0.9375rem var(--box-bg-shadow);
	animation-duration: 0.5s;
	transition: all 0.2s ease-in-out 0s;
	visibility: hidden;

	&:hover {
		box-shadow: 0 0 1.5rem var(--box-bg-shadow);

		.cover {
			transform: scale(1.05) rotate(1deg);
		}
	}

	&:nth-child(even) {
		flex-direction: row-reverse;

		.article-cover {
			margin-right: auto;
			margin-left: 1.5rem;
			-webkit-clip-path: polygon(0 0, 100% 0, 100% 100%, 8% 100%);
			clip-path: polygon(0 0, 100% 0, 100% 100%, 8% 100%);
			border-radius: 0 0.625rem 0.625rem 0;
		}

		.article-info {
			padding: 1rem 0 3rem 1.5rem;

			.article-meta {
				justify-content: flex-start;
			}
		}

		.article-btn {
			display: flex;
			left: 0;
			right: auto;
			border-radius: 0 1rem;

			&:hover {
				transform: translateZ(2rem);
			}
		}

		.article-tag-item {
			right: 0.5rem;
			justify-content: flex-start;
		}
	}
}

.article-cover {
	width: 50%;
	margin-right: 1.5rem;
	clip-path: polygon(0 0, 92% 0, 100% 100%, 0 100%);
	border-radius: 0.625rem 0 0 0.625rem;
	overflow: hidden;

	.cover {
		width: 100%;
		height: 100%;
		object-fit: cover;
		transition: all 0.2s ease-in-out 0s;
	}
}

.article-info {
	position: relative;
	width: 50%;
	padding: 1rem 1.5rem 3rem 0;
	perspective: 62.5rem;

	.article-item-tag {
		display: flex;
		justify-content: flex-start;
		font-size: 1rem;
		color: var(--color-orange);
	}

	.top {
		background-image: linear-gradient(to right, #3ca5f6 0, #a86af9 100%);
		padding-left: 5px;
		padding-right: 5px;
		display: inline-block;
		border-top-right-radius: 5px;
		border-bottom-left-radius: 5px;
		font-size: 0.7rem;
		margin-right: 5px;
		color: #fff;
		height: 1.25rem;
	}

	.meta-item {
		display: flex;
		align-items: center;
	}

	.ml:not(:first-child) {
		margin-left: 0.625rem;
	}

	.article-title {
		text-overflow: ellipsis;
		white-space: nowrap;
		margin: 0.625rem 0;
		color: var(--color-blue1);
		overflow: hidden;
	}

	.article-content {
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 3;
		max-height: 18rem;
		font-size: 0.875em;
		overflow: hidden;
	}
}

.article-tag-item {
	position: absolute;
	display: flex;
	align-items: center;
	bottom: 0.5rem;
	font-size: 1em;
	color: var(--color-orange);
}

.article-btn {
	display: flex;
	position: absolute;
	align-items: center;
	bottom: 0;
	right: 0;
	padding: 0.3rem 1rem;
	border-radius: 1rem 0;
	color: var(--color-blue1);

	&:hover {
		transform: translateZ(2rem);
	}
}

@media (max-width: 767px) {
	.article-item {
		flex-direction: column;
		height: fit-content;

		.article-cover {
			width: 100%;
			height: 18rem;
			margin: auto;
			clip-path: polygon(0 0, 100% 0, 100% 92%, 0 100%);
			border-radius: 0.625rem 0.625rem 0 0;
		}

		.article-info {
			width: 100%;
			height: 18rem;
			padding: 0 1rem 3rem;
		}

		&:nth-child(even) {
			flex-direction: column;

			.article-cover {
				width: 100%;
				margin: auto;
				clip-path: polygon(0 0, 100% 0, 100% 100%, 0 92%);
				border-radius: 0.625rem 0.625rem 0 0;
			}

			.article-info {
				padding: 0 1rem 3rem;
			}
		}
	}
}


</style>
