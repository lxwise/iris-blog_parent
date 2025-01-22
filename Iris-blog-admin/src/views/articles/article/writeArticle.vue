<template>
  <el-form :model="articleForm" :rules="formRules" label-width="100px" v-loading="formLoading">
    <el-form-item label="文章标题:" prop="title">
      <el-input v-model="articleForm.title" placeholder="请输入文章标题" />
    </el-form-item>
    <el-form-item label="文章摘要:" prop="intro">
      <el-input type="textarea" v-model="articleForm.intro" placeholder="请输入文章简介" />
    </el-form-item>
    <el-button type="danger" style="margin-bottom: 20px" @click="openModel">发布文章</el-button>
    <el-button type="primary" style="margin-bottom: 20px" @click="triggerFileInput" v-hasPermi="['system:article:export']">导入文章</el-button>
    <!-- 文章内容 -->
    <md-editor
      style="height: calc(100vh - 200px); min-height: 300px"
      ref="editorRef"
      v-model="articleForm.content"
      :theme="isDark ? 'dark' : 'light'"
      :toolbars="toolbars"
      @on-upload-img="uploadImg"
      placeholder="请输入文章内容..."
    >
      <template #defToolbars>
        <emoji-extension :on-insert="insert" />
      </template>
    </md-editor>
  </el-form>

  <input
    ref="fileInput"
    type="file"
    style="display: none"
    accept=".md"
    @change="handleFileSelect"
  />
  <!-- 发布或修改对话框 -->
  <el-dialog title="发布文章" v-model="dialogVisible" width="600px" top="0.5vh" append-to-body>
    <el-form ref="articleFormRef" label-width="80px" :model="articleForm" :rules="rules">
      <!-- 文章分类 -->
      <el-form-item label="文章分类" prop="categoryName">
        <el-tag
          type="success"
          v-show="articleForm.categoryName"
          :disable-transitions="true"
          :closable="true"
          @close="removeCategory"
        >
          {{ articleForm.categoryName }}
        </el-tag>
        <!-- 分类选项 -->
        <el-popover
          v-if="!articleForm.categoryName"
          placement="bottom-start"
          width="460"
          trigger="click"
        >
          <template #reference>
            <el-button type="success" plain>
              <Icon class="mr-5px" icon="ep:plus" />
              添加分类
            </el-button>
          </template>
          <div class="popover-title">分类</div>
          <!-- 搜索框 -->
          <el-autocomplete
            style="width: 100%"
            v-model="categoryName"
            :fetch-suggestions="searchCategory"
            clearable
            placeholder="请输入分类名搜索,回车键可添加自定义分类"
            :trigger-on-focus="false"
            @keyup.enter="saveCategory"
            @select="handleSelectCategory"
          >
            <template #default="{ item }">
              <div>{{ item.name }}</div>
            </template>
          </el-autocomplete>
          <!-- 分类 -->
          <div class="popover-container">
            <div
              v-for="item of categoryList"
              :key="item.id"
              class="category-item"
              @click="addCategory(item.name)"
            >
              {{ item.name }}
            </div>
          </div>
        </el-popover>
      </el-form-item>
      <!-- 文章标签 -->
      <el-form-item label="文章标签" prop="tagNameList">
        <el-tag
          v-for="(item, index) of articleForm.tagNameList"
          :key="index"
          :disable-transitions="true"
          :closable="true"
          @close="removeTag(item)"
          style="margin-left: 3px"
        >
          {{ item }}
        </el-tag>
        <!-- 标签选项 -->
        <el-popover
          placement="bottom-start"
          width="450"
          trigger="click"
          v-if="articleForm.tagNameList.length < 3"
        >
          <template #reference>
            <el-button type="primary" plain size="small">
              <Icon class="mr-5px" icon="ep:plus" />
              添加标签
            </el-button>
          </template>
          <div style="margin-bottom: 4px; text-align: center">标签</div>
          <!-- 搜索框 -->
          <el-autocomplete
            style="width: 100%"
            v-model="tagName"
            :fetch-suggestions="searchTag"
            placeholder="请输入标签名搜索,回车可添加自定义标签"
            :trigger-on-focus="false"
            @keyup.enter="saveTag"
            @select="handleSelectTag"
          >
            <template #default="{ item }">
              <div>{{ item.name }}</div>
            </template>
          </el-autocomplete>
          <!-- 标签 -->
          <div style="height: 220px; margin-top: 4px; overflow-y: auto">
            <div style="margin-bottom: 4px">添加标签</div>
            <el-tag
              v-for="(item, index) of tagList"
              :key="index"
              :class="tagClass(item.name)"
              @click="addTag(item.name)"
              style="margin-right: 5px"
            >
              {{ item.name }}
            </el-tag>
          </div>
        </el-popover>
      </el-form-item>
      <!-- 文章类型 -->
      <el-form-item label="文章类型" prop="isForward">
        <el-radio-group v-model="articleForm.isForward">
          <el-radio key="true" :label="true">原创</el-radio>
          <el-radio key="false" :label="false">转载</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 推荐文章 -->
      <el-form-item label="是否推荐" prop="isRecommend">
        <el-radio-group v-model="articleForm.isRecommend">
          <el-radio key="true" :label="true">是</el-radio>
          <el-radio key="false" :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 缩略图 -->
      <el-form-item label="缩略图">
        <el-radio-group v-model="storageType" class="mb-20px">
          <el-radio :value="1">文件上传</el-radio>
          <el-radio :value="2">文件地址</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item  v-if="storageType === 1" prop="coverImage">
        <el-upload
          drag
          :show-file-list="false"
          :action="uploadUrl"
          :http-request="httpRequest"
          accept="image/*"
          :before-upload="beforeUpload"
          :on-success="handleSuccess"
        >
          <el-icon class="el-icon--upload" v-if="articleForm.coverImage === ''">
            <upload-filled />
          </el-icon>
          <div class="el-upload__text" v-if="articleForm.coverImage === ''">
            将文件拖到此处，或<em>点击上传</em>
          </div>
          <img v-else :src="articleForm.coverImage" width="360" />
        </el-upload>
      </el-form-item>
      <el-form-item v-if="storageType === 2" prop="coverImage">
        <el-input v-model="articleForm.coverImage" placeholder="请输入缩略图地址"/>
      </el-form-item>
      <!-- 阅读方式 -->
      <el-form-item label="阅读方式" prop="readType">
        <el-radio-group v-model="articleForm.readType">
          <el-radio key="true" :label="true">点赞阅读</el-radio>
          <el-radio key="false" :label="false">无需验证</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- 置顶 -->
      <el-form-item label="置顶" prop="isTop">
        <el-switch v-model="articleForm.isTop" :active-value="true" :inactive-value="false" />
      </el-form-item>
    </el-form>
    <!-- 文章类型 -->
    <el-form-item label="发布状态" prop="status">
      <el-radio-group v-model="articleForm.status">
        <el-radio :label="0">草稿</el-radio>
        <el-radio :label="1">发布</el-radio>
        <el-radio :label="2">下架</el-radio>
      </el-radio-group>
    </el-form-item>
    <!-- 文章详情图片数组 -->
    <el-form-item label="预览图" prop="imageDetails">
      <UploadImgs v-model="articleForm.imageDetails" />
    </el-form-item>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
