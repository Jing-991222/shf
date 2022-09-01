package com.jing.service;

import com.github.pagehelper.PageInfo;
import com.jing.base.BaseService;
import com.jing.entity.UserFollow;
import com.jing.vo.UserFollowVo;

public interface UserFollowService extends BaseService<UserFollow> {
    void follow(Long userId, Long houseId);
    Boolean isFollowed(Long userId,Long houseId);

    PageInfo<UserFollowVo> findListPage(Integer pageNum, Integer pageSize, Long userId);

    void cancelFollow(Long id);
}
