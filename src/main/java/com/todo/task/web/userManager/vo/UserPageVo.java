package com.todo.task.web.userManager.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/27/12:34
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageVo {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("邮箱")
    private String phoneNumber;

    @ApiModelProperty("头像地址")
    private String avatarAddress;
}
