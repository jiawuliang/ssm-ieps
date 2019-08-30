package com.ieps.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ieps.common.Const;
import com.ieps.common.ServerResponse;
import com.ieps.dto.UserAdminDto;
import com.ieps.mapper.RoleMapper;
import com.ieps.mapper.UserInfoMapper;
import com.ieps.mapper.UserMapper;
import com.ieps.mapper.UserRoleMapper;
import com.ieps.pojo.Role;
import com.ieps.pojo.User;
import com.ieps.pojo.UserInfo;
import com.ieps.pojo.UserRole;
import com.ieps.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw
 */
@Service
public class UserAdminServiceImpl implements UserAdminService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Override
    public ServerResponse getAllUserList(int pageNum, int pageSize, String userNum, int roleId) {
        // startPage -- start
        // 填充自己的sql查询逻辑
        // pageHelper -- 收尾
    
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
    
        List<UserAdminDto> userAdminList = Lists.newArrayList();
    
        if (roleId == Const.ROLEID_COLLEGE) {
            // 查询全校所有的用户条数
            userAdminList = userMapper.selectAllUser(userNum);
        }
        else {
            // 查询所有的条数
            // 查询所在学院的用户
            userAdminList = userMapper.selectAllUserByUserNum(userNum);
        }
    
        // 设置分页
        PageInfo pageInfo = new PageInfo(userAdminList);
    
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse searchUserAdminListWithCondition(int pageNum, int pageSize, String userNum, int roleId, UserInfo userInfo) {
        // startPage -- start
        // 填充自己的sql查询逻辑
        // pageHelper -- 收尾
        
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
    
        List<UserAdminDto> userAdminList = Lists.newArrayList();
    
        if (roleId == Const.ROLEID_COLLEGE) {
            // 查询全校所有的用户条数
            userAdminList = userMapper.selectAllUserWithCondition(Const.ACADEMY_COLLEGE, userInfo);
        }
        else {
            // 查询所有的条数
            // 查询所在学院的用户
            userAdminList = userMapper.selectAllUserByUserNumWithCondition(userNum, userInfo);
        }
    
        // 设置分页
        PageInfo pageInfo = new PageInfo(userAdminList);
        // pageInfo.setList(userAdminList);
    
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse modifySexWithUserNum(String userNum, Integer sex) {
        int result = userInfoMapper.updateSexByUserNum(userNum, sex);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("对不起，修改用户：" + userNum + "性别失败，请重新尝试！");
        }
        
        return ServerResponse.createBySuccessMessage("亲，恭喜你，修改用户：" + userNum + "性别成功！");
    }
    
    @Override
    public ServerResponse modifyStatusWithUserNum(String userNum, Integer status) {
        int result = userMapper.updateStatusByUserNum(userNum, status);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("对不起，修改用户：" + userNum + "状态失败，请重新尝试！");
        }
        
        return ServerResponse.createBySuccessMessage("亲，修改用户：" + userNum + "状态成功！");
    }
    
    @Override
    public ServerResponse modifyUserByUserNum(UserInfo userInfo) {
        
        if (userInfoMapper.updateByUserNumSelective(userInfo) == 0) {
            return ServerResponse.createByErrorMessage("对不起，修改用户：" + userInfo.getUserNum() + "信息失败，请重新尝试！");
        }
        
        return ServerResponse.createBySuccessMessage("亲，修改用户：" + userInfo.getUserNum() + "信息成功！");
        
    }
    
    @Override
    public ServerResponse removeUserByUserNum(String userNum) {
        if (userMapper.deleteByUserNum(userNum) == 0 && userInfoMapper.deleteByUserNum(userNum) == 0) {
            return ServerResponse.createByErrorMessage("删除用户：" + userNum + "失败，请重试！");
        }
        return ServerResponse.createBySuccessMessage("删除用户：" + userNum + "成功！");
    }
    
    /**
     * 管理员新增用户
     * @param userAdminDto
     * @param roleId
     * @return
     */
    @Override
    public ServerResponse addUserAdmin(UserAdminDto userAdminDto, int roleId) {
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(userAdminDto, userInfo);
        if (userInfoMapper.insertSelective(userInfo) == 0) {
            return ServerResponse.createByErrorMessage("对不起，新增用户失败！");
        }
        
        User user = new User();
        user.setUserNum(userAdminDto.getUserNum());
        user.setUserPwd(Const.UNIFORM_USERPWD);
        user.setUserStatus(Const.UNIFORM_STATUS);
        if (userMapper.insertSelective(user) == 0) {
            return ServerResponse.createByErrorMessage("对不起，新增用户失败！");
        }
        
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserNum(userAdminDto.getUserNum());
        if (userRoleMapper.insertSelective(userRole) == 0) {
            return ServerResponse.createByErrorMessage("对不起，新增用户失败！");
        }
        
        return ServerResponse.createBySuccessMessage("恭喜你，新增用户成功！");
    }
    
    /**
     * 批量删除用户
     * @param userNums
     * @return
     */
    @Override
    public ServerResponse batchRemoveUser(String[] userNums) {
        if (userInfoMapper.deleteUserInfoByUserNum(userNums) < 1) {
            return ServerResponse.createByErrorMessage("删除用户ieps_user_info表失败!");
        }
        
        if (userMapper.deleteUserByUserNum(userNums) < 1) {
            return ServerResponse.createByErrorMessage("删除用户ieps_user表失败");
        }
    
        if (userRoleMapper.deleteUserRoleByUserNum(userNums) < 1) {
            return ServerResponse.createByErrorMessage("删除用户ieps_user_role表失败");
        }
    
        return ServerResponse.createBySuccessMessage("删除用户信息成功!");
    }
    
    /**
     * 通过姓名查找用户信息
     * @param userName
     * @return
     */
    @Override
    public ServerResponse getUserInfoByUserName(String userName) {
        UserInfo userInfo = userInfoMapper.selectUserInfoByUserName(userName);
        if (userInfo == null) {
            return ServerResponse.createByErrorMessage("对不起，暂时没有这个人，可能查找出错了！");
        }
        
        return ServerResponse.createBySuccess(userInfo);
    }
    
    /**
     * 管理员可以管理的角色
     * @param roleId
     * @return
     */
    @Override
    public ServerResponse getAllRoleIdWithRoleIdByAdmin(Integer roleId) {
        List<Role> roleIdList = roleMapper.selectRoleIdWithRoleIdByAdmin(roleId);
        if (roleIdList.size() == 0) {
            return ServerResponse.createByError();
        }
        
        return ServerResponse.createBySuccess(roleIdList);
    }
    
    @Override
    public ServerResponse batchAddUser(UserAdminDto userAdminDto, Integer roleId, int roleIdAdmin) {
        
        if (roleIdAdmin != Const.ROLEID_ACADEMY && Const.ROLEID_COLLEGE != roleIdAdmin) {
            return ServerResponse.createByErrorMessage("对不起，目前你没有权限执行操作！");
        }
        
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(userAdminDto, userInfo);
    
        User user = new User();
        
        user.setUserPwd(Const.UNIFORM_USERPWD);
        user.setUserStatus(Const.UNIFORM_STATUS);
    
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        
        String userNum = "";
        String userName = "";
        
        int isFlag = 0;
        int length = userAdminDto.getUserNums().length;
        for (int i = 0; i < length; i++) {
            userNum = userAdminDto.getUserNums()[i];
            userName = userAdminDto.getUserNames()[i];
            
            userInfo.setUserNum(userNum).setUserName(userName);
    
            user.setUserNum(userNum);
    
            userRole.setUserNum(userNum);
    
            if (userInfoMapper.insertSelective(userInfo) == 0) {
                continue;
            }
            
            if (userMapper.insertSelective(user) == 0) {
                continue;
            }
    
            if (userRoleMapper.insertSelective(userRole) == 0) {
                continue;
            }
            
            isFlag++;
        }
        
        if (isFlag < length) {
            return ServerResponse.createByErrorMessage("插入失败 " + (length - isFlag) + " 条数据！");
        }
        
        return ServerResponse.createBySuccessMessage("恭喜你，批量新增用户 " + length + " 条数据成功！");
    }
    
}
