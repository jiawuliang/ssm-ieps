package com.ieps.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by ljw
 */
@Data
// @AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    
    // 账号（学号/教师号）
    private String userNum;

    // 密码
    private String userPwd;
    
    // 新密码
    private String newPassword;
    
    // 确认密码/旧密码
    private String rePassword;
    
    // 用户状态：1是激活；2是禁用；
    private Integer userStatus;

    private Date createTime;

    private Date updateTime;
    
    // 当前用户角色
    private Integer roleId;
    
    // 角色集合
    private Set<Role> roleList;
    
    public User(String userNum, String userPwd,Integer userStatus, Date createTime, Date updateTime) {
        this.userNum = userNum;
        this.userPwd = userPwd;
        this.userStatus = userStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
