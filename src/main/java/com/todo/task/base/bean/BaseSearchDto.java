package com.todo.task.base.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author xtf
 * @Date 03/06/2020 17:06
 * @Description 通用查询条件
 * @Version 1.0
 */
@Data
public class BaseSearchDto implements Serializable {
    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "数量")
    private Integer limit = 10;

    @ApiModelProperty(value = "模糊查询")
    private String s;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