import * as ArticleApi from '@/api/articles/article/index'
import * as ArticleCategoryApi from '@/api/articles/category/index'
import * as ArticleTagApi from '@/api/articles/tag/index'

/** 文章表表单 */
defineOptions({ name: 'WriteArticle' })
import EmojiExtension from '@/components/EmojiExtension/index.vue'
import type { ExposeParam, InsertContentGenerator } from 'md-editor-v3'
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'
import { toolbars } from '@/components/EmojiExtension/staticConfig'
import { useDark } from '@vueuse/core'
import { AxiosResponse } from 'axios'
import { UploadRawFile, FormInstance } from 'element-plus'
import * as imageConversion from 'image-conversion'
import { useUpload } from '@/components/UploadFile/src/useUpload'
import * as FileApi from '@/api/system/file'
import { UploadFilled } from '@element-plus/icons-vue'

const articleFormRef = ref<FormInstance>()
const { uploadUrl, httpRequest } = useUpload()

const isDark = useDark()
const editorRef = ref<ExposeParam>()
const { t } = useI18n() // 国际化
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const message = useMessage() // 消息弹窗
const route = useRoute() // 路由
const { push } = useRouter()
const id = route.query.id
const formRules = reactive({
  title: [{ required: true, message: '标题', trigger: 'blur' }],
  intro: [{ required: true, message: '文章简介', trigger: 'blur' }],
  content: [{ required: true, message: '文章内容', trigger: 'blur' }]
})
const storageType = ref(0)
/**
 * 插入表情
 * @param generator
 */
