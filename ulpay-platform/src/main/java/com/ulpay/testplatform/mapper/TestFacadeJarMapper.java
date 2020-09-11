package com.ulpay.testplatform.mapper;

import com.ulpay.testplatform.domain.TestFacadeJar;
import java.util.List;

/**
 * Facade包Mapper接口
 * 
 * @author zhangsy
 * @date 2020-06-16
 */
public interface TestFacadeJarMapper 
{
    /**
     * 查询Facade包
     * 
     * @param id Facade包ID
     * @return Facade包
     */
    public TestFacadeJar selectTestFacadeJarById(Integer id);

    /**
     * 查询Facade包列表
     * 
     * @param testFacadeJar Facade包
     * @return Facade包集合
     */
    public List<TestFacadeJar> selectTestFacadeJarList(TestFacadeJar testFacadeJar);

    /**
     * 新增Facade包
     * 
     * @param testFacadeJar Facade包
     * @return 结果
     */
    public int insertTestFacadeJar(TestFacadeJar testFacadeJar);

    /**
     * 修改Facade包
     * 
     * @param testFacadeJar Facade包
     * @return 结果
     */
    public int updateTestFacadeJar(TestFacadeJar testFacadeJar);

    /**
     * 删除Facade包
     * 
     * @param id Facade包ID
     * @return 结果
     */
    public int deleteTestFacadeJarById(Integer id);

    /**
     * 批量删除Facade包
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestFacadeJarByIds(String[] ids);
}
