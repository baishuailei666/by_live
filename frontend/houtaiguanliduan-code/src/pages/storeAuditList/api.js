import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取商户列表页表格数据
export function auditList(params) {
  return fetch({
    url: SH + '/user/merchant/audit/list',
    method: 'POST',
    data: {
      ...params,
    },
  });
}
//2、 拒绝同意接口
export function audit(params) {
  return fetch({
    url: SH + '/user/merchant/audit',
    method: 'POST',
    data: {
      ...params,
    },
  });
}