package com.ulpay.testplatform.mapper;

import com.ulpay.testplatform.domain.TestResult;

import java.util.List;

/**
 * 测试计划执行结果Mapper接口
 * 
 * @author zhangsy
 * @date 2019-10-31
 */
public interface TestResultMapper 
{
    /**
     * 查询测试计划执行结果
     * 
     * @param resultId 测试计划执行结果ID
     * @return 测试计划执行结果
     */
    public TestResult selectTestResultById(Long resultId);

    /**
     * 查询测试计划执行结果列表
     * 
     * @param testResult 测试计划执行结果
     * @return 测试计划执行结果集合
     */
    public List<TestResult> selectTestResultList(TestResult testResult);

    /**
     * 新增测试计划执行结果
     * 
     * @param testResult 测试计划执行结果
     * @return 结果
     */
    public int insertTestResult(TestResult testResult);

    /**
     * 修改测试计划执行结果
     * 
     * @param testResult 测试计划执行结果
     * @return 结果
     */
    public int updateTestResult(TestResult testResult);

    /**
     * 删除测试计划执行结果
     * 
     * @param resultId 测试计划执行结果ID
     * @return 结果
     */
    public int deleteTestResultById(Long resultId);

    /**
     * 批量删除测试计划执行结果
     * 
     * @param resultIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestResultByIds(String[] resultIds);
}
