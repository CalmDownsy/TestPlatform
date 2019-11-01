package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/*
test interface  查询支持银行列表
@date:20190627
@author:zxb
 */

public class TestQueryBankList extends RequestMsgUtils{
    private static final Logger logger = LoggerFactory.getLogger(TestQueryBankList.class);
    private RandomUtils randomUtils = new RandomUtils();
    private HttpSendUtils http = new HttpSendUtils();
    private String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "query/banklist";

    public void initReqMap() throws Exception {
        mapInit();
        infoMap.put("TRX_CODE","200010");//Y 交易代码
        infoMap.put("VERSION","2.0");//Y 版本
        infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
        infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
        bodyMap.put("BUSINESS_NAME", "B0006");//Y 业务名称 B0001  批量代扣  B0006：协议支付
        bodyMap.put("PAYMENT_MODE", "1");//N 支付方式 借记卡：1

        bodyMap.put("EXTEND1", "备用域EXTEND1");
        bodyMap.put("EXTEND2", "备用域EXTEND2");
        bodyMap.put("EXTEND3", "备用域EXTEND3");

    }



    /**
     * 查询支持银行列表
     */
    @Test
    public void test001()throws Exception{
        initReqMap();
        bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
        bodyMap.put("BUSINESS_NAME", "B0006");//Y 业务名称 B0001  批量代扣  B0006：协议支付
        bodyMap.put("PAYMENT_MODE", "1");//N 支付方式 借记卡：1

        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, url);
        logger.info("respMap:{}",respMap);
        Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }

}
