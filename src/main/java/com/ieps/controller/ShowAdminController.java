package com.ieps.controller;

import com.ieps.common.ServerResponse;
import com.ieps.service.ShowAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ljw
 */
@Controller
public class ShowAdminController {

    @Autowired
    private ShowAdminService showAdminService;
    
    /**
     * 各学院历届的参赛人数
     * @param itemDate
     * @return
     */
    @RequestMapping("/getAcademyStuSex.do")
    @ResponseBody
    public ServerResponse getAcademyStuSex(String itemDate) {
    
        return showAdminService.getAcademyStuSex(itemDate);
    }
    
    /**
     * 每年的参赛项目数量
     * @return
     */
    @RequestMapping("/getItemsWithYear.do")
    @ResponseBody
    public ServerResponse getItemsWithYear() {
        
        return showAdminService.getItemsWithYear();
    }
    
    
    
}
