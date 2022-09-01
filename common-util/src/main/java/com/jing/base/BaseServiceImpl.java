package com.jing.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jing.util.CastUtil;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseServiceImpl<T> implements BaseService<T>{

    protected abstract BaseDao<T> getEntityDao();
    @Override
    public Integer insert(T t) {
        return getEntityDao().insert(t);
    }

    @Override
    public T getById(Serializable id) {
        return getEntityDao().getById(id);
    }

    @Override
    public Integer update(T t) {
        return getEntityDao().update(t);
    }

    @Override
    public void delete(Serializable id) {
        getEntityDao().delete(id);
    }

    @Override
    public PageInfo<T> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(getEntityDao().findPage(filters));

    }
}
