package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.commonutils.SignUtilTest;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.LinkedHashMap;
import java.util.Map;

/*
test interface  查询支持银行列表
@author:zxb
 */

public class TestQuerySupportBankList extends RequestMsgUtils{
    private static final Logger logger = LoggerFactory.getLogger(TestQuerySupportBankList.class);
    private RandomUtils randomUtils = new RandomUtils();
    private HttpSendUtils http = new HttpSendUtils();
    private String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/supportbanklist";
    private String mercUserNo = randomUtils.getRandomString(8);

    /**
     * 查询支持银行列表
     */
    @Test
    public void test001()throws Exception{

        String identificationUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/supportbanklist";
        Map<String, String> transMap = new LinkedHashMap<>();

        transMap.put("platOrderNo", "53190704152138599100010010");
        transMap.put("mercId", AutoConstant.MERC_ID_20019);
        transMap.put("mercUserNo", mercUserNo);
        transMap.put("sign", SignUtilTest.addSign(transMap));

        respMap = http.sendPostRequest(transMap, identificationUrl);
        Assert.assertEquals(respMap.get("retCode"), "0000");

    }
}
