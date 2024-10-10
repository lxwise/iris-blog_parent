<template>
	<div class="page-header">
		<h1 class="page-title">
			<div class="icon-and-text">
				<svg-icon icon-class="archives" size="2rem"></svg-icon>
				归档 ({{ count }})
			</div>
		</h1>
		<img class="page-cover" src="http://test.yudao.iocoder.cn/goods/20240805/home.jpg"
				 alt="">
		<!-- 波浪 -->
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container">
			<div class="archive-title">文章总览 - {{ count }}</div>
			<div class="archive-list">
				<div class="archive-item" v-for="archive in archivesList" :key="archive.id">
					<router-link class="article-cover" :to="`/article/${archive.id}`">
						<img class="cover" v-lazy="archive.articleCover">
					</router-link>
					<div class="article-info">
						<router-link class="article-title" :to="`/article/${archive.id}`">
							{{ archive.articleTitle }}
						</router-link>
						<div class="article-time">
							<svg-icon icon-class="calendar" style="margin-right:0.4rem;"></svg-icon>
							<time>{{ formatDate(archive.createTime) }}</time>
						</div>
					</div>
				</div>
			</div>

			<Pagination
				:pageNo="queryParams.pageNo"
				:pages="pages"
				@changePage="handlePage"
			/>
		</div>
	</div>
</template>

<script setup lang="ts">
import {getArchivesList} from '@/api/archives';
import {Archives} from '@/api/archives/types';
import Pagination from '@/components/Pagination/index.vue';
import {PageQuery} from '@/model';
import {formatDate} from '@/utils/date';

const data = reactive({
	count: 0,
	pages: 0,
	queryParams: {
		pageNo: 1,
		pageSize: 5,
		action: {}
	} as PageQuery,
	archivesList: [] as Archives[],
});

const {
	count,
	pages,
	queryParams,
	archivesList,
} = toRefs(data);

watch(
	() => queryParams.value.pageNo,
	() => {
		getList();
	}
);

onMounted(() => {
	getList();
});

/** 查询列表 */
const getList = async () => {
	try {
		const response = await getArchivesList(queryParams.value);
		archivesList.value = [...archivesList.value, ...response.records]; // 将新数据追加到现有列表
		pages.value = response.totalPages;
		count.value = response.total;
	} finally {
		// 可以在此处处理其他需要的逻辑
	}
};

/** 分页 */
const handlePage = (pageNo: number) => {
	queryParams.value.pageNo = pageNo;
};

</script>

<style lang="scss" scoped>
.archive-title {
	position: relative;
	margin-left: 10px;
	padding-bottom: 20px;
	padding-left: 20px;
	font-size: 1.5rem;

	&::before {
		position: absolute;
		top: 16px;
		left: -8px;
		z-index: 1;
		width: 18px;
		height: 18px;
		border: 5px solid var(--color-blue);
		border-radius: 10px;
		content: '';
		line-height: 10px;
	}

	&::after {
		position: absolute;
		bottom: 0;
		left: 0;
		z-index: 0;
		width: 2px;
		height: 1.5em;
		background: #aadafa;
		content: '';
	}
}

.archive-title:hover:before {
	border-color: var(--color-orange);
}

.archive-list {
	margin-left: 10px;
	padding-left: 20px;
	border-left: 2px solid #aadafa;
}

.archive-item {
	position: relative;
	display: flex;
	align-items: center;
	margin: 0 0 20px 10px;

}

.archive-item:hover:before {
	border-color: var(--color-orange);
}

.archive-item::before {
	position: absolute;
	left: -36px;
	width: 10px;
	height: 10px;
	border: 3px solid var(--color-blue);
	border-radius: 6px;
	background: var(--grey-0);
	content: '';
}

.article-cover {
	width: 120px;
	height: 120px;
	overflow: hidden;
	border-radius: 12px;

	.cover {
		width: 100%;
		height: 100%;
		object-fit: cover;
		transition: filter 375ms ease-in 0.2s, transform 0.6s;
	}
}

.cover:hover {
	transform: scale(1.1);
}

.article-info {
	display: flex;
	flex-direction: column;
	flex: 1;
	margin: 0 1rem;

	.article-time {
		font-size: 14px;
	}

	.article-title {
		font-size: 0.9rem;
		margin: 2px 0;
	}
}

.article-title:hover {
	color: var(--primary-color);
	transform: translateX(10px);

}
</style>
