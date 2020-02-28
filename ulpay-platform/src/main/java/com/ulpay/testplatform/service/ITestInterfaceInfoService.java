package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.TestInterfaceInfo;

import java.util.List;

/**
 * 接口信息实体Service接口
 * 
 * @author zhangsy
 * @date 2019-12-06
 */
public interface ITestInterfaceInfoService 
{
    /**
     * 查询接口信息实体
     * 
     * @param interfaceId 接口信息实体ID
     * @return 接口信息实体
     */
    public TestInterfaceInfo selectTestInterfaceInfoById(Long interfaceId);

    /**
     * 查询接口信息实体列表
     * 
     * @param testInterfaceInfo 接口信息实体
     * @return 接口信息实体集合
     */
    public List<TestInterfaceInfo> selectTestInterfaceInfoList(TestInterfaceInfo testInterfaceInfo);

    /**
     * 新增接口信息实体
     * 
     * @param testInterfaceInfo 接口信息实体
     * @return 结果
     */
    public int insertTestInterfaceInfo(TestInterfaceInfo testInterfaceInfo);

    /**
     * 修改接口信息实体
     * 
     * @param testInterfaceInfo 接口信息实体
     * @return 结果
     */
    public int updateTestInterfaceInfo(TestInterfaceInfo testInterfaceInfo);

    /**
     * 批量删除接口信息实体
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestInterfaceInfoByIds(String ids);

    /**
     * 删除接口信息实体信息
     * 
     * @param interfaceId 接口信息实体ID
     * @return 结果
     */
    public int deleteTestInterfaceInfoById(Long interfaceId);
}
