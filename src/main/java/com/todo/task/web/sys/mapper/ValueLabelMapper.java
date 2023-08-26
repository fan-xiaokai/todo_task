package com.todo.task.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @ClassName ValueLabelMapper
 * @Description value-label
 * @Author sfm
 * @Date 2021/1/22 10:59
 * @ModifyDate 2021/1/22 10:59
 * @Version 0.0.1
 */
@Mapper
@Repository
public interface ValueLabelMapper {

    /**
     * selectIdByValidateField
     *
     * @param tableName     表名
     * @param fieldName     验证字段名称
     * @param validateField 验证的字段的数据
     * @return java.lang.String
     * @Description 查询
     * @Author sfm
     * @Date 2021/1/22 11:14
     * @ModifyDate 2021/1/22 11:14
     **/
    String selectIdByValidateField(@Param("tableName") String tableName, @Param("fieldName") String fieldName, @Param("validateField") String validateField);

    /**
     * selectIdByValidateFields
     *
     * @param tableName     表名
     * @param validateFieldAndValues 验证的字段的对应数据
     * @return java.lang.String
     * @Description 查询
     * @Author sfm
     * @Date 2021/1/22 11:14
     * @ModifyDate 2021/1/22 11:14
     **/
    String selectIdByValidateFields(@Param("tableName") String tableName, @Param("validateFieldAndValues") Map<String, String> validateFieldAndValues);
}