package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;



/*
test interface  卡bin查询
@date:20190626
@author:zxb
 */

public class TestCardBinQuery extends RequestMsgUtils{
    private static final Logger logger = LoggerFactory.getLogger(TestCardBinQuery.class);
    private RandomUtils randomUtils = new RandomUtils();
    private HttpSendUtils http = new HttpSendUtils();
    private String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "cardbin/cardbinquery";

    public void initReqMap() throws Exception {
        mapInit();
        infoMap.put("TRX_CODE","100020");//Y 交易代码
        infoMap.put("VERSION","2.0");//Y 版本
        infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
        infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
        bodyMap.put("CARD_NO", "");//Y 卡号

        bodyMap.put("EXTEND1", "备用域EXTEND1");
        bodyMap.put("EXTEND2", "备用域EXTEND2");
        bodyMap.put("EXTEND3", "备用域EXTEND3");

    }



    /**
     * 卡bin查询
     */
    @Test
    public void test001()throws Exception{
        initReqMap();
        bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
        bodyMap.put("CARD_NO", "6222023803013297860");//Y 卡号

        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, url);
        logger.info("respMap:{}",respMap);
        Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }

}
