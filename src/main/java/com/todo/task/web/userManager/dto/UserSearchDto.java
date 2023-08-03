package com.todo.task.web.userManager.dto;

import com.todo.task.base.bean.BaseSearchDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author 樊小凯
 * @Date 2023/07/27/12:42
 * @Description: 用户查询条件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDto extends BaseSearchDto {

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("电话号码")
    private String phoneNumber;

}
