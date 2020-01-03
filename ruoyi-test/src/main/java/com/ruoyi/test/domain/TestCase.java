package com.ruoyi.test.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 用例实体对象 test_case
 * 
 * @author zhangsy
 * @date 2020-01-02
 */
public class TestCase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用例Id */
    private Long caseId;

    /** 用例名称 */
    @Excel(name = "用例名称")
    private String caseName;

    /** 所属接口 */
    @Excel(name = "所属接口")
    private Long interfaceId;

    /** 用例实参 */
    private String parameterJson;

    /** 用例类型，auto,manual */
    @Excel(name = "用例类型，auto,manual")
    private String caseType;

    /** 用例状态（1：正常；2：禁用） */
    @Excel(name = "用例状态", readConverterExp = "1=：正常；2：禁用")
    private String status;

    /** 所属环境 */
    @Excel(name = "所属环境")
    private String env;

    /** 首次执行时间 */
    private Date firstRunTime;

    /** 最近执行时间 */
    @Excel(name = "最近执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastRunTime;

    /** 运行次数 */
    private Long runCount;

    /** 校验规则 */
    private String checkRuleFlag;

    /** 校验表达式 */
    private String checkExpression;

    /** 前置动作 */
    private Long preActionId;

    public void setCaseId(Long caseId) 
    {
        this.caseId = caseId;
    }

    public Long getCaseId() 
    {
        return caseId;
    }
    public void setCaseName(String caseName) 
    {
        this.caseName = caseName;
    }

    public String getCaseName() 
    {
        return caseName;
    }
    public void setInterfaceId(Long interfaceId) 
    {
        this.interfaceId = interfaceId;
    }

    public Long getInterfaceId() 
    {
        return interfaceId;
    }
    public void setParameterJson(String parameterJson) 
    {
        this.parameterJson = parameterJson;
    }

    public String getParameterJson() 
    {
        return parameterJson;
    }
    public void setCaseType(String caseType) 
    {
        this.caseType = caseType;
    }

    public String getCaseType() 
    {
        return caseType;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setEnv(String env) 
    {
        this.env = env;
    }

    public String getEnv() 
    {
        return env;
    }
    public void setFirstRunTime(Date firstRunTime) 
    {
        this.firstRunTime = firstRunTime;
    }

    public Date getFirstRunTime() 
    {
        return firstRunTime;
    }
    public void setLastRunTime(Date lastRunTime) 
    {
        this.lastRunTime = lastRunTime;
    }

    public Date getLastRunTime() 
    {
        return lastRunTime;
    }
    public void setRunCount(Long runCount) 
    {
        this.runCount = runCount;
    }

    public Long getRunCount() 
    {
        return runCount;
    }
    public void setCheckRuleFlag(String checkRuleFlag) 
    {
        this.checkRuleFlag = checkRuleFlag;
    }

    public String getCheckRuleFlag() 
    {
        return checkRuleFlag;
    }
    public void setCheckExpression(String checkExpression) 
    {
        this.checkExpression = checkExpression;
    }

    public String getCheckExpression() 
    {
        return checkExpression;
    }
    public void setPreActionId(Long preActionId) 
    {
        this.preActionId = preActionId;
    }

    public Long getPreActionId() 
    {
        return preActionId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("caseId", getCaseId())
            .append("caseName", getCaseName())
            .append("interfaceId", getInterfaceId())
            .append("parameterJson", getParameterJson())
            .append("caseType", getCaseType())
            .append("status", getStatus())
            .append("env", getEnv())
            .append("firstRunTime", getFirstRunTime())
            .append("lastRunTime", getLastRunTime())
            .append("runCount", getRunCount())
            .append("checkRuleFlag", getCheckRuleFlag())
            .append("checkExpression", getCheckExpression())
            .append("preActionId", getPreActionId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
