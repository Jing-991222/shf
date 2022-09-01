package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    List<HouseBroker> findListByHouseId(Long id);
}
