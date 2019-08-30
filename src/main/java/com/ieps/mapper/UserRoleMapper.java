package com.ieps.mapper;

import com.ieps.pojo.UserRole;

public interface UserRoleMapper {
    
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
    
    
    
    
    
    
    int deleteUserRoleByUserNum(String[] userNums);
    
    UserRole selectRoleWithUserNum(String userNum);
    
    
}