package com.ieps.service.impl;

import com.ieps.common.ServerResponse;
import com.ieps.dto.UserAdminDto;
import com.ieps.mapper.UserInfoMapper;
import com.ieps.pojo.UserInfo;
import com.ieps.service.UserInfoService;
import com.ieps.util.MailUtil;
import com.ieps.util.miaodiyun.IndustrySMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

/**
 * Created by ljw
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    
    @Autowired
    private UserInfoMapper userInfoMapper;
    
    @Override
    public ServerResponse<UserInfo> findByUserNum(String userNum) {
        UserInfo userInfo = userInfoMapper.selectByUserNum(userNum);
        userInfo.setUserNum(userNum);
        
        if (userInfo == null) {
            return ServerResponse.createByErrorMessage("用户过时或不存在，请重新登录！");
        }
        
        return ServerResponse.createBySuccess(userInfo);
    }
    
    @Override
    public ServerResponse modifyUserInfo(UserInfo userInfo) {
        int result = userInfoMapper.updateByUserNumSelective(userInfo);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("你还没有修改任何信息");
        }
        
        return ServerResponse.createBySuccessMessage("恭喜你，修改成功！");
    }
    
    @Override
    public ServerResponse<String> getVerifyCode(String verifyNum) {
        // 邮箱验证
        if (verifyNum.indexOf("@") != -1) {
            int randNum = (int) (Math.random() * 1000000);
            try {
                MailUtil.send_mail(verifyNum, String.valueOf(randNum));
                System.out.println("邮箱验证码发送成功！" + randNum);
                return ServerResponse.createBySuccess("邮箱验证码已发送，请及时输入验证", String.valueOf(randNum));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        
        return ServerResponse.createBySuccess("短信验证码已发送，请及时输入验证", IndustrySMS.execute(verifyNum));
    }
    
    @Override
    public ServerResponse checkVerifyNum(String userNum, String verifyNum) {
        int result = 0;
        
        if (verifyNum.lastIndexOf("@") != -1) {
            result = userInfoMapper.selectByUserNumAndEmail(userNum, verifyNum);
        }
        else {
            result = userInfoMapper.selectByUserNumAndPhotoNum(userNum, verifyNum);
        }
        
        if (result == 0) {
            return ServerResponse.createByErrorMessage("账号与手机号码或邮箱不匹配，请重新尝试！");
        }
        
        return ServerResponse.createBySuccess();
    }
    
    @Override
    public ServerResponse getUserInfoWithItemNum(String itemNum) {
        List<UserAdminDto> userAdminDtoList = userInfoMapper.selectUserInfoWithItemNum(itemNum);
        if (userAdminDtoList.size() == 0) {
            return ServerResponse.createByError();
        }
        
        return ServerResponse.createBySuccess(userAdminDtoList);
    }
}
