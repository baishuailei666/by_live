import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

/**
 *  导入数据
 * @param {String} params
 */
//1、 已查看列表接口
export function collect(params) {
  return fetch({
    url: SH + '/anchor/collect',
    method: 'GET',
    params,
  });
}
// 2、移除主播接口
export function remove(params) {
  return fetch({
    url: SH + '/anchor/remove',
    method: 'GET',
    params,
  });
} // 3、已经加微信接口
export function addWx(params) {
  return fetch({
    url: SH + '/anchor/addWx',
    method: 'GET',
    params,
  });
}
// 4、跟进记录列表接口
export function follow(params) {
  return fetch({
    url: SH + '/anchor/follow',
    method: 'GET',
    params,
  });
}
// 5、跟进记录添加接口
export function add(params) {
  return fetch({
    url: SH + '/anchor/follow/add',
    method: 'GET',
    params,
  });
}