## 1.Blog Introduction

<p align="center">
  <a href="https://www.lstar.icu/">
    <img src="./doc/Iris_blog_300.png" alt="鸢尾博客" style="border-radius: 50%;">
  </a>
</p>
<p align="center">
A front-end and back-end separated blog developed using Springboot, Vue3, and TypeScript.
</p>


<p align="center">
   <a target="_blank" href="https://gitee.com/lxwise/iris-blog_parent">
      <img src="https://img.shields.io/hexpm/l/plug.svg"/>
      <img src="https://img.shields.io/badge/JDK-17-green"/>
      <img src="https://img.shields.io/badge/springboot-2.7-green"/>
      <img src="https://img.shields.io/badge/saToken-1.34-green"/>
      <img src="https://img.shields.io/badge/mysql-8.0-green"/>
      <img src="https://img.shields.io/badge/mybatis--plus-3.5-green"/>
      <img src="https://img.shields.io/badge/redis-6.x-green"/>
      <img src="https://img.shields.io/badge/vue-3.x-green"/>
      <img src="https://img.shields.io/badge/typescript-5.3-green"/>
      <img src="https://img.shields.io/badge/node-v18.20.2-blue"/>
      <img src="https://img.shields.io/badge/npm-10.5.0-blue"/>
      <img src="https://img.shields.io/badge/pnpm-9.0.6-blue"/>
       <img src="https://img.shields.io/badge/MdEditorV3-MD%E7%BC%96%E8%BE%91%E5%99%A8-green"/>
       <img src="https://img.shields.io/badge/naiveUi-2.38-green"/>
       <img src="https://img.shields.io/badge/danmaku-弹幕交互-green"/>
   </a>
</p>




