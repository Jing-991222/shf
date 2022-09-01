package com.jing.dao;

import com.github.pagehelper.Page;
import com.jing.base.BaseDao;
import com.jing.entity.UserFollow;
import com.jing.vo.UserFollowVo;
import org.apache.ibatis.annotations.Param;

public interface UserFollowDao extends BaseDao<UserFollow> {
    Integer countByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId") Long houseId);

    Page<UserFollowVo> findListPage(Long userId);
}
