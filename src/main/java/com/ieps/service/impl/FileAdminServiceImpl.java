package com.ieps.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ieps.common.Const;
import com.ieps.common.ServerResponse;
import com.ieps.mapper.FileHubMapper;
import com.ieps.mapper.UserInfoMapper;
import com.ieps.pojo.FileHub;
import com.ieps.service.FileAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by ljw
 */
@Service
public class FileAdminServiceImpl implements FileAdminService {
    
    @Autowired
    private FileHubMapper fileHubMapper;
    
    @Autowired
    private UserInfoMapper userInfoMapper;
    
    /**
     * 通过上传文件的用户编号查找文件列表
     * @param pageNum
     * @param pageSize
     * @param userNumAdmin
     * @param roleId
     * @param fileHub
     * @return
     */
    @Override
    public ServerResponse getFileListByUserNum(int pageNum, int pageSize, String userNumAdmin, int roleId, FileHub fileHub) {
        
        PageHelper.startPage(pageNum, pageSize);
        
        List<FileHub> fileHubList = Lists.newArrayList();
        
        if (Const.ROLEID_COLLEGE == roleId) {
            // 查看所有的文件
            fileHubList = fileHubMapper.selectAllFileHub(fileHub);
            
        } else if (Const.ROLEID_ACADEMY == roleId) {
            // 只能查看学校管理员发布的，以及本学院的文件
            fileHubList = fileHubMapper.selectAllFileByUserNum(Const.USERNUM_COLLEGE, userNumAdmin, fileHub);
        }
        
        if (fileHubList.size() == 0) {
            return ServerResponse.createByErrorMessage("对不起，可能暂无数据，请稍候再试！");
        }
        
        // 设置分页
        PageInfo pageInfo = new PageInfo(fileHubList);
        
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    /**
     * 删除服务上的文件
     * @return
     */
    private static ServerResponse deleteServerFile(String filePath, String[] fileNames) {
    
        File file = null;
        
        for (int i = 0; i < fileNames.length; i++) {
            file  = new File(filePath + fileNames[i]);
    
            if (!file.exists()) {
                return ServerResponse.createByErrorMessage("文件不存在，请检查数据库！");
            }
    
            if (!file.isFile()) {
                return ServerResponse.createByErrorMessage("不是文件，未知是文件夹还是目录，请检查数据库！");
            }
    
            if (!file.delete()) {
                return ServerResponse.createByErrorMessage("删除文件失败！");
            }
        }
    
        return ServerResponse.createBySuccess("删除文件成功！");
    }
    
    /**
     * 通过文件id删除文件
     * @param filePath
     * @param fileName
     * @param userNum
     * @param id
     * @param roleId
     * @return
     */
    @Override
    public ServerResponse removeFileById(String filePath, String fileName, String userNum, Integer id, int roleId) {
        String[] fileNames = {fileName};
        
        // 学校管理员
        if (Const.ROLEID_COLLEGE == roleId) {
            if (fileHubMapper.deleteByPrimaryKey(id) > 0) {
                return deleteServerFile(filePath, fileNames);
            }
        }
        // 学院管理员
        else if (Const.ROLEID_ACADEMY == roleId) {
            if (!Const.USERNUM_COLLEGE.equals(userNum)) {
                if (fileHubMapper.deleteByPrimaryKey(id) > 0) {
                    return deleteServerFile(filePath, fileNames);
                }
            } else {
                return ServerResponse.createByErrorMessage("对不起，目前你没有权限删除该文件记录！");
            }
        }
        
        return ServerResponse.createByErrorMessage("删除该文件失败，请重试！");
    }
    
    /**
     * 批量删除文件
     * @param filePath
     * @param fileNames
     * @param userNums
     * @param ids
     * @param roleId
     * @return
     */
    @Override
    public ServerResponse batchRemoveFile(String filePath, String[] fileNames, String[] userNums, Integer[] ids, int roleId) {
        // 学校管理员
        if (Const.ROLEID_COLLEGE == roleId) {
            if (fileHubMapper.batchDeleteFileByIds(ids) > 0) {
                return deleteServerFile(filePath, fileNames);
            }
        }
        // 学院管理员
        else if (Const.ROLEID_ACADEMY == roleId) {
            // int resultRows = 0;
            for (int i = 0; i < ids.length; i++) {
                // 不是学校管理员发布的内容，学院管理员只可以删除自己的文件
                if (!Const.USERNUM_COLLEGE.equals(userNums[i])) {
                    if (fileHubMapper.deleteByPrimaryKey(ids[i]) > 0) {
                        return deleteServerFile(filePath, fileNames);
                    }
                }
            }
    
            return ServerResponse.createByErrorMessage("对不起，目前你没有权限删除该文件记录！");
        }
        
        return ServerResponse.createByErrorMessage("删除该文件失败，请重试！");
    }
    
    /**
     * 保存文件
     * @param file
     * @param path
     * @param currentTime
     * @return
     */
    private boolean saveFile(MultipartFile file, String path, long currentTime) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                File filePath = new File(path);
                if (!filePath.exists())
                    filePath.mkdirs();
                // 文件保存路径
                String savePath = path + currentTime + "-" + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(savePath));
                
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * 批量上传文件
     * @param files
     * @param userNum
     * @param filePath
     * @param fileKind
     * @param typeNum
     * @return
     */
    @Override
    public ServerResponse batchUploadFile(MultipartFile[] files, String userNum, String filePath, int fileKind, String typeNum) {
        
        String fileName = "";
        
        //判断file数组不能为空并且长度大于0
        if (files != null && files.length > 0) {
            //循环获取file数组中得文件
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                //保存文件
                long currentTime = System.currentTimeMillis();
                //保存文件
                if (saveFile(file, filePath, currentTime)) {
                    
                    FileHub fileHub = new FileHub();
                    if (typeNum == null || typeNum == "") {
                        typeNum = " -1";
                    }
                    
                    fileHub.setFileKind(fileKind);
                    fileHub.setTypeNum(typeNum);
                    fileHub.setFileName(currentTime + "-" + file.getOriginalFilename());
                    fileHub.setFileSize((int) file.getSize());
                    fileHub.setUserNum(userNum);
                    fileHub.setAcademy(userInfoMapper.selectByUserNum(userNum).getAcademy());
                    
                    fileName = fileHub.getFileName();
                    
                    fileHubMapper.insertSelective(fileHub);
                }
            }
    
            return ServerResponse.createBySuccess(fileName);
        }
    
