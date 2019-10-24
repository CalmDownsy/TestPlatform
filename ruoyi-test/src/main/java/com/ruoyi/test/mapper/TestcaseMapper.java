package com.ruoyi.test.mapper;

import com.ruoyi.test.domain.Testcase;

import java.util.List;

/**
 * 测试用例Mapper接口
 * 
 * @author zhangsy
 * @date 2019-10-24
 */
public interface TestcaseMapper 
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
     * 删除测试用例
     * 
     * @param testCaseId 测试用例ID
     * @return 结果
     */
    public int deleteTestcaseById(Long testCaseId);

    /**
     * 批量删除测试用例
     * 
     * @param testCaseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestcaseByIds(String[] testCaseIds);
}
