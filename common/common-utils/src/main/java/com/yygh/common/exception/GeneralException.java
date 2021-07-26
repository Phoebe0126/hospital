package com.yygh.common.exception;

import com.yygh.common.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 陈玉婷
 * @create: 2021-07-20 20:11
 **/
@Data
@ApiModel(value = "自定义全局通用异常类")
public class GeneralException extends RuntimeException{
    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /**
     * 通过状态码和异常消息创建异常对象
     * @param message
     * @param code
     */
    public GeneralException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public GeneralException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GeneralException{" +
                "code=" + code +
                '}';
    }
}
