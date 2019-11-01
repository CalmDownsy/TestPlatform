package com.ruoyi.test.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 测试计划执行结果对象 test_result
 * 
 * @author zhangsy
 * @date 2019-10-31
 */
public class TestResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 结果Id */
    private Long resultId;

    /** 测试计划Id */
    @Excel(name = "测试计划Id")
    private Long testPlanId;

    /** 测试标题 */
    @Excel(name = "测试标题")
    private String testTitle;

    /** 测试结果 */
    @Excel(name = "测试结果")
    private String testResult;

    /** 用例个数 */
    @Excel(name = "用例个数")
    private Integer caseCount;

    /** 成功个数 */
    @Excel(name = "成功个数")
    private Integer successCount;

    /** 失败个数 */
    @Excel(name = "失败个数")
    private Integer failCount;

    /** 跳过个数 */
    @Excel(name = "跳过个数")
    private Integer skipCount;

    /** 耗时 */
    @Excel(name = "耗时")
    private Long duration;

    /** 报告路径 */
    @Excel(name = "报告路径")
    private String reportPath;

    public void setResultId(Long resultId) 
    {
        this.resultId = resultId;
    }

    public Long getResultId() 
    {
        return resultId;
    }
    public void setTestPlanId(Long testPlanId) 
    {
        this.testPlanId = testPlanId;
    }

    public Long getTestPlanId() 
    {
        return testPlanId;
    }
    public void setTestTitle(String testTitle) 
    {
        this.testTitle = testTitle;
    }

    public String getTestTitle() 
    {
        return testTitle;
    }
    public void setTestResult(String testResult) 
    {
        this.testResult = testResult;
    }

    public String getTestResult() 
    {
        return testResult;
    }
    public void setCaseCount(Integer caseCount) 
    {
        this.caseCount = caseCount;
    }

    public Integer getCaseCount() 
    {
        return caseCount;
    }
    public void setSuccessCount(Integer successCount) 
    {
        this.successCount = successCount;
    }

    public Integer getSuccessCount() 
    {
        return successCount;
    }
    public void setFailCount(Integer failCount) 
    {
        this.failCount = failCount;
    }

    public Integer getFailCount() 
    {
        return failCount;
    }
    public void setSkipCount(Integer skipCount) 
    {
        this.skipCount = skipCount;
    }

    public Integer getSkipCount() 
    {
        return skipCount;
    }
    public void setDuration(Long duration) 
    {
        this.duration = duration;
    }

    public Long getDuration() 
    {
        return duration;
    }
    public void setReportPath(String reportPath) 
    {
        this.reportPath = reportPath;
    }

    public String getReportPath() 
    {
        return reportPath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("resultId", getResultId())
            .append("testPlanId", getTestPlanId())
            .append("testTitle", getTestTitle())
            .append("testResult", getTestResult())
            .append("caseCount", getCaseCount())
            .append("successCount", getSuccessCount())
            .append("failCount", getFailCount())
            .append("skipCount", getSkipCount())
            .append("duration", getDuration())
            .append("reportPath", getReportPath())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
