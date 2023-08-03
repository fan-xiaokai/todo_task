package com.todo.task.web.file.bean;

import com.todo.task.base.bean.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * @author fanxiaokai
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("base64文件上传请求")
public class FileUploadBase64Request extends BaseBean {

    @ApiModelProperty(value = "文件名称", example = "123456.jpg")
    private String fileName;

    @ApiModelProperty(value = "文件流", required = true, example = "123str")
    @NotNull(message = "图片base64缺失")
    private String base64;
}
