<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="文章标题" prop="title">
        <el-input
          v-model="queryParams.action.title"
          placeholder="请输入文章标题"
          clearable
          @keyup.enter="handleQuery"
          class="!w-150px"
        />
      </el-form-item>
      <el-form-item label="文章分类" prop="categoryId">
        <el-select
          v-model="queryParams.action.categoryId"
          placeholder="请选择分类"
          clearable
          class="!w-150px"
        >
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="文章标签" prop="tags">
        <el-select
          v-model="queryParams.action.tags"
          placeholder="请选择标签"
          clearable
          class="!w-150px"
        >
          <el-option v-for="item in tagList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否置顶" prop="isTop">
        <el-select
          v-model="queryParams.action.isTop"
          placeholder="是否置顶"
          clearable
          class="!w-130px"
        >
          <el-option
            v-for="status in getBoolDictOptions(DICT_TYPE.SYSTEM_BOOLEAN_STATUS)"
            :key="status.value"
            :label="status.value ? '是' : '否'"
            :value="status.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="文章状态" prop="status">
        <el-select
          v-model="queryParams.action.status"
          placeholder="文章状态"
          clearable
          class="!w-130px"
        >
          <el-option
            v-for="status in getIntDictOptions(DICT_TYPE.ARTICLE_STATUS)"
            :key="status.value"
            :label="status.label"
            :value="status.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否原创" prop="isForward">
        <el-select
          v-model="queryParams.action.isForward"
          placeholder="是否原创"
          clearable
          class="!w-130px"
        >
          <el-option
            v-for="status in getBoolDictOptions(DICT_TYPE.SYSTEM_BOOLEAN_STATUS)"
            :key="status.value"
            :label="status.value ? '原创' : '转载'"
            :value="status.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" plain @click="handleQuery">
          <Icon icon="ep:search" class="mr-5px" />
          搜索
        </el-button>
        <el-button type="primary" plain @click="resetQuery">
          <Icon icon="ep:refresh" class="mr-5px" />
          重置
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <div class="mb-15px">
    <router-link :to="'/articles/article/writeArticle'">
      <el-button type="success" v-hasPermi="['system:article:save']">
        <Icon icon="ep:plus" class="mr-5px" />
        发布文章
      </el-button>
    </router-link>
    <el-button
      class="ml-5px"
      type="warning"
      @click="handleExport"
      :loading="exportLoading"
      v-hasPermi="['system:article:export']"
    >
      <Icon icon="ep:download" class="mr-5px" />
      导出
    </el-button>
  </div>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" highlight-current-row fit>
      <el-table-column type="index" label="编号" width="100" align="center" />
      <el-table-column label="标题" align="center" prop="title" :show-overflow-tooltip="true">
        <template #default="scope">
          <el-link
            type="warning"
            :underline="false"
            :href="baseUrl + scope.row.id"
            target="_blank"
            >{{ scope.row.title }}</el-link
          >
        </template>
      </el-table-column>
      <el-table-column label="封面地址" align="center" prop="coverImage">
        <template #default="{ row }">
          <el-image
            class="h-80px w-80px"
            lazy
            :src="row.coverImage"
            :preview-src-list="[row.coverImage]"
            preview-teleported
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="文章分类" align="center" prop="categoryName" />
      <el-table-column align="center" width="220" label="标签">
        <template #default="{ row }">
          <el-tag
            style="margin-left: 3px"
            align="center"
            type="primary"
            v-for="(item, index) in tagsArray(row.tags)"
            :key="index"
            >{{ item }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="阅读方式" align="center" prop="readType">
        <template #default="scope">
          <el-tag :type="scope.row.readType ? 'primary' : 'success'">{{
            scope.row.readType ? '点赞阅读' : '无需验证'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否置顶" align="center" prop="isTop">
        <template #default="scope">
          <el-tag :type="scope.row.isTop ? 'danger' : 'info'">{{
            scope.row.isTop ? '是' : '否'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="文章状态" align="center" prop="status">
        <template #default="scope">
          <el-tag type="warning" v-if="scope.row.status === 0">{{ '草稿' }}</el-tag>
          <el-tag type="success" v-if="scope.row.status === 1">{{ '发布' }}</el-tag>
          <el-tag type="danger" v-if="scope.row.status === 2">{{ '下架' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否转载" align="center" prop="isForward">
        <template #default="scope">
          <el-tag :type="scope.row.isForward ? 'success' : 'warning'">{{
            scope.row.isForward ? '原创' : '转载'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否推荐" align="center" prop="isRecommend">
        <template #default="scope">
          <el-tag :type="scope.row.isRecommend ? 'success' : 'warning'">{{
            scope.row.isRecommend ? '是' : '否'
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="点赞数量" align="center" prop="likes" />
      <el-table-column label="收藏数量" align="center" prop="collection" />
      <el-table-column label="评论数量" align="center" prop="comment" />
      <el-table-column label="阅读数量" align="center" prop="views" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <router-link :to="'/articles/article/writeArticle?id=' + scope.row.id">
            <el-button link type="primary" class="mr-5px" v-hasPermi="['system:article:update']">
              <Icon icon="ep:edit" />
              编辑
            </el-button>
          </router-link>
          <el-button
            link
            type="warning"
            @click="handleTop(scope.row.id, scope.row.isTop)"
            v-hasPermi="['system:article:update']"
          >
            <Icon icon="ep:top" />
            {{ scope.row.isTop ? '取消置顶' : '置顶' }}
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['system:article:delete']"
          >
            <Icon icon="ep:delete" />
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>
</template>
<script setup lang="ts">
import { DICT_TYPE, getBoolDictOptions, getIntDictOptions } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as ArticleApi from '@/api/articles/article/index'
import * as ArticleCategoryApi from '@/api/articles/category/index'
import * as ArticleTagApi from '@/api/articles/tag/index'
import { exportArticleExcel, topArticle } from '@/api/articles/article/index'
import { CommonStatusEnum } from '@/utils/constants'
const baseUrl = ref(import.meta.env.VITE_BASE_URL + 'article/')
const createTime = ref([])
/** 文章表 列表 */
defineOptions({ name: 'Article' })
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const categoryList = ref<ArticleCategoryApi.ArticleCategoryVO[]>()
const tagList = ref<ArticleTagApi.ArticleTagVO[]>()
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    categoryId: undefined,
    tags: undefined,
    title: undefined,
    isTop: undefined,
    status: undefined,
    isForward: undefined,
    createTimeStart: null,
    createTimeEnd: null
  }
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    if (createTime.value) {
      queryParams.action.createTimeStart = createTime.value[0]
      queryParams.action.createTimeEnd = createTime.value[1]
    }
    const data = await ArticleApi.getArticlePage(queryParams)
    list.value = data.records
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryParams.action = {}
  createTime.value = []
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await ArticleApi.deleteArticle(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
/** 置顶按钮操作 */
const handleTop = async (id: number, isTop: boolean) => {
  try {
    const text = isTop ? '取消置顶' : '置顶'
    // 置顶的二次确认
    await message.confirm('确认要' + text + '文章吗?')
    // 发起置顶
    await ArticleApi.topArticle(id)
    message.success('文章' + text + '成功')
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await ArticleApi.exportArticleExcel(queryParams)
    download.excel(data, '文章表.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

// 计算属性，将逗号分隔的tags字符串转换为数组
const tagsArray = (tags: string | object) => {
  return typeof tags === 'string' ? tags.split(',') : []
}
/** 初始化 **/
onMounted(async () => {
  await getList()
  //文章分类下拉
  categoryList.value = await ArticleApi.getSelectArticleCategoryList()
  //文章标签下拉
  tagList.value = await ArticleApi.getSelectArticleTagList()
})
</script>
