// 引入unocss css
import '@/plugins/unocss'

// 导入全局的svg图标
import '@/plugins/svgIcon'

// 初始化多语言
import { setupI18n } from '@/plugins/vueI18n'

// 引入状态管理
import { setupStore } from '@/store'

// 全局组件
import { setupGlobCom } from '@/components'

// 引入 element-plus
import { setupElementPlus } from '@/plugins/elementPlus'

// 引入 form-create
import { setupFormCreate } from '@/plugins/formCreate'

// 引入全局样式
import '@/styles/index.scss'

// 引入动画
import '@/plugins/animate.css'

// 引入路由
import router, { setupRouter } from '@/router'

// 引入权限
import { setupAuth } from '@/directives'

// 引入粒子动画
import Particles from "@tsparticles/vue3";
import { loadSlim } from "@tsparticles/slim";

import { createApp } from 'vue'

import App from './App.vue'

//权限
import './permission'
//统计
import '@/plugins/tongji'
//日志
import Logger from '@/utils/Logger'
// 解决v-html 的安全隐患
import VueDOMPurifyHTML from 'vue-dompurify-html'
//markdown组件
import { a } from '@/utils/antiDebug'
//鼠标光标
import './style.css'
//字体样式
// import './assets/font/iconfont.js'
// 创建实例
const setupAll = async () => {
  const app = createApp(App)
  await setupI18n(app)

  setupStore(app)

  setupGlobCom(app)

  setupElementPlus(app)

  setupFormCreate(app)

  setupRouter(app)

  setupAuth(app)

  await router.isReady()

  app.use(VueDOMPurifyHTML)

  //粒子效果
  app.use(Particles as any, {
    init: async (engine: any) => {
      await loadSlim(engine);
    },
  });

  app.mount('#app')
}

setupAll()
a();
Logger.prettyPrimary(`欢迎使用`, import.meta.env.VITE_APP_TITLE)