        return ServerResponse.createByErrorMessage("上传文件失败");
        
    }
    
    /**
     * 通过userNum判断有无权限修改文件类型
     * @param roleId
     * @param fileHub
     * @return
     */
    @Override
    public ServerResponse modifyFileKindWithUserNum(int roleId, FileHub fileHub) {
        // 学校管理员
        if (Const.ROLEID_COLLEGE == roleId) {
            if (fileHubMapper.updateByPrimaryKeySelective(fileHub) > 0) {
                return ServerResponse.createBySuccess();
            }
        }
        // 学院管理员
        else if (Const.ROLEID_ACADEMY == roleId) {
            if (!Const.USERNUM_COLLEGE.equals(fileHub.getUserNum())) {
                if (fileHubMapper.updateByPrimaryKeySelective(fileHub) > 0) {
                    return ServerResponse.createBySuccess();
                }
            } else {
                return ServerResponse.createByErrorMessage("对不起，目前你没有权限删除该文件记录！");
            }
        }
        
        return ServerResponse.createByErrorMessage("修改该文件的类型属性失败，请重试！");
    }
    
    /**
     * 管理员查看文件列表
     * @param pageNum
     * @param pageSize
     * @param fileName
     * @param updateTime
     * @return
     */
    @Override
    public ServerResponse getFileListByAdmin(int pageNum, int pageSize, String fileName, String updateTime) {
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        
        List<FileHub> fileHubList = fileHubMapper.selectAllFileByAdminWithKind(Const.FIRST_FILE_KIND, fileName, updateTime);
        if (fileHubList.size() == 0) {
            return ServerResponse.createByErrorMessage("没有文件，数据为空！");
        }
        
        // 设置分页
        PageInfo pageInfo = new PageInfo(fileHubList);
        
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    /**
     * 获取项目编号对应的申请文件
     * @param itemNum
     * @return
     */
    @Override
    public ServerResponse getFileWithItemNum(String itemNum) {
        FileHub fileHub = fileHubMapper.selectFileWithItemNum(itemNum);
        if (fileHub == null) {
            return ServerResponse.createByErrorMessage("对不起，你还没有上传项目附件呢");
        }
        
        return ServerResponse.createBySuccess(fileHub);
    }
    
    
}
