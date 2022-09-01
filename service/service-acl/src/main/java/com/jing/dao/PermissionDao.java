package com.jing.dao;

import com.jing.base.BaseDao;
import com.jing.entity.Permission;

import java.util.List;

public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAll();

    List<Permission> findListByAdminId(Long adminId);

    List<String> findAllCode();

    List<String> findCodeListByAdminId(Long adminId);
}
