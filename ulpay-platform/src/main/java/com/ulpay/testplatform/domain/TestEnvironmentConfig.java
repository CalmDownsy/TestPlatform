package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 环境配置对象 test_environment_config
 * 
 * @author ruoyi
 * @date 2020-01-06
 */
public class TestEnvironmentConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 环境标识 */
    private String envFlag;

    /** 名称 */
    @Excel(name = "名称")
    private String envName;

    /** 服务器地址 */
    @Excel(name = "服务器地址")
    private String host;

    /** 端口 */
    @Excel(name = "端口")
    private String port;

    /** 分组 */
    @Excel(name = "分组")
    private String groups;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setEnvName(String envName) 
    {
        this.envName = envName;
    }

    public String getEnvName() 
    {
        return envName;
    }
    public void setHost(String host) 
    {
        this.host = host;
    }

    public String getHost() 
    {
        return host;
    }
    public void setPort(String port) 
    {
        this.port = port;
    }

    public String getPort() 
    {
        return port;
    }
    public void setGroups(String groups) 
    {
        this.groups = groups;
    }

    public String getGroups() 
    {
        return groups;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public String getEnvFlag() {
        return envFlag;
    }

    public void setEnvFlag(String envFlag) {
        this.envFlag = envFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("envFlag",getEnvFlag())
            .append("envName", getEnvName())
            .append("host", getHost())
            .append("port", getPort())
            .append("groups", getGroups())
            .append("description", getDescription())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
