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
      redirect: "/personal",
      // 这个路由到时候肯定是活的
      children: [{
          path: '/business-listings',
          component: () => import('@/pages/businessListings/index') //商户列表
        },
        {
          path: '/store-audit-list',
          component: () => import('@/pages/storeAuditList/index') //店铺审核列表
        },
        {
          path: '/equity-record',
          component: () => import('@/pages/equityRecord/index') //店铺购买记录
        },
        {
          path: '/merchant-resources',
          component: () => import('@/pages/merchantResources/index') //商户资源
        }, {
          path: '/funding-list',
          component: () => import('@/pages/fundingList/index') //订单列表
        },
        {
          path: '/invoice',
          component: () => import('@/pages/invoice/index') //发票列表
        },
        {
          path: '/contract-review',
          component: () => import('@/pages/contractReview/index') //合同列表
        },
        {
          path: '/configuration-list',
          component: () => import('@/pages/configurationList/index') //配置列表
        },
        {
          path: '/user',
          component: () => import('@/pages/user/index') //用户管理
        },
        {
          path: '/material-management',
          component: () => import('@/pages/materialManagement/index') //素材管理
        },
        // {
        //   path: '/payment-voucher',
        //   component: () => import('@/pages/paymentVoucher/index') //支付凭证
        // },
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