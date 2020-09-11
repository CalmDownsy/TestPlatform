package com.ulpay.testplatform.common.enums;

import com.ulpay.testplatform.handler.message.BaseMessageParseHandler;

/**
 * @author zhangsy
 * @date 2020/8/7 15:18
 * @description
 */
public enum BusinessLine {

    RDC("rdc", "研发中心"),

    BUSIMG("busimg", "业务平台组"),

    RISK("risk", "风控组"),

    ARC("arc", "架构组"),

    CAPITAL("capital", "资金组"),

    BASEPAY("basepay", "基础支付组"),

    APPRODUCT("approduct", "产品应用组");

    private String code;
    private String busiLine;

    BusinessLine(String code, String busiLine) {
        this.code = code;
        this.busiLine = busiLine;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBusiLine() {
        return busiLine;
    }

    public void setBusiLine(String busiLine) {
        this.busiLine = busiLine;
    }

    public static String getBusiLine(String code) {
        for (BusinessLine businessLine : values()) {
            if (code.equals(businessLine.getCode())) {
                return businessLine.getBusiLine();
            }
        }
        return null;
    }
}
