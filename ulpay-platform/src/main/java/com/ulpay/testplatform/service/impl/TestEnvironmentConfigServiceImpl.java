package com.ulpay.testplatform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.TestEnvironmentConfigMapper;
import com.ulpay.testplatform.domain.TestEnvironmentConfig;
import com.ulpay.testplatform.service.ITestEnvironmentConfigService;
import com.ruoyi.common.core.text.Convert;

/**
 * 环境配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-01-06
 */
@Service
public class TestEnvironmentConfigServiceImpl implements ITestEnvironmentConfigService 
{
    @Autowired
    private TestEnvironmentConfigMapper testEnvironmentConfigMapper;

    /**
     * 查询环境配置
     * 
     * @param id 环境配置ID
     * @return 环境配置
     */
    @Override
    public TestEnvironmentConfig selectTestEnvironmentConfigById(Integer id)
    {
        return testEnvironmentConfigMapper.selectTestEnvironmentConfigById(id);
    }

    /**
     * 查询环境配置列表
     * 
     * @param testEnvironmentConfig 环境配置
     * @return 环境配置
     */
    @Override
    public List<TestEnvironmentConfig> selectTestEnvironmentConfigList(TestEnvironmentConfig testEnvironmentConfig)
    {
        return testEnvironmentConfigMapper.selectTestEnvironmentConfigList(testEnvironmentConfig);
    }

    /**
     * 新增环境配置
     * 
     * @param testEnvironmentConfig 环境配置
     * @return 结果
     */
    @Override
    public int insertTestEnvironmentConfig(TestEnvironmentConfig testEnvironmentConfig)
    {
        testEnvironmentConfig.setCreateTime(DateUtils.getNowDate());
        return testEnvironmentConfigMapper.insertTestEnvironmentConfig(testEnvironmentConfig);
    }

    /**
     * 修改环境配置
     * 
     * @param testEnvironmentConfig 环境配置
     * @return 结果
     */
    @Override
    public int updateTestEnvironmentConfig(TestEnvironmentConfig testEnvironmentConfig)
    {
        testEnvironmentConfig.setUpdateTime(DateUtils.getNowDate());
        return testEnvironmentConfigMapper.updateTestEnvironmentConfig(testEnvironmentConfig);
    }

    /**
     * 删除环境配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestEnvironmentConfigByIds(String ids)
    {
        return testEnvironmentConfigMapper.deleteTestEnvironmentConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除环境配置信息
     * 
     * @param id 环境配置ID
     * @return 结果
     */
    public int deleteTestEnvironmentConfigById(Integer id)
    {
        return testEnvironmentConfigMapper.deleteTestEnvironmentConfigById(id);
    }

    @Override
    public List<TestEnvironmentConfig> selectDistinctEnvNameList() {
        return testEnvironmentConfigMapper.selectDistinctEnvNameList();
    }
}
