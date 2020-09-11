package com.ulpay.testplatform.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * @author zhangsy
 * @date 2020/8/8 16:02
 * @description
 */
public class WorkOrderReportData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String weekTime;

    private String busiLine;

    private String workOrderType;

    private Integer count;

    public String getWeekTime() {
        return weekTime;
    }

    public void setWeekTime(String weekTime) {
        this.weekTime = weekTime;
    }

    public String getBusiLine() {
        return busiLine;
    }

    public void setBusiLine(String busiLine) {
        this.busiLine = busiLine;
    }

    public String getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "WorkOrderReportData{" +
                "weekTime='" + weekTime + '\'' +
                ", busiLine='" + busiLine + '\'' +
                ", workOrderType='" + workOrderType + '\'' +
                ", count=" + count +
                '}';
    }
}
