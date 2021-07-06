package com.xxxx.crm.dao;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    //根据用户ID查询用户角色记录
    int countUserRoleByUserId(int userId);
    //根据用户ID删除用户角色记录
    int deleteUserRoleByUserId(int userId);

}