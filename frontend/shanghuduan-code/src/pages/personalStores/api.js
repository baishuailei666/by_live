import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

/**
 *  导入数据
 * @param {String} params
 */

//1、 获取我的店铺接口
export function shop(params) {
  return fetch({
    url: SH + '/merchant/shop',
    method: 'GET',
    params,
  });
}

//2、 修改店铺接口
export function modify(params) {
  return fetch({
    url: SH + '/merchant/shop/modify',
    method: 'POST',
    data: {
      ...params,
    }
  });
}
//3、 删除店铺接口
export function del(params) {
  return fetch({
    url: SH + '/merchant/shop/del',
    method: 'GET',
    params,
  });
}