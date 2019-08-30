package com.ieps.controller;

import com.ieps.common.ServerResponse;
import com.ieps.dto.ReviewAdminDto;
import com.ieps.pojo.User;
import com.ieps.service.ReviewAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by ljw
 */
@Controller
public class ReviewAdminController {

    @Autowired
    private ReviewAdminService reviewAdminService;
    
    /**
     * 根据项目编号（itemNum）评审结果，分页显示
     * @param page
     * @param itemNum
     * @param limit
     * @param session
     * @param userNum
     * @return
     */
    @RequestMapping("/getAllReviewWithItemNum.do")
    @ResponseBody
    public ServerResponse getAllReviewWithItemNum(@RequestParam(value = "page", defaultValue = "1") int page, String itemNum,
                                                  @RequestParam(value = "limit", defaultValue = "10") int limit, HttpSession session,
                                                  @RequestParam("userNum") String userNum) {
    
    
        User user = (User) session.getAttribute("activeUser");
    
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return reviewAdminService.getAllReviewListWithItemNum(page, limit, itemNum);
    
    }
    
    
    
    
    @RequestMapping("/checkReview.do")
    @ResponseBody
    public ServerResponse checkReview(ReviewAdminDto reviewAdminDto, HttpSession session) {
        
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return reviewAdminService.checkReview(reviewAdminDto);
        
    }
    

}
