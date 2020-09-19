package com.hx_ai.nlp.simple.query.tuling.enums;

import cn.hutool.core.util.ObjectUtil;

/**
 * @Description: 错误码集合
 * @Author: lidisong
 * @Date: [2020-09-17/05:09:12]
 * @return: null
 **/
public enum ErrorCodeEnum {
    
    ERROR_CODE_ENUM_5000(5000, "无解析结果"),
    ERROR_CODE_ENUM_6000(6000, "暂不支持该功能"),
    ERROR_CODE_ENUM_4000(4000, "请求参数格式错误"),
    ERROR_CODE_ENUM_4001(4001, "加密方式错误"),
    ERROR_CODE_ENUM_4002(4002, "无功能权限"),
    ERROR_CODE_ENUM_4003(4003, "该apikey没有可用请求次数"),
    ERROR_CODE_ENUM_4005(4005, "无功能权限"),
    ERROR_CODE_ENUM_4007(4007, "apikey不合法"),
    ERROR_CODE_ENUM_4100(4100, "userid获取失败"),
    ERROR_CODE_ENUM_4200(4200, "上传格式错误"),
    ERROR_CODE_ENUM_4300(4300, "批量操作超过限制"),
    ERROR_CODE_ENUM_4400(4400, "没有上传合法userid"),
    ERROR_CODE_ENUM_4500(4500, "userid申请个数超过限制"),
    ERROR_CODE_ENUM_4600(4600, "输入内容为空"),
    ERROR_CODE_ENUM_4602(4602, "输入文本内容超长(上限150)"),
    ERROR_CODE_ENUM_7002(7002, "上传信息失败"),
    ERROR_CODE_ENUM_8008(4001, "服务器错误"),

    ;

    private int errorCode;
    private String errorMsg;

    public static ErrorCodeEnum getFromErrorCodeEnum(int errorCode){
        ErrorCodeEnum[] errorCodeEnums = ErrorCodeEnum.values();
        for (ErrorCodeEnum errorCodeEnum : errorCodeEnums) {
            if (ObjectUtil.isNotEmpty(errorCodeEnum) && errorCodeEnum.getErrorCode() == errorCode) {
                return errorCodeEnum;
            }
        }
        return null;
    }

    ErrorCodeEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
