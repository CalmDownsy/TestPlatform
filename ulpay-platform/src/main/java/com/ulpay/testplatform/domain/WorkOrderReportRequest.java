package com.ulpay.testplatform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zhangsy
 * @date 2020/8/8 20:38
 * @description 这个东西多此一举了，直接使用jsonObject就行
 */
public class WorkOrderReportRequest extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 工单类型 PJ/DB/BG/CF
     */
    private String workOrderType;

    /**
     * 开始时间（与截止构成工单周期）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",  timezone = "GMT+8")
    private Date beginTime;
    /**
     * 截止时间（与开始构成工单周期）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    /**
     * 业务线
     */
    private String busiLine;

    public String getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBusiLine() {
        return busiLine;
    }

    public void setBusiLine(String busiLine) {
        this.busiLine = busiLine;
    }

    @Override
    public String toString() {
        return "WorkOrderReportRequest{" +
                "workOrderType='" + workOrderType + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", busiLine='" + busiLine + '\'' +
                '}';
    }
}
