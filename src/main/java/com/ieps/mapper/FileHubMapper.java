package com.ieps.mapper;

import com.ieps.pojo.FileHub;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileHubMapper {
    
    int deleteByPrimaryKey(Integer id);
    
    int insert(FileHub record);
    
    int insertSelective(FileHub record);
    
    FileHub selectByPrimaryKey(Integer id);
    
    int updateByPrimaryKeySelective(FileHub record);
    
    int updateByPrimaryKey(FileHub record);
    
    
    
    FileHub selectByFileName(String fileName);
    
    // 学校管理员  文件管理列表
    List<FileHub> selectAllFileHub(FileHub fileHub);
    
    // 学院管理员  文件管理列表
    List<FileHub> selectAllFileByUserNum(@Param("userNumCollege") String userNumCollege, @Param("userNumAcademy") String userNumAcademy, @Param("fileHub") FileHub fileHub);
    
    // 管理员批删除文件
    int batchDeleteFileByIds(Integer[] ids);
    
    int deleteFileHubByItemNum(String[] itemNums);
    
    
    List<FileHub> selectAllFileByAdminWithKind(@Param("fileKind") int fileKind, @Param("fileName") String fileName, @Param("updateTime") String updateTime);
    
    FileHub selectFileWithItemNum(String typeNum);
    
    
    

}