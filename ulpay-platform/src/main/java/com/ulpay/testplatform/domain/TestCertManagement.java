package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 证书管理对象 test_cert_management
 * 
 * @author ruoyi
 * @date 2020-01-17
 */
public class TestCertManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 证书文件名称 */
    @Excel(name = "证书文件名称")
    private String certFilename;

    /** 证书密码 */
    private String certPass;

    /** 证书文件存储地址 */
    private String certFilepath;

    /** 证书类型 */
    @Excel(name = "证书类型")
    private String certType;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setCertFilename(String certFilename) 
    {
        this.certFilename = certFilename;
    }

    public String getCertFilename() 
    {
        return certFilename;
    }
    public void setCertPass(String certPass) 
    {
        this.certPass = certPass;
    }

    public String getCertPass() 
    {
        return certPass;
    }
    public void setCertFilepath(String certFilepath) 
    {
        this.certFilepath = certFilepath;
    }

    public String getCertFilepath() 
    {
        return certFilepath;
    }
    public void setCertType(String certType) 
    {
        this.certType = certType;
    }

    public String getCertType() 
    {
        return certType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("certFilename", getCertFilename())
            .append("certPass", getCertPass())
            .append("certFilepath", getCertFilepath())
            .append("certType", getCertType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
