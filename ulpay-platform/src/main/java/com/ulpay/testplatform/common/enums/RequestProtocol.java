package com.ulpay.testplatform.common.enums;

/**
 * @author zhangsy
 */
public enum RequestProtocol {

    /**
     * http
     */
    HTTP("1", "http"),
    /**
     * http
     */
    HTTPS("2", "https"),
    /**
     * dubbo
     */
    DUBBO("3", "dubbo");

    private String code;
    private String protocol;

    RequestProtocol(String code, String protocol) {
        this.code = code;
        this.protocol = protocol;
    }

    public String getCode() {
        return code;
    }

    public String getProtocol() {
        return protocol;
    }
}
