package com.jing.base;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Map;

public interface BaseDao<T> {

    Integer insert(T t);

    T getById(Serializable id);

    Integer update(T t);

    void delete(Serializable id);

    Page<T> findPage(Map<String, Object> filters);
}
