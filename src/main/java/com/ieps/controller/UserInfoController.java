package com.ieps.controller;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.User;
import com.ieps.pojo.UserInfo;
import com.ieps.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * Created by ljw
 */
@Controller
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    
    @RequestMapping("/checkVerifyNum.do")
    @ResponseBody
    public ServerResponse checkVerifyNum(String userNum, String verifyNum) {
        return userInfoService.checkVerifyNum(userNum, verifyNum);
    }
    
    @RequestMapping("/getVerifyCode.do")
    @ResponseBody
    public ServerResponse<String> getVerifyCode(String verifyNum) {
        return userInfoService.getVerifyCode(verifyNum);
    }
    
    @RequestMapping("/getUserInfo.do")
    @ResponseBody
    public ServerResponse<UserInfo> getUserInfo(String userNum, HttpSession session) {
        User user = (User) session.getAttribute("activeUser");
        
        /*if (!user.getUserNum().equals(userNum)) {
            return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        }*/
        
        return userInfoService.findByUserNum(userNum);
    }
    
    @RequestMapping(value = "/modifyUserInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse modifyUserInfo(UserInfo userInfo, HttpSession session) {
        User user = (User) session.getAttribute("activeUser");
        
       /* if (!user.getUserNum().equals(userInfo.getUserNum())) {
            return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        }*/
        
        return userInfoService.modifyUserInfo(userInfo);
    }
    
    
    @RequestMapping(value = "/changeUserImg.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse changeUserImg(@RequestParam("file") MultipartFile file, String userNum,
                                        HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("activeUser");
    
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
    
        //如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            //上传文件名
            String fileName = file.getOriginalFilename();
            System.out.println(fileName + "\t文件名");
            
            //上传文件路径
            String storePath = request.getServletContext().getRealPath("/hub/images/");
            // 前缀
            String prefix = "/hub/images/";
    
            File filepath = new File(storePath, fileName);
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            try {
                file.transferTo(new File(storePath + File.separator + fileName));// 把文件写入目标文件地址
    
                UserInfo userInfo = new UserInfo();
                userInfo.setUserNum(userNum);
                userInfo.setUserImg(prefix + fileName);
                
                userInfoService.modifyUserInfo(userInfo);
    
                return ServerResponse.createBySuccessMessage("上传" + fileName + "成功！");
            } catch (Exception e) {
                System.out.println("FileHubController：" + e.getStackTrace());
                return ServerResponse.createByErrorMessage("文件" + fileName + "上传异常，请重试！");
            }
        }
        
        return ServerResponse.createByErrorMessage("上传文件为空，请检查配置！");
    }
    
    
    @RequestMapping(value = "/getUserInfoWithItemNum.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getUserInfoWithItemNum(String itemNum, HttpSession session) {
        User user = (User) session.getAttribute("activeUser");
        
       /* if (!user.getUserNum().equals(userInfo.getUserNum())) {
            return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        }*/
        
        return userInfoService.getUserInfoWithItemNum(itemNum);
    }
    
}
