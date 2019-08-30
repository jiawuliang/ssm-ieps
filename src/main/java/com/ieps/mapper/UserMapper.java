package com.ieps.mapper;

import com.ieps.pojo.User;
import com.ieps.pojo.UserInfo;
import com.ieps.dto.UserAdminDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    
    int deleteByPrimaryKey(Integer userId);
    
    int insert(User record);
    
    int insertSelective(User record);
    
    User selectByPrimaryKey(Integer userId);
    
    int updateByPrimaryKeySelective(User record);
    
    int updateByPrimaryKey(User record);
    
    
    
    
    
    
    /** 登录 */
    User login(@Param("userNum") String userNum, @Param("userPwd") String userPwd);
    
    User checkUser(@Param("userNum") String userNum, @Param("userPwd") String userPwd);
    /** 查找菜单 */
    User selectMenu(String userNum);
    
    /** 根据学号或者教师号，查找用户是否已注册或存在 */
    User selectByUserNum(String userNum);
    
    int selectUserPwdWithUserNum(@Param("userNum") String userNum, @Param("userPwd") String userPwd);
    
    /** 修改密码  */
    int updatePwd(@Param("userNum") String userNum, @Param("userPwd") String userPwd);
    
    List<UserAdminDto> selectAllUser(@Param("userNum") String userNum);
    
    List<UserAdminDto> selectAllUserByUserNum(@Param("userNumAdmin") String userNumAdmin);
    
    List<UserAdminDto> selectAllUserWithCondition(@Param("academyAdmin") String academyAdmin, @Param("userInfo") UserInfo userInfo);
    
    List<UserAdminDto> selectAllUserByUserNumWithCondition(@Param("userNumAdmin") String userNumAdmin, @Param("userInfo") UserInfo userInfo);
    
    
    List<UserAdminDto> selectAllUserWithUserNum(@Param("academy") String academy, @Param("list") List<String> list);
    
    int updateStatusByUserNum(@Param("userNum") String userNum, @Param("userStatus")Integer userStatus);
    
    int deleteByUserNum(String userNum);
    
    int deleteUserByUserNum(String[] userNums);
    
    
    
    
    

}