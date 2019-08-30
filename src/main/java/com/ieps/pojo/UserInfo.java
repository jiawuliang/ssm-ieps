package com.ieps.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ljw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private Integer id;

    // 用户Id
    private String userNum;
    
    // 用户姓名
    private String userName;

    // 用户头像
    private String userImg;

    // 手机号码
    private String photoNum;

    // 邮箱
    private String email;

    // 职称：0：学生；1：助理研究员；2：讲师；3：高级实验师；4：副教授；5：教授
    private Integer title;

    // 性别
    private Integer sex;

    // 学院
    private String academy;

    // 年级
    private String grade;

    // 专业
    private String major;

    // 出生日期
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    private Date createTime;

    private Date updateTime;
    
}
