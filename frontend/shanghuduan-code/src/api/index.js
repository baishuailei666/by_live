import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

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
// 退出登录接口
export function logout(params) {
  return fetch({
    url: SH + '/logout',
    method: 'GET',
    params,
  });
}
//2、获取客服信息
export function getKef(params) {
  return fetch({
    url: SH + '/common/kef',
    method: 'GET',
    params,
  });
}
//3、获取服务价格
export function price(params) {
  return fetch({
    url: SH + '/common/price',
    method: 'GET',
    params,
  });
}
// 4、获取验证码接口
export function getCode(params) {
  return fetch({
    url: SH + '/common/code',
    method: 'GET',
    params
  });
}

//1、 获取个人中心信息-放在这里为了验证是否已经登录了；
export function info(params) {
  return fetch({
    url: SH + '/merchant/info',
    method: 'GET',
    params,
  });
}