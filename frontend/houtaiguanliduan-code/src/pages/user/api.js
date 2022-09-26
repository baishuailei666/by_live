import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取用户列表页表格数据
export function user(params) {
  return fetch({
    url: SH + '/user',
    method: 'GET',
    params,
  });
}
//2、 新增用户接口
export function create(params) {
  return fetch({
    url: SH + '/user/create',
    method: 'POST',
    data: {
      ...params,
    }
  });
}
//3、 删除用户接口
export function del(params) {
  return fetch({
    url: SH + '/user/del',
    method: 'GET',
    params,
  });
}

//4、 重置密码接口
export function reset(params) {
  return fetch({
    url: SH + '/common/pwd/reset',
    method: 'GET',
    params,
  });
}