<template>
	<div class="reply-warp" id="reply-wrap">
		<div class="reply-title">
			<svg-icon icon-class="comment" size="1.4rem" style="margin-right: 5px;"></svg-icon>
			评论
		</div>
		<ReplyBox @reload="reloadComments" :comment-type="commentType" :type-id="typeId"></ReplyBox>
		<div v-if="pages > 0 && reFresh">
			<div class="reply-item" v-for="(comment, index) of commentList" :key="comment.id">
				<div class="reply-box-avatar">
					<img class="iris-avatar" :src="comment.avatar">
				</div>
				<div class="content-warp">
					<div class="user-info">
						<div class="user-name">{{ comment.fromNickname }}</div>
						<svg-icon v-if="comment.fromUid == 1" icon-class="badge-check"></svg-icon>
					</div>
					<div class="reply-content" v-html="comment.commentContent"></div>
					<div class="reply-info">
						<span class="reply-time">{{ formatDateTime(comment.createTime) }}</span>
						<span class="reply-like" @click="like(comment)">
              <svg-icon class="like" icon-class="like" size="0.8rem" :class="isLike(comment.id)"
												style="margin-right: 5px"></svg-icon>
              <span v-show="comment.likeCount">{{ comment.likeCount }}</span>
            </span>
						<span class="reply-btn" @click="handleReply(index, comment)">回复</span>
					</div>
					<div class="sub-reply-item" v-for="reply of comment.replyVOList" :key="reply.id">
						<div class="sub-user-info">
							<img class="sub-reply-avatar" :src="reply.avatar"/>
							<div class="sub-user-name">{{ reply.fromNickname }}</div>
							<svg-icon v-if="reply.fromUid == 1" icon-class="badge" style="margin-left: 5px;"></svg-icon>
						</div>
						<span class="reply-content">
              <template v-if="reply.fromUid !== reply.toUid">回复 <span style="color: #008ac5">@{{
									reply.toNickname
								}}</span> :</template>
              <span v-html="reply.commentContent"></span>
            </span>
						<div class="reply-info">
							<span class="reply-time">{{ formatDateTime(reply.createTime) }}</span>
							<span class="reply-like" @click="like(reply)">
                <svg-icon class="like" icon-class="like" size="0.8rem" :class="isLike(reply.id)"
													style="margin-right: 5px"></svg-icon>
                <span v-show="reply.likeCount > 0">{{ reply.likeCount }}</span>
              </span>
							<span class="reply-btn" @click="handleReply(index, reply)">回复</span>
						</div>
					</div>
					<div ref="readMoreRef" class="view-more" v-show="comment.replyCount > 3">
						<span>共{{ comment.replyCount }}条回复, </span>
						<span class="view-more-btn" @click="readMoreComment(index, comment)">点击查看</span>
					</div>
					<Paging ref="pageRef" :total="comment.replyCount" :index="index" :commentId="comment.id"
									@get-current-page="getCurrentPage"></Paging>
					<ReplyBox ref="replyRef" class="mt-4" :show="false" :comment-type="commentType" :type-id="typeId"
										@reload="reloadReplies(index)">
					</ReplyBox>
					<div class="bottom-line"></div>
				</div>
			</div>
			<!--			<div class="loading-warp-more" v-if="pages > 1">-->
			<!--				<div-->
			<!--					class="page hand-style no-select"-->
			<!--					@click="getList"-->
			<!--				>-->
			<!--					加载更多...-->
			<!--				</div>-->

			<!--			</div>-->
			<Pagination
				:pageNo="queryParams.pageNo"
				:pages="pages"
				@changePage="handlePage"
			/>
		</div>
		<div v-else style="padding: 1.25rem; text-align: center">来发评论吧~</div>
	</div>
</template>

<script setup lang="ts">
import {getCommentList, getReplyList, likeComment} from '@/api/comment';
import {Comment, CommentQuery, Reply} from '@/api/comment/types';
import {useAppStore, useUserStore} from "@/store";
import {formatDateTime} from '@/utils/date';
import Pagination from "@/components/Pagination/index.vue";

const user = useUserStore();
const app = useAppStore();
const replyRef = ref<any>([]);
const pageRef = ref<any>([]);
const readMoreRef = ref<any>([]);
const props = defineProps({
	commentType: {
		type: Number,
	}
});
const emit = defineEmits(["getCommentCount"]);
const typeId = computed(() => Number(useRoute().params.id) ? Number(useRoute().params.id) : undefined);
const isLike = computed(() => (id: number) => user.commentLikeSet.indexOf(id) != -1 ? "like-flag" : "");

const data = reactive({
	pages: 0,
	reFresh: true,
	queryParams: {
		pageNo: 1,
		pageSize: 5,
		action: {
			typeId: typeId.value,
			commentType: props.commentType
		},
	} as CommentQuery,
	commentList: [] as Comment[],
});
const {pages, reFresh, queryParams, commentList} = toRefs(data);


const like = async (comment: Comment | Reply) => {
	if (!user.id) {
		app.setLoginFlag(true);
		return;
	}
	let id = comment.id;
	await likeComment(id);
	//判断是否点赞
	if (user.commentLikeSet.indexOf(id) != -1) {
		comment.likeCount -= 1;
		window.$message?.success("取消点赞成功");
	} else {
		comment.likeCount += 1;
		window.$message?.success("点赞成功");
	}
	user.commentLike(id);

};

