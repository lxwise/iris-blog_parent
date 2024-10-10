<template>
	<div class="page-header">
		<h1 class="page-title">
			<div class="icon-and-text">
				<svg-icon icon-class="talk" size="2rem"></svg-icon>
				说说 ({{ talkList.length }})
			</div>
		</h1>
		<img alt="" class="page-cover"
				 src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641">
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container">
			<router-link v-for="talk in talkList" :key="talk.id" v-animate="['slideUpBigIn']" :to="`/talk/${talk.id}`"
									 class="talk-item">
				<div class="talk-meta">
					<!-- 用户头像 -->
					<img :src="talk.avatar" class="user-avatar">
					<div class="talk-info">
            <span class="talk-user-name">{{ talk.nickname }}<svg-icon icon-class="badge"
																																			style="margin-left: 0.4rem;"></svg-icon></span>
						<span class="talk-time">{{ formatDateTime(talk.createTime) }}</span>
					</div>
				</div>
				<!-- 说说内容 -->
				<div class="talk-content" v-html="talk.talkContent">
				</div>
				<!-- 说说图片 -->
				<div v-viewer class="talk-image">
					<img v-for="(img, index) in talk.imgList" :key="index" v-lazy="img" class="image" @click.prevent/>
				</div>
				<!-- 说说信息 -->
				<div class="info" style="margin-top: 0.5rem;">
					<!-- 点赞量 -->
					<span class="talk-like info">
            <svg-icon icon-class="like" size="0.8rem" style="margin-right: 5px;"></svg-icon>{{
							talk.likeCount
						}}
          </span>
					<!-- 评论量 -->
					<span class="talk-comment info">
            <svg-icon icon-class="comment" size="0.9rem" style="margin-right: 5px;"></svg-icon>{{
							talk.commentCount
						}}
          </span>
				</div>
			</router-link>
		</div>
	</div>
	<Pagination
		:pageNo="queryParams.pageNo"
		:pages="pages"
		@changePage="handlePage"
	/>
</template>

<script setup lang="ts">
import {getTalkList} from "@/api/talk";
import {Talk} from "@/api/talk/types";
import {PageQuery} from "@/model";
import {formatDateTime} from "@/utils/date";
import Pagination from "@/components/Pagination/index.vue";

const data = reactive({
	pages: 0,
	queryParams: {
		pageNo: 1,
		pageSize: 5,
		action: {}
	} as PageQuery,
	talkList: [] as Talk[],
});
const {
	pages,
	queryParams,
	talkList,
} = toRefs(data);

watch(
	() => queryParams.value.pageNo,
	() => {
		getList();
	}
);

const getList = async () => {
	try {
		const response = await getTalkList(queryParams.value);
		talkList.value = [...talkList.value, ...response.records]; // 将新数据追加到现有列表
		pages.value = response.totalPages;

	} finally {
	}
};
onMounted(() => {
	getList();
})
/** 分页 */
const handlePage = (pageNo: number) => {
	queryParams.value.pageNo = pageNo;
};
</script>

<style lang="scss" scoped>
.talk-item {
	display: flex;
	flex-direction: column;
	padding: 1rem 1.25rem;
	border-radius: 0.5rem;
	box-shadow: 0 0.625rem 1.875rem -0.9375rem var(--box-bg-shadow);
	animation-duration: 0.5s;
	transition: all 0.2s ease-in-out 0s;
	visibility: hidden;

	&:hover {
		box-shadow: 0 0 2rem var(--box-bg-shadow);
	}

	&:not(:first-child) {
		margin-top: 1.25rem;
	}
}

.talk-meta {
	display: flex;
	align-items: center;
	width: 100%;
}

.talk-info {
	display: flex;
	flex-direction: column;
	margin-left: 0.5rem;
}

.user-avatar {
	width: 2.8rem;
	height: 2.8rem;
	border-radius: 10px;
}

.info {
	display: flex;
	align-items: center;
}
</style>
