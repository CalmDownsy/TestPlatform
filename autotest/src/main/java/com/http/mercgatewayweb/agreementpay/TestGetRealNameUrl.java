package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * @Auther: zhangsy
 * @Date: 2019/6/14 13:00
 * @Description: 获取实名URL(200019)
 */
public class TestGetRealNameUrl extends RequestMsgUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(TestGetRealNameUrl.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/getrealnameurl";

    @BeforeMethod
    public void method_setup() {
        LOGGER.info("组装报文 - setup");

        infoMap.put("TRX_CODE", "200019");//Y
        infoMap.put("VERSION", "2.2");//Y
        infoMap.put("DATA_TYPE", "0");//Y
        infoMap.put("SIGNED_MSG", "signedMsg");//Y

        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y
        bodyMap.put("MERC_USER_NO", "userno_" + randomUtils.getRandomString(8));//Y  20181023131739
        bodyMap.put("MERC_ORDER_NO", randomUtils.getRandomTime(20));//Y
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//YY
        bodyMap.put("NOTIFY_URL", "http://10.63.13.81:12790/simulator-web/mercservice/signBackNotify");//Y后台通知
        bodyMap.put("RETURN_URL", "http://www.baidu.com");//Y前台通知
        bodyMap.put("EXTEND1", "");//N
        bodyMap.put("EXTEND2", "");//N
        bodyMap.put("EXTEND3", "");//N
    }

    @Test(description = "获取实名URL(200019)")
    public void getRealNameUrl() {
        LOGGER.info("组装<获取实名认证url>报文");
        requestMap = getRequestMap();
        LOGGER.info("发送<获取实名认证url>请求");
        respMap = http.sendHttpRequest(requestMap, reqUrl);
        LOGGER.info("<获取实名认证url>响应: {}", respMap);
        LOGGER.info("<获取实名认证url>断言");
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        LOGGER.info("<获取实名认证url>测试结果: PASS!");
    }
}
