package com.ulpay.testplatform.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.JobResultRelationsMapper;
import com.ulpay.testplatform.domain.JobResultRelations;
import com.ulpay.testplatform.service.IJobResultRelationsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 任务结果Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-03-20
 */
@Service
public class JobResultRelationsServiceImpl implements IJobResultRelationsService 
{
    @Autowired
    private JobResultRelationsMapper jobResultRelationsMapper;

    /**
     * 查询任务结果
     * 
     * @param relId 任务结果ID
     * @return 任务结果
     */
    @Override
    public JobResultRelations selectJobResultRelationsById(Long relId)
    {
        return jobResultRelationsMapper.selectJobResultRelationsById(relId);
    }

    /**
     * 查询任务结果列表
     * 
     * @param jobResultRelations 任务结果
     * @return 任务结果
     */
    @Override
    public List<JobResultRelations> selectJobResultRelationsList(JobResultRelations jobResultRelations)
    {
        return jobResultRelationsMapper.selectJobResultRelationsList(jobResultRelations);
    }

    /**
     * 新增任务结果
     * 
     * @param jobResultRelations 任务结果
     * @return 结果
     */
    @Override
    public int insertJobResultRelations(JobResultRelations jobResultRelations)
    {
        return jobResultRelationsMapper.insertJobResultRelations(jobResultRelations);
    }

    /**
     * 修改任务结果
     * 
     * @param jobResultRelations 任务结果
     * @return 结果
     */
    @Override
    public int updateJobResultRelations(JobResultRelations jobResultRelations)
    {
        return jobResultRelationsMapper.updateJobResultRelations(jobResultRelations);
    }

    /**
     * 删除任务结果对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJobResultRelationsByIds(String ids)
    {
        return jobResultRelationsMapper.deleteJobResultRelationsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除任务结果信息
     * 
     * @param relId 任务结果ID
     * @return 结果
     */
    public int deleteJobResultRelationsById(Long relId)
    {
        return jobResultRelationsMapper.deleteJobResultRelationsById(relId);
    }
}
