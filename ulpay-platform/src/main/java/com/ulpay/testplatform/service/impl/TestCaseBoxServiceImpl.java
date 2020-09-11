package com.ulpay.testplatform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.TestCaseBoxMapper;
import com.ulpay.testplatform.domain.TestCaseBox;
import com.ulpay.testplatform.service.ITestCaseBoxService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用例集合Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-02-25
 */
@Service
public class TestCaseBoxServiceImpl implements ITestCaseBoxService 
{
    @Autowired
    private TestCaseBoxMapper testCaseBoxMapper;

    /**
     * 查询用例集合
     * 
     * @param id 用例集合ID
     * @return 用例集合
     */
    @Override
    public TestCaseBox selectTestCaseBoxById(Long id)
    {
        return testCaseBoxMapper.selectTestCaseBoxById(id);
    }

    /**
     * 查询用例集合列表
     * 
     * @param testCaseBox 用例集合
     * @return 用例集合
     */
    @Override
    public List<TestCaseBox> selectTestCaseBoxList(TestCaseBox testCaseBox)
    {
        return testCaseBoxMapper.selectTestCaseBoxList(testCaseBox);
    }

    /**
     * 新增用例集合
     * 
     * @param testCaseBox 用例集合
     * @return 结果
     */
    @Override
    public int insertTestCaseBox(TestCaseBox testCaseBox)
    {
        testCaseBox.setCreateTime(DateUtils.getNowDate());
        return testCaseBoxMapper.insertTestCaseBox(testCaseBox);
    }

    /**
     * 修改用例集合
     * 
     * @param testCaseBox 用例集合
     * @return 结果
     */
    @Override
    public int updateTestCaseBox(TestCaseBox testCaseBox)
    {
        testCaseBox.setUpdateTime(DateUtils.getNowDate());
        return testCaseBoxMapper.updateTestCaseBox(testCaseBox);
    }

    /**
     * 删除用例集合对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestCaseBoxByIds(String ids)
    {
        return testCaseBoxMapper.deleteTestCaseBoxByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用例集合信息
     * 
     * @param id 用例集合ID
     * @return 结果
     */
    public int deleteTestCaseBoxById(Long id)
    {
        return testCaseBoxMapper.deleteTestCaseBoxById(id);
    }
}
