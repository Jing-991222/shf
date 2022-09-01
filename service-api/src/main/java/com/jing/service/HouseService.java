package com.jing.service;

import com.github.pagehelper.PageInfo;
import com.jing.base.BaseService;
import com.jing.entity.House;
import com.jing.vo.HouseQueryVo;
import com.jing.vo.HouseVo;

public interface HouseService extends BaseService<House> {
    void publish(Long id, Integer status);

    PageInfo<HouseVo> findPage(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
