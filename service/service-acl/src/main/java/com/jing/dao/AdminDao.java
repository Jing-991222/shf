package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.Admin;

import java.util.List;

public interface AdminDao extends BaseDao<Admin> {
    List<Admin> findAll();

    Admin getByUsername(String username);
}
