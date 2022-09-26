import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 用户登录接口
export function login(params) {
  return fetch({
    url: SH + '/login',
    method: 'POST',
    data: {
      ...params,
    },
  });
}
//2、退出登录的接口
export function logOut() {
  return fetch({
    url: SH + '/logout',
    method: 'GET',
  });
}
// 3、消息通知接口
export function msg(params) {
  return fetch({
    url: SH + '/common/msg',
    method: 'GET',
    params
  });
}