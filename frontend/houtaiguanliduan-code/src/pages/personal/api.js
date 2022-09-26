import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 上传二维码接口
export function img(params) {
  return fetch({
    url: SH + '/common/upload/img',
    method: 'POST',
    data: {
      ...params,
    }
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