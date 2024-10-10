<template>
  <div class="app-container">
    <el-tabs type="border-card" class="demo-tabs" @tab-change="getSiteConfig">
      <!-- 网站信息 -->
      <el-tab-pane label="info">
        <template #label>
                    <span class="custom-tabs-label">
                    <Icon class="mr-3px" icon="ep:monitor"/>
                        <span>网站信息</span>
                    </span>
        </template>
        <el-form label-width="80px" :model="siteConfig" label-position="left">
          <el-row>
            <el-col :md="6">
              <el-form-item label="用户头像">
                <el-upload
                    :show-file-list="false"
                    :action="uploadUrl"
                    :http-request="httpRequest"
                    accept="image/*"
                    :before-upload="beforeUpload"
                    :on-success="handleSuccess"
                >
                  <img v-if="siteConfig.userAvatar && siteConfig.userAvatar !== ''" :src="siteConfig.userAvatar" style="width: 150px; height: 150px; object-fit: contain;"/>
                  <el-icon v-else class="el-icon--upload" style="width: 80px; height: 80px;">
                    <upload-filled style="width: 100%; height: 100%;"/>
                  </el-icon>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :md="6">
              <el-form-item label="游客头像">
                <el-upload
                    :show-file-list="false"
                    :action="uploadUrl"
                    :http-request="httpRequest"
                    accept="image/*"
                    :before-upload="beforeUpload"
                    :on-success="handleSuccess"
                >
                  <img v-if="siteConfig.touristAvatar && siteConfig.touristAvatar !== ''" :src="siteConfig.touristAvatar" style="width: 150px; height: 150px; object-fit: contain;"/>
                  <el-icon v-else class="el-icon--upload" style="width: 80px; height: 80px;">
                    <upload-filled style="width: 100%; height: 100%;"/>
                  </el-icon>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="网站名称">
            <el-input v-model="siteConfig.siteName" style="width: 400px;"/>
          </el-form-item>
          <el-form-item label="网站地址">
            <el-input v-model="siteConfig.siteAddress" style="width: 400px;"/>
          </el-form-item>
          <el-form-item label="网站简介">
            <el-input v-model="siteConfig.siteIntro" style="width: 400px;"/>
          </el-form-item>
          <el-form-item label="网站公告">
            <el-input style="width: 400px;" v-model="siteConfig.siteNotice"
                      :autosize="{ minRows: 4, maxRows: 5 }" resize="none"
                      type="textarea"/>
          </el-form-item>
          <el-form-item label="建站日期">
            <el-date-picker v-model="siteConfig.createSiteTime" value-format="YYYY-MM-DD"
                            type="date"
                            placeholder="选择日期"/>
          </el-form-item>
          <el-form-item label="备案号">
            <el-input v-model="siteConfig.recordNumber" style="width: 400px;"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleUpdate">保 存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <!-- 作者信息 -->
      <el-tab-pane label="author">
        <template #label>
                    <span class="custom-tabs-label">
                    <Icon class="mr-3px" icon="ep:user"/>
                        <span>作者信息</span>
                    </span>
        </template>
        <el-form label-width="80px" :model="siteConfig" label-position="left">
          <el-form-item label="作者头像">
            <el-upload
                drag
                :show-file-list="false"
                :action="uploadUrl"
                :http-request="httpRequest"
                accept="image/*"
                :before-upload="beforeUpload"
                :on-success="handleSuccess"
            >

              <img v-if="siteConfig.authorAvatar && siteConfig.authorAvatar !== ''" :src="siteConfig.authorAvatar" style="width: 150px; height: 150px; object-fit: contain;"/>
              <el-icon  v-else class="el-icon--upload" style="width: 80px; height: 80px;">
                <upload-filled style="width: 100%; height: 100%;"/>
              </el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item label="网站作者">
            <el-input v-model="siteConfig.siteAuthor" style="width: 400px;"/>
          </el-form-item>
          <el-form-item label="关于我">
            <md-editor
                style="height: calc(100vh - 200px); min-height: 300px"
                ref="editorRef"
                v-model="siteConfig.aboutMe"
                :theme="isDark ? 'dark' : 'light'"
                :toolbars="toolbars"
                @on-upload-img="uploadImg"
                placeholder="请输入文章内容..."
            >
              <template #defToolbars>
                <emoji-extension :on-insert="insert"/>
              </template>
            </md-editor>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleUpdate">保 存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <!-- 社交信息 -->
      <el-tab-pane label="social">
        <template #label>
                    <span class="custom-tabs-label">
                    <Icon class="mr-3px" icon="ep:share"/>
                        <span>社交信息</span>
                    </span>
        </template>
        <el-form label-width="70px" :model="siteConfig" label-position="left">
          <el-checkbox-group v-model="socialList">
            <el-form-item label="Email">
              <el-input v-model="siteConfig.email"
                        style="width: 400px; margin-right: 1rem"/>
              <el-checkbox label="email">是否展示</el-checkbox>
            </el-form-item>
            <el-form-item label="Github">
              <el-input v-model="siteConfig.github"
                        style="width: 400px; margin-right: 1rem"/>
              <el-checkbox label="github">是否展示</el-checkbox>
            </el-form-item>
            <el-form-item label="Gitee">
              <el-input v-model="siteConfig.gitee"
                        style="width: 400px; margin-right: 1rem"/>
              <el-checkbox label="gitee">是否展示</el-checkbox>
            </el-form-item>
            <el-form-item label="QQ">
              <el-input v-model="siteConfig.qq" style="width: 400px; margin-right: 1rem"/>
              <el-checkbox label="qq">是否展示</el-checkbox>
            </el-form-item>
            <el-form-item label="QQ群">
              <el-input v-model="siteConfig.qqGroup" style="width: 400px; margin-right: 1rem"/>
              <el-checkbox label="qqGroup">是否展示</el-checkbox>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdate">保 存</el-button>
            </el-form-item>
          </el-checkbox-group>
        </el-form>
      </el-tab-pane>
      <!-- 审核&打赏 -->
      <el-tab-pane label="check">
        <template #label>
                    <span class="custom-tabs-label">
                    <Icon class="mr-3px" icon="ep:coordinate"/>
                        <span>审核&打赏</span>
                    </span>
        </template>
        <el-form label-width="100px" :model="siteConfig" label-position="left">
          <el-form-item label="评论审核">
            <el-radio-group v-model="siteConfig.commentCheck">
              <el-radio :label="0">关闭</el-radio>
              <el-radio :label="1">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="留言审核">
            <el-radio-group v-model="siteConfig.messageCheck">
              <el-radio :label="0">关闭</el-radio>
              <el-radio :label="1">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="打赏状态">
            <el-radio-group v-model="siteConfig.isReward">
              <el-radio :label="0">关闭</el-radio>
              <el-radio :label="1">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-row style="width: 600px" v-if="siteConfig.isReward == 1">
            <el-col :md="12">
              <el-form-item label="微信收款码">
                <el-upload
                    drag
                    :show-file-list="false"
                    :action="uploadUrl"
                    :http-request="httpRequest"
                    accept="image/*"
                    :before-upload="beforeUpload"
                    :on-success="handleSuccess"
                >
                  <img v-if="siteConfig.wechatCode && siteConfig.wechatCode !== ''" :src="siteConfig.wechatCode" style="width: 150px; height: 150px; object-fit: contain;"/>
                  <el-icon  v-else class="el-icon--upload" style="width: 80px; height: 80px;">
                    <upload-filled style="width: 100%; height: 100%;"/>
                  </el-icon>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :md="12">
              <el-form-item label="支付宝收款码">
                <el-upload
                    drag
                    :show-file-list="false"
                    :action="uploadUrl"
                    :http-request="httpRequest"
                    accept="image/*"
                    :before-upload="beforeUpload"
                    :on-success="handleSuccess"
                >
                  <img v-if="siteConfig.alipayCode && siteConfig.alipayCode !== ''" :src="siteConfig.alipayCode" style="width: 150px; height: 150px; object-fit: contain;"/>
                  <el-icon  v-else class="el-icon--upload" style="width: 80px; height: 80px;">
                    <upload-filled style="width: 100%; height: 100%;"/>
                  </el-icon>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item>
            <el-button type="primary" @click="handleUpdate">保 存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <!-- 其他设置 -->
      <el-tab-pane label="other">
        <template #label>
                    <span class="custom-tabs-label">
                      <Icon class="mr-3px" icon="ep:setUp"/>
                      <span>其他设置</span>
                    </span>
        </template>
        <el-form label-width="120px" :model="siteConfig" label-position="left">
          <el-form-item label="文章默认封面">
            <el-upload
                drag
                :show-file-list="false"
                :action="uploadUrl"
                :http-request="httpRequest"
                accept="image/*"
                :before-upload="beforeUpload"
                :on-success="handleSuccess"
            >
              <img v-if="siteConfig.articleCover && siteConfig.articleCover !== ''" :src="siteConfig.articleCover" style="width: 300px; height: 150px; object-fit: contain;"/>
              <el-icon  v-else class="el-icon--upload" style="width: 120px; height: 80px;">
                <upload-filled style="width: 100%; height: 100%;"/>
              </el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item label="邮箱通知">
            <el-radio-group v-model="siteConfig.emailNotice">
              <el-radio :label="0">关闭</el-radio>
              <el-radio :label="1">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="第三方登录">
            <el-checkbox-group v-model="loginList">
              <el-checkbox label="qq">QQ</el-checkbox>
              <el-checkbox label="gitee">Gitee</el-checkbox>
              <el-checkbox label="github">Github</el-checkbox>
              <el-checkbox label="weibo">Weibo</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="音乐播放器">
            <el-radio-group v-model="siteConfig.isMusic">
              <el-radio :label="0">关闭</el-radio>
              <el-radio :label="1">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="网易云歌单Id" v-if="siteConfig.isMusic == 1">
            <el-input v-model="siteConfig.musicId" style="width: 400px;"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleUpdate">保 存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import {toolbars} from "@/components/EmojiExtension/staticConfig";

