package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.HouseUser;

import java.util.List;

public interface HouseUserDao extends BaseDao<HouseUser> {
    List<HouseUser> findListByHouseId(Long id);
}
