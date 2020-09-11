package com.ulpay.testplatform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.TestCertManagementMapper;
import com.ulpay.testplatform.domain.TestCertManagement;
import com.ulpay.testplatform.service.ITestCertManagementService;
import com.ruoyi.common.core.text.Convert;

/**
 * 证书管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-01-17
 */
@Service
public class TestCertManagementServiceImpl implements ITestCertManagementService 
{
    @Autowired
    private TestCertManagementMapper testCertManagementMapper;

    /**
     * 查询证书管理
     * 
     * @param id 证书管理ID
     * @return 证书管理
     */
    @Override
    public TestCertManagement selectTestCertManagementById(Integer id)
    {
        return testCertManagementMapper.selectTestCertManagementById(id);
    }

    /**
     * 根据文件名查询证书
     *
     * @param fileName
     * @return
     */
    @Override
    public TestCertManagement selectTestCertManagementByName(String fileName){
        return testCertManagementMapper.selectTestCertManagementByName(fileName);
    }

    /**
     * 查询证书管理列表
     * 
     * @param testCertManagement 证书管理
     * @return 证书管理
     */
    @Override
    public List<TestCertManagement> selectTestCertManagementList(TestCertManagement testCertManagement)
    {
        return testCertManagementMapper.selectTestCertManagementList(testCertManagement);
    }

    /**
     * 新增证书管理
     * 
     * @param testCertManagement 证书管理
     * @return 结果
     */
    @Override
    public int insertTestCertManagement(TestCertManagement testCertManagement)
    {
        testCertManagement.setCreateTime(DateUtils.getNowDate());
        return testCertManagementMapper.insertTestCertManagement(testCertManagement);
    }

    /**
     * 修改证书管理
     * 
     * @param testCertManagement 证书管理
     * @return 结果
     */
    @Override
    public int updateTestCertManagement(TestCertManagement testCertManagement)
    {
        testCertManagement.setUpdateTime(DateUtils.getNowDate());

        return testCertManagementMapper.updateTestCertManagement(testCertManagement);
    }

    /**
     * 删除证书管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestCertManagementByIds(String ids)
    {
        return testCertManagementMapper.deleteTestCertManagementByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除证书管理信息
     * 
     * @param id 证书管理ID
     * @return 结果
     */
    public int deleteTestCertManagementById(Integer id)
    {
        return testCertManagementMapper.deleteTestCertManagementById(id);
    }
}
