package com.todo.task.result;

/**
 * @author 樊小凯
 */
public enum ReturnCode {
    RC200(200, "操作成功"),
    RC401(401, "无操作权限"),
    RC400(400, "操作失败");

    /**
     * 自定义状态码
     */
    private final Integer code;

    /**
     * 自定义描述
     */
    private final String msg;

    ReturnCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
