package com.ruoyi.test.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 测试用例对象 testcase
 * 
 * @author zhangsy
 * @date 2019-10-24
 */
public class Testcase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** null */
    private Long testCaseId;

    /** 用例父标题 */
    @Excel(name = "用例父标题")
    private String parentTitle;

    /** 用例标题 */
    @Excel(name = "用例标题")
    private String testCaseTitle;

    /** 用例描述 */
    @Excel(name = "用例描述")
    private String description;

    /** 用例类型，auto,manual */
    @Excel(name = "用例类型，auto,manual")
    private String testCaseType;

    public void setTestCaseId(Long testCaseId) 
    {
        this.testCaseId = testCaseId;
    }

    public Long getTestCaseId() 
    {
        return testCaseId;
    }
    public void setParentTitle(String parentTitle) 
    {
        this.parentTitle = parentTitle;
    }

    public String getParentTitle() 
    {
        return parentTitle;
    }
    public void setTestCaseTitle(String testCaseTitle) 
    {
        this.testCaseTitle = testCaseTitle;
    }

    public String getTestCaseTitle() 
    {
        return testCaseTitle;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setTestCaseType(String testCaseType) 
    {
        this.testCaseType = testCaseType;
    }

    public String getTestCaseType() 
    {
        return testCaseType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("testCaseId", getTestCaseId())
            .append("parentTitle", getParentTitle())
            .append("testCaseTitle", getTestCaseTitle())
            .append("description", getDescription())
            .append("testCaseType", getTestCaseType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
