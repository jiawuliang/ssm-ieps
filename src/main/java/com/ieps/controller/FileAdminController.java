package com.ieps.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ieps.common.ServerResponse;
import com.ieps.dto.CkeditorUploadFileDto;
import com.ieps.pojo.FileHub;
import com.ieps.pojo.User;
import com.ieps.service.FileAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.ieps.util.PreviewFileUtil.SubmitPost;

/**
 * Created by ljw
 */
@Controller
public class FileAdminController {
    
    @Autowired
    private FileAdminService fileAdminService;
    
    /**
     * 通过userNum获取全部的文件列表信息
     * @param page
     * @param fileHub
     * @param limit
     * @param session
     * @param userNumAdmin
     * @param roleId
     * @return
     */
    @RequestMapping("/getFileListByUserNum.do")
    @ResponseBody
    public ServerResponse getFileListByUserNum(@RequestParam(value = "page", defaultValue = "1") int page, FileHub fileHub,
                                               @RequestParam(value = "limit", defaultValue = "5") int limit, HttpSession session,
                                               @RequestParam("userNumAdmin") String userNumAdmin, @RequestParam("roleId") Integer roleId) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        return fileAdminService.getFileListByUserNum(page, limit, userNumAdmin, roleId, fileHub);
    }
    
    /**
     * 根据文件id删除文件
     * @param userNum
     * @param session
     * @param request
     * @param id
     * @param userNumAdmin
     * @param roleId
     * @param fileName
     * @return
     */
    @RequestMapping("/removeFileById.do")
    @ResponseBody
    public ServerResponse removeFileById(@RequestParam("userNum") String userNum, HttpSession session, HttpServletRequest request,
                                         @RequestParam("id") Integer id, @RequestParam("userNumAdmin") String userNumAdmin,
                                         @RequestParam("roleId") Integer roleId, @RequestParam("fileName") String fileName) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNum)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
    
        String filePath = request.getServletContext().getRealPath("/hub/");
        
        return fileAdminService.removeFileById(filePath, fileName, userNum, id, roleId);
    }
    
    /**
     * 批量删除文件
     * @param userNumAdmin
     * @param session
     * @param request
     * @param userNums
     * @param ids
     * @param roleId
     * @param fileNames
     * @return
     */
    @RequestMapping("/batchRemoveFile.do")
    @ResponseBody
    public ServerResponse batchRemoveFile(@RequestParam("userNum") String userNumAdmin, HttpSession session, HttpServletRequest request,
                                          @RequestParam("userNums") String[] userNums, @RequestParam("ids") Integer[] ids,
                                          @RequestParam("roleId") Integer roleId, @RequestParam("fileNames") String[] fileNames) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
    
        String filePath = request.getServletContext().getRealPath("/hub/");
        
        return fileAdminService.batchRemoveFile(filePath, fileNames, userNums, ids, roleId);
    }
    
    
    /**
     * 批量删除文件
     * @param files
     * @param userNum
     * @param itemNum
     * @param session
     * @param request
     * @param fileKind
     * @return
     */
    @RequestMapping(value = "/batchUploadFile.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse batchUploadFile(@RequestParam("files") MultipartFile[] files, String userNum, String itemNum, HttpSession session,
                                          HttpServletRequest request, int fileKind) {
    // public ServerResponse batchUploadFile(@RequestParam("files") MultipartFile[] files, @RequestParam(value = "userNum") String userNum,
    //                                       @RequestParam(value = "itemNum", defaultValue = "-1") String itemNum, HttpSession session, HttpServletRequest request,
    //                                       @RequestParam(value = "fileKind", defaultValue = "2") Integer fileKind) {
        // public ServerResponse batchUploadFile(@RequestParam("files") MultipartFile[] files, @RequestParam(value = "userNum") String userNum,
        //                                       @RequestParam(value = "itemNum") String itemNum, HttpSession session, HttpServletRequest request,
        //                                       @RequestParam(value = "fileKind") int fileKind) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        
        String filePath = request.getServletContext().getRealPath("/hub/");
        
        return fileAdminService.batchUploadFile(files, userNum, filePath, fileKind, itemNum);
    }
    
    /**
     * CKeditor批量上传文件
     * @param files
     * @param userNum
     * @param itemNum
     * @param session
     * @param request
     * @param fileKind
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/batchCkeditorUploadFile.do", method = RequestMethod.POST)
    @ResponseBody
    public CkeditorUploadFileDto batchCkeditorUploadFile(@RequestParam("upload") MultipartFile[] files, String userNum, String itemNum, HttpSession session,
                                          HttpServletRequest request, Integer fileKind) throws Exception  {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        
        // http://127.0.0.1:8080/batchCkeditorUploadFile.do
        // String filePath = request.getServletContext().getContextPath();
        // String filePath = "../" + request.getRequestURL();
        
        // 绝对路经
        String filePath = request.getSession().getServletContext().getRealPath("/hub/");
    
        CkeditorUploadFileDto ckeditorUploadFileDto = new CkeditorUploadFileDto();
        
        ServerResponse response = fileAdminService.batchUploadFile(files, userNum, filePath, fileKind, itemNum);

        if(response.getStatus() == 0) {
            filePath = request.getRequestURL().toString();
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            
            ckeditorUploadFileDto.setUploaded(1).setFileName(files[0].getOriginalFilename()).setUrl(filePath + "/hub/" +  response.getData());
        }

        return ckeditorUploadFileDto;
    }
    
    /**
     * 根据文件名下载文件
     * @param fileName
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadFile.do", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response) {
        // request.getServletContext()得到的是ServletContext对象，
        // getRealPath(“/”) 获取实际路径,“/”指代项目根目录,所以代码返回的是项目在容器中的实际发布运行的根路径。
        
        try {
            
            String path = request.getServletContext().getRealPath("/hub/") + fileName;
            
            System.out.println(path);
            // 获取输入流
            InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
            // 转码，免得文件名中文乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            
            // 设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            int length = 0;
            while ((length = bis.read()) != -1) {
                out.write(length);
                out.flush();
            }
            out.close();
        } catch (IOException e) {
            System.out.println("你关闭了已连接的对象流！");
            System.out.println("FileAdminController：" + e.getMessage());
        }
    }
    
    /**
     * 根据文件名预览文件
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/previewFile.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse previewFile(String fileName, HttpServletRequest request, HttpServletResponse response) {
    
        String path = request.getServletContext().getRealPath("/hub/") + fileName;
    
        System.out.println(path);
        
        //文件上传转换,获取返回数据
        String convertByFile = SubmitPost("http://dcs.yozosoft.com:80/upload", path, "1");
        // String convertByFile = SubmitPost("http://dcs.yozosoft.com:80/upload", "C:/Users/ljw/Desktop/PDF.js", "1");
        JSONObject obj = JSONObject.parseObject(convertByFile);
        if ("0".equals(obj.getString("result"))) {// 转换成功
            String urlData = obj.getString("data");
            urlData = urlData.replace("[\"", "");//去掉[
            urlData = urlData.replace("\"]", "");//去掉]
    
    
            //最后urlData是文件的浏览地址
            System.out.println(urlData);//打印网络文件预览地址
            
            return ServerResponse.createBySuccess("预览文件正在打开，请稍等！", urlData);
            
            
        
            // mining of.docx
            // http://dcs.yozosoft.com:8000/2019/05/27/MTkwNTI3OTMxMDIxMzUw.html
        
        
            // PDF.js
            // http://dcs.yozosoft.com:8000/2019/05/27/MTkwNTI3OTczNzEwMDA.html
        
        }
        else {// 转换失败
            System.out.println("转换失败");
            
            return ServerResponse.createByErrorMessage("文档过大，打开失败");
        }
    }
    
    
    // // 不能以ajax下载
    // @RequestMapping(value = "/downloadFile.do", method = RequestMethod.GET)
    // @ResponseBody
    // public ResponseEntity<byte[]> downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
    //     HttpHeaders headers = new HttpHeaders();
    //
    //     // getRealPath(“/”) 获取实际路径,“/”指代项目根目录,所以代码返回的是项目在容器中的实际发布运行的根路径。
    //     String filePath = request.getServletContext().getRealPath("/hub/") + fileName;
    //     File file = new File(filePath);
    //
    //     headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    //     headers.setContentDispositionFormData("attachment", fileName);
    //
    //     return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
    //             headers, HttpStatus.CREATED);
    // }
    
    /**
     * 根据userNum修改文件类型
     * @param fileHub
     * @param userNumAdmin
     * @param session
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/modifyFileKindWithUserNum.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse modifyFileKindWithUserNum(FileHub fileHub, @RequestParam("userNumAdmin") String userNumAdmin,
                                                    HttpSession session, @RequestParam("roleId") int roleId) {
        
        User user = (User) session.getAttribute("activeUser");
        
        // if (!user.getUserNum().equals(userNumAdmin)) {
        //     return ServerResponse.createByErrorMessage("安全检查不通过，用户已过时或不存在！");
        // }
        
        // return fileAdminService.batchRemoveFile(userNums, ids, roleId);
        return fileAdminService.modifyFileKindWithUserNum(roleId, fileHub);
    }
    
    /**
     * 管理员根据fileKind获取文件列表
     * @param pageNum
     * @param pageSize
     * @param session
     * @param fileName
     * @param updateTime
     * @return
     */
    @RequestMapping(value = "/getFileListByAdminWithKind.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getFileListByAdminWithKind(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                     @RequestParam(value = "pageSize",defaultValue = "8") int pageSize,
                                                     HttpSession session, String fileName, String updateTime) {
        return fileAdminService.getFileListByAdmin(pageNum, pageSize, fileName, updateTime);
    }
    
    /**
     * 根据项目编号itemNum下载文件
     * @param itemNum
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/downloadFileWithItemNum.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse downloadFileWithItemNum(@RequestParam("itemNum") String itemNum, HttpServletRequest request,
                                                  HttpServletResponse response) {
        
        System.out.println(itemNum);
        
        if (fileAdminService.getFileWithItemNum(itemNum).getStatus() != 0) {
            return ServerResponse.createByErrorMessage("对不起，你还没有上传项目附件呢!请关闭窗口再重试！");
        }
        
        FileHub fileHub = (FileHub) fileAdminService.getFileWithItemNum(itemNum).getData();
        
        String fileName = fileHub.getFileName();
        
        // request.getServletContext()得到的是ServletContext对象，
        // getRealPath(“/”) 获取实际路径,“/”指代项目根目录,所以代码返回的是项目在容器中的实际发布运行的根路径。
        String path = request.getServletContext().getRealPath("/hub/") + fileName;
        
        System.out.println(path);
        try {
            // 获取输入流
            InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
            // 转码，免得文件名中文乱码
            fileName = URLEncoder.encode(fileHub.getFileName(), "UTF-8");
            
            // 设置文件下载头
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            int length = 0;
            while ((length = bis.read()) != -1) {
                out.write(length);
                out.flush();
            }
            out.close();
        } catch (IOException e) {
            System.out.println("你关闭了已连接的对象流！");
            return null;
        }
        
        return ServerResponse.createBySuccess();
    }
    
    /**
     * 一键下载文件
     * @param userNum
     * @param roleId
     * @param request
     * @param fileNames
     * @param response
     * @return
     */
    @RequestMapping(value = "/onekeyDownloadFile.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse onekeyDownloadFile(@RequestParam("userNum") String userNum, @RequestParam("roleId") int roleId,
                                             HttpServletRequest request, String[] fileNames, HttpServletResponse response) {
        
        try {
            //     String fileName = "";
            //
            //     for (int i = 0; i < fileNames.length; i++) {
            //         fileName = fileNames[i];
            //         // request.getServletContext()得到的是ServletContext对象，
            //         // getRealPath(“/”) 获取实际路径,“/”指代项目根目录,所以代码返回的是项目在容器中的实际发布运行的根路径。
            //         String path = request.getServletContext().getRealPath("/hub/") + fileName;
            //         // 获取输入流
            //         InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
            //         // 转码，免得文件名中文乱码
            //         fileName = URLEncoder.encode(fileName, "UTF-8");
            //
            //         // 设置文件下载头
            //         response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //         //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
            //         response.setContentType("multipart/form-data");
            //         BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            //         int length = 0;
            //         while ((length = bis.read()) != -1) {
            //             out.write(length);
            //             out.flush();
            //         }
            //         out.close();
            //     }
            //
            // }
            
            // response.setContentType("text/html; charset=UTF-8"); //设置编码字符
            // response.setContentType("application/octet-stream"); //设置内容类型为下载类型
            // response.setHeader("Content-disposition", "attachment;filename=" + fileName);//设置下载的文件名称
            
            OutputStream out = response.getOutputStream();   //创建页面返回方式为输出流，会自动弹出下载框
            
            String zipBasePath = request.getServletContext().getRealPath("/hub/");
            String zipName = System.currentTimeMillis() + ".zip";
            String zipFilePath = zipBasePath + File.separator + zipName;
            
            List<String> filePaths = Lists.newArrayList();
            
            for (int i = 0; i < fileNames.length; i++) {
                System.out.println(i + "     11111");
                filePaths.add(zipBasePath + File.separator + fileNames[i]);
            }
    
            System.out.println(filePaths.size());
            
            File zip = new File(zipFilePath);
            
            if (!zip.exists()) {
                zip.createNewFile();
            }
            //创建zip文件输出流
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
            this.zipFile(zipBasePath, zipName, zipFilePath, filePaths, zos);
            zos.close();
            response.setHeader("Content-disposition", "attachment;filename=" + zipName);//设置下载的压缩文件名称
            
            //将打包后的文件写到客户端，输出的方法同上，使用缓冲流输出
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFilePath));
            byte[] buff = new byte[bis.available()];
            bis.read(buff);
            bis.close();
            out.write(buff);//输出数据文件
            out.flush();//释放缓存
            out.close();//关闭输出流
        } catch (Exception e) {
            System.out.println("你关闭了已连接的对象流！");
        }
        
        return ServerResponse.createBySuccess("文件已打包下载,请到指定路径下查看!");
    }
    
    /**
     * 压缩文件
     *
     * @param zipBasePath 临时压缩文件基础路径
     * @param zipName     临时压缩文件名称
     * @param zipFilePath 临时压缩文件完整路径
     * @param filePaths   需要压缩的文件路径集合
     * @throws IOException
     */
    private String zipFile(String zipBasePath, String zipName, String zipFilePath, List<String> filePaths, ZipOutputStream zos) {
        
        try {
            
            //循环读取文件路径集合，获取每一个文件的路径
            for (String filePath : filePaths) {
                File inputFile = new File(filePath);  //根据文件路径创建文件
                if (inputFile.exists()) { //判断文件是否存在
                    if (inputFile.isFile()) {  //判断是否属于文件，还是文件夹
                        //创建输入流读取文件
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
                        
                        //将文件写入zip内，即将文件进行打包
                        zos.putNextEntry(new ZipEntry(inputFile.getName()));
                        
                        //写入文件的方法，同上
                        int size = 0;
                        byte[] buffer = new byte[1024];  //设置读取数据缓存大小
                        while ((size = bis.read(buffer)) > 0) {
                            zos.write(buffer, 0, size);
                        }
                        //关闭输入输出流
                        zos.closeEntry();
                        bis.close();
                        
                    } else {  //如果是文件夹，则使用穷举的方法获取文件，写入zip
                        try {
                            File[] files = inputFile.listFiles();
                            List<String> filePathsTem = new ArrayList<String>();
                            for (File fileTem : files) {
                                filePathsTem.add(fileTem.toString());
                            }
                            return zipFile(zipBasePath, zipName, zipFilePath, filePathsTem, zos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("FileAdminException e关闭数据流失败!");
        }
        return null;
    }
    
}
