package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报文实体对象 test_message
 * 
 * @author zhangsy
 * @date 2019-12-09
 */
public class TestMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报文Id */
    private Long messageId;

    /** 报文名称 */
    @Excel(name = "报文名称")
    private String messageName;

    /** 报文格式：xml、json、url */
    @Excel(name = "报文格式：xml、json、url")
    private String messageType;

    private String messageRes;

    private String messageProcess;

    /** 所属接口 */
    @Excel(name = "所属接口")
    private Long interfaceId;

    /** 入参报文 */
    @Excel(name = "入参报文")
    private String parameterJson;

    /** 报文状态（1：正常；2：禁用） */
    @Excel(name = "报文状态", readConverterExp = "1=：正常；2：禁用")
    private String status;

    public void setMessageId(Long messageId) 
    {
        this.messageId = messageId;
    }

    public Long getMessageId() 
    {
        return messageId;
    }
    public void setMessageName(String messageName) 
    {
        this.messageName = messageName;
    }

    public String getMessageName() 
    {
        return messageName;
    }
    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }

    public void setMessageRes(String messageRes) {
        this.messageRes = messageRes;
    }

    public String getMessageRes(){
        return messageRes;
    }
    public void setInterfaceId(Long interfaceId) 
    {
        this.interfaceId = interfaceId;
    }

    public String getMessageProcess() {
        return messageProcess;
    }

    public void setMessageProcess(String messageProcess) {
        this.messageProcess = messageProcess;
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
            .append("messageId", getMessageId())
            .append("messageName", getMessageName())
            .append("messageType", getMessageType())
            .append("messageRes", getMessageRes())
            .append("messageProcess", getMessageProcess())
            .append("interfaceId", getInterfaceId())
            .append("parameterJson", getParameterJson())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
