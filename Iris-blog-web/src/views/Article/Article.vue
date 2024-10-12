<template>
	<div class="page-header" v-if="article">
		<div class="page-title">
			<h1 class="article-title">{{ article.articleTitle }}</h1>
			<div class="article-meta">
				<div class="first-meta">
          <span><svg-icon icon-class="calendar" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">发表于 </span>{{ formatDate(article.createTime) }}
          </span>
					<span class="item"><svg-icon icon-class="eye" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">阅读量 </span>{{ article.viewCount }}</span>
					<span class="item"><svg-icon icon-class="like" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">点赞量 </span>{{ article.likeCount === null ? article.likeCount : 0 }}</span>
				</div>
				<div class="second-meta">
          <span><svg-icon icon-class="edit" size="0.9rem" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">字数统计 </span>{{ count(wordNum) }} 字
          </span>
					<span class="item"><svg-icon icon-class="clock" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">阅读时长 </span>{{ readTime }} 分钟
          </span>
					<span class="item">
            <svg-icon icon-class="category" style="margin-right:0.15rem;"></svg-icon>{{
							article.categoryName
						}}
          </span>
				</div>
			</div>
		</div>
		<img class="page-cover" v-if="article.articleCover" :src="article.articleCover" alt="">
		<!-- 波浪 -->
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="main-container" v-if="article">
			<div class="left-container" :class="app.sideFlag ? 'test' : ''">
				<div class="article-container">
					<v-md-preview ref="articleRef" class="md" v-viewer
												:text="(article.readType != 0 && !isUserLike(article.id)) ? getPreviewContent(article.articleContent) : article.articleContent"/>
					<!--	隐藏文章内容	-->
					<div v-if="(article.readType != 0 && !isUserLike(article.id))" class="articleHidden">
						<div class="item-box">
            <span>
              {{ "点赞阅读" }}
            </span>
							<div class="neirong">
								<svg-icon icon-class="Lock" size="1rem" color="black"></svg-icon>
								以下内容已隐藏，请{{ "点赞" }}后查看
							</div>
							<n-button
								v-if="article.readType == 1"
								@click="checkLikeAndComment('请到文章内容底部完成点赞')"
								class="btn"
								type="info"
								size="small"
							>立即点赞
							</n-button>
						</div>
					</div>
					<div class="article-post">
						<div class="tag-share">
							<router-link :to="`/tag/${tag.id}`" class="article-tag" v-for="tag in article.tagVOList" :key="tag.id">
								<svg-icon icon-class="tag" size="0.8rem"></svg-icon>
								{{ tag.tagName }}
							</router-link>
							<Share class="share-info" :url="articleHref" :title="article.articleTitle"></Share>
						</div>
						<div class="reward">
							<button class="btn" :class="isLike(article.id)" @click="like">
								<svg-icon icon-class="like" size="0.9rem"></svg-icon>
								点赞
								<span>{{ article.likeCount }}</span>
							</button>
							<n-popover trigger="click" v-if="blog.blogInfo.isReward">
								<template #trigger>
									<button class="btn reward-btn">
										<svg-icon icon-class="qr_code" size="0.9rem"></svg-icon>
										打赏
									</button>
								</template>
								<div class="reward-all">
                  <span>
                    <img class="reward-img" v-lazy="blog.blogInfo.wechatCode"/>
                    <div class="reward-desc">微信</div>
                  </span>
									<span style="margin-left: 0.3rem;">
                    <img class="reward-img" v-lazy="blog.blogInfo.alipayCode"/>
                    <div class="reward-desc">支付宝</div>
                  </span>
								</div>
							</n-popover>
							<p class="tea" v-if="blog.blogInfo.isReward">请我喝[茶]~(￣▽￣)~*</p>
						</div>
						<div class="copyright">
							<ul>
								<li class="author">
									<svg-icon icon-class="author" size="0.9rem" style="margin-right:0.3rem"></svg-icon>
									<strong>本文作者： </strong>{{ blog.blogInfo.siteAuthor }}
								</li>
								<li class="link">
									<svg-icon icon-class="article_link" size="0.9rem" style="margin-right:0.3rem"></svg-icon>
									<strong>本文链接：</strong>
									<a :href="articleHref">{{ articleHref }}</a>
								</li>
								<li class="license">
									<svg-icon icon-class="article_share" size="0.8rem" style="margin-right:0.3rem"></svg-icon>
									<strong>版权声明： </strong>本站所有文章除特别声明外，均采用
									<a href="https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh" target="_blank">CC
										BY-NC-SA 4.0</a>
									许可协议。转载请注明文章出处！
								</li>
							</ul>
						</div>
						<!-- 上下文 -->
						<div class="post-nav">
							<div class="item" v-if="article.lastArticle">
								<router-link :to="`/article/${article.lastArticle?.id}`" class="post-cover"
														 :style="articleCover(article.lastArticle.articleCover)">
									<span class="post-last-next">上一篇</span>
									<h3 class="post-title">{{ article.lastArticle.articleTitle }}</h3>
								</router-link>
							</div>
							<div class="item" v-if="article.nextArticle">
								<router-link :to="`/article/${article.nextArticle?.id}`" class="post-cover"
														 :style="articleCover(article.nextArticle.articleCover)">
									<span class="post-last-next">下一篇</span>
									<h3 class="post-title">{{ article.nextArticle.articleTitle }}</h3>
								</router-link>
							</div>
						</div>
						<CommentList :comment-type="commentType"></CommentList>
					</div>
				</div>
			</div>
			<div class="right-container" :class="app.sideFlag ? 'temp' : ''">
				<Author class="side-card"></Author>
				<div class="side-card">
					<Catalog v-if="articleLoaded" :domRef="articleRef"></Catalog>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import {getArticle, likeArticle} from "@/api/article";
