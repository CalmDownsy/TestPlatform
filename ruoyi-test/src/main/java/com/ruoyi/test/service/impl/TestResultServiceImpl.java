package com.ruoyi.test.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.test.mapper.TestResultMapper;
import com.ruoyi.test.domain.TestResult;
import com.ruoyi.test.service.ITestResultService;
import com.ruoyi.common.core.text.Convert;

/**
 * 测试计划执行结果Service业务层处理
 * 
 * @author zhangsy
 * @date 2019-10-31
 */
@Service
public class TestResultServiceImpl implements ITestResultService 
{
    @Autowired
    private TestResultMapper testResultMapper;

    /**
     * 查询测试计划执行结果
     * 
     * @param resultId 测试计划执行结果ID
     * @return 测试计划执行结果
     */
    @Override
    public TestResult selectTestResultById(Long resultId)
    {
        return testResultMapper.selectTestResultById(resultId);
    }

    /**
     * 查询测试计划执行结果列表
     * 
     * @param testResult 测试计划执行结果
     * @return 测试计划执行结果
     */
    @Override
    public List<TestResult> selectTestResultList(TestResult testResult)
    {
        return testResultMapper.selectTestResultList(testResult);
    }

    /**
     * 新增测试计划执行结果
     * 
     * @param testResult 测试计划执行结果
     * @return 结果
     */
    @Override
    public int insertTestResult(TestResult testResult)
    {
        testResult.setCreateTime(DateUtils.getNowDate());
        return testResultMapper.insertTestResult(testResult);
    }

    /**
     * 修改测试计划执行结果
     * 
     * @param testResult 测试计划执行结果
     * @return 结果
     */
    @Override
    public int updateTestResult(TestResult testResult)
    {
        testResult.setUpdateTime(DateUtils.getNowDate());
        return testResultMapper.updateTestResult(testResult);
    }

    /**
     * 删除测试计划执行结果对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestResultByIds(String ids)
    {
        return testResultMapper.deleteTestResultByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除测试计划执行结果信息
     * 
     * @param resultId 测试计划执行结果ID
     * @return 结果
     */
    public int deleteTestResultById(Long resultId)
    {
        return testResultMapper.deleteTestResultById(resultId);
    }
}
