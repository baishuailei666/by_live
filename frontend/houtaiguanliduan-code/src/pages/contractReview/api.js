import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取合同列表页表格数据
export function list(params) {
  return fetch({
    url: SH + '/contract/list',
    method: 'POST',
    data: {
      ...params,
    },
  });
}
//2、 下载接口
export function down(params) {
  return fetch({
    url: SH + '/contract/down',
    method: 'GET',
    params,
  });
}
//3、 查看接口
export function info(params) {
  return fetch({
    url: SH + '/contract/info',
    method: 'GET',
    params,
  });
}