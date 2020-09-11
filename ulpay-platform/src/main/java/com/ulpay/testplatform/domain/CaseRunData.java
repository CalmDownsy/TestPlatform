package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class CaseRunData implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用例Id */
    private Long caseId;

    /** 执行环境 */
    private String envConf;

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getEnvConf() {
        return envConf;
    }

    public void setEnvConf(String envConf) {
        this.envConf = envConf;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("caseId", getCaseId())
                .append("envConf", getEnvConf())
                .toString();
    }
}
