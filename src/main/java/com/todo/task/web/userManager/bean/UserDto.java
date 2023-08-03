package com.todo.task.web.userManager.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/29/14:31
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @ApiModelProperty("用户名")
    @NotBlank
    private String userName;

    @ApiModelProperty("账号")
    @NotBlank
    private String account;

    @ApiModelProperty("密码")
    @NotBlank
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("头像地址")
    private String avatarAddress;

}
