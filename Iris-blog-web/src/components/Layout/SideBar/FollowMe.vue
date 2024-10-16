<template>
	<div class="side-card">
		<!-- Title -->
		<div class="comment-title">
			<svg-icon icon-class="badge" size="1.475rem"></svg-icon>
			关注我
		</div>
		<!-- Contact List -->
		<ul class="contact-list">
			<li class="contact-item qq">
				<svg-icon icon-class="qq"></svg-icon>
				<a href="//wpa.qq.com/msgrd?v=3&amp;uin=1444073716&amp;site=qq&amp;menu=yes" target="_blank">
					1444073716
				</a>
				<span @click="copy('1444073716')" class="label">QQ号</span>
			</li>
			<li class="contact-item github">
				<svg-icon icon-class="github"></svg-icon>
				<a href="https://github.com/lxwise" target="_blank">https://github.com/lxwise</a>
				<span @click="copy('https://github.com/lxwise')" class="label">github</span>
			</li>
			<li class="contact-item gitee">
				<svg-icon icon-class="gitee"></svg-icon>
				<a href="https://gitee.com/lxwise" target="_blank">https://gitee.com/lxwise</a>
				<span @click="copy('https://gitee.com/lxwise')" class="label">gitee</span>
			</li>
			<li class="contact-item email">
				<svg-icon icon-class="email"></svg-icon>
				<a href="mailto:1444073716@qq.com" target="_blank">1444073716@qq.com</a>
				<span @click="copy('1444073716@qq.com')" class="label">邮箱</span>
			</li>
			<li class="contact-item wechat">
				<svg-icon icon-class="wechat"></svg-icon>
				<span>lstar980</span>
				<span @click="copy('lstar980')" class="label">微信</span>
			</li>
			<li class="contact-item qqgroup">
				<svg-icon icon-class="qqGroup" color="#ea9518"></svg-icon>
				<span>836922638</span>
				<span @click="copy('836922638')" class="label">QQ群</span>
			</li>
		</ul>
	</div>
</template>

<script setup lang="ts">

const copy = (text: string) => {
	navigator.clipboard.writeText(text).then(() => {
		window.$message?.success('已复制到剪贴板')
	}).catch(() => {
		window.$message?.error('复制失败')
	})
}
</script>

<style scoped lang="scss">
.side-card {
	background-color: var(--background-color);
	padding: 16px;
	border-radius: 8px;
}

.comment-title {
	font-size: 1.2em;
	margin-bottom: 16px;
	display: flex;
	align-items: center;

	svg {
		margin-right: 8px;
	}
}


.contact-list {
	list-style: none;
	padding: 8px;

	.contact-item {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-bottom: 12px;
		border-radius: 4px;
		border: 1px solid #dcdcdc;
		padding: 8px;
		position: relative;
		font-size: 0.75rem;

		svg-icon {
			margin-right: 8px; /* 在图标和文本之间添加间距 */
		}

		a, span:first-of-type {
			flex: 1;
			text-decoration: none;
			color: inherit;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
			margin-left: 8px; /* 在图标和 a/span 元素之间添加间距 */
		}

		.label {
			color: white;
			padding: 4px 8px;
			border-top-right-radius: 4px;
			border-bottom-right-radius: 4px;
			cursor: pointer;
			position: absolute;
			right: 0;
			top: 0;
			bottom: 0;
			display: flex;
			align-items: center;
			justify-content: center;
			width: 50px; /* 初始宽度 */
			min-width: 50px;
			background-color: #409eff;
			transition: width 0.25s ease-in-out, background-color 0.25s ease-in-out; /* 平滑动画 */

			&:hover {
				width: 80px; /* 悬停时扩展宽度 */
			}

			/* 工具提示样式 */
			&::after {
				content: '点击复制';
				position: absolute;
				bottom: 100%;
				left: 50%;
				transform: translateX(-50%);
				background-color: black;
				color: white;
				padding: 4px 6px;
				border-radius: 4px;
				opacity: 0;
				white-space: nowrap;
				pointer-events: none;
				transition: opacity 0.25s ease; /* 平滑淡入 */
				font-size: 12px;
			}

			&:hover::after {
				opacity: 1; /* 工具提示在悬停时淡入 */
			}
		}

		&.qq,
		&.qqgroup {
			border-color: #409eff;

			.label {
				background-color: #409eff;
			}
		}

		&.github {
			border-color: #606266;

			.label {
				background-color: #606266;
			}
		}

		&.gitee {
			border-color: #e32c32;

			.label {
				background-color: #e32c32;
			}
		}

		&.email {
			border-color: #e98f36;

			.label {
				background-color: #e98f36;
			}
		}

		&.wechat {
			border-color: #67c23a;

			.label {
				background-color: #67c23a;
			}
		}
	}
}





</style>
