package com.jing.service;

import com.jing.base.BaseService;
import com.jing.entity.Admin;

import java.util.List;

public interface AdminService extends BaseService<Admin> {
    List<Admin> findAll();

    Admin getByUsername(String username);
}
