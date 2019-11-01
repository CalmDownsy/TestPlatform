package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.commonutils.SignUtilTest;
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
 * @Date: 2019/6/14 15:50
 * @Description: 实名订单查询(200015)
 */
public class TestSignOrder extends RequestMsgUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(TestSignOrder.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String mercUserNo = "userno_" + randomUtils.getRandomString(8);
    private String mercOrderNo = randomUtils.getRandomTime(20);
    private String userName = randomUtils.getRandomName();
    private String idNo = randomUtils.getIdNo();

    @BeforeMethod
    public void method_setup() {
        LOGGER.info("组装报文 - setup");

        infoMap.put("TRX_CODE", "200019");//Y
        infoMap.put("VERSION", "2.2");//Y
        infoMap.put("DATA_TYPE", "0");//Y
        infoMap.put("SIGNED_MSG", "signedMsg");//Y

        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y  20181023131739
        bodyMap.put("MERC_ORDER_NO", mercOrderNo);//Y
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//YY
        bodyMap.put("NOTIFY_URL", "http://10.63.13.81:12790/simulator-web/mercservice/signBackNotify");//Y后台通知
        bodyMap.put("RETURN_URL", "http://www.baidu.com");//Y前台通知
        bodyMap.put("EXTEND1", "");//N
        bodyMap.put("EXTEND2", "");//N
        bodyMap.put("EXTEND3", "");//N
    }

    @Test(description = "实名订单查询(200015)")
    public void signOrder() {
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
        LOGGER.info("发送<实名认证>请求");
        respMap = http.sendPostRequest(transMap, identificationUrl);
        LOGGER.info("<实名认证>响应: {}", respMap);
        LOGGER.info("<实名认证>断言");
        Assert.assertEquals(respMap.get("retCode"), "0000");
        LOGGER.info("<实名认证>测试结果: PASS!");

        LOGGER.info("---------------------<实名订单查询>-----------------------");
        String signOrderUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "query/signorder";
        LOGGER.info("初始化infoMap");
        mapInit();
        LOGGER.info("组装<实名订单查询>报文");
        infoMap.put("TRX_CODE", "200015");//Y
        infoMap.put("VERSION", "2.0");//Y
        infoMap.put("DATA_TYPE", "0");//Y
        infoMap.put("SIGNED_MSG", "signedMsg");//Y

        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y
        bodyMap.put("MERC_ORDER_NO", mercOrderNo);
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y
        requestMap = getRequestMap();
        LOGGER.info("发送<实名订单查询>请求");
        respMap = http.sendHttpRequest(requestMap, signOrderUrl);
        LOGGER.info("<实名订单查询>响应: {}", respMap);
        LOGGER.info("<实名订单查询>断言");
        Assert.assertEquals(respMap.get("RET_CODE"), "0000");
        LOGGER.info("<实名订单查询>测试结果: PASS!");

        LOGGER.info("---------------------<实名信息查询>-----------------------");
        String identityUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "query/identity";
        LOGGER.info("初始化infoMap");
        mapInit();
        LOGGER.info("组装<实名信息查询>报文");
        infoMap.put("TRX_CODE","200020");//Y 交易代码
        infoMap.put("VERSION","2.0");//Y 版本
        infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
        infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//商户编号,Y,
        bodyMap.put("MERC_USER_NO", mercUserNo);//商户用户号,Y,用户在商户端的唯一标识
        bodyMap.put("EXTEND1", "");//备用域1
        bodyMap.put("EXTEND2", "");//备用域2
        bodyMap.put("EXTEND3", "");//备用域3
        requestMap = getRequestMap();
        LOGGER.info("发送<实名信息查询>请求");
        respMap = http.sendHttpRequest(requestMap, identityUrl);
        LOGGER.info("<实名信息查询>响应: {}", respMap);
        LOGGER.info("<实名信息查询>断言");
        Assert.assertEquals(respMap.get("RET_CODE"), "0000");
        LOGGER.info("<实名信息查询>测试结果: PASS!");
    }
}
