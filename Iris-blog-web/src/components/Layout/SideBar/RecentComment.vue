<template>
  <div class="side-card">
    <!-- 标题 -->
    <div class="comment-title">
      <svg-icon icon-class="comment" size="1.1875rem"></svg-icon>
      最新评论
    </div>
    <!-- 滚动模块包裹评论 -->
    <AutoScrollList :list="commentList" liHeight="5rem">
      <template #default="{ row }">
        <!-- 评论项 -->
        <div class="comment-item">
          <!-- 头像 -->
          <img class="user-avatar" :src="row.avatar" alt=""/>
          <div class="comment-content">
            <div class="info">
              <!-- 昵称 -->
              <span class="comment-name">{{ row.nickname }}</span>
              <!-- 时间 -->
              <div>{{ formatDate(row.createTime) }}</div>
            </div>
            <!-- 内容 -->
            <span class="content" v-html="row.commentContent"></span>
          </div>
        </div>
      </template>
    </AutoScrollList>
  </div>
</template>

<script setup lang="ts">
import {getRecentComment} from "@/api/comment";
import {RecentComment} from "@/api/comment/types";
import {formatDate} from "@/utils/date";

const commentList = ref<RecentComment[]>([]);

onMounted(async () => {
  commentList.value = await getRecentComment();
});
</script>

<style lang="scss" scoped>
.comment-title {
  font-size: 1.2em;
}

.comment-name {
  overflow: hidden;
  text-overflow: ellipsis;
  display: inherit;
}

.comment-item {
  display: flex;
  align-items: center;
  padding: 0.375rem 0;
}

.user-avatar {
  width: 4.2rem;
  height: 4.2rem;
  border-radius: 0.75rem;
}

.comment-content {
  width: calc(100% - 4.2rem);
  padding-left: 0.625rem;

  .info {
    font-size: 5%;
    line-height: 1rem;
    color: var(--grey-6);
  }

  .content {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    font-size: 95%;
    line-height: 1.5;
    overflow: hidden;
  }
}
</style>
