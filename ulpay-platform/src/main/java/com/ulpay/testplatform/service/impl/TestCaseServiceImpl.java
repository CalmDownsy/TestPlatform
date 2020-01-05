package com.ulpay.testplatform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.TestCaseMapper;
import com.ulpay.testplatform.domain.TestCase;
import com.ulpay.testplatform.service.ITestCaseService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用例实体Service业务层处理
 * 
 * @author zhangsy
 * @date 2020-01-02
 */
@Service
public class TestCaseServiceImpl implements ITestCaseService 
{
    @Autowired
    private TestCaseMapper testCaseMapper;

    /**
     * 查询用例实体
     * 
     * @param caseId 用例实体ID
     * @return 用例实体
     */
    @Override
    public TestCase selectTestCaseById(Long caseId)
    {
        return testCaseMapper.selectTestCaseById(caseId);
    }

    /**
     * 查询用例实体列表
     * 
     * @param testCase 用例实体
     * @return 用例实体
     */
    @Override
    public List<TestCase> selectTestCaseList(TestCase testCase)
    {
        return testCaseMapper.selectTestCaseList(testCase);
    }

    /**
     * 新增用例实体
     * 
     * @param testCase 用例实体
     * @return 结果
     */
    @Override
    public int insertTestCase(TestCase testCase)
    {
        testCase.setCreateTime(DateUtils.getNowDate());
        return testCaseMapper.insertTestCase(testCase);
    }

    /**
     * 修改用例实体
     * 
     * @param testCase 用例实体
     * @return 结果
     */
    @Override
    public int updateTestCase(TestCase testCase)
    {
        testCase.setUpdateTime(DateUtils.getNowDate());
        return testCaseMapper.updateTestCase(testCase);
    }

    /**
     * 删除用例实体对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestCaseByIds(String ids)
    {
        return testCaseMapper.deleteTestCaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用例实体信息
     * 
     * @param caseId 用例实体ID
     * @return 结果
     */
    public int deleteTestCaseById(Long caseId)
    {
        return testCaseMapper.deleteTestCaseById(caseId);
    }
}
