<template>
	<div class="page-header">
		<h1 class="page-title">
			<div class="icon-and-text">
				<svg-icon icon-class="info" size="2rem"></svg-icon>
				关于本站
			</div>
		</h1>
		<img class="page-cover" src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641"
				 alt=""/>
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container">
			<div class="avatar-box">
				<img class="author-avatar" :src="blog.blogInfo.authorAvatar"/>
			</div>
			<v-md-preview class="md" :text="blog.blogInfo.aboutMe"></v-md-preview>
		</div>
	</div>
</template>

<script setup lang="ts">
import {useBlogStore} from "@/store";

const blog = useBlogStore();
</script>

<style scoped>
.author-avatar {
	width: 110px;
	height: 110px;
	border-radius: 50%;
	transition: all 0.5s;
}

.author-avatar:hover {
	transform: rotate(360deg);
}
</style>
