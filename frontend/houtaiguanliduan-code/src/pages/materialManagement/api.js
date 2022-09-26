import {
  fetch
} from '@/plugin/axios';

const SH = '/ZBDH/BACK';

/**
 *  导入数据
 * @param {String} params
 */
//1、 上传二维码接口
export function videoParam(params) {
  return fetch({
    url: SH + '/common/upload/videoParam',
    method: 'POST',
    data: {
      ...params,
    }
  });
}