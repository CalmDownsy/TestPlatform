package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.TestEnvironmentConfig;
import java.util.List;

/**
 * 环境配置Service接口
 * 
 * @author ruoyi
 * @date 2020-01-06
 */
public interface ITestEnvironmentConfigService 
{
    /**
     * 查询环境配置
     * 
     * @param id 环境配置ID
     * @return 环境配置
     */
    public TestEnvironmentConfig selectTestEnvironmentConfigById(Integer id);

    /**
     * 查询环境配置列表
     * 
     * @param testEnvironmentConfig 环境配置
     * @return 环境配置集合
     */
    public List<TestEnvironmentConfig> selectTestEnvironmentConfigList(TestEnvironmentConfig testEnvironmentConfig);

    /**
     * 新增环境配置
     * 
     * @param testEnvironmentConfig 环境配置
     * @return 结果
     */
    public int insertTestEnvironmentConfig(TestEnvironmentConfig testEnvironmentConfig);

    /**
     * 修改环境配置
     * 
     * @param testEnvironmentConfig 环境配置
     * @return 结果
     */
    public int updateTestEnvironmentConfig(TestEnvironmentConfig testEnvironmentConfig);

    /**
     * 批量删除环境配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestEnvironmentConfigByIds(String ids);

    /**
     * 删除环境配置信息
     * 
     * @param id 环境配置ID
     * @return 结果
     */
    public int deleteTestEnvironmentConfigById(Integer id);

    /**
     * 去重查询已添加的环境名称
     * @return
     */
    public List<TestEnvironmentConfig> selectDistinctEnvNameList();
}
