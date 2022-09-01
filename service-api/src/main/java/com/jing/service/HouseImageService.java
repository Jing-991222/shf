package com.jing.service;

import com.jing.base.BaseService;
import com.jing.entity.HouseImage;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {
    List<HouseImage> findListByHouseId(Long id,Integer type);
}
