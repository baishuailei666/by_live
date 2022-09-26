import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取发票列表表格数据
export function list(params) {
  return fetch({
    url: SH + '/invoice/list',
    method: 'POST',
    data: {
      ...params,
    },
  });
}
//2、 发票状态修改
export function check(params) {
  return fetch({
    url: SH + '/invoice/check',
    method: 'POST',
    data: {
      ...params,
    },
  });
}