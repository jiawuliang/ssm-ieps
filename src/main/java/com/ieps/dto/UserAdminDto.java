package com.ieps.dto;

import com.ieps.pojo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by ljw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class UserAdminDto extends UserInfo {
    
    // 登录状态
    private Integer userStatus;
    
    // 用户项目级别
    // 身份标识：负责人/成员/指导老师/院内评委/院内评委组长/校内评委/校内评委组长
    private int identity;
    
    // 男性 male
    private int male;
    
    // 女性 female
    private int female;
    
    // 学院学生总数
    private int stuNum;
    
    // 批量注册
    // 账号
    private String[] userNums;
    
    // 姓名
    private String[] userNames;
}
