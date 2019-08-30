package com.ieps.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ljw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRole implements Serializable {

    private Integer id;

    // 用户 user_num
    private String userNum;

    // 角色Id
    private Integer roleId;

    private Date createTime;

    private Date updateTime;
    
}