[**中文文档**](https://gitee.com/lxwise/iris-blog_parent)

[**英文文档**](https://gitee.com/lxwise/iris-blog_parent/blob/master/README.en.md)

## 2.Online Address

**Blog Links：** [www.lstar.icu](https://www.lstar.icu)

**Backstage Links：** [admin.lstar.icu](https://admin.lstar.icu)

**Test Account：** test@qq.com，**password**：123456

**Gitee Address：** [https://gitee.com/lxwise/iris-blog_parent](https://gitee.com/lxwise/iris-blog_parent)

**Github Address：** [https://github.com/lxwise/iris-blog_parent](https://github.com/lxwise/iris-blog_parent)

## 3.Star

ps: Although I know that most people like to get something for free like the author, they all leave after reading and downloading the source code. But I still want to ask all the friends who like this project: **Star**, **Star**, **Star**. Only with your **Star** can more people see this project, and more like-minded friends will join in improving this project. Please move your cute little hands and give this project a **Star**. **Also welcome everyone to submit PR and improve the project together**.

## 4.Directory Structure

```
Iris-blog_parent
├── doc    --  说明文档
├── Iris-blog       --  博客后端父项目
	├── Iris-admin  --  博客后端项目
		├── src
			├── main
				├── java
					├── com.iris.blog  		-- 主包
						├── common			-- 通用模块
						├── components		-- 组件
						├── config			-- 通用配置
						├── controller		-- 前端控制器
						├── dao				-- mapper/实体嘞
						├── domain			-- VO类
						├── service			-- 接口
						├── strategy		-- 策略类
						├── utils			-- 工具类
						AdminApplication.java
				├── resources
		pom.xml
	├── Iris-generator		--  代码生成器项目
├── Iris-blog-admin		-- 前端后台项目        
├── Iris-blog-web       -- 前端博客项目
├── sql        --  sql文件
```

## 5.Project Features

- The layout style of the blog interface mainly refers to Hexo's [ShokaX](https://github.com/theme-shoka-x/hexo-theme-shokaX) and [Butterfly](https://github.com/jerryc127/hexo-theme-butterfly) design, the page is beautiful and supports mobile adaptation.

- The blog backend management is reconstructed based on the main framework of **[yudao-ui-admin-vue3](https://gitee.com/yudaocode/yudao-ui-admin-vue3)**, including dynamic routing permissions, sidebars, internationalization, theme switching, etc.

- Adopting the RABC permission model, using [Sa-Token](https://gitee.com/dromara/sa-token) for permission management, supporting dynamic permission modification, dynamic menus and routing.

- It has functions such as articles, timelines, sayings, notifications, tags, categories, messages, friend links, problem feedback, etc.

- Supports data traffic statistics, IP access monitoring, service health monitoring, dynamic management of front-end website information, and other functions.

- Supports dynamic scheduled tasks, dynamic storage management (OSS, COS, Kodo, Minio, local), log management, and other functions.

- Supports article code block highlighting, image preview, night mode, likes, unlikes, posting comments, replying to comments, emoticons, and other functions.

- Send HTML email comment reply reminders, detailed content, article dynamic notifications, and other functions.

- Access to third parties such as QQ, Weibo, Gitee, Github, etc. for quick login to reduce registration costs.

- Supports advanced article search, search keyword highlighting and word segmentation.

- Article editing uses Markdown editor, which is simple and convenient to write.

- Contains real-time weather forecast, tag cloud, latest comments, article directory, article recommendation and article pinning functions.

- Adopts Restful style API, complete comments, and the code follows Alibaba development specifications, which is conducive to developer learning.

  ​    

## 6.Technical Introduction

**front end：** Vue3  +Vite + Pinia + Vue Router + Vueuse + Vue-i18n + Unocss +Iconify  + TypeScript + Axios + Element Plus + Naive UI + Echarts + Swiper

**rear end：** SpringBoot + Javafx + Mysql + Redis + Oss/Cos/Qiniu/Minio + Mail + Quartz + Thymeleaf + Nginx + Docker + Sa-Token + Swagger2 + knife4j + MyBatisPlus + lombok + ip2region

**other：** Access QQ、Weibo、Gitee、Github Third-party login,Hutool、Guava、Commons Toolkit

## 7.Operating Environment

**Server:** Tencent Cloud 2 cores 4G CentOS7.6

**Object storage:** Alibaba Cloud OSS, Tencent Cloud COS, Qiniu Cloud Kodo, Minio

**Minimum configuration:** 2 cores 2G server ps: If mysql and Redis are not deployed on the same server, 1 core 2G can also run

## 8.Development Environment

|   Development Tools   |     illustrate     |
| :-------------------: | :----------------: |
|         IDEA          | Java 开发工具 IDE  |
|       Webstorm        | 前端 开发工具 IDE  |
|    Navicat/DBeaver    | MySQL 远程连接工具 |
| Redis Desktop Manager | Redis 远程连接工具 |
|      Finalshell       | Linux 远程连接工具 |
|        ApiFox         |    接口调试工具    |
|     SceneBuilder      |   JavaFx开发工具   |

| Environment | Version |
| :---------: | :-----: |
|   OpenJDK   | 17.0.9  |
|    MySQL    | 8.0.11  |
|    Redis    |  6.2.6  |
|    Nginx    | 1.22.1  |
|    Node     | 18.0.02 |
|     Npm     | 10.5.0  |
|    Pnpm     |  9.0.6  |



## 9.Local Run

1. MySQL version is `8.0.11`, npm version is `10.5.0`, node version is `v18.0.02`
2. The SQL file is located in the root directory of `iris_blog.sql`, import the data into your local database
3. Modify the database and other connection information in the backend application.yml configuration file. The Alibaba Cloud, Tencent Cloud functions and third-party authorized login used in the project need to be enabled by yourself
4. Then `npm install`, `npm run dev`
5. After the project is started, use the `admin@qq.com` administrator account to log in to the backend, and the password is `123456`

**ps：Please run the backend project first, then start the frontend project. The frontend project configuration is dynamically loaded by the backend.**

## 10.Project screenshots

#### Backstage

* **登录页**

<img src="doc/blog_back_01.gif" style="zoom:80%;" />

* **首页数据大屏**

<img src="doc/blog_back_02.gif" style="zoom:80%;" />

* **文章管理**

<img src="doc/blog_back_03.png" style="zoom:80%;" />

* **黑夜模式首页**

<img src="doc/blog_back_24.png" style="zoom:80%;" />

* **文章发布页**

<img src="doc/blog_back_04.png" style="zoom:80%;" />

* **分类管理**

<img src="doc/blog_back_05.png" style="zoom:80%;" />

* **评论管理**

<img src="doc/blog_back_06.png" style="zoom:80%;" />

* **留言管理**

<img src="doc/blog_back_07.png" style="zoom:80%;" />

* **说说管理**

<img src="doc/blog_back_08.png" style="zoom:80%;" />

* **网站配置**

<img src="doc/blog_back_09.png" style="zoom:80%;" />

* **轮播图管理**

<img src="doc/blog_back_10.png" style="zoom:80%;" />

* **网关日志**

<img src="doc/blog_back_11.png" style="zoom:80%;" />

* **用户管理**

<img src="doc/blog_back_12.png" style="zoom:80%;" />

* **菜单管理**

<img src="doc/blog_back_13.png" style="zoom:80%;" />

* **字典管理**

<img src="doc/blog_back_14.png" style="zoom:80%;" />

* **日志管理**

<img src="doc/blog_back_15.png" style="zoom:80%;" />

* **定时任务**

<img src="doc/blog_back_16.png" style="zoom:80%;" />
<img src="doc/blog_back_17.png" style="zoom:80%;" />

* **存储管理**

<img src="doc/blog_back_18.png" style="zoom:80%;" />
<img src="doc/blog_back_19.png" style="zoom:80%;" />

* **Redis监控**

<img src="doc/blog_back_20.png" style="zoom:80%;" />

* **应用监控**

<img src="doc/blog_back_21.png" style="zoom:80%;" />

* **Mysql监控**

<img src="doc/blog_back_22.png" style="zoom:80%;" />

* **系统监控**

<img src="doc/blog_back_23.png" style="zoom:80%;" />



#### Front Desk

* **首页**

![blog_01.gif](doc/blog_01.gif)

* **明亮模式**

![blog_02.png](doc/blog_02.png)





* **暗黑模式**

![blog_14.png](doc/blog_14.png)

* **文章归档**

<img src="doc/blog_03.png" style="zoom:80%;" />



* **分类模块**

<img src="doc/blog_04.png" style="zoom:80%;" />

* **标签模块**

<img src="doc/blog_05.png" style="zoom:80%;" />

* **说说模块**

<img src="doc/blog_06.png" style="zoom:80%;" />



* **友链模块**

<img src="doc/blog_07.png" style="zoom:80%;" />

* **留言板**

<img src="doc/blog_08.png" style="zoom:80%;" />

* **用户反馈**

<img src="doc/blog_09.png" style="zoom:80%;" />

* **消息通知**

<img src="doc/blog_10.png" style="zoom:80%;" />

* **文章搜索**

<img src="doc/blog_11.png" style="zoom:80%;" />

* **登录认证**

<img src="doc/blog_12.png" style="zoom:80%;" />

* **文章详情**

<img src="doc/blog_13.gif" style="zoom:80%;" />





## 11.Next steps

- [x] Import and export blog articles
- [ ] Mobile article directory
- [ ] Add photo album function
- [ ] Add poster
- [ ] Add encrypted articles
- [ ] Optimize interactive experience

## 12.Project Summary

After months of hard work and exploration, I finally completed my blog project. The whole project took a lot of effort, from the initial conception to the final realization. This process was not only full of challenges, but also allowed me to learn a lot of valuable knowledge and skills. During the development process, I referred to many excellent open source projects, which provided important guidance and support for my project. Here, I would like to express my deepest gratitude to all the contributors. Without your work, my project would not have been completed so smoothly.

- I would like to express my special thanks to the following open source project contributors, whose work made my project possible:

  - [hexo-theme-shokaX](https://github.com/theme-shoka-x/hexo-theme-shokaX) 
  -  [hexo-theme-butterfly](https://github.com/jerryc127/hexo-theme-butterfly)
  - **[yudao-ui-admin-vue3](https://gitee.com/yudaocode/yudao-ui-admin-vue3)**
  - [Sa-Token](https://gitee.com/dromara/sa-token) 
  - [基于 Vue.js 的弹幕交互组件](https://github.com/hellodigua/vue-danmaku) 
  - ......

  Finally, I hope that my blogging project can bring you the same gains and joy. If you have any suggestions or comments, please feel free to contact me. Let's share knowledge and grow together!