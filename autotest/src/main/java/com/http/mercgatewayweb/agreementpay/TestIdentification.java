package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.*;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: zhangsy
 * @Date: 2019/6/14 13:49
 * @Description: 实名认证
 */
public class TestIdentification extends RequestMsgUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(TestIdentification.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String mercUserNo = "userno_" + randomUtils.getRandomString(8);
    private String userName = randomUtils.getRandomName();
    private String idNo = randomUtils.getIdNo();
    private String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVOt0u8IFdOQWFcQew/I3p+euhSI3M8vdf5ILbkmXVimmRM1mNSNp4ETZRHd1g6+RdPl7ZS+/5gdf9YEkWdkAbcyvBsAQwKYI7IPNhk7YhONF8WpBp4szrM4G45zH+v6JbuA92eSCG66nErp/OWuBid6wl+7kPRs9PnXENF0kHwf3nzDdM6adcIx89y9qIXhJjXG3av8E9HmUDxxPoJJB2BIIjnLxSiISvk5+eNyP+zXTOuvYcRPpn0sXodFiwllR9bmbBYNi6/M30mKtVKMNzsolQ6kMWsi/SkachTP1Dvi4gySQmUZlkl64W2skUMUBJz6Omz5MbUzPCvU4xTHx3AgMBAAECggEAXdYn4y5EilQizqgmh/onWqtWqZIv6GIXf2r3hg5mjoJ8o1tAwoC/L6TTHOrEm/95F9DyX31KpwAoyyDSlJdC9H/VUfPWjlprk7bCk+cILZfG0oU4Lfz5kiRPO+/VFjV2aGoy/vJnq69gj1anEmE+m2xZCYFh+uMx1QRRZEPyHje8QMImeKYtLUKaphe6xqW2EKeH9p4p5+6o0i/9m3fNiQ32OZjg5f4R8q41eWv932/QaC0xSStMlKu72FIOzPtcgIwZ6nqDPG0UqFtELAd8dAV1D2JWc/kvuTCLOKhoSWKH2fgFqzqxIWF8dPu0tnnNm24GDQOSA/z6LnNLHuJEMQKBgQDc/H5Lc5Is7F241L6YtIx8K9RGDHtOf8O4bbSIo4xTUZz5wONoNVScNA8BRYSNrE9Ue8nWqyv4exO2EuTNake9BGteTaTazc/LPTwyhmk8buy6GPNhMpA35U6ckyuuPGW39i4bESDk0PKkAL1mGySoR5uFiXpnT+p2GVmIKDuD/QKBgQCs39WZFi3IqaTsgAWPqZktN87FR/lHWpdJeS5jSlNtdFg7DEUMNcJjjTi0tleFvVFQMcJpSe7dxUkEQSrDv5poPJCEPi76txdpIdY5uNiyHwNaIMWvBlBWXE60eRxYeOwXHlGiv+I4PDZEOoQ/ibm2LeOmRusy7cSqjo6KTIVagwKBgQCJaNtjMT7Qfj0cw/Sr4YCFaX95+yLlQd7CnDyoxgFogZP5XK5KyvaMlnZKFR8CNQWzA7ISsKcLwAQWsBLjg9WNu+TDY8ZBY750ouP+isN/F35NjVwwsQ8qIoTKKVz834Nka0hp/jGU9jQaNHeQuQoHNVeIJl8dRH0G6GZamqG/RQKBgHZyx3+k2qnq0r3Y2fadVf7NhjdWIU7qm04UYdxWSONuHCYZOhYrn41mbbwkU8Om+9fewD40Eh6R1n27yPlmhv10wfBaSwYt/TRjoszaAl/JMBrI6aoKwXDqvmKZuPpFoD4W4AiLQQ0W9He7QHHog/53YsvDO9QhZecW1kOcOqLRAoGAJQ0GZh7H7OZCHS2HJPsOH64eZah3pZ7DlDEeQXfLiGps2BHsFu9cxpTPLlSmjddYG1Fov7m7yWv/xDR43u/WnaIDqXh8otU0C/pNOWIaWY/fSexWMxSjwXw1pYM01UsyHWBlORLAojYmCLO0x4Vsk8aduVjm/e/oTnEgz9lTkcI=";

    @BeforeMethod
    public void method_setup() {
        LOGGER.info("组装报文 - setup");

        infoMap.put("TRX_CODE", "200019");//Y
        infoMap.put("VERSION", "2.2");//Y
        infoMap.put("DATA_TYPE", "0");//Y
        infoMap.put("SIGNED_MSG", "signedMsg");//Y

        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y  20181023131739
        bodyMap.put("MERC_ORDER_NO", randomUtils.getRandomTime(20));//Y
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//YY
        bodyMap.put("NOTIFY_URL", "http://10.63.13.81:12790/simulator-web/mercservice/signBackNotify");//Y后台通知
        bodyMap.put("RETURN_URL", "http://www.baidu.com");//Y前台通知
        bodyMap.put("EXTEND1", "");//N
        bodyMap.put("EXTEND2", "");//N
        bodyMap.put("EXTEND3", "");//N
    }

    @Test(description = "实名认证")
    public void identification() throws Exception {
        LOGGER.info("---------------------<获取实名认证url>-----------------------");
        String getRealNameUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/getrealnameurl";
        LOGGER.info("组装<获取实名认证url>报文");
        requestMap = getRequestMap();
        LOGGER.info("发送<获取实名认证url>请求");
        respMap = http.sendHttpRequest(requestMap, getRealNameUrl);
        LOGGER.info("<获取实名认证url>响应: {}", respMap);
        LOGGER.info("<获取实名认证url>断言");
        Assert.assertEquals(respMap.get("RET_CODE"), "0000");
        LOGGER.info("<获取实名认证url>测试结果: PASS!");
        String platOrderNo = randomUtils.getParamByUrl(respMap.get("PAY_URL"), "sid");

        LOGGER.info("---------------------<实名认证>-----------------------");
        String identificationUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/identification";
        Map<String, String> transMap = new LinkedHashMap<>();
        LOGGER.info("组装<实名认证>报文");
        transMap.put("mercId", AutoConstant.MERC_ID_40001);
        transMap.put("mercUserNo", mercUserNo);
        transMap.put("sid", platOrderNo);
        transMap.put("sign", SignUtilTest.addSign(transMap));
        transMap.put("userName", userName);
        transMap.put("idNo", idNo);
        LOGGER.info("发送<实名认证>请求: {}", transMap);
        respMap = http.sendPostRequest(transMap, identificationUrl);
        LOGGER.info("<实名认证>响应: {}", respMap);
        LOGGER.info("<实名认证>断言");
        Assert.assertEquals(respMap.get("retCode"), "0000");
        LOGGER.info("<实名认证>测试结果: PASS!");

        LOGGER.info("---------------------<实名校验初始化（找回支付密码时调用）>-----------------------");
        String queryUserInfoUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/queryuserinfo";
        transMap.clear();
        LOGGER.info("组装<实名校验初始化>报文");
        transMap.put("mercId", AutoConstant.MERC_ID_40001);
        transMap.put("mercUserNo", mercUserNo);//商户号
        transMap.put("reqSn", platOrderNo);
        String strData = SignUtilTest.encode2Form(transMap);
        byte[] encodedData = strData.getBytes();
        String sign = RSAUtils.sign(encodedData, privateKey);
        transMap.put("sign", sign);
        LOGGER.info("发送<实名校验初始化>请求: {}", transMap);
        respMap = http.sendPostRequest(transMap, queryUserInfoUrl);
        LOGGER.info("<实名认证>响应: {}", respMap);
        LOGGER.info("<实名认证>断言");
        Assert.assertEquals(respMap.get("retCode"), "0000");
        LOGGER.info("<实名认证>测试结果: PASS!");

        LOGGER.info("---------------------<实名校验（找回支付密码时调用）>-----------------------");
        String checkUserInfoUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/checkuserinfo";
        transMap.clear();
        LOGGER.info("组装<实名校验>报文");
        transMap.put("mercId", AutoConstant.MERC_ID_40001);
        transMap.put("mercUserNo", mercUserNo);//商户号
        transMap.put("reqSn", platOrderNo);
        strData = SignUtilTest.encode2Form(transMap);
        encodedData = strData.getBytes();
        sign = RSAUtils.sign(encodedData, privateKey);
        transMap.put("sign", sign);
        transMap.put("userName", userName);
        transMap.put("idNo", idNo);
        LOGGER.info("发送<实名校验>请求: {}", transMap);
        respMap = http.sendPostRequest(transMap, checkUserInfoUrl);
        LOGGER.info("<实名校验>响应: {}", respMap);
        LOGGER.info("<实名校验>断言");
        Assert.assertEquals(respMap.get("retCode"), "0000");
        LOGGER.info("<实名校验>测试结果: PASS!");
    }
}
