package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.TestCertManagement;
import java.util.List;

/**
 * 证书管理Service接口
 * 
 * @author ruoyi
 * @date 2020-01-17
 */
public interface ITestCertManagementService 
{
    /**
     * 查询证书管理
     * 
     * @param id 证书管理ID
     * @return 证书管理
     */
    public TestCertManagement selectTestCertManagementById(Integer id);

    /**
     * 根据文件名查询证书
     *
     * @param fileName
     * @return
     */
    public TestCertManagement selectTestCertManagementByName(String fileName);

    /**
     * 查询证书管理列表
     * 
     * @param testCertManagement 证书管理
     * @return 证书管理集合
     */
    public List<TestCertManagement> selectTestCertManagementList(TestCertManagement testCertManagement);

    /**
     * 新增证书管理
     * 
     * @param testCertManagement 证书管理
     * @return 结果
     */
    public int insertTestCertManagement(TestCertManagement testCertManagement);

    /**
     * 修改证书管理
     * 
     * @param testCertManagement 证书管理
     * @return 结果
     */
    public int updateTestCertManagement(TestCertManagement testCertManagement);

    /**
     * 批量删除证书管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestCertManagementByIds(String ids);

    /**
     * 删除证书管理信息
     * 
     * @param id 证书管理ID
     * @return 结果
     */
    public int deleteTestCertManagementById(Integer id);
}
