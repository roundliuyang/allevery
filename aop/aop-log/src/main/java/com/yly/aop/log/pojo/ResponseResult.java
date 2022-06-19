package com.yly.aop.log.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.yly.aop.log.emums.ResponseCodeEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一响应结果类
 */
@JsonView(View.BasicView.class)
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回信息描述
     */
    private String msg;

    /**
     * 返回详细信息
     */
    private T data;

    public static <T> ResponseResult<T> success(String message,T data){
        ResponseResult result = new ResponseResult();
        result.code(ResponseCodeEnum.SUCCESS.getCode()).msg(message).data(data);
        return result;
    }

    public static <T> ResponseResult<T> success(T data){
        return success(ResponseCodeEnum.SUCCESS.getDesc(),data);
    }

    public static <T> ResponseResult<T> success(){
        return success(null);
    }

    public static <T> ResponseResult<T> error(int code,String msg,T data){
        ResponseResult result = new ResponseResult();
        result.code(code).msg(msg).data(data);
        return result;
    }

    public static <T> ResponseResult<T> error(int code,String msg){
        return error(code,msg,null);
    }

    public static <T> ResponseResult<T> error(ResponseCodeEnum responseCodeEnum){
        return error(responseCodeEnum.getCode(), responseCodeEnum.getDesc());
    }

    public static <T> ResponseResult<T> error(ResponseCodeEnum responseCodeEnum, T data){
        ResponseResult result = new ResponseResult();
        result.code(responseCodeEnum.getCode()).msg(responseCodeEnum.getDesc()).data(data);
        return result;
    }

    public int getCode() {
        return code;
    }

    public ResponseResult code(int code) {
        this.code = code;
        return this;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseResult msg(String msg) {
        this.msg = msg;
        return this;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public ResponseResult data(T data) {
        this.data = data;
        return this;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseResult<?> result = (ResponseResult<?>) o;
        return code == result.code &&
                Objects.equals(msg, result.msg) &&
                Objects.equals(data, result.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, msg, data);
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
