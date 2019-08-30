package com.ieps.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ljw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Review implements Serializable {
    
    private Integer id;
    
    // 评委职工号
    private String userNum;
    
    // 项目编号
    private String itemNum;
    
    // 分数
    private BigDecimal reviewScore;
    
    // 评审意见
    private String reviewOption;
    
    // 评审类型（0：立项申请；1：中期检查；2：结题申请）
    private Integer reviewType;
    
    // 评审级别（0：指导老师评审；1：学院评审；2：学校评审）
    private Integer reviewLevel;
    
    // 评审时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reviewTime;
    
    private Date createTime;
    
    private Date updateTime;
    
}
