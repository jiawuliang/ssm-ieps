package com.ieps.service;

import com.ieps.common.ServerResponse;
import com.ieps.pojo.FileHub;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ljw
 */
public interface FileAdminService {
    
    ServerResponse getFileListByUserNum(int pageNum, int pageSize, String userNumAdmin, int roleId, FileHub fileHub);
    // filePath, fileName, userNum, userNumAdmin, id, roleId
    ServerResponse removeFileById(String filePath, String fileName, String userNum, Integer id, int roleId);
    
    ServerResponse batchRemoveFile(String filePath, String[] fileNames, String[] userNums, Integer[] ids, int roleId);
    
    ServerResponse batchUploadFile(MultipartFile[] files, String userNum, String filePath, int fileKind, String typeNum);
    
    ServerResponse modifyFileKindWithUserNum(int roleId, FileHub fileHub);
    
    // 首页的常用下载文件列表
    ServerResponse getFileListByAdmin(int pageNum, int pageSize, String fileName, String updateTime);
    
    // 根据项目编号获取文件名
    ServerResponse getFileWithItemNum(String itemNum);
    
    //
    
    
    
}
