package com.jing.service;

import com.jing.base.BaseService;
import com.jing.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {
    List<HouseUser> findListByHouseId(Long id);
}
