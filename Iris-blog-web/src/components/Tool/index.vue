<template>
	<div class="tool" :style="y > 0 ? show : ''">
		<div class="item" v-if="route.meta.title == '文章'" @click="handleSide">
			<svg-icon :icon-class="!app.sideFlag ? 'heng' : 'shou'" size="2rem"></svg-icon>
		</div>
		<div class="item" v-if="commentShow(route.name as string)" @click="handleToComment">
			<svg-icon icon-class="comments" size="1.75rem"></svg-icon>
		</div>
		<div class="item back-to-top" style="margin-top:0.2rem">
			<svg-icon style="cursor: pointer;" :icon-class="isDark ? 'sun' : 'moon'" @click="toggle()"
								size="1.75rem"></svg-icon>
		</div>

		<div class="item back-to-top" @click="handleBackToTop" style="margin-top:0.8rem">
			<svg-icon icon-class="back-to-top" size="1.75rem"></svg-icon>
			<span class="count">{{ process }}%</span>
		</div>
	</div>
</template>

<script setup lang="ts">
import {useAppStore} from "@/store";
import {useDark, useEventListener, useScroll, useToggle} from "@vueuse/core";

const app = useAppStore();
const route = useRoute();
const process = ref(0);
const show = reactive({
	transform: "translateX(-45px)"
});
const {y} = useScroll(window);
const commentList = ["article", "friend", "talkInfo"];
const commentShow = computed(() => (value: string) => commentList.includes(value));
const isDark = useDark({
	selector: 'html',
	attribute: 'theme',
	valueDark: 'dark',
	valueLight: 'light',
})
const toggle = useToggle(isDark);
useEventListener(document, "scroll", () => {
	process.value = Number(
		(
			(window.pageYOffset /
				(document.documentElement.scrollHeight - window.innerHeight)) *
			100
		).toFixed(0)
	)
});
const handleSide = () => {
	app.sideFlag = !app.sideFlag;
};
const handleToComment = () => {
	document.getElementById("reply-wrap")?.scrollIntoView({
		block: "start",
		inline: "nearest",
		behavior: 'smooth'
	});
}
const handleBackToTop = () => {
	window.scrollTo({
		top: 0,
		behavior: 'smooth'
	});
};

</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.tool {
	position: fixed;
	right: -40px;
	bottom: 85px;
	z-index: 9;
	color: var(--color-blue1);
	transition: all 0.5s;

	.item {
		@include flex;
		flex-direction: column;
		width: 2.65rem;
		padding: 0.3125rem 0 0;
		opacity: 1;
		cursor: pointer;
		transition: all 0.2s ease-in-out 0s;

		.count {
			font-size: 1.15em;
		}

		&:hover {
			opacity: 0.9;
		}
	}
}
</style>
