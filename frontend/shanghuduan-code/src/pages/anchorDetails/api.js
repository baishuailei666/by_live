import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取单个主播详细信息接口
export function info(params) {
  return fetch({
    url: SH + '/anchor/info',
    method: 'GET',
    params,
  });
}