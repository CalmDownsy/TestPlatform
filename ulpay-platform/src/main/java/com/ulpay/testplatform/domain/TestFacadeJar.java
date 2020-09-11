package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Facade包对象 test_facade_jar
 * 
 * @author zhangsy
 * @date 2020-06-16
 */
public class TestFacadeJar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** jar文件名称 */
    @Excel(name = "jar文件名称")
    private String jarName;

    /** 所属应用 */
    @Excel(name = "所属应用")
    private String appName;

    /** jar文件存储地址 */
    private String jarFilepath;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setJarName(String jarName) 
    {
        this.jarName = jarName;
    }

    public String getJarName() 
    {
        return jarName;
    }
    public void setAppName(String appName) 
    {
        this.appName = appName;
    }

    public String getAppName() 
    {
        return appName;
    }
    public void setJarFilepath(String jarFilepath) 
    {
        this.jarFilepath = jarFilepath;
    }

    public String getJarFilepath() 
    {
        return jarFilepath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jarName", getJarName())
            .append("appName", getAppName())
            .append("jarFilepath", getJarFilepath())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
