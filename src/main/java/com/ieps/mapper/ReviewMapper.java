package com.ieps.mapper;

import com.ieps.dto.ReviewAdminDto;
import com.ieps.pojo.Review;

import java.util.List;

public interface ReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    int insertSelective(Review record);

    Review selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
    
    List<ReviewAdminDto> selectAllReviewListWithItemNum(String itemNum);
    
    List<ReviewAdminDto> selectReviewWithMultCondition(ReviewAdminDto reviewAdminDto);
    
    
}