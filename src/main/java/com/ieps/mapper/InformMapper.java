package com.ieps.mapper;

import com.ieps.pojo.Inform;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InformMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Inform record);

    int insertSelective(Inform record);

    Inform selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inform record);

    int updateByPrimaryKey(Inform record);
    
    
    
    List<Inform> selectByUserNum(String publisher);
    
    List<Inform> selectAllInformList(List<String> list);
    
    List<Inform> selectInformList(@Param("list") List<String> list, @Param("subject") String subject);
    
    List<Inform> selectAll();
    
    int deleteInformByIds(Integer[] ids);
    
    List<Inform> selectAllWithCondition(Inform inform);
    
    List<Inform> selectAllInformListWithCondition(@Param("list") List<String> list, @Param("inform") Inform inform);
    
    List<Inform> selectAllInformByAdminWithUserNum(@Param("publisher") String publisher, @Param("head") String head, @Param("pubdate") String pubdate);
    
}