import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取商户资源表格数据
export function list(params) {
  return fetch({
    url: SH + '/resource/list',
    method: 'GET',
    params,
  });
}
//2、 商户资源意向程度修改
export function edit(params) {
  return fetch({
    url: SH + '/resource/edit',
    method: 'POST',
    data: {
      ...params
    }
  });
}
//3、 商户资源备注列表
export function note(params) {
  return fetch({
    url: SH + '/resource/note',
    method: 'GET',
    params
  });
}
//4、 商户资源备注添加接口
export function add(params) {
  return fetch({
    url: SH + '/resource/note/add',
    method: 'POST',
    data: {
      ...params
    }
  });
}