import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取订单记录接口
export function order(params) {
  return fetch({
    url: SH + '/merchant/order',
    method: 'GET',
    params,
  });
}
// 
//2、 开票提交接口
export function create(params) {
  return fetch({
    url: SH + '/merchant/invoice/create',
    method: 'POST',
    data: {
      ...params,
    }
  });
}