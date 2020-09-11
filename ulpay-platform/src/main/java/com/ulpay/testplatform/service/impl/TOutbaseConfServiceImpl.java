package com.ulpay.testplatform.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.TOutbaseConfMapper;
import com.ulpay.testplatform.domain.TOutbaseConf;
import com.ulpay.testplatform.service.ITOutbaseConfService;
import com.ruoyi.common.core.text.Convert;

/**
 * 外部数据源维护Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-03-27
 */
@Service
public class TOutbaseConfServiceImpl implements ITOutbaseConfService 
{
    @Autowired
    private TOutbaseConfMapper tOutbaseConfMapper;

    /**
     * 查询外部数据源维护
     * 
     * @param baseId 外部数据源维护ID
     * @return 外部数据源维护
     */
    @Override
    public TOutbaseConf selectTOutbaseConfById(Long baseId)
    {
        return tOutbaseConfMapper.selectTOutbaseConfById(baseId);
    }

    /**
     * 查询外部数据源维护列表
     * 
     * @param tOutbaseConf 外部数据源维护
     * @return 外部数据源维护
     */
    @Override
    public List<TOutbaseConf> selectTOutbaseConfList(TOutbaseConf tOutbaseConf)
    {
        return tOutbaseConfMapper.selectTOutbaseConfList(tOutbaseConf);
    }

    /**
     * 新增外部数据源维护
     * 
     * @param tOutbaseConf 外部数据源维护
     * @return 结果
     */
    @Override
    public int insertTOutbaseConf(TOutbaseConf tOutbaseConf)
    {
        return tOutbaseConfMapper.insertTOutbaseConf(tOutbaseConf);
    }

    /**
     * 修改外部数据源维护
     * 
     * @param tOutbaseConf 外部数据源维护
     * @return 结果
     */
    @Override
    public int updateTOutbaseConf(TOutbaseConf tOutbaseConf)
    {
        return tOutbaseConfMapper.updateTOutbaseConf(tOutbaseConf);
    }

    /**
     * 删除外部数据源维护对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTOutbaseConfByIds(String ids)
    {
        return tOutbaseConfMapper.deleteTOutbaseConfByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除外部数据源维护信息
     * 
     * @param baseId 外部数据源维护ID
     * @return 结果
     */
    public int deleteTOutbaseConfById(Long baseId)
    {
        return tOutbaseConfMapper.deleteTOutbaseConfById(baseId);
    }
}
