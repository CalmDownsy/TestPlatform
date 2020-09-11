package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.TOutbaseConf;
import java.util.List;

/**
 * 外部数据源维护Service接口
 * 
 * @author ruoyi
 * @date 2020-03-27
 */
public interface ITOutbaseConfService 
{
    /**
     * 查询外部数据源维护
     * 
     * @param baseId 外部数据源维护ID
     * @return 外部数据源维护
     */
    public TOutbaseConf selectTOutbaseConfById(Long baseId);

    /**
     * 查询外部数据源维护列表
     * 
     * @param tOutbaseConf 外部数据源维护
     * @return 外部数据源维护集合
     */
    public List<TOutbaseConf> selectTOutbaseConfList(TOutbaseConf tOutbaseConf);

    /**
     * 新增外部数据源维护
     * 
     * @param tOutbaseConf 外部数据源维护
     * @return 结果
     */
    public int insertTOutbaseConf(TOutbaseConf tOutbaseConf);

    /**
     * 修改外部数据源维护
     * 
     * @param tOutbaseConf 外部数据源维护
     * @return 结果
     */
    public int updateTOutbaseConf(TOutbaseConf tOutbaseConf);

    /**
     * 批量删除外部数据源维护
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTOutbaseConfByIds(String ids);

    /**
     * 删除外部数据源维护信息
     * 
     * @param baseId 外部数据源维护ID
     * @return 结果
     */
    public int deleteTOutbaseConfById(Long baseId);
}
