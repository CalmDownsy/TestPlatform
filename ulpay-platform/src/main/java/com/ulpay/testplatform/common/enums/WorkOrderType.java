package com.ulpay.testplatform.common.enums;

/**
 * @author zhangsy
 * @date 2020/8/9 16:25
 * @description
 */
public enum WorkOrderType {

    PJ("1", "PJ"),
    DB("2", "DB"),
    BG("3", "BG"),
    CF("4", "CF");

    private String code;
    private String workOrderType;

    WorkOrderType(String code, String workOrderType) {
        this.code = code;
        this.workOrderType = workOrderType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public static String getWorkOrderType(String code) {
        for (WorkOrderType workOrderType : values()) {
            if (code.equals(workOrderType.getCode())) {
                return workOrderType.workOrderType;
            }
        }
        return null;
    }



}
