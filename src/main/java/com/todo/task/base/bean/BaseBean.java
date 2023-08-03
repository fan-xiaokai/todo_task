package com.todo.task.base.bean;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 * @author chenhu
 * @date 2021-09-02
 */
public class BaseBean implements Serializable {
    /**
     * 对象转换
     *
     * @param srcObj       元对象
     * @param requiredType 转换类型
     */
    public <T> T fromBean(Object srcObj, Class<T> requiredType) {
        try {
            T targetObj = requiredType.newInstance();
            BeanUtil.copyProperties(srcObj, targetObj, CopyOptions.create().setIgnoreNullValue(true));
            return targetObj;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换成目标类型的对象
     *
     * @param requiredType 目标类型
     */
    public <T> T toBean(Class<T> requiredType) {
        return this.fromBean(this, requiredType);
    }


    /**
     * 转换为json字符串
     *
     * @return
     */
    public final String toJsonString() {
        return new Gson().toJson(this);
    }
}