import {ArticleInfo, ArticlePagination} from "@/api/article/types";
import {useAppStore, useBlogStore, useUserStore} from "@/store";
import {formatDate} from "@/utils/date";
import {Share} from 'vue3-social-share';
import 'vue3-social-share/lib/index.css';

const user = useUserStore();
const app = useAppStore();
const blog = useBlogStore();
const articleRef = ref();
const route = useRoute();
const articleHref = window.location.href;
const data = reactive({
	articleLoaded: false,
	wordNum: 0,
	readTime: 0,
	commentType: 1,
	article: {
		id: 0,
		readType: 1,
		articleCover: "",
		articleTitle: "",
		articleContent: "",
		articleType: 0,
		viewCount: 0,
		likeCount: 0,
		categoryId: 0,
		categoryName: "",
		tagVOList: [],
		createTime: "",
		lastArticle: {} as ArticlePagination,
		nextArticle: {} as ArticlePagination,
		updateTime: ""
	} as ArticleInfo,
});
const {articleLoaded, wordNum, readTime, commentType, article} = toRefs(data);
const articleCover = computed(() => (cover: string) => 'background-image:url(' + cover + ')');
const isLike = computed(() => (id: number) => user.articleLikeSet.indexOf(id) != -1 ? "like-btn-active" : "like-btn");
const isUserLike = computed(() => (id: number) => user.articleLikeSet.indexOf(id) != -1);
const count = (value: number) => {
	if (value >= 1000) {
		return (value / 1000).toFixed(1) + "k";
	}
	return value;
};
const deleteHTMLTag = (content: string) => {
	return content
		.replace(/<\/?[^>]*>/g, "")
		.replace(/[|]*\n/, "")
		.replace(/&npsp;/gi, "");
};
const like = async () => {
	if (!user.id) {
		app.setLoginFlag(true);
		return;
	}
	let id = article.value.id;
	await likeArticle(id);
	//判断是否点赞
	if (user.articleLikeSet.indexOf(id) != -1) {
		article.value.likeCount -= 1;
	} else {
		article.value.likeCount += 1;
	}
	user.articleLike(id);

};
onMounted(async () => {
	article.value = await getArticle(Number(route.params.id));
	document.title = article.value.articleTitle;
	wordNum.value = deleteHTMLTag(article.value.articleContent).length;
	readTime.value = Math.round(wordNum.value / 400);
	articleLoaded.value = true;
})

