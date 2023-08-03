package com.todo.task.web.file.controller;

import com.todo.task.result.ResultData;
import com.todo.task.utils.ImgUtil;
import com.todo.task.utils.MinioClientUtil;
import com.todo.task.utils.UUIDUtil;
import com.todo.task.web.file.bean.FileUploadBase64Request;
import com.todo.task.web.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    private MinioClientUtil minioClientUtil;


    /**
     * 上传
     *
     * @param file
     * @return
     */
    @ApiOperation("上传")
    @PostMapping("/upload")
    public ResultData fdfsUpload(@RequestParam("file") MultipartFile file) {
        return ResultData.success(fileService.upload(file));
    }

    @ApiOperation(value = "上传文件(图片base64)")
    @RequestMapping(value = "/upload/base64", method = RequestMethod.POST)
    public ResultData<String> uploadBase64(
            @RequestBody FileUploadBase64Request fileUploadBase64Request) throws IOException {
        String base64 = fileUploadBase64Request.getBase64();
        String fileName = fileUploadBase64Request.getFileName();
        fileName = StringUtils.isNotBlank(fileName) ? fileName : UUIDUtil.uuidIngoreHyphen() + ".jpg";

        // base64转图片流
        byte[] data = ImgUtil.getStringImage(base64.split(",")[1]);
        MultipartFile file = new MockMultipartFile( fileName, fileName, ContentType.IMAGE_PNG.toString(), data);

        return ResultData.success(fileService.upload(file));
    }


        /**
         * 下载
         *
         * @param filePath
         * @param request
         * @param response
         * @throws IOException
         */
    @ApiOperation("下载")
    @GetMapping("/download")
    public void download(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fileService.download(filePath, request, response);
    }


    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    @ApiOperation("删除")
    @GetMapping("/deleteFile")
    public String delFile(String filePath) {
        String objectKey = StringUtils.substringAfterLast(filePath, File.separator);
        try {
            minioClientUtil.deleteFile(objectKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


}