defineOptions({name: 'WebSiteConfig'})
const message = useMessage() // 消息弹窗
const {t} = useI18n() // 国际化
import {useUpload} from '@/components/UploadFile/src/useUpload';
import {UploadFilled} from '@element-plus/icons-vue'
import {AxiosResponse} from "axios";

const {uploadUrl, httpRequest} = useUpload()
import * as SiteConfigApi from '@/api/website/siteconfig/index'
import {UploadRawFile} from 'element-plus'
import * as imageConversion from 'image-conversion'
import {useDark} from "@vueuse/core";

const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
import * as FileApi from '@/api/system/file'

import EmojiExtension from '@/components/EmojiExtension/index.vue'
import type {ExposeParam, InsertContentGenerator} from 'md-editor-v3'
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'

const isDark = useDark()
const editorRef = ref<ExposeParam>()

const data = reactive({
  siteConfig: {
    id: undefined,
    userAvatar: '',
    touristAvatar: '',
    siteName: '',
    siteAddress: '',
    siteIntro: '',
    siteNotice: '',
    createSiteTime: '',
    recordNumber: '',
    authorAvatar: '',
    siteAuthor: '',
    articleCover: '',
    aboutMe: '',
    email:'',
    github: '',
    gitee: '',
    qqGroup: '',
    qq: '',
    commentCheck: 0,
    messageCheck: 0,
    isReward: 0,
    wechatCode: '',
    alipayCode: '',
    emailNotice: 0,
    socialList: '',
    loginList: '',
    isMusic: 0,
    musicId: ''
  } as SiteConfigApi.SiteConfigVO,
  socialList: [] as string[],
  loginList: [] as string[],
});
const {
  siteConfig,
  socialList,
  loginList,
} = toRefs(data);

