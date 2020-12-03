package com.netty.service.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: QiDing
 * @date : 2020/8/26 0026 10:09
 * description: TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyException extends RuntimeException {
    private Integer code;
    private String msg;

    public MyException(Integer code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public MyException(Integer code){
        super(MyExceptionEnum.getMsgByCode(code));
        this.code = code;
        this.msg = MyExceptionEnum.getMsgByCode(code);
    }
    public MyException(String msg){
        super(msg);
        this.msg = msg;
    }
}