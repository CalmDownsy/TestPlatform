package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.TestInterfaceMock;
import java.util.List;

/**
 * Mock配置Service接口
 * 
 * @author zhangsy
 * @date 2020-06-19
 */
public interface ITestInterfaceMockService 
{
    /**
     * 查询Mock配置
     * 
     * @param mockId Mock配置ID
     * @return Mock配置
     */
    public TestInterfaceMock selectTestInterfaceMockById(Long mockId);

    /**
     * 查询Mock配置列表
     * 
     * @param testInterfaceMock Mock配置
     * @return Mock配置集合
     */
    public List<TestInterfaceMock> selectTestInterfaceMockList(TestInterfaceMock testInterfaceMock);

    /**
     * 新增Mock配置
     * 
     * @param testInterfaceMock Mock配置
     * @return 结果
     */
    public int insertTestInterfaceMock(TestInterfaceMock testInterfaceMock);

    /**
     * 修改Mock配置
     * 
     * @param testInterfaceMock Mock配置
     * @return 结果
     */
    public int updateTestInterfaceMock(TestInterfaceMock testInterfaceMock);

    /**
     * 批量删除Mock配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestInterfaceMockByIds(String ids);

    /**
     * 删除Mock配置信息
     * 
     * @param mockId Mock配置ID
     * @return 结果
     */
    public int deleteTestInterfaceMockById(Long mockId);
}
