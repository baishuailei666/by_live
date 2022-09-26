// 引入vue模块
import Vue from 'vue'
// 引入路由模块
import Router from 'vue-router'
// 引入登录组件
import Home from '../pages/Home.vue'
import Login from '../pages/Login.vue'
Vue.use(Router)
// 定义路由实例
const router = new Router({
  mode: 'history',
  routes: [{
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/home',
      name: 'Home',
      component: Home,
      redirect: "/workbench",
      // 这个路由到时候肯定是活的
      children: [{
          path: '/workbench',
          component: () => import('@/pages/workbench/index') //工作台(这个改成了1100)
        },
        {
          path: '/bind-store',
          component: () => import('@/pages/bindStore/index') //绑定店铺
        },
        {
          path: '/personal-stores',
          component: () => import('@/pages/personalStores/index') //我的店铺
        },
        {
          path: '/find-anchor',
          component: () => import('@/pages/findAnchor/index') //找主播
        },
        {
          path: '/anchor-details',
          component: () => import('@/pages/anchorDetails/index') //主播详情页面
        },
        {
          path: '/viewed',
          component: () => import('@/pages/viewed/index') //已查看
        },
        {
          path: '/open-service',
          component: () => import('@/pages/openService/index'), //开通服务

        },
        {
          path: '/open-service/open-card',
          component: () => import('@/pages/openCard/index') //开卡
        },
        {
          path: '/open-service/cashier',
          component: () => import('@/pages/cashier/index') //收银台
        },
        {
          path: '/learning-center',
          component: () => import('@/pages/learningCenter/index') //学习中心
        },
        {
          path: '/order-record',
          component: () => import('@/pages/orderRecord/index') //订单记录
        },
        {
          path: '/notification-center',
          component: () => import('@/pages/notificationCenter/index') //通知中心
        },

        {
          path: '/personal',
          component: () => import('@/pages/personal/index') //个人中心
        },
      ]
    },
  ]
})

// 将路由实例暴露出来
export default router