/**
 * 获取网站配置信息
 * */
const getSiteConfig = async () => {
  const config = await SiteConfigApi.getSiteConfig();
  siteConfig.value = config;
  if (config.socialList) {
    socialList.value = config.socialList.split(",");
  }

  if (config.loginList) {
    loginList.value = config.loginList.split(",");
  }

};

/**
 * 更新网站配置信息
 */
const handleUpdate = async () => {
  // 提交请求
  formLoading.value = true
  try {
    if (loginList.value.length > 0) {
      siteConfig.value.loginList = loginList.value.toString();
    } else {
      siteConfig.value.loginList = "";
    }
    if (socialList.value.length > 0) {
      siteConfig.value.socialList = socialList.value.toString();
    } else {
      siteConfig.value.socialList = "";
    }
    const data = siteConfig.value as unknown as SiteConfigApi.SiteConfigVO
    await SiteConfigApi.saveSiteConfig(data);
    message.success(t('common.updateSuccess'))
  } finally {
    formLoading.value = false
  }
};
/***************图片上传相关方法***************************/
const handleSuccess = (type: number, response: AxiosResponse) => {

  if (type === 1) {
    siteConfig.value.userAvatar = response.data.url
  }
  if (type === 2) {
    siteConfig.value.touristAvatar = response.data.url
  }
  if (type === 3) {
    siteConfig.value.authorAvatar = response.data.url
  }
  if (type === 4) {
    siteConfig.value.wechatCode = response.data.url
  }
  if (type === 5) {
    siteConfig.value.alipayCode = response.data.url
  }
  if (type === 6) {
    siteConfig.value.articleCover = response.data.url
  }
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

/*************md编辑器相关方法****************************/
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
          FileApi.updateFile({file: file})
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
onMounted(() => {
  getSiteConfig();
});
</script>

<style scoped>
.demo-tabs>.el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;

}

.demo-tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
}

.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 4px;
}

</style>
