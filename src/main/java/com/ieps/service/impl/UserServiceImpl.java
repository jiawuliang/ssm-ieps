package com.ieps.service.impl;

import com.ieps.common.ServerResponse;
import com.ieps.mapper.UserInfoMapper;
import com.ieps.mapper.UserMapper;
import com.ieps.mapper.UserRoleMapper;
import com.ieps.pojo.User;
import com.ieps.pojo.UserInfo;
import com.ieps.pojo.UserRole;
import com.ieps.service.UserService;
import com.ieps.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ljw
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Override
    public ServerResponse login(String userNum, String userPwd) {
        User user = userMapper.login(userNum, EncryptUtil.AESencode(userPwd, "123456"));
        
        System.out.println(user);

        if (user == null) {
            return ServerResponse.createByErrorMessage("登录失败，请重新登录！");
        }
    
        if (user.getUserStatus() == 0) {
            return ServerResponse.createByErrorMessage("对不起，该账号目前已经被锁定，请联系管理员激活！");
        }

        return ServerResponse.createBySuccess("登录成功，请尽情享用！", user);
    }
    
    @Override
    public ServerResponse<User> getMenu(String userNum) {
        User user = userMapper.selectMenu(userNum);
        if (user == null) {
            return ServerResponse.createByErrorMessage("连接超时，请重新登录！");
        }
        return ServerResponse.createBySuccess("加载菜单完成，请尽情享受！", user);
    }
    
    // 注册
    @Override
    public ServerResponse register(User user, UserInfo userInfo, Integer roleId) {
        if (!user.getUserPwd().equals(user.getRePassword())) {
            return ServerResponse.createByErrorMessage("前后两次密码不一致，请重新输入！");
        }
        
        if (userMapper.selectByUserNum(user.getUserNum()) != null) {
            return ServerResponse.createByErrorMessage("账号已注册，请重新输入！");
        }
        
        // 使用AES加密算法经行加密
        user.setUserPwd(EncryptUtil.AESencode(user.getUserPwd(), "123456"));
        
        int result = userMapper.insertSelective(user);
        if (result < 1) {
            return ServerResponse.createByErrorMessage("插入User用户表失败");
        }
    
        result = userInfoMapper.insertSelective(userInfo);
        if (result < 1) {
            return ServerResponse.createByErrorMessage("插入UserInfo用户信息表失败");
        }
        
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserNum(user.getUserNum());
        result = userRoleMapper.insertSelective(userRole);
        if (result < 1) {
            return ServerResponse.createByErrorMessage("插入UserRole用户信息表失败");
        }
        
        return ServerResponse.createBySuccessMessage("注册" + user.getUserNum() + "账号成功，返回首页！");
    }
    
    @Override
    public ServerResponse checkUserPwdWithUserNum(String userNum, String userPwd) {
        
        if (userMapper.selectUserPwdWithUserNum(userNum, EncryptUtil.AESencode(userPwd, "123456")) <= 0) {
            return ServerResponse.createByErrorMessage("用户名与密码不匹配,请重新输入！");
        }
        
        return ServerResponse.createBySuccessMessage("用户名与密码匹配，请继续！");
    }
    
    
    @Override
    public ServerResponse forgetPwd(String userNum, String userPwd) {
        User user = userMapper.selectByUserNum(userNum);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户不存在，请重新填写！");
        }
        
        if (userMapper.updatePwd(userNum, EncryptUtil.AESencode(userPwd, "123456")) < 1) {
            return ServerResponse.createByErrorMessage("重置密码失败，请重新填写！");
        }
        
        return ServerResponse.createBySuccessMessage("重置密码成功，请使用新密码重新登录！");
    }
    
    @Override
    public ServerResponse modifyPwd(User user) {
        // 使用AES加密算法经行加密
        user.setUserPwd(EncryptUtil.AESencode(user.getUserPwd(), "123456"));
        user.setNewPassword(EncryptUtil.AESencode(user.getNewPassword(), "123456"));
        user.setRePassword(EncryptUtil.AESencode(user.getRePassword(), "123456"));
    
        User checkUser = userMapper.checkUser(user.getUserNum(), user.getUserPwd());
        if (checkUser == null) {
            return ServerResponse.createByErrorMessage("账号与旧密码不匹配，请重新填写！");
        }
        
        if (!user.getNewPassword().equals(user.getRePassword())) {
            return ServerResponse.createByErrorMessage("前后两次密码不一致，请重新填写！");
        }
        
        if (userMapper.updatePwd(user.getUserNum(), user.getNewPassword()) < 1) {
            return ServerResponse.createByErrorMessage("修改密码失败，请重新填写！");
        }
        
        return ServerResponse.createBySuccessMessage("修改密码成功，请重新登录！");
    }
    
    @Override
    public ServerResponse checkUser(String userNum) {
        User user = userMapper.selectByUserNum(userNum);
        if (user == null) {
            return ServerResponse.createBySuccess();
        }
        
        return ServerResponse.createByErrorMessage("用户已存在，请重新填写账号！");
    }
    
    @Override
    public ServerResponse getUserPwdWithUserNum(String userNum) {
        User user = userMapper.selectByUserNum(userNum);
        
        user.setUserPwd(EncryptUtil.AESdecode(user.getUserPwd(), "123456"));
        
        return ServerResponse.createBySuccess(user);
    }
    
    
}
