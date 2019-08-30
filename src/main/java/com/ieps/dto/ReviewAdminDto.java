package com.ieps.dto;

import com.ieps.pojo.Review;
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
public class ReviewAdminDto extends Review {
    
    // 评审人姓名
    private String userName;
    
    // 评审类型
    private String reviewAdminType;
    
    // 评审级别
    private String reviewAdminLevel;
    
    // 评审文件名
    private String fileName;
    
    // 上传的文件类型
    private Integer fileKind;
    
    
    
}
