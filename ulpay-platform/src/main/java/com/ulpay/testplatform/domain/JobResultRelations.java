package com.ulpay.testplatform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 任务结果对象 job_result_relations
 * 
 * @author ruoyi
 * @date 2020-03-20
 */
public class JobResultRelations extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long relId;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private Integer jobType;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long jobId;

    /** 收集到的各任务名称，用于展示及快速搜索 */
    @Excel(name = "任务名称")
    private String jobName;

    /** 任务发起人 */
    @Excel(name = "任务发起人")
    private String testUser;

    /** 任务执行状态 */
    private Long jobStatus;

    /** 任务执行时间 */
    @Excel(name = "任务执行时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jobRunTime;

    /** 仅用于页面查询条件 */
    private Date beginJobRunTime;
    private Date endJobRunTime;

    /** 任务执行结果ID集合 */
    private String jobResults;

    public void setRelId(Long relId) 
    {
        this.relId = relId;
    }

    public Long getRelId() 
    {
        return relId;
    }
    public void setJobType(Integer jobType) 
    {
        this.jobType = jobType;
    }

    public Integer getJobType() 
    {
        return jobType;
    }
    public void setJobId(Long jobId) 
    {
        this.jobId = jobId;
    }

    public Long getJobId() 
    {
        return jobId;
    }
    public void setTestUser(String testUser) 
    {
        this.testUser = testUser;
    }

    public String getTestUser() 
    {
        return testUser;
    }
    public void setJobStatus(Long jobStatus)
    {
        this.jobStatus = jobStatus;
    }

    public Long getJobStatus()
    {
        return jobStatus;
    }
    public void setJobRunTime(Date jobRunTime) 
    {
        this.jobRunTime = jobRunTime;
    }

    public Date getJobRunTime() 
    {
        return jobRunTime;
    }
    public void setJobResults(String jobResults) 
    {
        this.jobResults = jobResults;
    }

    public String getJobResults() 
    {
        return jobResults;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setBeginJobRunTime(Date beginJobRunTime) {
        this.beginJobRunTime = beginJobRunTime;
    }

    public void setEndJobRunTime(Date endJobRunTime) {
        this.endJobRunTime = endJobRunTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("relId", getRelId())
            .append("jobType", getJobType())
            .append("jobId", getJobId())
            .append("jobName", getJobName())
            .append("testUser", getTestUser())
            .append("jobStatus", getJobStatus())
            .append("jobRunTime", getJobRunTime())
            .append("jobResults", getJobResults())
            .append("remark", getRemark())
            .toString();
    }
}
