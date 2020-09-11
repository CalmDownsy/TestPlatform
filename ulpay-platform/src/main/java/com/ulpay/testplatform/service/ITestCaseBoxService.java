package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.TestCaseBox;
import java.util.List;

/**
 * 用例集合Service接口
 * 
 * @author ruoyi
 * @date 2020-02-25
 */
public interface ITestCaseBoxService 
{
    /**
     * 查询用例集合
     * 
     * @param id 用例集合ID
     * @return 用例集合
     */
    public TestCaseBox selectTestCaseBoxById(Long id);

    /**
     * 查询用例集合列表
     * 
     * @param testCaseBox 用例集合
     * @return 用例集合集合
     */
    public List<TestCaseBox> selectTestCaseBoxList(TestCaseBox testCaseBox);

    /**
     * 新增用例集合
     * 
     * @param testCaseBox 用例集合
     * @return 结果
     */
    public int insertTestCaseBox(TestCaseBox testCaseBox);

    /**
     * 修改用例集合
     * 
     * @param testCaseBox 用例集合
     * @return 结果
     */
    public int updateTestCaseBox(TestCaseBox testCaseBox);

    /**
     * 批量删除用例集合
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestCaseBoxByIds(String ids);

    /**
     * 删除用例集合信息
     * 
     * @param id 用例集合ID
     * @return 结果
     */
    public int deleteTestCaseBoxById(Long id);
}
