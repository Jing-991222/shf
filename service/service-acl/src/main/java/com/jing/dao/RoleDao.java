package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.Role;

import java.util.List;

public interface RoleDao extends BaseDao<Role> {
    List<Role> findAll();
}
