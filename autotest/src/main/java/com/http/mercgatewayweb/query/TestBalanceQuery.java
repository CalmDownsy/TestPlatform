package com.http.mercgatewayweb.query;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @ClassName TestBalanceQuery
 * @Description 余额查询（200031）
 * @Author owen.liu
 * @Date 2019.6.17 17:49
 * @Version 1.0
 */
public class TestBalanceQuery extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestBalanceQuery.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "balance/query";
    private String mercId = AutoConstant.MERC_ID_40001;
    //初始化数据
    public void initData() {
        mapInit();
        infoMap.put("TRX_CODE", "200031");//Y 交易代码 定值
        infoMap.put("VERSION", "2.0");//Y 版本 定值
        infoMap.put("DATA_TYPE", "0");//Y 数据格式 定值
        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG", "signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("CUST_TYPE", "2");//客户类型，1：个人，2：商户
        bodyMap.put("CUST_ID", mercId);//客户编号，1：个人，2：商户
    }

    @Test
    public void testQuerySignCheck() throws Exception {
        logger.info("开始执行 余额查询（200031）（merc-gateway-web/balance/query） 用例...");
        initData();
        bodyMap.put("CUST_TYPE", "2");//客户类型，1：个人，2：商户
        bodyMap.put("CUST_ID", mercId);//客户编号，1：个人，2：商户
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrl);
        respMap.put("SIGNED_MSG","");
        logger.info("响应结果：{}",respMap);
        logger.info("判断查询结果？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"), "0000");
    }
}
