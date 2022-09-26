import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/MERCHANT';

/**
 *  导入数据
 * @param {String} params
 */
//1、 学习中心列表接口
export function centre(params) {
  return fetch({
    url: SH + '/merchant/video/centre',
    method: 'GET',
    params,
  });
}
//2、 视频获取接口
export function play(params) {
  return fetch({
    url: SH + '/merchant/video/play',
    method: 'GET',
    params,
  });
}