package com.ieps.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ieps.common.Const;
import com.ieps.common.ServerResponse;
import com.ieps.mapper.InformMapper;
import com.ieps.mapper.UserInfoMapper;
import com.ieps.pojo.Inform;
import com.ieps.pojo.UserInfo;
import com.ieps.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ljw
 */
@Service
public class InformServiceImpl implements InformService {
    
    @Autowired
    private InformMapper informMapper;
    
    @Autowired
    private UserInfoMapper userInfoMapper;
    
    /**
     * 根据userNum查看通知公告
     * @param userNum
     * @return
     */
    @Override
    public ServerResponse getInformByUserNum(String userNum) {
        List<Inform> informList = informMapper.selectByUserNum(userNum);
        
        if (informList.size() == 0) {
            return ServerResponse.createByErrorMessage("目前该用户尚未发布任何通知，请重新输入查找！");
        }
        
        return ServerResponse.createBySuccess(informList);
    }
    
    /**
     *
     * @param pageNum
     * @param pageSize
     * @param userNum
     * @param roleId
     * @return
     */
    @Override
    public ServerResponse getAllInformList(int pageNum, int pageSize, String userNum, Integer roleId) {
        
        // startPage -- start
        // 填充自己的sql查询逻辑
        // pageHelper -- 收尾
    
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
    
        List<Inform> informList = Lists.newArrayList();
        
        if (roleId == Const.ROLEID_COLLEGE) {
            // 查询所有的条数
            informList = informMapper.selectAll();
        }
        else {
            // 查询所有的条数
            // 查询学校和所在学院的通知
            UserInfo userInfo = userInfoMapper.selectByUserNum(userNum);
            
            List<String> subjectList = Lists.newArrayList();
            subjectList.add(userInfo.getAcademy());
            subjectList.add(Const.ACADEMY_COLLEGE);
            
            informList = informMapper.selectAllInformList(subjectList);
        }
        
        // 设置分页
        PageInfo pageInfo = new PageInfo(informList);
        pageInfo.setList(informList);
        
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse searchInformListWithCondition(int pageNum, int pageSize, String userNum, Integer roleId, Inform inform) {
        
        UserInfo userInfo = userInfoMapper.selectByUserNum(userNum);
        
    
        // startPage -- start
        // 填充自己的sql查询逻辑
        // pageHelper -- 收尾
    
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
    
        List<Inform> informList = Lists.newArrayList();
    
        if (roleId == Const.ROLEID_COLLEGE) {
            // 查询所有的条数
            informList = informMapper.selectAllWithCondition(inform);
        }
        else {
            // 查询所有的条数
            // 查询学校和所在学院的通知
            List<String> subjectList = Lists.newArrayList();
            subjectList.add(userInfo.getAcademy());
            subjectList.add(Const.ACADEMY_COLLEGE);
        
            informList = informMapper.selectAllInformListWithCondition(subjectList, inform);
        }
    
        // 设置分页
        PageInfo pageInfo = new PageInfo(informList);
        pageInfo.setList(informList);
    
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse removeInformById(Integer id, Integer roleId) {
        
        if (roleId < informMapper.selectByPrimaryKey(id).getRoleId()) {
            return ServerResponse.createByErrorMessage("对不起，目前你还没有权限执行该行的删除操作！");
        }
        
        int result = informMapper.deleteByPrimaryKey(id);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("删除当前行：id" + id + " 失败!");
        }
        
        return ServerResponse.createBySuccessMessage("删除当前行：id" + id + " 成功!");
    }
    
    @Override
    public ServerResponse modifyInformById(Inform inform) {
        int result = informMapper.updateByPrimaryKeySelective(inform);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("更新当前行：id" + inform.getId() + " 失败!");
        }
    
        return ServerResponse.createBySuccessMessage("更新当前行：id" + inform.getId() + " 成功!");
    }
    
    @Override
    public ServerResponse addInform(Inform inform) {
        int result = informMapper.insert(inform);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("插入数据失败，请重新填写!");
        }
    
        return ServerResponse.createBySuccessMessage("插入数据成功!");
    }
    
    @Override
    public ServerResponse batchRemoveInform(Integer[] ids, Integer roleId) {
    
        for (int i = 0; i < ids.length; i++) {
            if (roleId < informMapper.selectByPrimaryKey(ids[i]).getRoleId()) {
                return ServerResponse.createByErrorMessage("对不起，目前你还没有权限执行该行的删除操作！");
            }
        }
    
        if (informMapper.deleteInformByIds(ids) < 1) {
            return ServerResponse.createByErrorMessage("删除通知公告失败!");
        }

        return ServerResponse.createBySuccessMessage("删除通知公告成功!");
    }
    
    
    @Override
    public ServerResponse getInformListByAdmin(int pageNum, int pageSize, String head, String pubdate) {
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        
        // 只查询学校管理员发布的通知
        List<Inform> informList = informMapper.selectAllInformByAdminWithUserNum(Const.USERNUM_COLLEGE, head, pubdate);
        if (informList.size() == 0) {
            return ServerResponse.createByErrorMessage("没有通知，数据为空！");
        }
    
        // 设置分页
        PageInfo pageInfo = new PageInfo(informList);
        pageInfo.setList(informList);
    
        return ServerResponse.createBySuccess(pageInfo);
    }
    
    @Override
    public ServerResponse getInformListByAdminWithHead(Integer id) {
        Inform inform = informMapper.selectByPrimaryKey(id);
        if (inform == null) {
            return ServerResponse.createByErrorMessage("");
        }
        
        return ServerResponse.createBySuccess(inform);
    }
    
    @Override
    public ServerResponse getInformDetailById(Integer id) {
        Inform inform = informMapper.selectByPrimaryKey(id);
        if (inform == null) {
            return ServerResponse.createByErrorMessage("");
        }
        
        return ServerResponse.createBySuccess(inform);
    }
    
    
}
