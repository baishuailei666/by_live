import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取订单记录接口
export function center(params) {
  return fetch({
    url: SH + '/common/msg/center',
    method: 'GET',
    params,
  });
}