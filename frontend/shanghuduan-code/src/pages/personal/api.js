import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取个人中心信息
export function info(params) {
  return fetch({
    url: SH + '/merchant/info',
    method: 'GET',
    params,
  });
}
//2、 获取手机号验证码
export function code(params) {
  return fetch({
    url: SH + '/common/code',
    method: 'GET',
    params,
  });
}
//3、 修改密码接口
export function modify(params) {
  return fetch({
    url: SH + '/common/pwd/modify',
    method: 'POST',
    data: {
      ...params,
    }

  });
}