const insert = (generator: InsertContentGenerator) => {
  editorRef.value?.insert(generator)
}
/**
 * 图片上传
 * @param files
 * @param callback
 */
const uploadImg = async (files: Array<File>, callback: (urls: string[]) => void) => {
  const res = await Promise.all(
    files.map((file) => {
      return new Promise((resolve, reject) => {
        FileApi.updateFile({ file: file })
          .then((res) => {
            if (res.code === 200) {
              resolve(res)
            } else {
              reject(res)
            }
          })
          .catch((res) => {
            reject(res)
          })
      })
    })
  )
  callback(res.map((item: any) => item.data.url))
}

/** 提交表单 */
const submitForm = async () => {
  // 校验表单
  if (!articleFormRef) return
  const valid = await articleFormRef.value?.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  // 提交请求
  formLoading.value = true
  try {
    if (articleForm.value.imageDetails){
      articleForm.value.imageDetails = articleForm.value.imageDetails.map((item: any) => item.url)
    }
    const data = articleForm.value as unknown as ArticleApi.ArticleVO
    if (!id) {
      await ArticleApi.createArticle(data)
      message.success(t('common.createSuccess'))
    } else {
      await ArticleApi.updateArticle(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    push('/articles/article').then(() => {
      window.location.reload()
    })
  } finally {
    formLoading.value = false
  }
}
/************文章发布相关***********************/
const openModel = async () => {
  if (articleForm.value.title.trim() == '') {
    message.error('文章标题不能为空')
    return false
  }
  if (articleForm.value.content.trim() == '') {
    message.error('文章内容不能为空')
    return false
  }
  articleFormRef.value?.clearValidate()
  //文章分类下拉
  categoryList.value = await ArticleApi.getSelectArticleCategoryList()
  //文章标签下拉
  tagList.value = await ArticleApi.getSelectArticleTagList()
  dialogVisible.value = true
}

/****************导入文章*************************/
const triggerFileInput  = async () => {
  fileInput.value?.click();
}

// 文件选择逻辑
const handleFileSelect = async (event: Event) => {
  const target = event.target as HTMLInputElement;
  const file = target.files?.[0];
  if (!file) return;

  const formData = new FormData();
  formData.append("file", file);

  try {
    const content = await ArticleApi.importArticle(formData);
    console.log("文章导入成功:", content.data.data);
    articleForm.value.content = content.data.data; // 将内容赋值到表单
    message.success("文章导入成功！");
  } catch (error) {
    console.error("文章导入失败:", error);
    message.error("文章导入失败！");
  } finally {
    target.value = ""; // 重置文件选择器
  }
};


const rules = reactive({
  title: [{ required: true, message: '标题', trigger: 'blur' }],
  coverImage: [{ required: true, message: '封面地址', trigger: 'blur' }],
  intro: [{ required: true, message: '文章简介', trigger: 'blur' }],
  content: [{ required: true, message: '文章内容', trigger: 'blur' }],
  readType: [{ required: true, message: '阅读方式 0无需验证 1点赞阅读', trigger: 'blur' }],
  isTop: [{ required: true, message: '是否置顶 0否 1是', trigger: 'blur' }],
  isForward: [{ required: true, message: '是否转载  0：转载 1:原创', trigger: 'blur' }],
  status: [{ required: true, message: '状态 0：草稿 1：发布 2:下架', trigger: 'blur' }],
  tagNameList: [{ required: true, message: '标签不能为空', trigger: 'blur' }]
})

const data = reactive({
  dialogVisible: false,
  typeList: [
    {
      value: true,
      label: '原创'
    },
    {
      value: false,
      label: '转载'
    }
  ],
  articleForm: {
    id: undefined,
    categoryName: '',
    title: '',
    coverImage: '',
    intro: '',
    content: '',
    contentMd: '',
    readType: false,
    isTop: false,
    status: 0,
    isForward: true,
    isRecommend: false,
    forwardUrl: '',
    tagNameList: [],
    imageDetails: []
  } as ArticleApi.ArticleVO,
  categoryList: [] as ArticleCategoryApi.ArticleCategoryVO[],
  tagList: [] as ArticleTagApi.ArticleTagVO[],
  categoryName: '',
  tagName: ''
})

const { dialogVisible, articleForm, categoryList, tagList, categoryName, tagName } = toRefs(data)

/**************分类相关方法******************************/
/**
 * 选择分类
 * @param item
 */
const handleSelectCategory = (item: ArticleCategoryApi.ArticleCategoryVO) => {
  addCategory(item.name)
}
const addCategory = (item: string) => {
  articleForm.value.categoryName = item
}
/**
 *移除分类
 */
const removeCategory = () => {
  articleForm.value.categoryName = ''
}
/**
 * 搜索文章分类
 */
const searchCategory = (
  keyword: string,
  cb: (arg: ArticleCategoryApi.ArticleCategoryVO[]) => void
) => {
  const results = keyword
    ? categoryList.value.filter(createCategoryFilter(keyword))
    : categoryList.value
  cb(results)
}

/**
 *分类搜索过滤
 * @param queryString
 */
const createCategoryFilter = (queryString: string) => {
  return (restaurant: ArticleCategoryApi.ArticleCategoryVO) => {
    return restaurant.name.toLowerCase().indexOf(queryString.toLowerCase()) !== -1
  }
}

/**
 * 保存分类
 */
const saveCategory = () => {
  // 分类不为空
  if (categoryName.value.trim() != '') {
    addCategory(categoryName.value)
    categoryName.value = ''
  }
}

/***************标签相关方法***************************/
/**
 * 移除标签
 * @param item
 */
const removeTag = (item: string) => {
  const index = articleForm.value.tagNameList.indexOf(item)
  articleForm.value.tagNameList.splice(index, 1)
}

/**
 * 选择标签
 * @param item
 */
const handleSelectTag = (item: ArticleTagApi.ArticleTagVO) => {
  addTag(item.name)
}
/**
 * 保存标签
 */
const saveTag = () => {
  if (tagName.value.trim() != '') {
    addTag(tagName.value)
    tagName.value = ''
  }
}
/**
 * 添加标签
 * @param item
 */
const addTag = (item: string) => {
  if (articleForm.value.tagNameList.indexOf(item) == -1) {
    articleForm.value.tagNameList.push(item)
  }
}
/**
 * 标签搜索
 * @param keyword
 * @param cb
 */
const searchTag = (keyword: string, cb: (arg: ArticleTagApi.ArticleTagVO[]) => void) => {
  const results = keyword ? tagList.value.filter(createTagFilter(keyword)) : tagList.value
  cb(results)
}
const createTagFilter = (queryString: string) => {
  return (restaurant: ArticleTagApi.ArticleTagVO) => {
    return restaurant.name.toLowerCase().indexOf(queryString) !== -1
  }
}
/**
 * 点击添加标签
 */
const tagClass = computed(() => {
  return function (item: string) {
    const index = articleForm.value.tagNameList.indexOf(item)
    return index !== -1 ? 'tag-item-select' : 'tag-item'
  }
})

/***************图片上传相关方法***************************/
const handleSuccess = (response: AxiosResponse) => {
  articleForm.value.coverImage = response.data.url
}
const beforeUpload = (rawFile: UploadRawFile) => {
  return new Promise((resolve) => {
    if (rawFile.size / 1024 < 200) {
      resolve(rawFile)
    }
    // 压缩到200KB,这里的200就是要压缩的大小,可自定义
    imageConversion.compressAccurately(rawFile, 200).then((res) => {
      resolve(res)
    })
  })
}
/** 初始化 **/
onMounted(async () => {
  if (id) {
    articleForm.value = await ArticleApi.getArticle(id)
  }
})
</script>
<style scoped>
.category-item {
  padding: 0.6rem 0.5rem;
  cursor: pointer;
}

.category-item:hover {
  color: #67c23a;
  background-color: #f0f9eb;
}
</style>
