import Vue from 'vue';
import axios from 'axios';
// 引入复制组件
// import VueClipBoard from "vue-clipboard2";
// Vue.use(VueClipBoard);
axios.defaults.timeout = 60000;
axios.defaults.retry = 0;
axios.defaults.retryDelay = 2000;

function axiosRetryInterceptor(err) {
  if (!err.response) {
    Vue.prototype.$message({
      type: 'error',
      message: '网络异常，请稍后再试',
    });
  }
  var config = err.config;

  // If config does not exist or the retry option is not set, reject
  if (!config || !config.retry) return Promise.reject(err);

  // Set the variable for keeping track of the retry count
  config.__retryCount = config.__retryCount || 0;

  // Check if we've maxed out the total number of retries
  if (config.__retryCount >= config.retry) {
    // Reject with the error
    return Promise.reject(err);
  }

  // Increase the retry count
  config.__retryCount += 1;

  // Create new promise to handle exponential backoff
  var backoff = new Promise(function (resolve) {
    setTimeout(function () {
      resolve();
    }, config.retryDelay || 1);
  });

  // Return the promise in which recalls axios to retry the request
  return backoff.then(function () {
    return axios(config);
  });
}

axios.interceptors.request.use(
  config => {
    config.url = config.url;
    const token = localStorage.getItem('token')
    if (token) {
      // 请求头携带token
      config.headers.token = token
    }
    config.headers.from = "merchant"
    config.params = {
      ...config.params,
    };

    config.headers = config.headers;

    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// axios.interceptors.response.use(res => res.data, err => Promise.reject(err))
axios.interceptors.response.use(res => {
  if (res.data && res.data.errno == 0) {
    return res.data;
  }

  // 登录过期跳到登录页面
  if (res.data.code == 10) {
    // console.log(window.location.href)
    let url = window.location.href.split('/')[0]
    window.location.replace(url + '/login')
    return res.data
  }

  return res.data;
}, axiosRetryInterceptor);

/**
 * usage
 * import { fetch } from '@/plugin/axios';
 *
 */
export {
  axios as fetch
};

export default {
  install: function (Vue, options) {
    Vue.prototype.$axios = axios;
  },
};