package com.todo.task.web.file.service.impl;


import com.todo.task.utils.MinioClientUtil;
import com.todo.task.web.file.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName UploadServiceImpl
 * @Description 文件管理 服务实现类
 * @Author sfm
 * @Date 2021/6/2 10:32
 * @ModifyDate 2021/6/2 10:32
 * @Version 0.0.1
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private MinioClientUtil minioClientUtil;

    @Override
    public String upload(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            return minioClientUtil.uploadInputStream(System.currentTimeMillis() + "_" + originalFilename, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void download(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String objectKey = StringUtils.substringAfterLast(filePath, File.separator);

        try {
            InputStream input = minioClientUtil.download(objectKey);

            //根据文件名获取 MIME 类型
            System.out.println("fileName :" + objectKey);

            String contentType = request.getServletContext().getMimeType(objectKey);
            String contentDisposition = "attachment;filename=" + objectKey;

            // 设置头
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Disposition", contentDisposition);

            // 获取绑定了客户端的流
            ServletOutputStream output = response.getOutputStream();

            // 把输入流中的数据写入到输出流中
            IOUtils.copy(input, output);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}