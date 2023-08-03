package com.todo.task.result;

import lombok.Data;

/**
 * @author 樊小凯
 */
@Data
public class ResultData<T> {

    /**
     * 结果状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 接口请求时间
     */
    private long timestamp;

    public ResultData() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData resultData = new ResultData();
        resultData.setCode(ReturnCode.RC200.getCode());
        resultData.setMessage(ReturnCode.RC200.getMsg());
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> successWithMessageNoData() {
        ResultData resultData = new ResultData();
        resultData.setCode(ReturnCode.RC200.getCode());
        resultData.setMessage(ReturnCode.RC200.getMsg());
        return resultData;
    }

    public static <T> ResultData<T> successWithMessage(String message) {
        ResultData resultData = new ResultData();
        resultData.setCode(ReturnCode.RC200.getCode());
        resultData.setMessage(message);
        return resultData;
    }

    public static <T> ResultData<T> fail(String message) {
        ResultData resultData = new ResultData();
        resultData.setCode(ReturnCode.RC400.getCode());
        resultData.setMessage(message);
        return resultData;
    }
}
