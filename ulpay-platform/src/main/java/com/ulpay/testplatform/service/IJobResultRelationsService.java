package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.JobResultRelations;
import java.util.List;

/**
 * 任务结果Service接口
 * 
 * @author ruoyi
 * @date 2020-03-20
 */
public interface IJobResultRelationsService 
{
    /**
     * 查询任务结果
     * 
     * @param relId 任务结果ID
     * @return 任务结果
     */
    public JobResultRelations selectJobResultRelationsById(Long relId);

    /**
     * 查询任务结果列表
     * 
     * @param jobResultRelations 任务结果
     * @return 任务结果集合
     */
    public List<JobResultRelations> selectJobResultRelationsList(JobResultRelations jobResultRelations);

    /**
     * 新增任务结果
     * 
     * @param jobResultRelations 任务结果
     * @return 结果
     */
    public int insertJobResultRelations(JobResultRelations jobResultRelations);

    /**
     * 修改任务结果
     * 
     * @param jobResultRelations 任务结果
     * @return 结果
     */
    public int updateJobResultRelations(JobResultRelations jobResultRelations);

    /**
     * 批量删除任务结果
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteJobResultRelationsByIds(String ids);

    /**
     * 删除任务结果信息
     * 
     * @param relId 任务结果ID
     * @return 结果
     */
    public int deleteJobResultRelationsById(Long relId);
}
