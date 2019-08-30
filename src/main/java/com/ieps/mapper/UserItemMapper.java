package com.ieps.mapper;

import com.ieps.pojo.UserItem;
import org.apache.ibatis.annotations.Param;

public interface UserItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserItem record);

    int insertSelective(UserItem record);

    UserItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserItem record);

    int updateByPrimaryKey(UserItem record);
    
    
    
    int deleteUserItemByItemNums(String[] itemNums);
    
    int updateUserNumByItemNumAndUserNum(@Param("itemNum") String itemNum, @Param("userNum") String userNum);

    int insertUserItemWithItemNumAndUserNum(@Param("userNum") String userNum, @Param("itemNum") String itemNum, @Param("identity") int identity);

    int selectUserItemWithItemNumAndUserNum(@Param("itemNum")String itemNum, @Param("userNum") String userNum, @Param("reviewLevel") int reviewLevel);
    
    int deleteUsetItemWithReviews(String itemNum);
    
}