package com.todo.task.utils;

import java.util.UUID;

/**
 * @author chenhu
 * @date 2018-11-02
 */
public class UUIDUtil {

    private UUIDUtil() {
    }

    /**
     * uuid
     * 型如 'adf968be-ed06-4b47-b1e7-9604c73d557b'
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * uuid
     * 忽略横线，型如 'ce40b611309c4081b41bf3d205c3f038'
     * 短横线（连字符）hyphen
     * 长横线        dash
     * 下划线        underline
     * @return
     */
    public static String uuidIngoreHyphen() {
        String uuid = uuid();
        return uuid.replaceAll("-","");
    }
}
