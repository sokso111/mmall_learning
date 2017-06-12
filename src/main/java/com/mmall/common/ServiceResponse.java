package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by mayilong on 2017/6/6.
 */
//json序列化为null不返回
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServiceResponse<T> implements Serializable{

    private int status;
    private String msg;
    private T data;

    private ServiceResponse(int status){
        this.status = status;
    }

    private ServiceResponse(int status,T data){
        this.status = status;
        this.data = data;
    }

    private  ServiceResponse(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServiceResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
    //序列化不返回
    @JsonIgnore
    public boolean isSuccess(){
       return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }

    public T getData(){
        return data;
    }

    public  String getMsg(){
        return msg;
    }

    public static <T> ServiceResponse<T> createBySuccess(){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServiceResponse<T> createBySuccessMessage(String msg){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServiceResponse<T> createBySuccess(T data){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServiceResponse<T> createBySuccess(String msg,T data){
        return new ServiceResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServiceResponse<T> createByError(){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServiceResponse<T> createByErrorMessage(String msg){
        return new ServiceResponse<T>(ResponseCode.ERROR.getCode(),msg);
    }

    public static <T> ServiceResponse<T> createByErrorCodeMessage(int errorCode,String msg){
        return new ServiceResponse<T>(errorCode,msg);
    }
}
