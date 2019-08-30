package com.ieps.dto;

import com.ieps.pojo.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by ljw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class ItemAdminDto extends Item {
    
    // 项目级别：2：校级；3：省区级；4：国家级 默认： 1  无
    private int itemLevel;
    
    // 项目类型：1：创新训练；2：创业训练；3：创业实践   默认：1
    private int itemType;
    
    // 项目简介，少于200个字
    private String summary;
    
    // 校级经费
    private BigDecimal collegeFunds;
    
    // 财政经费
    private BigDecimal governFunds;
    
    // 附件名
    private String fileName;
    
    // 身份标识：负责人/成员/指导老师/院内评委/院内评委组长/校内评委/校内评委组长
    private int identity;
    
    // 院级得分
    private float academyScore;
    
    // 校级得分
    private float collegeScore;
    
    // 省区级得分
    private float governScore;
    
    // 查询的年份
    private String itemYear;
    
    // 评审级别： 1：院级评审；2：校级评审；3：省区级评审；4：国家级评审
    private int reviewLevel;
    
}
