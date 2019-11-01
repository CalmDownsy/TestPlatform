package com.ruoyi.test.service;

import com.ruoyi.test.domain.Testcase;

import java.util.List;

/**
 * 测试用例Service接口
 * 
 * @author zhangsy
 * @date 2019-10-24
 */
public interface ITestcaseService 
{
    /**
     * 查询测试用例
     * 
     * @param testCaseId 测试用例ID
     * @return 测试用例
     */
    public Testcase selectTestcaseById(Long testCaseId);

    /**
     * 查询测试用例列表
     * 
     * @param testcase 测试用例
     * @return 测试用例集合
     */
    public List<Testcase> selectTestcaseList(Testcase testcase);

    /**
     * 新增测试用例
     * 
     * @param testcase 测试用例
     * @return 结果
     */
    public int insertTestcase(Testcase testcase);

    /**
     * 修改测试用例
     * 
     * @param testcase 测试用例
     * @return 结果
     */
    public int updateTestcase(Testcase testcase);

    /**
     * 批量删除测试用例
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestcaseByIds(String ids);

    /**
     * 删除测试用例信息
     * 
     * @param testCaseId 测试用例ID
     * @return 结果
     */
    public int deleteTestcaseById(Long testCaseId);

    /**
     * 扫描@Test注解的测试用例
     * @return
     */
    public List<Testcase> scanTestcases();

    /**
     * 执行测试用例
     * @param testcaseList
     */
    public void runCase(List<Testcase> testcaseList);
}
