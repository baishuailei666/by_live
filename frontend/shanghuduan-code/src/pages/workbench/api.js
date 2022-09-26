import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取我的店铺接口
export function shop(params) {
  return fetch({
    url: SH + '/merchant/shop',
    method: 'GET',
    params,
  });
}
// 1、签署信息返回（商户端）
export function content(params) {
  return fetch({
    url: SH + '/merchant/contract/content',
    method: 'GET',
    params,
  });
}
// 2、合同提交（商户端）
export function submit(params) {
  return fetch({
    url: SH + '/merchant/contract/submit',
    method: 'POST',
    data: {
      ...params,
    }
  });
}
//3、 创建电子签接口
export function signCreate(params) {
  return fetch({
    url: SH + '/merchant/sign/create',
    method: 'GET',
    params,
  });
}
//4、 电子签-获取链接接口
export function url(params) {
  return fetch({
    url: SH + '/merchant/sign/url',
    method: 'GET',
    params,
  });
}
// 
//5、 判断电子签签署状态；
export function status(params) {
  return fetch({
    url: SH + '/merchant/sign/status',
    method: 'GET',
    params,
  });
}

//6、 判断电子签是否创建未签署状态；
export function check(params) {
  return fetch({
    url: SH + '/merchant/sign/check',
    method: 'GET',
    params,
  });
}