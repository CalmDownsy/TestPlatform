package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用例集合对象 test_case_box
 * 
 * @author ruoyi
 * @date 2020-02-25
 */
public class TestCaseBox extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用例集名称 */
    @Excel(name = "用例集名称")
    private String boxName;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 包含的用例 */
    @Excel(name = "包含的用例")
    private String caseBox;

    /** 用例集状态 （1：正常；2：禁用） */
    @Excel(name = "用例集状态", readConverterExp = "1=：正常；2：禁用")
    private String status;

    /** 当前用例集的运行环境，不入库，只在每次执行时从前台接收数据 */
    private String runEnv;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBoxName(String boxName) 
    {
        this.boxName = boxName;
    }

    public String getBoxName() 
    {
        return boxName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setCaseBox(String caseBox) 
    {
        this.caseBox = caseBox;
    }

    public String getCaseBox() 
    {
        return caseBox;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public String getRunEnv() {
        return runEnv;
    }

    public void setRunEnv(String runEnv) {
        this.runEnv = runEnv;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("boxName", getBoxName())
            .append("description", getDescription())
            .append("caseBox", getCaseBox())
            .append("status", getStatus())
            .append("runEnv",getRunEnv())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
