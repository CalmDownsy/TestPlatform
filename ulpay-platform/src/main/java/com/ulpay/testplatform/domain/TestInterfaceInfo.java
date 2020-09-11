package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 接口信息实体对象 test_interface_info
 * 
 * @author zhangsy
 * @date 2019-12-06
 */
public class TestInterfaceInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 接口Id */
    private Long interfaceId;

    /** 接口英文名称，必须为英文 */
    @Excel(name = "接口英文名称，必须为英文")
    private String interfaceName;

    /** 接口中文名称 */
    @Excel(name = "接口中文名称")
    private String interfaceChName;

    /** 接口所属环境名称 */
    @Excel(name = "接口所属环境名称")
    private String envNameLink;

    /** 请求路径 */
    @Excel(name = "请求路径")
    private String requestUrl;

    /** 接口分类（收、付、退、鉴权等） */
    @Excel(name = "接口分类", readConverterExp = "收、付、退、鉴权等")
    private String interfaceType;

    /** 接口协议（http、https、dubbo） */
    @Excel(name = "接口协议", readConverterExp = "http、https、dubbo")
    private String interfaceProtocol;

    /** 请求类型（协议http(s)时，为post、get等） */
    @Excel(name = "请求类型", readConverterExp = "协议http(s)时，为post、get等")
    private String requestType;

    /** 接口状态（1：正常；2：禁用） */
    @Excel(name = "接口状态", readConverterExp = "1：正常；2：禁用")
    private String status;

    public void setInterfaceId(Long interfaceId) 
    {
        this.interfaceId = interfaceId;
    }

    public Long getInterfaceId() 
    {
        return interfaceId;
    }
    public void setInterfaceName(String interfaceName) 
    {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceName() 
    {
        return interfaceName;
    }
    public void setInterfaceChName(String interfaceChName) 
    {
        this.interfaceChName = interfaceChName;
    }

    public String getInterfaceChName() 
    {
        return interfaceChName;
    }
    public void setRequestUrl(String requestUrl) 
    {
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() 
    {
        return requestUrl;
    }
    public void setInterfaceType(String interfaceType) 
    {
        this.interfaceType = interfaceType;
    }

    public String getInterfaceType() 
    {
        return interfaceType;
    }
    public void setInterfaceProtocol(String interfaceProtocol) 
    {
        this.interfaceProtocol = interfaceProtocol;
    }

    public String getInterfaceProtocol() 
    {
        return interfaceProtocol;
    }
    public void setRequestType(String requestType) 
    {
        this.requestType = requestType;
    }

    public String getRequestType() 
    {
        return requestType;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public String getEnvNameLink() {
        return envNameLink;
    }

    public void setEnvNameLink(String envNameLink) {
        this.envNameLink = envNameLink;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("interfaceId", getInterfaceId())
            .append("interfaceName", getInterfaceName())
            .append("interfaceChName", getInterfaceChName())
            .append("envNameLink", getEnvNameLink())
            .append("requestUrl", getRequestUrl())
            .append("interfaceType", getInterfaceType())
            .append("interfaceProtocol", getInterfaceProtocol())
            .append("requestType", getRequestType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
