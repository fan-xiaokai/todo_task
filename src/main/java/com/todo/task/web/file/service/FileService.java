package com.todo.task.web.file.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName UploadService
 * @Description 文件管理 服务类
 * @Author sfm
 * @Date 2021/6/2 10:30
 * @ModifyDate 2021/6/2 10:30
 * @Version 0.0.1
 */
public interface FileService {

    /**
     * upload
     *
     * @param file 文件
     * @return java.lang.String
     * @Description 上传
     * @Author sfm
     * @Date 2021/6/2 10:35
     * @ModifyDate 2021/6/2 10:35
     **/
    String upload(MultipartFile file);

    /**
     * download
     *
     * @param filePath 文件路径
     * @param request  request
     * @param response response
     * @return void
     * @Description 下载
     * @Author sfm
     * @Date 2021/6/2 10:37
     * @ModifyDate 2021/6/2 10:37
     **/
    void download(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException;
}