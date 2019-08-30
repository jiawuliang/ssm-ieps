package com.ieps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by ljw
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CkeditorUploadFileDto implements Serializable {
    
    // 状态码
    private Integer uploaded;
    
    // 文件名
    private String fileName;
    
    // 上传服务器的地址URL
    private String url;
    
    
}
