package com.ulpay.testplatform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.TestInterfaceMockMapper;
import com.ulpay.testplatform.domain.TestInterfaceMock;
import com.ulpay.testplatform.service.ITestInterfaceMockService;
import com.ruoyi.common.core.text.Convert;

/**
 * Mock配置Service业务层处理
 * 
 * @author zhangsy
 * @date 2020-06-19
 */
@Service
public class TestInterfaceMockServiceImpl implements ITestInterfaceMockService 
{
    @Autowired
    private TestInterfaceMockMapper testInterfaceMockMapper;

    /**
     * 查询Mock配置
     * 
     * @param mockId Mock配置ID
     * @return Mock配置
     */
    @Override
    public TestInterfaceMock selectTestInterfaceMockById(Long mockId)
    {
        return testInterfaceMockMapper.selectTestInterfaceMockById(mockId);
    }

    /**
     * 查询Mock配置列表
     * 
     * @param testInterfaceMock Mock配置
     * @return Mock配置
     */
    @Override
    public List<TestInterfaceMock> selectTestInterfaceMockList(TestInterfaceMock testInterfaceMock)
    {
        return testInterfaceMockMapper.selectTestInterfaceMockList(testInterfaceMock);
    }

    /**
     * 新增Mock配置
     * 
     * @param testInterfaceMock Mock配置
     * @return 结果
     */
    @Override
    public int insertTestInterfaceMock(TestInterfaceMock testInterfaceMock)
    {
        testInterfaceMock.setCreateTime(DateUtils.getNowDate());
        return testInterfaceMockMapper.insertTestInterfaceMock(testInterfaceMock);
    }

    /**
     * 修改Mock配置
     * 
     * @param testInterfaceMock Mock配置
     * @return 结果
     */
    @Override
    public int updateTestInterfaceMock(TestInterfaceMock testInterfaceMock)
    {
        testInterfaceMock.setUpdateTime(DateUtils.getNowDate());
        return testInterfaceMockMapper.updateTestInterfaceMock(testInterfaceMock);
    }

    /**
     * 删除Mock配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestInterfaceMockByIds(String ids)
    {
        return testInterfaceMockMapper.deleteTestInterfaceMockByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Mock配置信息
     * 
     * @param mockId Mock配置ID
     * @return 结果
     */
    public int deleteTestInterfaceMockById(Long mockId)
    {
        return testInterfaceMockMapper.deleteTestInterfaceMockById(mockId);
    }

    /**
     * 匹配mock规则
     * @param requestMap
     * @param mockId
     * @return
     */
    public String checkMockRule(HashMap<String, Object> requestMap, Long mockId) {
        String responseMsg = "";
        TestInterfaceMock interfaceMock = selectTestInterfaceMockById(mockId);
        String rulesetJson = interfaceMock.getRulesetJson();
        JSONObject ruleJson = JSON.parseObject(rulesetJson);
        HashMap<String,Object> mockRequestMap = JSONObject.toJavaObject(ruleJson.getJSONObject("requestParam"), HashMap.class);

        // 获取Mock入参并遍历
        for (Map.Entry<String,Object> entry : mockRequestMap.entrySet()) {
            if (requestMap.containsKey(entry.getKey())) {
                // 比较实际参数与mock规则是否匹配
                Object mockValue = entry.getValue();
                Object actualValue = requestMap.get(entry.getKey());
                if (actualValue.equals(mockValue)) {
                    //
                    responseMsg = ruleJson.getString("responseMsg");
                }
            }
        }
        return responseMsg;
    }
}
