package com.ulpay.testplatform.mapper;

import com.ulpay.testplatform.domain.TestCase;
import java.util.List;

/**
 * 用例实体Mapper接口
 * 
 * @author zhangsy
 * @date 2020-01-02
 */
public interface TestCaseMapper 
{
    /**
     * 查询用例实体
     * 
     * @param caseId 用例实体ID
     * @return 用例实体
     */
    public TestCase selectTestCaseById(Long caseId);

    /**
     * 查询用例实体列表
     * 
     * @param testCase 用例实体
     * @return 用例实体集合
     */
    public List<TestCase> selectTestCaseList(TestCase testCase);

    /**
     * 新增用例实体
     * 
     * @param testCase 用例实体
     * @return 结果
     */
    public int insertTestCase(TestCase testCase);

    /**
     * 修改用例实体
     * 
     * @param testCase 用例实体
     * @return 结果
     */
    public int updateTestCase(TestCase testCase);

    /**
     * 删除用例实体
     * 
     * @param caseId 用例实体ID
     * @return 结果
     */
    public int deleteTestCaseById(Long caseId);

    /**
     * 批量删除用例实体
     * 
     * @param caseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestCaseByIds(String[] caseIds);
}
