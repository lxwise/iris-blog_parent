<template>
	<div class="page-header">
		<h1 class="page-title">
			<div class="icon-and-text">
				<svg-icon icon-class="notice" size="2rem"></svg-icon>
				通知({{ count }})
			</div>
		</h1>
		<img alt="" class="page-cover"
				 src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641">
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container" style="width: 85rem">
			<div style="display: flex">

				<n-tabs type="line" animated @update-value="handleTabChange" :value="activeTab">
					<n-tab-pane name="1">
						<template #tab>
							<svg-icon icon-class="notice"></svg-icon>
							<span style="margin-left: 10px; position: relative;">
				系统通知
								<!-- 当未读数大于 0 时显示红色数字 -->
				<span v-if="notReadCount.systemNoticeCount > 0" class="notification-badge">
					{{ notReadCount.systemNoticeCount }}
				</span>
			</span>
						</template>
					</n-tab-pane>
					<n-tab-pane name="2">
						<template #tab>
							<svg-icon icon-class="notice_comment"></svg-icon>
							<span style="margin-left: 10px; position: relative;">
				评论
								<!-- 当未读数大于 0 时显示红色数字 -->
				<span v-if="notReadCount.commentNoticeCount > 0" class="notification-badge">
					{{ notReadCount.commentNoticeCount }}
				</span>
			</span>
						</template>
					</n-tab-pane>
					<n-tab-pane name="3">
						<template #tab>
							<svg-icon icon-class="notice_like"></svg-icon>
							<span style="margin-left: 10px; position: relative;">
				点赞
								<!-- 当未读数大于 0 时显示红色数字 -->
				<span v-if="notReadCount.likeNoticeCount > 0" class="notification-badge">
					{{ notReadCount.likeNoticeCount }}
				</span>
			</span>
						</template>
					</n-tab-pane>
				</n-tabs>


				<div>
					<n-button quaternary round type="info" size="medium" @click="handlerClearAllNotice">
						<svg-icon icon-class="delete"></svg-icon>
						清除所有消息
					</n-button>
				</div>
			</div>

			<router-link v-for="notice in noticeList"
									 :key="notice.id"
									 v-animate="['slideUpBigIn']" class="notice-item"
									 :to="getRouterPath(notice)">
				<div class="notice-meta">
					<!-- 用户头像 -->
					<img :src="notice.toUserAvatar" class="user-avatar">
					<div class="notice-info">
						<span class="talk-user-name">{{ notice.toUserNickname }}
							<svg-icon icon-class="location" style="margin-left: 0.4rem;color: #fd2b18"></svg-icon>{{
								notice.province
							}}</span>
						<span class="talk-time">{{ formatDateTime(notice.createTime) }}</span>
					</div>
				</div>
				<!-- 内容 -->
				<div class="talk-content">
					<span v-if="notice.status === 0" class="status-indicator">
							<span class="red-dot"></span>
							<span class="status-text">[ 未读 ]</span>
					</span>
					<span v-else class="status-indicator">
							<span class="status-text-read">[ 已读 ]</span>
					</span>
					<span v-html="notice.content"></span>
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
import {PageQuery} from "@/model";
import {formatDateTime} from "@/utils/date";
import Pagination from "@/components/Pagination/index.vue";
import {AppNoticeCountVO, NoticeVO} from "@/api/notice/types";
import {clearAllNotice, getNoticeList, readAllNotice} from "@/api/notice";

const route = useRoute();
let notReadCount = ref<AppNoticeCountVO>(<AppNoticeCountVO>{});
const data = reactive({
	pages: 0,
	queryParams: {
		pageNo: 1,
		pageSize: 5,
		action: {
			noticeType: 1,
		}
	} as PageQuery,
	noticeList: [] as NoticeVO[],
	activeTab: route.params.id,
	count: 0
});
const {
	pages,
	queryParams,
	noticeList,
	activeTab,
	count
} = toRefs(data);

watch(
	() => queryParams.value.pageNo,
	() => {
		getList(true);
	}
);

const getList = async (add: boolean) => {
	try {
		const response = await getNoticeList(queryParams.value);
		if (add) {
			noticeList.value = [...noticeList.value, ...response.records];
		} else {
			noticeList.value = response.records;
		}
		pages.value = response.totalPages;
		count.value = response.total;
	} finally {
	}
};

const getNotReadCount = async () => {
	try {
		const response = await readAllNotice();
		notReadCount.value = response;
	} catch (error) {
		console.error("获取未读消息数量失败", error);
	}
};

onMounted(async () => {
	activeTab.value = Number(route.params.id);
	await getList(true);
	await getNotReadCount();
});

const handlePage = (pageNo: number) => {
	queryParams.value.pageNo = pageNo;
};

const handleTabChange = async (value: string | number) => {
	activeTab.value = value;
	queryParams.value.action.noticeType = Number(value);

	// 获取最新的未读数
	await getList(false); // 更新通知列表
	await getNotReadCount(); // 更新未读通知数
};


const handlerClearAllNotice = async () => {
	try {
		await clearAllNotice(queryParams.value.action.noticeType)
		window.$message?.success("清除成功");
		await getNotReadCount();  // 清除后更新未读计数
	} catch (e) {
		console.error("清除消息失败" + e);
	}
};

const getRouterPath = (notice: any) => {
	if (notice.noticeTypePath != 'friend') {
		return '/' + notice.noticeTypePath + '/' + notice.typeId;
	} else {
		return '/' + notice.noticeTypePath;
	}
};
</script>


<style lang="scss" scoped>
.notice-item {
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

.notice-meta {
	display: flex;
	align-items: center;
	width: 100%;
}

.notice-info {
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


.talk-content {
	display: flex;
	align-items: center;
}

.status-indicator {
	display: flex;
	align-items: center;
	margin-right: 10px; /* 调整间距 */
}

.red-dot {
	display: inline-block;
	width: 8px;
	height: 8px;
	background-color: #e74b07;
	border-radius: 50%;
	margin-right: 5px; /* 红点与文本之间的间距 */
}

.status-text {
	color: #e74b07;
}

.status-text-read {
	color: #83f588;
}

.notification-badge {
	position: absolute;
	top: -10px;
	right: -10px;
	background-color: #e74b07;
	color: white;
	border-radius: 50%;
	padding: 2px 6px;
	font-size: 12px;
	line-height: 1;
}

</style>
