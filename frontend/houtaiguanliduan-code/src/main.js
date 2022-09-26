import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
// 引入element-ui
import ElementUI from 'element-ui';
import 'lib-flexible/flexible'
import 'element-ui/lib/theme-chalk/index.css';
import '@/styles/global.css' //引入全局样式
// import Rsa from "@/utils/rsa.js" //引入RSA加密方法；
// Vue.prototype.Rsa = Rsa // 将Rsa注册为公共方法,方便其他页面调用
Vue.use(ElementUI);
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')