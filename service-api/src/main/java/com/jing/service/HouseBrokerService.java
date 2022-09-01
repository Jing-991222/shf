package com.jing.service;

import com.jing.base.BaseService;
import com.jing.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<HouseBroker> findListByHouseId(Long id);
}
