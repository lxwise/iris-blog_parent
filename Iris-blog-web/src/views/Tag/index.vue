<template>
	<div class="page-header">
		<h1 class="page-title">
			<div class="icon-and-text">
				<svg-icon icon-class="tag" size="2rem"></svg-icon>
				标签 ({{ tagList.length }})
			</div>
		</h1>
		<img class="page-cover" src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641"
				 alt="">
		<!-- 波浪 -->
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container">
			<div class="left">
				<!--		标签		-->
				<div class="tag-box">
					<div class="tag-title">
						<svg-icon icon-class="tag" color="#70B6FF"></svg-icon>
						标签 ({{ tagList.length }})
					</div>
					<div class="tag-list">
						<a
							ref="tagRef"
							:class="
                handleChoose({item : tag, index : index})
                  ? 'tag-option hand-style active'
                  : 'tag-option hand-style'
              "
							:style="{'color': getRandomColor() }"
							@click="handleClick({id : tag.id, index : index})"
							v-for="(tag, index) in tagList"
							:key="index"
						>
							{{ tag.tagName }} <span class="num">{{ tag.articleCount }}</span>
						</a>
					</div>
				</div>
				<hr class="divider"/>
				<!--	文章列表		-->
				<div class="article-item" v-animate="['slideUpBigIn']" v-for="article of articleList" :key="article.id">
					<!-- 文章缩略图 -->
					<div class="article-cover">
						<router-link :to="`/article/${article.id}`" href="">
							<img class="cover" v-lazy="article.articleCover"/>
						</router-link>
					</div>
					<!-- 文章信息 -->
					<div class="article-info">
						<div class="article-meta">
							<!-- 发表时间 -->
							<span class="meta-item ml">
          <svg-icon icon-class="calendar" size="0.9rem" style="margin-right: 0.15rem"></svg-icon>{{
									formatDate(article.createTime)
								}}
        </span>
						</div>
						<!-- 文章标题 -->
						<h3 class="article-title">
							<router-link :to="`/article/${article.id}`">
								{{ article.articleTitle }}
							</router-link>
						</h3>

						<!-- 文章分类 -->
						<div class="article-item-tag">
							<div class="article-tag-item">
								<router-link class="meta-item ml" :to="`/category/${article.categoryId}`">
									<n-tooltip trigger="hover">
										<template #trigger>
											<div>
												<svg-icon icon-class="qizhi" size="0.9rem" style="margin-right: 0.15rem"></svg-icon>
												{{ article.categoryName }}
											</div>
										</template>
										文章分类
									</n-tooltip>

								</router-link>
							</div>
						</div>

						<!-- 阅读按钮 -->
						<router-link class="article-btn" :to="`/article/${article.id}`">
							<n-avatar
								round
								size="medium"
								lazy
								:src="blog.blogInfo.authorAvatar"
							/>
							<span>{{ '鸢尾' }}</span>
						</router-link>
					</div>
				</div>

				<Pagination
					:pageNo="queryParams.pageNo"
					:pages="pages"
					@changePage="handlePage"
				/>

			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import {useBlogStore} from "@/store";
import {getTagList} from "@/api/tag";
import {Tag} from "@/api/tag/types";
import {formatDate} from "@/utils/date";
import {getArticleList} from "@/api/article";
import {Article} from "@/api/article/types";
import {PageQuery} from "@/model";
import Pagination from "@/components/Pagination/index.vue";

const blog = useBlogStore();

const getRandomColor = () => {
	return "rgb(" + Math.round(Math.random() * 255) + "," + Math.round(Math.random() * 255) + "," + Math.round(Math.random() * 255) + ")";
};
const tagList = ref<Tag[]>([]);

const lastIndex = ref(null);
const tagRef = ref();

const data = reactive({
	pages: 0,
	queryParams: {
		pageNo: 1,
		pageSize: 5,
		action: {
			tags: 0
		},
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

onMounted(async () => {
	tagList.value = await getTagList();

	queryParams.value.action.tags = tagList.value[0].id;
	getList();
});

/** 获取所有文章 */
const getList = async () => {
	try {
		const response = await getArticleList(queryParams.value);
		articleList.value = [...articleList.value, ...response.records]; // 将新数据追加到现有列表
		pages.value = response.totalPages;
	} finally {
	}
};

/** 分页 */
const handlePage = (pageNo: number) => {
	queryParams.value.pageNo = pageNo;
};

/***********新的相关************/

/**选中标签 */
function handleChoose({item}: { item: any }) {
	return item.id == queryParams.value.action.tags;
}

/**点击标签 */
function handleClick({id, index}: { id: any, index: any }) {
	if (index == lastIndex.value) {
		return;
	}
	tagRef.value[index].className = "tag-option hand-style active";
	if (lastIndex.value != null) {
		tagRef.value[lastIndex.value].className = "tag-option hand-style ";
	}
	lastIndex.value = index;
	queryParams.value.pageNo = 1;
	queryParams.value.action.tags = id;
	articleList.value = [];
	getList();
}
</script>

<style lang="scss" scoped>
.left {
	color: var(--text-color);
	margin-right: 15px;

	.divider {
		margin-top: 1.2rem;
		border: 2px dashed #d2ebfd;
	}

	.tag-box {
		background-color: var(--background-color);
		padding: 15px;
		border-radius: 5px;

		.tag-title {
			font-size: 1.2rem;
			margin-bottom: 20px;
			margin-top: 10px;

			svg {
				width: 20px;
				height: 20px;
				vertical-align: -5px;
			}
		}

		.tag-list {
			.tag-option {
				display: inline-block;
				border: 1rem solid var(--border-line);
				border-radius: 5px;
				margin-right: 10px;
				padding: 5px;
				margin-bottom: 10px;

				&:hover {
					background-color: rgb(171, 214, 214);
				}

				.num {
					display: inline-block;
					border-radius: 50%;
					width: 20px;
					height: 20px;
					background-color: #66b1ff;
					text-align: center;
					line-height: 20px;
					color: white;
					font-size: 12px;
				}
			}

			.active {
				background-color: rgb(171, 214, 214);
			}
		}
	}
}


.article-item {
	display: flex;
	height: 14rem;
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

	.article-meta {
		display: flex;
		justify-content: flex-end;
		font-size: 0.8125rem;
		color: var(--grey-5);
	}

	.article-item-tag {
		display: flex;
		justify-content: flex-start;
		font-size: 0.9125rem;
		color: var(--grey-5);
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
		max-height: 5rem;
		font-size: 0.875em;
		overflow: hidden;
	}
}

.article-tag-item {
	position: absolute;
	display: flex;
	align-items: center;
	bottom: 0.5rem;
	font-size: 0.9125em;
	color: var(--grey-5);
}

.article-btn {
	display: flex;
	position: absolute;
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
			height: 8rem;
			margin: auto;
			clip-path: polygon(0 0, 100% 0, 100% 92%, 0 100%);
			border-radius: 0.625rem 0.625rem 0 0;
		}

		.article-info {
			width: 100%;
			height: 8rem;
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
