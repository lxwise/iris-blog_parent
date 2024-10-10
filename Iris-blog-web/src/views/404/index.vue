<template>
	<div class="page-header">
		<h1 class="page-title">页面不存在</h1>
		<img class="page-cover" src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641"
				 alt="">
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container bg-img">
			<div class="notfound">亲,是迷路的吗😘</div>
		</div>
	</div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.notfound {
	width: 18.75rem;
	height: 22.625rem;
	text-align: center;
	margin: 6.25rem auto;
}

.bg-img {
	background: url("@/assets/images/404.svg") no-repeat 50% 100%; /* 水平居中，垂直方向上位于元素的最底部 */
	background-position-y: calc(100% + 12.5rem); /* 或者使用 background-position-y 属性直接增加垂直偏移 */
}
</style>
