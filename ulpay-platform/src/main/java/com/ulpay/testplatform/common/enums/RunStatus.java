package com.ulpay.testplatform.common.enums;

/**
 * @author zhangsy
 */
public enum RunStatus {

    /**
     * 成功
     */
    SUCCESS("1", "成功"),

    /**
     * 失败
     */
    FAIL("2", "失败"),

    /**
     * 终止
     */
    STOP("3", "终止"),


    /**
     * 部分成功
     */
    DEVIDED("4","部分成功");

    private String code;
    private String msg;

    RunStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
