import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
// 引入element-ui
import ElementUI from 'element-ui';

import 'element-ui/lib/theme-chalk/index.css';
import '@/styles/global.css' //引入全局样式
Vue.use(ElementUI);
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')