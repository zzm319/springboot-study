package com.study.springbootdemo.common;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    // 私有构造，外部用静态方法创建
    private Result() {
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // ========== 成功 ==========
    /** 成功，带数据 */
    /**
     * static：静态方法，用 Result.success(...) 调用
     * <T>：方法自己声明泛型
     * 方法自己声明泛型
     * Result<T>：返回带泛型的 Result
     * success(T data)：传入 data，类型自动推断
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "success", data);
    }

    /** 成功，无数据（如删除） */
    public static <T> Result<T> success() {
        return new Result<T>(200, "success", null);
    }

    /** 成功，自定义 message */
    // public static <T> Result<T> success(T data, String message) {
    //     return new Result<T>(200, message, data);
    // }

    // ========== 失败 ==========
    public static <T> Result<T> error(int code, String message) {
        return new Result<T>(code, message, null);
    }

}
