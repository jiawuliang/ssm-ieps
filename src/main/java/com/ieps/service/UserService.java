package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.User;
import com.ieps.pojo.UserInfo;

/**
 * Created by ljw
 */
public interface UserService {
    
    ServerResponse login(String userNum, String userPwd);
    
    ServerResponse getMenu(String userNum);
    
    ServerResponse register(User user, UserInfo userInfo, Integer roleId);
    
    ServerResponse checkUserPwdWithUserNum(String userNum, String userPwd);
    
    ServerResponse forgetPwd(String userNum, String userPwd);
    
    ServerResponse modifyPwd(User user);
    
    ServerResponse checkUser(String userNum);
    
    ServerResponse getUserPwdWithUserNum(String userNum);
}
