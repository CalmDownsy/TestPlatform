package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Mock配置对象 test_interface_mock
 * 
 * @author zhangsy
 * @date 2020-06-19
 */
public class TestInterfaceMock extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** MockId */
    @Excel(name = "MockId")
    private Long mockId;

    /** Mock名称 */
    @Excel(name = "Mock名称")
    private String mockName;

    /** 所属接口 */
    @Excel(name = "所属接口")
    private Long interfaceId;

    /** 请求路径 */
    @Excel(name = "请求路径")
    private String requestUrl;

    /** 规则集 */
    @Excel(name = "规则集")
    private String rulesetJson;

    /** Mock状态（1：正常；2：禁用） */
    @Excel(name = "Mock状态", readConverterExp = "1=：正常；2：禁用")
    private String status;

    public void setMockId(Long mockId) 
    {
        this.mockId = mockId;
    }

    public Long getMockId() 
    {
        return mockId;
    }
    public void setMockName(String mockName) 
    {
        this.mockName = mockName;
    }

    public String getMockName() 
    {
        return mockName;
    }
    public void setInterfaceId(Long interfaceId) 
    {
        this.interfaceId = interfaceId;
    }

    public Long getInterfaceId() 
    {
        return interfaceId;
    }
    public void setRequestUrl(String requestUrl) 
    {
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() 
    {
        return requestUrl;
    }
    public void setRulesetJson(String rulesetJson) 
    {
        this.rulesetJson = rulesetJson;
    }

    public String getRulesetJson() 
    {
        return rulesetJson;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("mockId", getMockId())
            .append("mockName", getMockName())
            .append("interfaceId", getInterfaceId())
            .append("requestUrl", getRequestUrl())
            .append("rulesetJson", getRulesetJson())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
