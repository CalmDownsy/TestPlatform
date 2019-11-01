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
 * @ClassName TestQuerySignCheck
 * @Description 签约四要素校验（API模式-头条专用）
 * @Author owen.liu
 * @Date 2019.6.14 10:11
 * @Version 1.0
 */
public class TestQuerySignCheck extends RequestMsgUtils{
    private static Logger logger = LoggerFactory.getLogger(TestQuerySignCheck.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "query/signcheck";
    private String mercId = AutoConstant.MERC_ID_40001;
    //签约四要素校验初始化数据
    public void initData() {
        mapInit();
        infoMap.put("TRX_CODE", "100050");//Y 交易代码 定值
        infoMap.put("VERSION", "2.0");//Y 版本 定值
        infoMap.put("DATA_TYPE", "0");//Y 数据格式 定值
        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG", "signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("MERC_USER_NO", "U" + randomUtils.getDateTime17());//商户用户号
        bodyMap.put("BUSI_AGREEMENT_NO", randomUtils.getRandomTime(20));//Y 签约协议号
        bodyMap.put("CARD_NO", "622848" + randomUtils.getRandomAll(13));//卡号
        bodyMap.put("BANK_ACCOUNT_NAME", randomUtils.getRandomName());//银行账户名称
        bodyMap.put("ID_TYPE", "00");//证件类型
        bodyMap.put("ID_NO", randomUtils.getIdNo());//证件号
        bodyMap.put("BANK_MOBILE_NO", randomUtils.getMobileNo());//银行预留手机号
        bodyMap.put("EXTEND1", "备用1");
        bodyMap.put("EXTEND2", "备用1");
        bodyMap.put("EXTEND3", "备用1");
    }

    @Test
    public void testQuerySignCheck() throws Exception {
        logger.info("开始执行 签约四要素校验（API模式-头条专用）（100050）（merc-gateway-web/query/signcheck） 用例...");
        initData();
        bodyMap.put("MERC_USER_NO", "U20190614102634710");//商户用户号
        bodyMap.put("BUSI_AGREEMENT_NO", "19190614102739856100010010");//Y 签约协议号
        bodyMap.put("CARD_NO", "6228486045903829283");//卡号
        bodyMap.put("BANK_ACCOUNT_NAME", "郭条擐");//银行账户名称
        bodyMap.put("ID_TYPE", "00");//证件类型
        bodyMap.put("ID_NO", "150624196910225115");//证件号
        bodyMap.put("BANK_MOBILE_NO", "13602782462");//银行预留手机号
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrl);
        respMap.put("SIGNED_MSG","");
        logger.info("响应结果：{}",respMap);
        logger.info("判断查询结果？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"), "0000");
        Assert.assertEquals(respMap.get("CHECK_STATUS"), "S");
    }
}
