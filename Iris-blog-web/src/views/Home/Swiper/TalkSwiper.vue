<template>
	<router-link v-if="talkList.length > 0" class="talk-swiper" to="/talk">
		<svg-icon class="laba" icon-class="laba" size="1.25rem"></svg-icon>
		<swiper :autoplay="{ delay: 3000, disableOnInteraction: false, }" :direction="'vertical'" :loop="true"
						:modules="modules" :slides-per-view="1"
						:speed="2000" class="swiper-container">
			<swiper-slide v-for="(talk, index) in talkList" :key="index">
				<div class="slide-content" v-html="talk"></div>
			</swiper-slide>
		</swiper>
		<svg-icon class="arrow" icon-class="right-arrow"></svg-icon>
	</router-link>
</template>

<script setup lang="ts">
import {getTalkHomeList} from '@/api/talk';
import {Autoplay} from 'swiper';
import {Swiper, SwiperSlide} from 'swiper/vue';
// 自动播放
const modules = [Autoplay];
const talkList = ref<string[]>([]);
onMounted(async () => {
	talkList.value = await getTalkHomeList();
});
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.talk-swiper {
	@include flex;
	margin: 0 0.5rem;
	padding: 0.6rem 1rem;
	font-size: 0.9375rem;
	border-radius: 0.5rem;
	box-shadow: 0 0.625rem 1.875rem -0.9375rem var(--box-bg-shadow);
	transition: all 0.2s ease-in-out 0s;

	&:hover {
		box-shadow: 0 0 2rem var(--box-bg-shadow);
	}
}

.swiper-container {
	width: 100%;
	height: 1.5625rem;
	line-height: 1.5625rem;
	border-radius: 0.75rem;
}

.slide-content {
	width: 100%;
	height: 100%;
	text-align: center;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

.arrow {
	animation: 1.5s passing infinite;
}
.laba {
  animation: 1.5s scaleEffect infinite alternate;
}

@keyframes passing {
	0% {
		transform: translateX(-50%);
		opacity: 0;
	}

	50% {
		transform: translateX(0);
		opacity: 1;
	}

	100% {
		transform: translateX(50%);
		opacity: 0;
	}
}

@keyframes scaleEffect {
  0% {
    transform: scale(1); /* 初始大小 */
  }
  50% {
    transform: scale(1.5); /* 放大到1.2倍 */
  }
  100% {
    transform: scale(1); /* 恢复初始大小 */
  }
}
</style>
