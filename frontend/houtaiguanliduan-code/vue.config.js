// const path = require("path");
// const resolve = function (dir) {
//   return path.join(__dirname, dir);
// };
// 基础路径 注意发布之前要先修改这里
let publicPath = '/'
module.exports = {
  publicPath: publicPath, // 根据你的实际情况更改这里
  lintOnSave: true,
  assetsDir: 'static',
  devServer: {
    publicPath: publicPath, // 和 baseUrl 保持一致
    proxy: {
      '/ZBDH/BACK': {
        // target: 'http://127.0.0.1:8081/',
        target: 'https://taizb.com/', // 线上
        changeOrigin: true,
        pathRewrite: {
          '^/ZBDH/BACK': ''
        }
      }
    }
  },
  css: { //这个是真正解决大屏适配有关的代码
    sourceMap: false,
    loaderOptions: {
      css: {

      },
      postcss: {
        plugins: [require('postcss-pxtorem')({
          "rootValue": 192, //设计稿宽度的1/10,（JSON文件中不加注释，此行注释及下行注释均删除）
          "propList": ["*"] //需要做转化处理的属性，如`hight`、`width`、`margin`等，`*`表示全部
        })]
      }
    }
  },
}