// 刷新评论列表
watch(
	commentList,
	() => {
		reFresh.value = false;
		nextTick(() => {
			reFresh.value = true;
		});
	},
	{deep: false},
);

// 查看更多评论
const readMoreComment = async (index: number, comment: Comment) => {
	const queryParams = {
		pageNo: 1,
		pageSize: 5,
		action: {commentId: comment.id}
	};
	const data = await getReplyList(queryParams);
	comment.replyVOList = data.records;
	if (comment.replyCount > 5) {
		pageRef.value[index].setPaging(true);
	}
	readMoreRef.value[index].style.display = "none";
};

// 查看当前页的回复评论
const getCurrentPage = async (current: number, index: number, commentId: number) => {
	const data = await getReplyList({
		pageNo: current,
		pageSize: 5,
		action: {commentId: commentId}
	});
	commentList.value[index].replyVOList = data.records;
};

const handleReply = (index: number, target: Comment | Reply) => {
	replyRef.value.forEach((element: any) => {
		element.setReply(false);
	});
	const currentReply = replyRef.value[index];
	currentReply.nickname = target.fromNickname;
	currentReply.commentForm.replyId = target.id;
	currentReply.commentForm.toUid = target.fromUid;
	currentReply.commentForm.parentId = commentList.value[index].id;
	currentReply.setReply(true);
};

const getList = async () => {
	if (queryParams.value.pageNo === 1) {
		commentList.value = [];
	}
	const response = await getCommentList(queryParams.value);
	commentList.value = [
		...commentList.value,
		...(Array.isArray(response.records) ? response.records : [])
	];
	pages.value = response.totalPages;
	emit("getCommentCount", response.totalPages);
};


// 重新加载评论列表
const reloadComments = () => {
	queryParams.value.pageNo = 1;
	getList();
};

// 重新加载回复评论
const reloadReplies = async (index: number) => {
	const queryParams = {
		pageNo: pageRef.value[index].pageNo,
		pageSize: 5,
		action: {commentId: commentList.value[index].id}
	};
	const data = await getReplyList(queryParams);
	commentList.value[index].replyVOList = data.records;
	commentList.value[index].replyCount++;
	replyRef.value[index].setReply(false);
	if (commentList.value[index].replyCount > 5) {
		pageRef.value[index].setPaging(true);
	}
	readMoreRef.value[index].style.display = "none";
};

/** 分页 */
const handlePage = (pageNo: number) => {
	queryParams.value.pageNo = pageNo;
	getList();
};

onMounted(() => {
	getList();
});
</script>


<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.reply-title {
	display: flex;
	align-items: center;
	margin: 22px 0;
	padding-left: 10px;
	font-size: 20px;
	font-weight: 600;
	line-height: 40px;
}

.sub-reply-avatar {
	position: absolute;
	left: 0;
	width: 1.5rem;
	height: 1.5rem;
	border-radius: 50%;
}

.reply-item {
	display: flex;
	padding-top: 1rem;

	.content-warp {
		flex: auto;
		margin-left: 0.6rem;
	}

	.bottom-line {
		border-bottom: 1px solid var(--grey-3);
		margin-top: 0.5rem;
	}
}

.user-info {
	display: flex;
	align-items: center;
	margin-bottom: 4px;

	.user-name {
		font-size: 0.875rem;
		font-weight: 500;
		margin-right: 0.3125rem;
	}
}

.sub-reply-item {
	position: relative;
	padding: 8px 0 8px 33px;
	font-size: 15px;
	line-height: 24px;

	.sub-user-info {
		display: inline-flex;
		align-items: center;
		margin-right: 9px;
		line-height: 24px;
	}

	.sub-user-name {
		font-size: 13px;
		line-height: 24px;
	}
}

.reply-info {
	display: flex;
	align-items: center;
	margin-top: 0.125rem;
	font-size: 0.8125rem;
	color: #9499a0;

	.reply-time {
		margin-right: 15px;
		padding-top: 2px;
	}

	.reply-like {
		display: flex;
		align-items: center;
		margin-right: 17px;
		cursor: pointer;

		&:hover .like {
			color: var(--color-blue);
		}
	}

	.reply-btn {
		cursor: pointer;

		&:hover {
			color: var(--color-blue);
		}
	}
}

.reply-content {
	overflow: hidden;
	word-wrap: break-word;
	word-break: break-word;
	white-space: pre-wrap;
	font-size: 0.9375rem;
	line-height: 1.5;
	vertical-align: baseline;
}

.view-more {
	font-size: 13px;
	color: #9499a0;

	.view-more-btn {
		cursor: pointer;

		&:hover {
			color: var(--color-blue);
		}
	}
}

.loading-warp-more {
	@include flex;
	margin-top: 20px;
}

.page {
	text-align: center;
	background-color: var(--grey-1);
	width: 120px;
	height: 30px;
	line-height: 30px;
	border-radius: 50px;
	margin: 0 auto;
	margin-top: 20px;
	position: relative;
	white-space: nowrap;
	border: 1px solid var(--grey-4);
	transition: all 0.3s;
	color: rgba(0, 0, 0, 0.65);

	&:hover {
		background-color: var(--grey-4);
	}

	&:active {
		transform: scale(0.7);
	}
}
</style>
