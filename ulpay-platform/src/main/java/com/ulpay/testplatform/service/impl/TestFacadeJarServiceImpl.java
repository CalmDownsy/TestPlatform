package com.ulpay.testplatform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.TestFacadeJarMapper;
import com.ulpay.testplatform.domain.TestFacadeJar;
import com.ulpay.testplatform.service.ITestFacadeJarService;
import com.ruoyi.common.core.text.Convert;

/**
 * Facade包Service业务层处理
 * 
 * @author zhangsy
 * @date 2020-06-16
 */
@Service
public class TestFacadeJarServiceImpl implements ITestFacadeJarService 
{
    @Autowired
    private TestFacadeJarMapper testFacadeJarMapper;

    /**
     * 查询Facade包
     * 
     * @param id Facade包ID
     * @return Facade包
     */
    @Override
    public TestFacadeJar selectTestFacadeJarById(Integer id)
    {
        return testFacadeJarMapper.selectTestFacadeJarById(id);
    }

    /**
     * 查询Facade包列表
     * 
     * @param testFacadeJar Facade包
     * @return Facade包
     */
    @Override
    public List<TestFacadeJar> selectTestFacadeJarList(TestFacadeJar testFacadeJar)
    {
        return testFacadeJarMapper.selectTestFacadeJarList(testFacadeJar);
    }

    /**
     * 新增Facade包
     * 
     * @param testFacadeJar Facade包
     * @return 结果
     */
    @Override
    public int insertTestFacadeJar(TestFacadeJar testFacadeJar)
    {
        testFacadeJar.setCreateTime(DateUtils.getNowDate());
        return testFacadeJarMapper.insertTestFacadeJar(testFacadeJar);
    }

    /**
     * 修改Facade包
     * 
     * @param testFacadeJar Facade包
     * @return 结果
     */
    @Override
    public int updateTestFacadeJar(TestFacadeJar testFacadeJar)
    {
        testFacadeJar.setUpdateTime(DateUtils.getNowDate());
        return testFacadeJarMapper.updateTestFacadeJar(testFacadeJar);
    }

    /**
     * 删除Facade包对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestFacadeJarByIds(String ids)
    {
        return testFacadeJarMapper.deleteTestFacadeJarByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Facade包信息
     * 
     * @param id Facade包ID
     * @return 结果
     */
    public int deleteTestFacadeJarById(Integer id)
    {
        return testFacadeJarMapper.deleteTestFacadeJarById(id);
    }
}
