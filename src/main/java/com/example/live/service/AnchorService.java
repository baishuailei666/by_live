package com.example.live.service;

import com.example.live.common.BaseResult;

/**
 * @author baishuailei@zhejianglab.com
 * @date 2022/7/10 13:49
 */
public interface AnchorService {

    BaseResult<?> anchorInfo(Integer id);

    BaseResult<?> anchorList();

    BaseResult<?> anchorCollect();

    BaseResult<?> anchorAddWx(Integer id);

    BaseResult<?> anchorRemove(Integer id);
}
