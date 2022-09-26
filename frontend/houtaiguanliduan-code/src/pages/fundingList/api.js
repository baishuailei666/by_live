import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取订单列表表格数据
export function order(params) {
  return fetch({
    url: SH + '/order',
    method: 'POST',
    data: {
      ...params
    }
  });
}
//2、 新增订单
export function ins(params) {
  return fetch({
    url: SH + '/order/ins',
    method: 'POST',
    data: {
      ...params
    }
  });
}
// 3、/user/merchant/search
export function search(params) {
  return fetch({
    url: SH + '/user/merchant/search',
    method: 'GET',
    params
  });
}