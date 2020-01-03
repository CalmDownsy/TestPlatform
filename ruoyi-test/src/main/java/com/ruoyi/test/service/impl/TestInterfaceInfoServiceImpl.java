package com.ruoyi.test.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.framework.util.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.test.mapper.TestInterfaceInfoMapper;
import com.ruoyi.test.domain.TestInterfaceInfo;
import com.ruoyi.test.service.ITestInterfaceInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 接口信息实体Service业务层处理
 * 
 * @author zhangsy
 * @date 2019-12-06
 */
@Service
public class TestInterfaceInfoServiceImpl implements ITestInterfaceInfoService 
{
    @Autowired
    private TestInterfaceInfoMapper testInterfaceInfoMapper;

    /**
     * 查询接口信息实体
     * 
     * @param interfaceId 接口信息实体ID
     * @return 接口信息实体
     */
    @Override
    public TestInterfaceInfo selectTestInterfaceInfoById(Long interfaceId)
    {
        return testInterfaceInfoMapper.selectTestInterfaceInfoById(interfaceId);
    }

    /**
     * 查询接口信息实体列表
     * 
     * @param testInterfaceInfo 接口信息实体
     * @return 接口信息实体
     */
    @Override
    public List<TestInterfaceInfo> selectTestInterfaceInfoList(TestInterfaceInfo testInterfaceInfo)
    {
        return testInterfaceInfoMapper.selectTestInterfaceInfoList(testInterfaceInfo);
    }

    /**
     * 新增接口信息实体
     * 
     * @param testInterfaceInfo 接口信息实体
     * @return 结果
     */
    @Override
    public int insertTestInterfaceInfo(TestInterfaceInfo testInterfaceInfo)
    {
        testInterfaceInfo.setCreateTime(DateUtils.getNowDate());
        testInterfaceInfo.setCreateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return testInterfaceInfoMapper.insertTestInterfaceInfo(testInterfaceInfo);
    }

    /**
     * 修改接口信息实体
     * 
     * @param testInterfaceInfo 接口信息实体
     * @return 结果
     */
    @Override
    public int updateTestInterfaceInfo(TestInterfaceInfo testInterfaceInfo)
    {
        testInterfaceInfo.setUpdateTime(DateUtils.getNowDate());
        testInterfaceInfo.setUpdateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return testInterfaceInfoMapper.updateTestInterfaceInfo(testInterfaceInfo);
    }

    /**
     * 删除接口信息实体对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestInterfaceInfoByIds(String ids)
    {
        return testInterfaceInfoMapper.deleteTestInterfaceInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除接口信息实体信息
     * 
     * @param interfaceId 接口信息实体ID
     * @return 结果
     */
    public int deleteTestInterfaceInfoById(Long interfaceId)
    {
        return testInterfaceInfoMapper.deleteTestInterfaceInfoById(interfaceId);
    }
}
