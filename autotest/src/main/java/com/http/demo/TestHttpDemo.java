package com.http.demo;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import com.ruoyi.common.annotation.AutoTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 示例方法
 *
 * @author yingjie.liu
 * @create 2018-02-28 16:21
 **/
@AutoTest(description = "查询卡宾")
public class TestHttpDemo extends RequestMsgUtils{
    private static Logger logger = LoggerFactory.getLogger(TestHttpDemo.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private Properties prop = randomUtils.getProperties("url.properties");

    @BeforeMethod
    public void setUp() throws Exception {
        logger.info("方法数据初始化...");
    }

    @AfterMethod
    public void tearDown() throws Exception {
        logger.info("方法数据销毁..");
    }
    //测试卡bin查询成功
    @Test(description = "查询卡宾成功")
    public void testCardbinquerySuccess() {
        logger.info("### 开始卡bin查询(100020) 用例执行...");
        String url = prop.getProperty("merc-gateway-web")+"cardbin/cardbinquery";
        logger.info("卡bin查询URL:{}", url);
        infoMap.put("TRX_CODE", "100020");
        infoMap.put("VERSION", "01");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("LEVEL", "0");
        infoMap.put("SIGNED_MSG", "signedMsg");
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);
        bodyMap.put("CARD_NO", "6214830112341234");
        requestMap=getRequestMap();
        Map<String, String> responseMap = http.sendHttpRequest(requestMap, url);
        Assert.assertEquals( "CMB", responseMap.get("BANK_CODE"),"卡bin查询银行简称不符！");
        Assert.assertEquals( "招商银行", responseMap.get("BANK_NAME"),"卡bin查询银行名称不符！");
    }
}


