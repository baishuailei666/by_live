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
      '/ZBDH/MERCHANT': {
        // target: 'http://10.11.12.212:8081/',
        target: 'https://taizb.com/', // 线上
        changeOrigin: true,
        pathRewrite: {
          '^/ZBDH/MERCHANT': ''
        }
      }
    }
  },
}