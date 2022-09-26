import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取配置列表页表格数据
export function data(params) {
  return fetch({
    url: SH + '/config/data',
    method: 'GET',
    params,
  });
}
//2、 新增和修改接口
export function modify(params) {
  return fetch({
    url: SH + '/config/data/modify',
    method: 'POST',
    data: {
      ...params,
    }
  });
}
//3、 查看服务价格接口
export function price(params) {
  return fetch({
    url: SH + '/config/price',
    method: 'GET',
    params,
  });
}
//4、 修改服务价格接口
export function modifyPrices(params) {
  return fetch({
    url: SH + '/config/price/modify',
    method: 'POST',
    data: {
      ...params,
    }
  });
}