<template>
	<div class="page-header">
		<h1 class="page-title">
			<div class="icon-and-text">
				<svg-icon icon-class="feedback" size="2rem"></svg-icon>
				用户反馈({{ count }})
			</div>
		</h1>
		<img alt="" class="page-cover"
				 src="https://ik.imagekit.io/irisblog/home.jpg?updatedAt=1723000780641">
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container" style="width: 85rem">
			<div style="display: flex; justify-content: space-between; align-items: center;">
				<div>
					<svg-icon icon-class="feedback" size="1.25rem"></svg-icon>
					<span style="font-size: 1.15rem">反馈列表</span>
				</div>
				<div>
					<n-button quaternary round type="info" size="medium" @click="openDialog">
						<svg-icon icon-class="add" size="1.75rem"></svg-icon>
						<span style="font-size: 1.15rem">添加反馈</span>
					</n-button>
				</div>
			</div>

			<div v-for="data in backList"
					 :key="data.id"
					 v-animate="['slideUpBigIn']" class="notice-item">
				<div class="notice-meta">
					<div class="notice-info">
						<span class="feedback-info">
							{{ data.backType ? '缺陷' : '需求' }}
							<svg-icon :icon-class="data.status ? 'solve' : 'unsolved'"
												style="margin-left: 0.4rem; color: #fd2b18"></svg-icon>
							<span v-if="data.status" style="color: #70B603;">已解决</span>
							<span v-else style="color: #fd2b18;">未解决</span>
						</span>
						<span class="talk-time">{{ formatDateTime(data.createTime) }}</span>
					</div>
				</div>
				<!-- 内容 -->
				<div class="talk-content">
					<span v-html="data.content"></span>
					<n-image
						width="100"
						:src="data.imgUrl"
					/>
				</div>
			</div>

			<n-modal
				class="dialog"
				type="info"
				title="添加反馈"
				v-model:show="dialogFormVisible"
				preset="dialog"
				:mask-closable="false">

				<n-form :model="form" :rules="rules" ref="formRef">
					<n-form-item
						label="反馈标题"
						:label-width="formLabelWidth"
						path="title"
					>
						<n-input v-model:value="form.title" placeholder="请输入反馈标题"></n-input>
					</n-form-item>
					<n-form-item
						label="反馈内容"
						:label-width="formLabelWidth"
						path="content"
					>
						<n-input v-model:value="form.content" type="textarea" placeholder="请输入反馈内容"></n-input>
					</n-form-item>

					<n-form-item
						label="反馈类型"
						:label-width="formLabelWidth"
						path="backType"
					>
						<n-radio-group v-model:value="form.backType" name="反馈类型">
							<n-space>
								<n-radio value=false>
									需求
								</n-radio>
								<n-radio value=true>
									缺陷
								</n-radio>
							</n-space>
						</n-radio-group>
					</n-form-item>

<!--					<n-form-item-->
<!--						label="反馈图片"-->
<!--						:label-width="formLabelWidth"-->
<!--						path="imgUrl"-->
<!--					>-->
<!--						<n-upload-->
<!--							class="mt-4"-->
<!--							action="#"-->
<!--							accept="image/*"-->
<!--							:file-list="fileList"-->
<!--							:on-update:file-list="handleFileChange"-->
<!--						>-->
<!--							<n-button text><svg-icon icon-class="cloud_upload" size="3rem"></svg-icon></n-button>-->
<!--						</n-upload>-->
<!--					</n-form-item>-->
				</n-form>
				<template #action>
					<div class="dialog-footer">
						<n-button @click="dialogFormVisible = false">取 消</n-button>
						<n-button style="margin-left: 0.5rem;" type="info" @click="handleAddFeedback">确 定</n-button>
					</div>
				</template>
			</n-modal>
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
import {AppFeedBackDTO, FeedBackVO} from "@/api/freedback/types";
import {getFeedBackList, saveFeedback} from "@/api/freedback";
import {NButton, NForm, NFormItem, NInput} from "naive-ui";
import {useAppStore, useUserStore} from "@/store";

const user = useUserStore();
const app = useAppStore();

const route = useRoute();
const data = reactive({
	pages: 0,
	queryParams: {
		pageNo: 1,
		pageSize: 5,
		action: {}
	} as PageQuery,
	backList: [] as FeedBackVO[],
	count: 0
});
const {
	pages,
	queryParams,
	backList,
	count
} = toRefs(data);

watch(
	() => queryParams.value.pageNo,
	() => {
		getList();
	}
);

const getList = async () => {
	try {
		const response = await getFeedBackList(queryParams.value);
		backList.value = [...backList.value, ...response.records];
		pages.value = response.totalPages;
		count.value = response.total;
	} finally {
	}
};

onMounted(async () => {
	await getList();
});

const handlePage = (pageNo: number) => {
	queryParams.value.pageNo = pageNo;
};

/**
 * 添加反馈相关
 *
 */
const dialogFormVisible = ref(false);
const form = ref<AppFeedBackDTO>(<AppFeedBackDTO>{});

const formLabelWidth = ref('80px');
const rules = ref({
	title: [{required: true, message: '请输入反馈标题', trigger: 'blur'}],
	content: [{required: true, message: '请输入反馈内容', trigger: 'blur'}],
	backType: [{required: true, message: '请选择反馈类型', trigger: 'blur'}],
	image: [{required: true, message: '请输入网站头像', trigger: 'blur'}],
});
const formRef = ref();
/**打开表单 */
const openDialog = () => {
	if (!user.id) {
		window.$message?.warning('请先登录');
		app.setLoginFlag(true);
		return;
	}
	form.value = {};
	dialogFormVisible.value = true;
}

/**提交 */
const handleAddFeedback = async () => {
	// 校验表单
	if (!formRef) return
	const valid = await formRef.value.validate()
	if (!valid) return
	try {
		await saveFeedback(form.value);
		dialogFormVisible.value = false;
		window.$message?.success('提交成功，感谢您的反馈,作者菌正在加班处理中...');
		await getList();
	} catch (e) {
		console.log("提交失败:" + form.value)
		window.$message?.error('提交失败,请重试');
	}

}


const fileList = ref([])

const handleFileChange = (newFileList: any[]) => {
	if (newFileList.length > 0) {
		// 只保留最新的上传文件
		fileList.value = [newFileList[newFileList.length - 1]]
	} else {
		fileList.value = newFileList
	}
}
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

.feedback-info {
	display: flex;
	align-items: center;
	font-size: 0.875rem;
	font-weight: 600;
	margin-right: 0.3125rem;
	color: var(--text-color);
}
</style>
