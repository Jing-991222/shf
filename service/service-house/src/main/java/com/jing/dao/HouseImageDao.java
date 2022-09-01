package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageDao extends BaseDao<HouseImage> {
    List<HouseImage> findListByHouseId(@Param("id") Long id,@Param("type") Integer type);
}
