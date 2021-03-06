package com.ulpay.testplatform.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ulpay.testplatform.domain.TestInterfaceInfo;
import com.ulpay.testplatform.domain.TestMessage;
import com.ulpay.testplatform.mapper.TestInterfaceInfoMapper;
import com.ulpay.testplatform.mapper.TestMessageMapper;
import com.ulpay.testplatform.service.ITestMessageService;
import com.ulpay.testplatform.utils.ScanJarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;

/**
 * 报文实体Service业务层处理
 * 
 * @author zhangsy
 * @date 2019-12-09
 */
@Service
public class TestMessageServiceImpl implements ITestMessageService
{

    // 绝对路径-固定
    private static final String jarFilePath = "";

    @Autowired
    private TestMessageMapper testMessageMapper;

    @Autowired
    private TestInterfaceInfoMapper testInterfaceInfoMapper;

    /**
     * 查询报文实体
     * 
     * @param messageId 报文实体ID
     * @return 报文实体
     */
    @Override
    public TestMessage selectTestMessageById(Long messageId)
    {
        return testMessageMapper.selectTestMessageById(messageId);
    }

    /**
     * 查询报文实体列表
     * 
     * @param testMessage 报文实体
     * @return 报文实体
     */
    @Override
    public List<TestMessage> selectTestMessageList(TestMessage testMessage)
    {
        return testMessageMapper.selectTestMessageList(testMessage);
    }

    /**
     * 新增报文实体
     * 
     * @param testMessage 报文实体
     * @return 结果
     */
    @Override
    public int insertTestMessage(TestMessage testMessage)
    {
        testMessage.setCreateTime(DateUtils.getNowDate());
        return testMessageMapper.insertTestMessage(testMessage);
    }

    /**
     * 修改报文实体
     * 
     * @param testMessage 报文实体
     * @return 结果
     */
    @Override
    public int updateTestMessage(TestMessage testMessage)
    {
        testMessage.setUpdateTime(DateUtils.getNowDate());
        return testMessageMapper.updateTestMessage(testMessage);
    }

    /**
     * 删除报文实体对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestMessageByIds(String ids)
    {
        return testMessageMapper.deleteTestMessageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除报文实体信息
     * 
     * @param messageId 报文实体ID
     * @return 结果
     */
    @Override
    public int deleteTestMessageById(Long messageId)
    {
        return testMessageMapper.deleteTestMessageById(messageId);
    }

    @Override
    public String getFacadeParams(Long interfaceId) {
        return null;
    }
}
