package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用例执行结果对象 case_run_result
 *
 * @author zhangsy
 * @date 2020-01-08
 */
public class CaseRunResult extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 结果Id
     */
    private Long resultId;

    /**
     * 报告Id
     */
    private Long reportId;

    /**
     * 用例Id
     */
    @Excel(name = "用例Id")
    private Long caseId;

    /**
     * 用例名称
     */
    private String caseName;

    /**
     * 请求类型（协议http(s)时，为post、get等）
     */
    @Excel(name = "请求类型", readConverterExp = "协议http(s)时，为post、get等")
    private String requestType;

    /**
     * 请求地址
     */
    @Excel(name = "请求地址")
    private String requestUrl;

    /**
     * 报文格式：xml、json、url
     */
    @Excel(name = "报文格式：xml、json、url")
    private String messageType;

    /**
     * 请求报文
     */
    @Excel(name = "请求报文")
    private String requestMsg;

    /**
     * 响应报文
     */
    @Excel(name = "响应报文")
    private String responseMsg;

    /**
     * 耗时(ms)
     */
    @Excel(name = "耗时(ms)")
    private Long consumingTime;

    /**
     * null
     */
    @Excel(name = "null")
    private String runStatus;

    /**
     * 结果描述
     */
    private String resultMsg;

    /**
     * 执行环境
     */
    @Excel(name = "执行环境")
    private String envConf;

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Long getResultId() {
        return resultId;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }

    public String getRequestMsg() {
        return requestMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setConsumingTime(Long consumingTime) {
        this.consumingTime = consumingTime;
    }

    public Long getConsumingTime() {
        return consumingTime;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public void setEnvConf(String envConf) {
        this.envConf = envConf;
    }

    public String getEnvConf() {
        return envConf;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("resultId", getResultId())
                .append("reportId", getReportId())
                .append("caseId", getCaseId())
                .append("caseName", getCaseName())
                .append("requestType", getRequestType())
                .append("requestUrl", getRequestUrl())
                .append("messageType", getMessageType())
                .append("requestMsg", getRequestMsg())
                .append("responseMsg", getResponseMsg())
                .append("consumingTime", getConsumingTime())
                .append("runStatus", getRunStatus())
                .append("resultMsg", getResultMsg())
                .append("envConf", getEnvConf())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}