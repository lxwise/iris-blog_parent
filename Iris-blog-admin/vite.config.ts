import { resolve } from 'path'
import { loadEnv } from 'vite'
import type { UserConfig, ConfigEnv } from 'vite'
import { createVitePlugins } from './build/vite'
import { include, exclude } from "./build/vite/optimize"
// 当前执行node命令时文件夹的地址(工作目录)
const root = process.cwd()

// 路径查找
function pathResolve(dir: string) {
  return resolve(root, '.', dir)
}

// https://vitejs.dev/config/
//获取各种环境下的对应的变量
export default ({ command, mode }: ConfigEnv): UserConfig => {
  let env = {} as any
  const isBuild = command === 'build'
  if (!isBuild) {
    env = loadEnv((process.argv[3] === '--mode' ? process.argv[4] : process.argv[3]), root)
  } else {
    env = loadEnv(mode, root)
  }
  return {
    base: env.VITE_BASE_PATH,
    root: root,
    // 服务端渲染
    server: {
      port: env.VITE_PORT, // 端口号
      host: "0.0.0.0",
      open: env.VITE_OPEN === 'true',
      // 本地跨域代理. 目前注释的原因：暂时没有用途，server 端已经支持跨域
      // proxy: {
      //   ['/admin-api']: {
      //     获取数据的服务器地址设置
      //     target: env.VITE_BASE_URL,
      //     ws: false,
      //     需要代理跨域
      //     changeOrigin: true,
      //     路径重写
      //     rewrite: (path) => path.replace(new RegExp(`^/admin-api`), ''),
      //   },
      // },
    },
    // 项目使用的vite插件。 单独提取到build/vite/plugin中管理
    plugins: createVitePlugins(),
    logLevel: 'info',
    //scss全局变量一个配置
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: '@import "./src/styles/variables.scss";',
          javascriptEnabled: true
        }
      }
    },
    //国际化处理
    resolve: {
      extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.scss', '.css'],
      alias: [
        {
          find: 'vue-i18n',
          replacement: 'vue-i18n/dist/vue-i18n.cjs.js'
        },
        // 相对路径别名配置，使用 @ 代替 src
        {
          find: /\@\//,
          replacement: `${pathResolve('src')}/`
        }
      ]
    },
    // 打包配置
    build: {
      minify: 'terser',
      outDir: env.VITE_OUT_DIR || 'dist',
      sourcemap: env.VITE_SOURCEMAP === 'true' ? 'inline' : false,
      // brotliSize: false,
      terserOptions: {
        compress: {
          drop_debugger: env.VITE_DROP_DEBUGGER === 'true',
          drop_console: env.VITE_DROP_CONSOLE === 'true'
        }
      }
    },
    optimizeDeps: { include, exclude }
  }
}
