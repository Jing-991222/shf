package com.jing.dao;

import com.github.pagehelper.Page;
import com.jing.base.BaseDao;
import com.jing.entity.House;
import com.jing.vo.HouseQueryVo;
import com.jing.vo.HouseVo;

public interface HouseDao extends BaseDao<House> {

    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
