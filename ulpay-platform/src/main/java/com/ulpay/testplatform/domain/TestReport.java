package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试报告对象 test_report
 * 
 * @author zhangsy
 * @date 2020-05-14
 */
public class TestReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报告Id */
    private Long reportId;

    /** 测试集Id */
    @Excel(name = "测试集Id")
    private Long caseboxId;

    /** 测试集名称 */
    @Excel(name = "测试集名称")
    private String caseboxName;

    /** 用例数量 */
    @Excel(name = "用例数量")
    private Integer caseNum;

    /** 成功数量 */
    @Excel(name = "成功数量")
    private Integer successNum;

    /** 失败数量 */
    @Excel(name = "失败数量")
    private Integer failNum;

    /** 跳过数量 */
    @Excel(name = "跳过数量")
    private Integer skipNum;

    /** 异常数量 */
    @Excel(name = "异常数量")
    private Integer excepNum;

    /** Y完成，N未完成 */
    @Excel(name = "Y完成，N未完成")
    private String finishFlag;

    /** 执行详情，展示报告使用 */
    private String resultDetails;

    private Set<CaseRunResult> caseRunResults = new HashSet<CaseRunResult>();

    public void setReportId(Long reportId) 
    {
        this.reportId = reportId;
    }

    public Long getReportId() 
    {
        return reportId;
    }
    public void setCaseboxId(Long caseboxId) 
    {
        this.caseboxId = caseboxId;
    }

    public Long getCaseboxId() 
    {
        return caseboxId;
    }
    public void setCaseboxName(String caseboxName) 
    {
        this.caseboxName = caseboxName;
    }

    public String getCaseboxName() 
    {
        return caseboxName;
    }
    public void setCaseNum(Integer caseNum) 
    {
        this.caseNum = caseNum;
    }

    public Integer getCaseNum() 
    {
        return caseNum;
    }
    public void setSuccessNum(Integer successNum) 
    {
        this.successNum = successNum;
    }

    public Integer getSuccessNum() 
    {
        return successNum;
    }
    public void setFailNum(Integer failNum) 
    {
        this.failNum = failNum;
    }

    public Integer getFailNum() 
    {
        return failNum;
    }
    public void setSkipNum(Integer skipNum) 
    {
        this.skipNum = skipNum;
    }

    public Integer getSkipNum() 
    {
        return skipNum;
    }
    public void setExcepNum(Integer excepNum) 
    {
        this.excepNum = excepNum;
    }

    public Integer getExcepNum() 
    {
        return excepNum;
    }
    public void setFinishFlag(String finishFlag) 
    {
        this.finishFlag = finishFlag;
    }

    public String getFinishFlag() 
    {
        return finishFlag;
    }
    public void setResultDetails(String resultDetails) 
    {
        this.resultDetails = resultDetails;
    }

    public String getResultDetails() 
    {
        return resultDetails;
    }

    public Set<CaseRunResult> getCaseRunResults() {
        return caseRunResults;
    }

    public void setCaseRunResults(Set<CaseRunResult> caseRunResults) {
        this.caseRunResults = caseRunResults;
    }

    // 统计当前报告下用例数量
    public void caseCount() {
        this.caseNum = this.getCaseRunResults().size();
        this.successNum = 0;
        this.failNum = 0;
        this.skipNum = 0;
        for (CaseRunResult testResult : this.getCaseRunResults()) {
            switch (testResult.getRunStatus()){
                case "1":
                    this.successNum ++;
                    break;
                case "2":
                    this.failNum ++ ;
                    break;
                case "3":
                    this.skipNum ++;
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("reportId", getReportId())
            .append("caseboxId", getCaseboxId())
            .append("caseboxName", getCaseboxName())
            .append("caseNum", getCaseNum())
            .append("successNum", getSuccessNum())
            .append("failNum", getFailNum())
            .append("skipNum", getSkipNum())
            .append("excepNum", getExcepNum())
            .append("finishFlag", getFinishFlag())
            .append("resultDetails", getResultDetails())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
