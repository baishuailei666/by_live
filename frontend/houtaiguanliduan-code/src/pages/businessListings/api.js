import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取商户列表页表格数据
export function list(params) {
  return fetch({
    url: SH + '/user/merchant/list',
    method: 'POST',
    data: {
      ...params,
    },
  });
}