<template>
	<div class="reply-box" v-if="show">
		<div class="box-normal">
			<div class="reply-box-avatar">
				<img class="iris-avatar" v-if="user.avatar" :src="user.avatar" alt=""/>
				<img class="iris-avatar" v-else :src="blog.blogInfo.touristAvatar" alt=""/>
			</div>
			<div class="reply-box-warp">
				<V3Emoji
					@click-emoji="handleEmoji"
					size="small"
					:recent="true"
					:text-area="true"
					fix-pos="downleft"
					v-model="commentContent" :style="sendActive ? lineStyle : ''"
					@input.prevent="inputActive" :placeholder="placeholderText"
				></V3Emoji>
			</div>
			<div class="reply-box-send" :class="sendActive ? 'send-active' : ''" @click="handleAdd">
				评论
			</div>
		</div>
		<div class="box-expand">

		</div>
	</div>
</template>

<script setup lang="ts">

//表情包处理
import V3Emoji from "vue3-emoji";
import "vue3-emoji/dist/style.css";

import {addComment} from "@/api/comment";
import {CommentForm} from "@/api/comment/types";
import {useAppStore, useBlogStore, useUserStore} from "@/store";

const user = useUserStore();
const blog = useBlogStore();
const app = useAppStore();
const lineStyle = {
	lineHeight: "normal",
	borderColor: "var(--color-blue)",
	backgroundColor: "var(--grey-0)",
};
const emit = defineEmits(["reload"]);
const props = defineProps({
	commentType: {
		type: Number,
	},
	show: {
		type: Boolean,
		default: true,
	},
	typeId: {
		type: Number,
	},
});
const data = reactive({
	nickname: "",
	sendActive: false,
	show: props.show,
	commentContent: "",
	emojiType: 0,
	commentForm: {
		typeId: props.typeId,
		commentType: props.commentType,
		parentId: undefined,
		replyId: undefined,
		toUid: undefined,
		commentContent: "",
	} as CommentForm,
});
const {nickname, sendActive, show, commentContent, emojiType, commentForm} = toRefs(data);
const placeholderText = computed(() =>
	nickname.value ? `回复 @${nickname.value}：` : "发一条友善的评论"
);
const inputActive = () => {
	if (commentContent.value.length == 0) {
		sendActive.value = false;
	} else {
		sendActive.value = true;
	}
};
const handleEmoji = (key: string) => {
	commentContent.value += key;
	sendActive.value = true;
};
const handleType = (key: number) => {
	emojiType.value = key;
};
const handleAdd = async () => {
	if (!user.id) {
		app.setLoginFlag(true);
		return;
	}
	if (commentContent.value.trim() == "") {
		window.$message?.error("评论不能为空");
		return;
	}
	// 解析表情
	commentForm.value.commentContent = commentContent.value.replace(/\[.+?\]/g, (str) => {

		return str;
	});

	await addComment(commentForm.value)
	sendActive.value = false;
	commentContent.value = "";
	if (blog.blogInfo.commentCheck) {
		window.$message?.warning("评论成功，正在审核中");
	} else {
		window.$message?.success("评论成功");
	}
	// 重新加载评论列表
	emit("reload");
};
const setReply = (flag: boolean) => {
	show.value = flag;
};
defineExpose({commentForm, nickname, setReply});
</script>

<style scoped></style>
