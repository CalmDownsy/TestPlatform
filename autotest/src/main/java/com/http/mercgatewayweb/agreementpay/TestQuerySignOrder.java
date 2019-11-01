package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/*
test interface  签约订单查询
@date:20190627
@author:zxb
 */

public class TestQuerySignOrder extends RequestMsgUtils {

    private static final Logger logger = LoggerFactory.getLogger(TestQuerySignOrder.class);
    private RandomUtils randomUtils = new RandomUtils();
    private HttpSendUtils http = new HttpSendUtils();
    private String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "query/signorder";

    public void initReqMap() throws Exception {
        mapInit();
        infoMap.put("TRX_CODE","200015");//Y 交易代码
        infoMap.put("VERSION","2.0");//Y 版本
        infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
        infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
        bodyMap.put("MERC_ORDER_NO", "");//Y 商户签约订单号 仅支持数字、字母、_
        bodyMap.put("MERC_ORDER_DATE", "");//Y 商户签约订单日期 YYYYMMDD

        bodyMap.put("EXTEND1", "备用域EXTEND1");
        bodyMap.put("EXTEND2", "备用域EXTEND2");
        bodyMap.put("EXTEND3", "备用域EXTEND3");

    }



    /**
     * 签约订单查询
     */
    @Test
    public void test001()throws Exception{
        initReqMap();
        bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
        bodyMap.put("MERC_ORDER_NO", "41906240000003717016");//Y 商户签约订单号
        bodyMap.put("MERC_ORDER_DATE", "20190624");//Y 商户签约订单日期

        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, url);
        logger.info("respMap:{}",respMap);
        Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }

}
