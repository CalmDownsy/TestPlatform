package com.ulpay.testplatform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created By: zhangsy
 * Description: 工单实体类
 * 2019/7/29 22:08
 */
public class WorkOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    private Long workOrderId;
    /**
     * 工单编号
     */
    private String workOrderNum;
    /**
     * 工单名称
     */
    private String workOrderName;
    /**
     * 工单类型 PJ/DB/BG/CF
     */
    private String workOrderType;
    /**
     * 工单状态
     */
    private String status;
    /**
     * 开始时间（与截止构成工单周期）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",  timezone = "GMT+8")
    private Date startTime;
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

    /**
     * 如果是db工单，需要细分类型
     */
    private String dbType;

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workOrderId", getWorkOrderId())
                .append("workOrderNum", getWorkOrderNum())
                .append("workOrderName", getWorkOrderName())
                .append("workOrderType", getWorkOrderType())
                .append("status", getStatus())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("busiLine", getRemark())
                .append("dbType", getRemark())
                .toString();
    }
}
