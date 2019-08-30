package com.ieps.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ieps.common.ServerResponse;
import com.ieps.pojo.RolePerm;
import com.ieps.pojo.User;
import com.ieps.pojo.UserInfo;
import com.ieps.service.RolePermService;
import com.ieps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ljw
 */
@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RolePermService rolePermService;
    
    /**
     * 通过userNum和userPwd登录系统
     * @param userNum
     * @param userPwd
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse login(String userNum, String userPwd, HttpSession session) {
   
        ServerResponse user = userService.login(userNum, userPwd);
        
        if (user.getStatus() == 0) {
            session.setAttribute("activeUser", user.getData());
        }
        
        return user;
    }
    
    /**
     * 根据roleId获取菜单
     * @param userNum
     * @param roleId
     * @param session
     * @return
     */
    @RequestMapping(value = "/getMenu.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getMenu(String userNum, Integer roleId, HttpSession session) {
        User user = (User) session.getAttribute("activeUser");
    
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        ServerResponse<List<RolePerm>> perm = rolePermService.getMenu(roleId);
        
        if (perm.getStatus() != 0) {
            return ServerResponse.createByErrorMessage(perm.getMsg());
        }
        
        return ServerResponse.createBySuccess(perm.getMsg(),
                JSONObject.toJSONString(perm.getData().get(0).getPermList(),
                        SerializerFeature.WriteMapNullValue));
    }
    
    /**
     * 注册用户信息
     * @param user
     * @param userInfo
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse register(User user, UserInfo userInfo, Integer roleId) {
        return userService.register(user, userInfo, roleId);
    }
    
    /**
     * 根据userNum检查用户原始密码是否正确
     * @param userNum
     * @param userPwd
     * @return
     */
    @RequestMapping(value = "/checkUserPwdWithUserNum.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse checkUserPwdWithUserNum(String userNum, String userPwd) {
        
        return userService.checkUserPwdWithUserNum(userNum, userPwd);
    }
    
    /**
     * 退出系统
     * @return
     */
    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    public String logout() {
        return "common/login";
    }
    
    /**
     * 返回前端首页
     * @return
     */
    @RequestMapping("/goHome.do")
    public String goHome() {
        return "home";
    }
    
    /**
     * 进入系统首页
     * @return
     */
    @RequestMapping("/goIndex.do")
    public String goIndex() {
        return "common/index";
    }
    
    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/goLogin.do")
    public String goLogin() {
        return "common/login";
    }
    
    /**
     * 注册页面
     * @return
     */
    @RequestMapping("/goRegister.do")
    public String goRegister() {
        return "common/register";
    }
    
    /**
     * 忘记密码页面
     * @return
     */
    @RequestMapping("/goForgetPwd.do")
    public String goForgotPwd() {
        return "common/forgetPwd";
    }
    
    
    //            首页
    // 重要通知详情
    @RequestMapping(value = "/goShowInformDetail.do", method = RequestMethod.GET)
    public String goShowInformDetail() {
        return "show/showInformDetail";
    }
    
    // 重要通知详情
    @RequestMapping(value = "/goShowInformMore.do", method = RequestMethod.GET)
    public String goShowInformMore() {
        return "show/showInformMore";
    }
    
    @RequestMapping(value = "/goShowInfoemDetail.do", method = RequestMethod.GET)
    public String goShowInfoemDetail() {
        return "show/showInformDetail";
    }
    
    @RequestMapping(value = "/goShowItemMore.do", method = RequestMethod.GET)
    public String goShowItemMore() {
        return "show/showItemMore";
    }
    
    @RequestMapping(value = "/goShowItemDetail.do", method = RequestMethod.GET)
    public String goShowItemDetail() {
        return "show/showItemDetail";
    }
    
    @RequestMapping(value = "/goShowDownLoadMore.do", method = RequestMethod.GET)
    public String goShowDownLoadMore() {
        return "show/showDownLoadMore";
    }
    
    
    
}
