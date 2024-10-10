<template>
	<div class="page-header">
		<h1 class="page-title">说说</h1>
		<img alt="" class="page-cover"
				 src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641">
		<Waves></Waves>
	</div>
	<div class="bg">
		<div v-if="talk" class="page-container">
			<div class="talk-item">
				<div class="talk-meta">
					<img :src="talk.avatar" class="user-avatar">
				</div>
				<div class="talk-content-wrap">
					<div class="talk-info">
            <span class="talk-user-name">{{ talk.nickname }}<svg-icon icon-class="badge"
																																			style="margin-left: 0.4rem;"></svg-icon></span>
						<span class="talk-time">{{ formatDateTime(talk.createTime) }}</span>
					</div>
					<div class="talk-content" v-html="talk.talkContent"></div>
					<div v-if="talk.imgList" v-viewer class="talk-image">
						<img v-for="(img, index) in talk.imgList" :key="index" v-lazy="img" class="image" @click.prevent/>
					</div>
					<div class="info" style="margin-top: 0.5rem;">
            <span class="talk-like info" @click="like">
              <svg-icon icon-class="like" size="0.8rem" :class="isLike(talk.id)" style="margin-right: 5px"></svg-icon>
              {{ talk.likeCount }}
            </span>
						<span class="talk-comment info">
              <svg-icon icon-class="comment" size="0.9rem" style="margin-right: 5px;"></svg-icon>
              {{ commentCount == null ? 0 : commentCount }}
            </span>
					</div>
				</div>
			</div>
			<CommentList :comment-type="commentType" @get-comment-count="getCommentCount"></CommentList>
		</div>
	</div>
</template>

<script setup lang="ts">
import {getTalk, likeTalk} from "@/api/talk";
import {Talk} from "@/api/talk/types";
import {useAppStore, useUserStore} from "@/store";
import {formatDateTime} from "@/utils/date";

const user = useUserStore();
const app = useAppStore();
const route = useRoute();
const isLike = computed(() => (id: number) => user.talkLikeSet.indexOf(id) != -1 ? "like-flag" : "");
const data = reactive({
	commentCount: 0,
	commentType: 3,
	talk: {
		id: 0,
		nickname: "",
		avatar: "",
		talkContent: "",
		imgList: [],
		isTop: 0,
		likeCount: 0,
		commentCount: 0,
		createTime: "",
	} as Talk,
});
const {commentCount, commentType, talk} = toRefs(data);
const getCommentCount = (count: number) => {
	commentCount.value = talk.value.commentCount;
};
const like = async () => {
	if (!user.id) {
		app.setLoginFlag(true);
		return;
	}
	let id = talk.value.id;
	await likeTalk(id);
	//判断是否点赞
	if (user.talkLikeSet.indexOf(id) != -1) {
		talk.value.likeCount -= 1;
	} else {
		talk.value.likeCount += 1;
	}
	user.talkLike(id);
};
onMounted(async () => {
	talk.value = await getTalk(Number(route.params.id));
})
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.talk-item {
	display: flex;
	align-items: flex-start; /* 保持头像和内容在顶部对齐 */
}

.talk-meta {
	flex-shrink: 0; /* 防止头像区域被压缩 */
	width: 3rem;
	height: 3rem; /* 固定头像区域的宽度和高度 */
}

.user-avatar {
	width: 100%; /* 确保头像在设定区域内始终占满宽度 */
	height: 100%; /* 高度也保持为 100%，防止变形 */
	border-radius: 50%; /* 保持圆形头像 */
	object-fit: cover; /* 保证图片在头像区域内保持比例不变 */
}

.talk-content-wrap {
	flex-grow: 1; /* 让内容区域占据剩余空间 */
	margin-left: 0.5rem; /* 增加头像与内容的间距 */
}

.talk-image {
	max-width: 100%; /* 保证图片不会超过容器宽度 */
}

.image {
	width: auto; /* 图片保持原有比例 */
	max-width: 100%; /* 图片最大宽度不超过容器 */
	height: auto;
}

</style>
