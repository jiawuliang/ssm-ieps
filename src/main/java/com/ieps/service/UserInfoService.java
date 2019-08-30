package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.UserInfo;

/**
 * Created by ljw
 */
public interface UserInfoService {
    
    ServerResponse<UserInfo> findByUserNum(String userNum);
    
    ServerResponse modifyUserInfo(UserInfo userInfo);
    
    ServerResponse<String> getVerifyCode(String verifyNum);
    
    ServerResponse checkVerifyNum(String userNum, String verifyNum);
    
    ServerResponse getUserInfoWithItemNum(String itemNum);
    

}
