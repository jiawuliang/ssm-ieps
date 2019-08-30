package com.ieps.controller;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.User;
import com.ieps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ljw
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/forgetPwd.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse forgetPwd(String userNum, String userPwd) {
        System.out.println(userNum + "   " + userPwd);
        return userService.forgetPwd(userNum, userPwd);
    }
    
    @RequestMapping(value = "/modifyPwd.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse modifyPwd(User user) {
        System.out.println(user.toString());
        
        return userService.modifyPwd(user);
    }
    
    @RequestMapping(value = "/checkUser.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse checkUser(String userNum) {
        return userService.checkUser(userNum);
    }
    
    @RequestMapping(value = "/getUserPwdWithUserNum.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getUserPwdWithUserNum(String userNum) {
        return userService.getUserPwdWithUserNum(userNum);
    }
    
    
    
    
    
    
    
    
}
