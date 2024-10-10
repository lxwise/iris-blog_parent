<template>
	<div class="">
		<!-- 分页按钮 -->
		<div
			class="page hand-style no-select"
			v-if="pageNo < pages"
			@click="$emit('changePage', pageNo + 1 )"
		>
			加载更多
		</div>
		<div class="tips" v-else>
			<div class="divider"></div>
			<div class="content">😛我也是有底线的.....</div>
			<div class="divider"></div>
		</div>
	</div>
</template>

<script setup name='page'>

const props = defineProps({
	pageNo: {
		type: Number,
		default: 0,
	},
	pages: {
		type: Number,
		default: 0,
	},
	changePage: {
		type: Function,
		default: () => {
		},
	},
});

</script>

<style lang='scss' scoped>
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

.tips {
	text-align: center;
	color: var(--text-color);
	display: flex;
	align-items: center;

	.divider {
		display: inline-block;
		width: 100%;
		background-color: var(--grey-3);
		height: 1px;
	}

	.content {
		display: inline-block;
		width: 400px;
		font-size: 14px;
	}
}
</style>
