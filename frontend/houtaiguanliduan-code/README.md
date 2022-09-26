# IntelligentPlatformWeb
=======
#### 介绍简介
后台管理系统架构


# 项目开发说明
## 一、主要文件说明：
此项目为SPA单页面富应用项目，主要文件说明如下：
### 1、App.vue文件；
入口文件
### 2、main.js文件；
全局配置文件；
## 二、从上往下说明src下的各个文件夹的作用：
### 3、src => api文件夹
项目各个模块接口调用的封装；
### 4、src => assets文件夹
用于存放项目各个模块组件的静态资源文件；
### 5、src => filters文件夹
过滤器文件，格式化时间、格式化一些数据……
### 6、src => pages文件夹
存放项目各个模块的各个组件的文件夹。（简单说就是各个页面文件）
### 7、src => plugin文件夹
用于存放插件，例如：封装axios,用到接口的调用文件中（src=>api文件夹）
### 8、src => router文件夹
路由的封装；
index.js是总的路由封装；
routerConfig.js是路由标配置文件；
### 9、src => store文件夹
使用vuex技术做状态管理统一管理页面所需数据供全局使用；
### 10、src => styles文件夹
存放“公共样式”
### 12、src => utils文件夹
用于存放资源类库
## 三、项目主要技术说明：
1、框架采用：Vue2.0 + Vue-cli4.0 + Vue-router + axios + Vuex；
2、UI组件采用：Element UI 组件库；
3、Css语法采用：less语法；