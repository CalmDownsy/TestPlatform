package com.ruoyi.test.service;

import com.ruoyi.test.domain.TestResult;
import java.util.List;

/**
 * 测试计划执行结果Service接口
 * 
 * @author zhangsy
 * @date 2019-10-31
 */
public interface ITestResultService 
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
     * 批量删除测试计划执行结果
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestResultByIds(String ids);

    /**
     * 删除测试计划执行结果信息
     * 
     * @param resultId 测试计划执行结果ID
     * @return 结果
     */
    public int deleteTestResultById(Long resultId);
}
