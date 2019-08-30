package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.dto.ReviewAdminDto;

/**
 * Created by ljw
 */
public interface ReviewAdminService {
    
    ServerResponse getAllReviewListWithItemNum(int pageNum, int pageSize, String itemNum);
    
    ServerResponse checkReview(ReviewAdminDto reviewAdminDto);
    
}
