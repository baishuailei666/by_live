import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取开通权益记录表格数据
export function order(params) {
  return fetch({
    url: SH + '/user/merchant/order',
    method: 'POST',
    data: {
      ...params,
    },
  });
}