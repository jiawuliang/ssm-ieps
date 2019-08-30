package com.ieps.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ieps.common.ServerResponse;
import com.ieps.dto.ReviewAdminDto;
import com.ieps.mapper.ReviewMapper;
import com.ieps.service.ReviewAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw
 */
@Service
public class ReviewAdminServiceImpl implements ReviewAdminService {
    
    @Autowired
    private ReviewMapper reviewMapper;
    
    
    @Override
    public ServerResponse getAllReviewListWithItemNum(int pageNum, int pageSize, String itemNum) {
    
        PageHelper.startPage(pageNum, pageSize);
    
        List<ReviewAdminDto> reviewAdminDtoList = reviewMapper.selectAllReviewListWithItemNum(itemNum);
        
        
        // 评审类型：1：立项评审；2：中期检查；3：结题评审
        for (int i = 0; i < reviewAdminDtoList.size(); i++) {
            if (reviewAdminDtoList.get(i).getReviewType() == 1) {
                reviewAdminDtoList.get(i).setReviewAdminType("立项评审");
            }
            else if (reviewAdminDtoList.get(i).getReviewType() == 2) {
                reviewAdminDtoList.get(i).setReviewAdminType("中期检查");
            }
            else if (reviewAdminDtoList.get(i).getReviewType() == 3) {
                reviewAdminDtoList.get(i).setReviewAdminType("结题评审");
            }
        }
        
        // 评审级别： 1：院级评审；2：校级评审；3：省区级评审；4：国家级评审
        for (int i = 0; i < reviewAdminDtoList.size(); i++) {
            if (reviewAdminDtoList.get(i).getReviewLevel() == 1) {
                reviewAdminDtoList.get(i).setReviewAdminLevel("院级评审");
            }
            else if (reviewAdminDtoList.get(i).getReviewLevel() == 2) {
                reviewAdminDtoList.get(i).setReviewAdminLevel("校级评审");
            }
            else if (reviewAdminDtoList.get(i).getReviewLevel() == 3) {
                reviewAdminDtoList.get(i).setReviewAdminLevel("省区级评审");
            }
            else if (reviewAdminDtoList.get(i).getReviewLevel() == 4) {
                reviewAdminDtoList.get(i).setReviewAdminLevel("国家级评审");
            }
        }
    
        PageInfo pageInfo = new PageInfo(reviewAdminDtoList);
        
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse checkReview(ReviewAdminDto reviewAdminDto) {
        List<ReviewAdminDto> reviewAdminDtoList = reviewMapper.selectReviewWithMultCondition(reviewAdminDto);
        if (reviewAdminDtoList.size() > 0 ) {
            return ServerResponse.createByErrorMessage("对不起，你已经上传了一次同类型的文件，请重新操作！");
        }
    
        return ServerResponse.createBySuccess("恭喜你，上传文件成功！");
    }
}
