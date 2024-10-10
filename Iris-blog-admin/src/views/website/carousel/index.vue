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
      <el-form-item>
        <el-button
            type="primary"
            @click="openForm('create')"
            v-hasPermi="['system:carousel:save']"
        >
          <Icon icon="ep:plus" class="mr-5px"/>
          上传
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>
  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :show-overflow-tooltip="true">
      <el-table-column type="index" label="编号" width="100" align="center"/>
      <el-table-column label="轮播图地址" align="center" prop="imgUrl">
        <template #default="{ row }">
          <el-image
              class="h-80px w-80px"
              lazy
              :src="row.imgUrl"
              :preview-src-list="[row.imgUrl]"
              preview-teleported
              fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="是否显示" key="status">
        <template #default="scope">
          <el-switch v-model="scope.row.status" @change="handleStatusChange(scope.row)" />
        </template>
      </el-table-column>
      <el-table-column
          label="创建时间"
          align="center"
          prop="createTime"
          :formatter="dateFormatter"
          width="180px"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
              link
              type="danger"
              @click="handleDelete(scope.row.id)"
              v-hasPermi="['system:carousel:delete']"
          >
            <Icon icon="ep:delete"/>
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
  <!-- 表单弹窗：添加/修改 -->
  <CarouselForm ref="formRef" @success="getList"/>
</template>
<script setup lang="ts">
import {dateFormatter} from '@/utils/formatTime'
import * as CarouselApi from '@/api/website/carousel'
import CarouselForm from './CarouselForm.vue'
import {CommonStatusBooleanEnum} from "@/utils/constants";
/** 首页轮播 列表 */
defineOptions({name: 'WebSiteCarousel'})
const message = useMessage() // 消息弹窗
const {t} = useI18n() // 国际化
const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  action: {
    status: undefined

  }
})
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CarouselApi.getCarouselPage(queryParams)
    list.value = data.records
    total.value = data.total
  } finally {
    loading.value = false
  }
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
    await CarouselApi.deleteCarousel(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {
  }
}

/** 修改是否显示*/
const handleStatusChange = async (row: CarouselApi.CarouselVO) => {
  try {
    // 修改状态的二次确认
    const text = row.status === CommonStatusBooleanEnum.ENABLE ? '显示' : '不显示'
    await message.confirm('确认要"' + text + '""轮播图吗?')
    // 发起修改状态
    await CarouselApi.updateCarouselStatus(row.id)
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status =
        row.status === CommonStatusBooleanEnum.ENABLE
            ? CommonStatusBooleanEnum.DISABLE
            : CommonStatusBooleanEnum.ENABLE
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
