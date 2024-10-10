<template>
	<div>
		<img class="user-avatar" :src="user.avatar" @click="dialogVisible = true"/>
		<n-modal class="bg" v-model:show="dialogVisible" preset="dialog" :show-icon="false" transform-origin="center"
						 :block-scroll="false" :closable="false" @after-leave="handleClose">
			<div style="width: 100%; height: 300px">
				<vue-cropper ref="cropperRef" :img="options.img" :info="true" :autoCrop="options.autoCrop"
										 :autoCropWidth="options.autoCropWidth" :autoCropHeight="options.autoCropHeight"
										 :fixedBox="options.fixedBox"
										 :outputType="options.outputType"></vue-cropper>
			</div>
			<n-upload class="mt-4" action="#" accept="image/*" :show-file-list="false" :custom-request="customUpload">
				<n-button>点击上传</n-button>
			</n-upload>
			<template #action>
				<n-button color="#3e999f" @click="dialogVisible = false">
					取消
				</n-button>
				<n-button color="#e9546b" @click="openDialog">
					提交
				</n-button>

				<n-modal
					v-model:show="showModal"
					preset="dialog"
					type="warning"
					title="修改头像"
					content="确定要修改头像吗?"
					positive-text="确认"
					negative-text="取消"
					@positive-click="handleUpload"
				/>
			</template>
		</n-modal>
	</div>
</template>

<script setup lang="ts">
import {updateUserAvatar} from "@/api/user";
import {useUserStore} from "@/store";
import {UploadCustomRequestOptions} from "naive-ui";
import {VueCropper} from 'vue-cropper';
import 'vue-cropper/dist/index.css';

const fileName = ref("");
const user = useUserStore();
const dialogVisible = ref(false);
const cropperRef = ref();
const options = reactive({
	img: user.avatar, // 裁剪图片的地址
	autoCrop: true, // 是否默认生成截图框
	autoCropWidth: 200, // 默认生成截图框宽度
	autoCropHeight: 200, // 默认生成截图框高度
	fixedBox: true, // 固定截图框大小 不允许改变
	outputType: "png", // 默认生成截图为PNG格式
});

/**打开模态框 */
const showModal = ref(false);
const openDialog = () => {
	showModal.value = true;
}
const customUpload = ({file}: UploadCustomRequestOptions) => {
	// 获取文件名
	fileName.value = file.name;
	// // 获取文件类型
	// const fileType = file.type;
	// console.log('文件类型:', fileType);
	const reader = new FileReader();
	reader.readAsDataURL(file.file as File);
	reader.onload = () => {
		options.img = reader.result as string;
	};
};
const handleClose = () => {
	options.img = user.avatar;
};
const handleUpload = () => {
	cropperRef.value.getCropBlob((blob: Blob) => {
		let formData = new FormData();
		formData.append("file", blob, fileName.value?fileName.value:"avatar.png");
		updateUserAvatar(formData).then(({data}) => {
			if (data) {
				// 更新用户头像并触发视图刷新
				options.img = data.url;
				user.avatar = data.url;  // 将新的头像 URL 赋值给用户头像

				// 关闭裁剪对话框
				dialogVisible.value = false;

				// 强制触发视图更新（必要时）
				// 防止缓存
				user.avatar = `${data.url}?t=${new Date().getTime()}`;
				nextTick(() => {
					console.log("头像更新成功:", user.avatar);
				});
			}
		}).catch((error) => {
			console.error("上传失败", error);
		});
	});
};

</script>

<style scoped>
.user-avatar {
	width: 140px;
	height: 140px;
	border-radius: 50%;
}
</style>
