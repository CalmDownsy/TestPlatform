package com.ulpay.testplatform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.CaseRunResultMapper;
import com.ulpay.testplatform.domain.CaseRunResult;
import com.ulpay.testplatform.service.ICaseRunResultService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用例执行结果Service业务层处理
 * 
 * @author zhangsy
 * @date 2020-01-13
 */
@Service
public class CaseRunResultServiceImpl implements ICaseRunResultService 
{
    @Autowired
    private CaseRunResultMapper caseRunResultMapper;

    /**
     * 查询用例执行结果
     * 
     * @param resultId 用例执行结果ID
     * @return 用例执行结果
     */
    @Override
    public CaseRunResult selectCaseRunResultById(Long resultId)
    {
        return caseRunResultMapper.selectCaseRunResultById(resultId);
    }

    /**
     * 查询用例执行结果列表
     * 
     * @param caseRunResult 用例执行结果
     * @return 用例执行结果
     */
    @Override
    public List<CaseRunResult> selectCaseRunResultList(CaseRunResult caseRunResult)
    {
        return caseRunResultMapper.selectCaseRunResultList(caseRunResult);
    }

    /**
     * 新增用例执行结果
     * 
     * @param caseRunResult 用例执行结果
     * @return 结果
     */
    @Override
    public int insertCaseRunResult(CaseRunResult caseRunResult)
    {
        caseRunResult.setCreateTime(DateUtils.getNowDate());
        return caseRunResultMapper.insertCaseRunResult(caseRunResult);
    }

    /**
     * 修改用例执行结果
     * 
     * @param caseRunResult 用例执行结果
     * @return 结果
     */
    @Override
    public int updateCaseRunResult(CaseRunResult caseRunResult)
    {
        caseRunResult.setUpdateTime(DateUtils.getNowDate());
        return caseRunResultMapper.updateCaseRunResult(caseRunResult);
    }

    /**
     * 删除用例执行结果对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCaseRunResultByIds(String ids)
    {
        return caseRunResultMapper.deleteCaseRunResultByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用例执行结果信息
     * 
     * @param resultId 用例执行结果ID
     * @return 结果
     */
    public int deleteCaseRunResultById(Long resultId)
    {
        return caseRunResultMapper.deleteCaseRunResultById(resultId);
    }

    @Override
    public List<CaseRunResult> selectResultByReportId(Long reportId) {
        return caseRunResultMapper.selectResultByReportId(reportId);
    }
}
