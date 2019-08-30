package com.ieps.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by ljw
 */
public class PreviewFileUtil {
    
    /**
     * 向指定 URL 上传文件POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param filepath 文件路径
     * @param type 转换类型
     * @return 所代表远程资源的响应结果, json数据
     */
    public static String SubmitPost(String url, String filepath, String type) {
        String requestJson = "";
        HttpClient httpclient =  HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url);
            FileBody file = new FileBody(new File(filepath));
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null,
                    Charset.forName("UTF-8"));
            reqEntity.addPart("file", file); // file为请求后台的File upload;属性
            reqEntity.addPart("convertType", new StringBody(type, Charset.forName("UTF-8")));
            httppost.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                requestJson = EntityUtils.toString(resEntity);
                EntityUtils.consume(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.getConnectionManager().shutdown();
            } catch (Exception ignore) {
            
            }
        }
        return requestJson;
    }
    
    
    public static void main(String[] args) {
        //文件上传转换,获取返回数据
        String convertByFile = SubmitPost("http://dcs.yozosoft.com:80/upload", "G:/eclipse开发教程.doc", "1");
        // String convertByFile = SubmitPost("http://dcs.yozosoft.com:80/upload", "C:/Users/ljw/Desktop/PDF.js", "1");
        JSONObject obj = JSONObject.parseObject(convertByFile);
        if ("0".equals(obj.getString("result"))) {// 转换成功
            String urlData = obj.getString("data");
            urlData = urlData.replace("[\"", "");//去掉[
            urlData = urlData.replace("\"]", "");//去掉]
            
            //最后urlData是文件的浏览地址
            System.out.println(urlData);//打印网络文件预览地址
    
            // mining of.docx
            // http://dcs.yozosoft.com:8000/2019/05/27/MTkwNTI3OTMxMDIxMzUw.html
    
    
            // PDF.js
            // http://dcs.yozosoft.com:8000/2019/05/27/MTkwNTI3OTczNzEwMDA.html
            
        }
        else {// 转换失败
            System.out.println("转换失败");
        }
    }
    
}
