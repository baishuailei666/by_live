import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 获取支付凭证列表页表格数据
export function pay(params) {
  return fetch({
    url: SH + '/config/pay',
    method: 'GET',
    params,
  });
}
//2、 支付凭证新增接口
export function ins(params) {
  return fetch({
    url: SH + '/config/pay/ins',
    method: 'POST',
    data: {
      ...params,
    }
  });
}
//3、 支付凭证删除接口
export function del(params) {
  return fetch({
    url: SH + '/config/pay/del',
    method: 'GET',
    params,
  });
}
//4、 支付凭证证书上传接口
export function cert(params) {
  return fetch({
    url: SH + '/common/upload/cert',
    method: 'POST',
    data: {
      ...params,
    }
  });
}