/*
* 权限查看
*/
const getPreviewContent = (content: any) => {
	// 获取预览内容的函数，只显示前250个字符
	return content.slice(0, 250) + '......';
}
/*
* 提示用户是否可以进行点赞和评论
*/
const checkLikeAndComment = (desc: string) => {
	window.$message?.warning(desc);
}
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.article-container {
	border-radius: 0.5rem;
	overflow: hidden;
	box-shadow: 0 0 1rem var(--box-bg-shadow);
}

.article-post {
	margin: 0 2rem;
	padding-bottom: 1rem;
}

.article-title {
	font-weight: 500;
	font-size: 2.5rem;
	letter-spacing: 0.125rem;
	text-align: center;
	color: var(--header-text-color);
}

.article-meta {
	@include flex;
	flex-direction: column;
	font-size: 0.875rem;

	.item {
		margin-left: 0.625rem;
	}
}

.tag-share {
	display: flex;
	align-items: center;

	.share-info {
		margin-left: auto;
	}
}

.reward {
	margin: 1.25rem auto;
	padding: 0.625rem 0;
	text-align: center;

	.btn {
		border-radius: 0.3125rem;
		color: var(--grey-0);
		cursor: pointer !important;
		padding: 0 0.9375rem;
		font: inherit;
	}

	.like-btn-active {
		background: var(--primary-color);
	}

	.like-btn {
		background: #999;
	}

	.reward-btn {
		position: relative;
		margin-left: 1rem;
		background: var(--primary-color);
	}

	.tea {
		font-size: 0.8125em;
		color: var(--grey-5);
		margin-top: 0.5rem;
	}
}

.reward-all {
	display: flex;
	align-items: center;
}

.reward-img {
	width: 330px;
	height: 330px;
	display: block;
}

.reward-desc {
	margin: -5px 0;
	color: #858585;
	text-align: center;
}

.copyright {
	font-size: 0.75em;
	padding: 1rem 2rem;
	margin-bottom: 2.5rem;
	border-radius: 0.625rem;
	background: var(--grey-2);
	color: var(--grey-6);
}

.post-nav {
	display: flex;
	margin-bottom: 2.5rem;
	border-radius: 0.625rem;
	overflow: hidden;

	.item {
		width: 50%;
	}

	.post-cover {
		display: flex;
		flex-direction: column;
		color: var(--header-text-color);
		padding: 1.25rem 2.5rem;
		background-size: cover;
		animation: blur 0.8s ease-in-out forwards;

		&:before {
			content: "";
			position: absolute;
			width: 100%;
			height: 100%;
			background: linear-gradient(135deg, #434343, #000);
			opacity: 0.5;
			transition: all 0.2s ease-in-out 0s;
			z-index: -1;
			top: 0;
			left: 0;
		}
	}

	.post-last-next {
		font-size: 0.8125rem;
	}
}

.post-cover:hover::before {
	opacity: 0.4;
}

@media (max-width: 767px) {
	.article-title {
		font-size: 1.5rem;
	}

	.article-meta .text {
		display: none;
	}

	.article-post {
		margin: 0 0.5rem;
	}

	.post-nav {
		flex-direction: column;
	}

	.post-nav .item {
		width: 100%;
	}

	.reward-img {
		width: 305px;
		height: 305px;
	}

}


.articleHidden {
	background: var(--article-srect-background);
	position: relative;
	height: 210px;
	padding: 5px;

	&::before {
		content: "";
		position: absolute;
		top: -80px;
		left: 0;
		width: 100%;
		height: 80px;
		z-index: 2;
		background: linear-gradient(180deg, rgba(55, 55, 55, 0), #ccc);
	}

	.item-box {
		border-radius: 10px;
		background-color: var(--grey-1);
		height: 150px;
		margin-left: 10px;
		margin-right: 10px;
		margin-top: 10px;
		margin-bottom: 10px;
		overflow: hidden;

		span {
			background: linear-gradient(135deg, #ff74cd 10%, #ec7d0b);
			border-top-left-radius: 10px;
			border-bottom-right-radius: 10px;
			padding: 5px;
			font-size: 0.9rem;
			color: #fff;
		}

		.neirong {
			text-align: center;
			margin-top: 15px;
			color: var(--text-color);
			font-size: 0.9rem;
		}

		.btn {
			margin: 0 auto;
			display: block;
			margin-top: 20px;
		}
	}
}

</style>
