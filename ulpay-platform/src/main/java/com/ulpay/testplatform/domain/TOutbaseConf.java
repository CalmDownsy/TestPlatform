package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 外部数据源维护对象 test_outbase_config
 * 
 * @author ruoyi
 * @date 2020-03-27
 */
public class TOutbaseConf extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long baseId;

    /** 外部数据库标签，用于快速筛选 */
    @Excel(name = "外部数据库标签，用于快速筛选")
    private String baseTag;

    /** 数据库类型： 1,oracle 2,mysql */
    @Excel(name = "数据库类型： 1,oracle 2,mysql")
    private Integer baseType;

    /** 数据库IP */
    @Excel(name = "数据库IP")
    private String baseUrl;

    /** 端口号 */
    private String basePort;

    /** 数据库服务名称 */
    @Excel(name = "数据库服务名称")
    private String baseServicename;

    /** 用户名 */
    private String baseUsername;

    /** 密码 */
    private String basePassword;

    public void setBaseId(Long baseId) 
    {
        this.baseId = baseId;
    }

    public Long getBaseId() 
    {
        return baseId;
    }
    public void setBaseTag(String baseTag) 
    {
        this.baseTag = baseTag;
    }

    public String getBaseTag() 
    {
        return baseTag;
    }
    public void setBaseType(Integer baseType) 
    {
        this.baseType = baseType;
    }

    public Integer getBaseType() 
    {
        return baseType;
    }
    public void setBaseUrl(String baseUrl) 
    {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() 
    {
        return baseUrl;
    }
    public void setBasePort(String basePort) 
    {
        this.basePort = basePort;
    }

    public String getBasePort() 
    {
        return basePort;
    }
    public void setBaseServicename(String baseServicename) 
    {
        this.baseServicename = baseServicename;
    }

    public String getBaseServicename() 
    {
        return baseServicename;
    }
    public void setBaseUsername(String baseUsername) 
    {
        this.baseUsername = baseUsername;
    }

    public String getBaseUsername() 
    {
        return baseUsername;
    }
    public void setBasePassword(String basePassword) 
    {
        this.basePassword = basePassword;
    }

    public String getBasePassword() 
    {
        return basePassword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("baseId", getBaseId())
            .append("baseTag", getBaseTag())
            .append("baseType", getBaseType())
            .append("baseUrl", getBaseUrl())
            .append("basePort", getBasePort())
            .append("baseServicename", getBaseServicename())
            .append("baseUsername", getBaseUsername())
            .append("basePassword", getBasePassword())
            .append("remark", getRemark())
            .toString();
    }
}
