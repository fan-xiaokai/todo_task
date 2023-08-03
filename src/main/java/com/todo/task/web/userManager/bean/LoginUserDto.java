package com.todo.task.web.userManager.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/30/15:32
 * @Description: 登录信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {

    @ApiModelProperty("账号")
    @NotBlank
    private String account;

    @ApiModelProperty("密码")
    @NotBlank
    private String password;
}
