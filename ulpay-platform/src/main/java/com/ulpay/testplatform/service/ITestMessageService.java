package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.TestMessage;

import java.util.List;

/**
 * 报文实体Service接口
 *
 * @author zhangsy
 * @date 2019-12-09
 */
public interface ITestMessageService {
    /**
     * 查询报文实体
     *
     * @param messageId 报文实体ID
     * @return 报文实体
     */
    public TestMessage selectTestMessageById(Long messageId);

    /**
     * 查询报文实体列表
     *
     * @param testMessage 报文实体
     * @return 报文实体集合
     */
    public List<TestMessage> selectTestMessageList(TestMessage testMessage);

    /**
     * 新增报文实体
     *
     * @param testMessage 报文实体
     * @return 结果
     */
    public int insertTestMessage(TestMessage testMessage);

    /**
     * 修改报文实体
     *
     * @param testMessage 报文实体
     * @return 结果
     */
    public int updateTestMessage(TestMessage testMessage);

    /**
     * 批量删除报文实体
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestMessageByIds(String ids);

    /**
     * 删除报文实体信息
     *
     * @param messageId 报文实体ID
     * @return 结果
     */
    public int deleteTestMessageById(Long messageId);

    /**
     * 获取facade接口的请求参数
     *
     * @param interfaceId 接口Id
     * @return 转换为json数组
     */
    public String getFacadeParams(Long interfaceId);
}
