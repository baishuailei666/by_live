import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

/**
 *  导入数据
 * @param {String} params
 */
//1、 支付宝支付接口
export function aliPay(params) {
  return fetch({
    url: SH + '/pay/ali',
    method: 'GET',
    params,
  });
}
//2、 微信支付接口
export function wxPay(params) {
  return fetch({
    url: SH + '/pay/wx',
    method: 'GET',
    params,
  });
}
// 3、微信支付成功的回调查看
export function paySuccess(params) {
  return fetch({
    url: SH + '/merchant/paySuccess',
    method: 'GET',
    params,
  });
}