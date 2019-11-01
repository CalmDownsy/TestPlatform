package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName TestCollectionpay
 * @Description 代收业务：
 * @Author owen.liu
 * @Date 2019.7.16 10:19
 * @Version 1.0
 */
public class TestCollectionpay extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestCollectionpay.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    Map<String, String> paramsSms = new LinkedHashMap<String, String>();
    Map<String, String> paramsCardSign = new LinkedHashMap<String, String>();
    private String reqUrlSignsms = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "collectionpay/signsms";
    private String reqUrlSign = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "collectionpay/sign";
    private String reqUrlUnsign = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "collectionpay/unsign";
    private String reqUrlPayment = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "collectionpay/payment";
    private static final String mercId = AutoConstant.MERC_ID_20019;
    @BeforeGroups(groups = {"signsms"})
    public void setUpSignsms() throws Exception {
        logger.info("1.签约获取短验初始化");
        mapInit();
        infoMap.put("TRX_CODE", "10073");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", randomUtils.getRandomTime(32));
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", "z" + randomUtils.getDateTime17());
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("MERC_USER_NO", "z" + randomUtils.getDateTime17());
        bodyMap.put("ACCOUNT_TYPE", "1"); // 1:银行卡，2:存折，3:对公账户
        bodyMap.put("ACCOUNT_NO", ""); // 银行卡、存折号或者对公账号
        bodyMap.put("ACCOUNT_NAME", ""); // 银行卡、存折或者对公账户的所有人姓名。
        bodyMap.put("ID_TYPE", ""); //取值范围：00:身份证,05:港、澳居民往来内地通行证,06:台湾居民来往大陆通行证,07:护照
        bodyMap.put("ID_NO", "");
        bodyMap.put("BANK_MOBILE_NO", "");
        bodyMap.put("BUSI_PROTOCOL_NO", "");
        bodyMap.put("BUSINESS_CATEGORY", "");
        bodyMap.put("PROTOCOL_BEGIN_TIME", ""); //协议开始日期
        bodyMap.put("PROTOCOL_END_TIME", ""); //协议结束日期
        bodyMap.put("MIN_LIMIT_AMT", ""); //代收最低限额
        bodyMap.put("MAX_LIMIT_AMT", ""); //代收最高限额
        bodyMap.put("COLLECTION_CYCLE", ""); //代收扣款周期 D:日,W:周,M:月,Q:季,Y:年
        bodyMap.put("COLLECTION_FREQUENCY", ""); //代收扣款频次 不大于9999
        bodyMap.put("EXTEND1", "");
        bodyMap.put("EXTEND2", "");
        bodyMap.put("EXTEND3", "");
    }

    @AfterGroups(groups = {"signsms"})
    public void tearDownSignsms() throws Exception {

    }

    @BeforeGroups(groups = {"sign"})
    public void setUpSign() throws Exception {
        logger.info("2.签约初始化");
        mapInit();
        infoMap.put("TRX_CODE", "10074");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", randomUtils.getRandomTime(30));
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", "");//调用签约短验时上送的商户订单号
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//调用签约短验时上送的商户订日期
        bodyMap.put("SMS_CODE", "");//做签约短验时收到的短信
        bodyMap.put("MERC_USER_NO", "");//用户在商户端的唯一标识
        bodyMap.put("TRANS_JRN_NO", "");//签约发短信返回的token
        bodyMap.put("EXTEND1", "");
        bodyMap.put("EXTEND2", "");
        bodyMap.put("EXTEND3", "");
    }

    @AfterGroups(groups = {"sign"})
    public void tearDownSign() throws Exception {

    }

    @BeforeGroups(groups = {"unsign"})
    public void setUpUnsign() throws Exception {
        logger.info("3.解约初始化");
        mapInit();
        infoMap.put("TRX_CODE", "10075");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", "R"+randomUtils.getRandomTime(30));
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", "");
        bodyMap.put("MERC_ORDER_DATE", "");
        bodyMap.put("MERC_USER_NO", "");
        bodyMap.put("MERC_AGREEMENT_NO", "");
        bodyMap.put("EXTEND1", "");
        bodyMap.put("EXTEND2", "");
        bodyMap.put("EXTEND3", "");

    }

    @AfterGroups(groups = {"unsign"})
    public void tearDownUnsign() throws Exception {

    }

    @BeforeGroups(groups = {"payment"})
    public void setUpPayment() throws Exception {
        logger.info("4.代收初始化");
        mapInit();
        infoMap.put("TRX_CODE", "10076");
        infoMap.put("VERSION", "2.2");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", "R"+randomUtils.getRandomTime(30));
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", "");
        bodyMap.put("MERC_ORDER_DATE", "");
        bodyMap.put("NOTIFY_URL", "");//后台通知地址
        bodyMap.put("TRANS_AMT", "");
        bodyMap.put("BUSINESS_CATEGORY", "");//业务种类
        bodyMap.put("MERC_AGREEMENT_NO", "");//商户代收协议号
        bodyMap.put("CURRENCY", "");
        bodyMap.put("SPLIT_FLAG", "");
        bodyMap.put("SPLIT_FORMULA", "");
        bodyMap.put("ORDER_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
        bodyMap.put("MERC_USER_NO", "");
        bodyMap.put("EXTEND1", "");
        bodyMap.put("EXTEND2", "");
        bodyMap.put("EXTEND3", "");
    }

    @AfterGroups(groups = {"payment"})
    public void tearDownPayment() throws Exception {

    }

    @Test(groups = {"signsms"})
    public void testSignSms() {
        logger.info("1.签约获取短验");
        bodyMap.put("ACCOUNT_TYPE", "1"); // 1:银行卡，2:存折，3:对公账户
        bodyMap.put("ACCOUNT_NO","6228481098503362671"); // 银行卡、存折号或者对公账号 randomUtils.getCardNo("ABC")
        bodyMap.put("ACCOUNT_NAME", "张三"); // 银行卡、存折或者对公账户的所有人姓名。 randomUtils.getRandomName(
        bodyMap.put("ID_TYPE", "00"); //取值范围：00:身份证,05:港、澳居民往来内地通行证,06:台湾居民来往大陆通行证,07:护照
        bodyMap.put("ID_NO","361329198212193000" );//randomUtils.getIdNo()
        bodyMap.put("BANK_MOBILE_NO", "13821843509");
        bodyMap.put("BUSI_PROTOCOL_NO", "B"+randomUtils.getDateTime17());
        bodyMap.put("BUSINESS_CATEGORY", "110001");//110001-水电煤缴费
        bodyMap.put("PROTOCOL_BEGIN_DATE", "20190716"); //协议开始日期
        bodyMap.put("PROTOCOL_END_DATE", "20290716"); //协议结束日期
        bodyMap.put("MIN_LIMIT_AMT", "1"); //代收最低限额
        bodyMap.put("MAX_LIMIT_AMT", "1000"); //代收最高限额
        bodyMap.put("COLLECTION_CYCLE", "Y"); //代收扣款周期 D:日,W:周,M:月,Q:季,Y:年
        bodyMap.put("COLLECTION_FREQUENCY", "5"); //代收扣款频次 不大于9999
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, reqUrlSignsms);
        logger.info("### 响应结果respMap:{}", respMap);
    }

    @Test(groups = {"sign"})
    public void testSign() {
        logger.info("2.签约");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", "z20190722194655606");//调用签约短验时上送的商户订单号
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//调用签约短验时上送的商户订日期
        bodyMap.put("SMS_CODE", "123456");//做签约短验时收到的短信
        bodyMap.put("MERC_USER_NO", "");//用户在商户端的唯一标识 N
        bodyMap.put("TRANS_JRN_NO", "48190722194700287100010000");//签约发短信返回的token
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, reqUrlSign);
        logger.info("### 响应结果respMap:{}", respMap);
    }

    @Test(groups = {"unsign"})
    public void testUnsign() {
        logger.info("3.解约");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", "z"+randomUtils.getRandomTime(20));
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("MERC_USER_NO", "");//有的话验证
        bodyMap.put("MERC_AGREEMENT_NO", "B20190722194655842");//商户代收协议号
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, reqUrlUnsign);
        logger.info("### 响应结果respMap:{}", respMap);
    }

    //借记卡
    @Test(groups = {"payment"})
    public void testPayment() throws UnsupportedEncodingException {
        logger.info("4.代收");
        bodyMap.put("MERCHANT_ID", mercId);
//        bodyMap.put("MERCHANT_ID", "800010000020019");
        bodyMap.put("MERC_ORDER_NO", "z"+randomUtils.getRandomTime(20));
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("NOTIFY_URL", "http://10.63.13.11:12790/simulator-web/mercservice/backNotify");//后台通知地址
        bodyMap.put("TRANS_AMT", "50");
        bodyMap.put("BUSINESS_CATEGORY", "110001");//业务种类
        bodyMap.put("MERC_AGREEMENT_NO", "B20190722194655842");//商户代收协议号 B20190717200101977
        bodyMap.put("CURRENCY", "CNY");//N 默认为CNY
        bodyMap.put("SPLIT_FLAG", "2");
//        String splitFormula = "2,800025000100028,金融开户-金融类one,30,分账说明5|2,700000000392039,企业名称zyh4,10,分账说明1|1,140000001361,邹国平一九零四三零零一,10,分账说明3";
//        String splitFormula = "2,800010000100012,业务测试,30,分账说明5|2,700010000392022,企业名称一九零四一七一零,10,分账说明1|1,140000001567,账单一九零六二七零二,10,分账个人";
//        bodyMap.put("SPLIT_FORMULA", Base64.encodeBase64String(splitFormula.getBytes("utf-8")));//分账公式

        bodyMap.put("ORDER_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
        bodyMap.put("MERC_USER_NO", "z20190722194655606");
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, reqUrlPayment);
        logger.info("### 响应结果respMap:{}", respMap);
    }

    //贷记卡
    @Test(groups = {"payment"})
    public void testPayment01() throws UnsupportedEncodingException {
        logger.info("4.代收");
        bodyMap.put("MERCHANT_ID", mercId);
//        bodyMap.put("MERCHANT_ID", "800010000020019");
        bodyMap.put("MERC_ORDER_NO", "z"+randomUtils.getRandomTime(20));
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("NOTIFY_URL", "http://10.63.13.11:12790/simulator-web/mercservice/backNotify");//后台通知地址
        bodyMap.put("TRANS_AMT", "50");
        bodyMap.put("BUSINESS_CATEGORY", "110001");//业务种类
        bodyMap.put("MERC_AGREEMENT_NO", "B20190719110318064");//商户代收协议号 B20190717200101977
        bodyMap.put("CURRENCY", "CNY");//N 默认为CNY
        bodyMap.put("SPLIT_FLAG", "1");
        String splitFormula = "2,800010000100012,业务测试,30,分账说明5|2,700000000392022,企业名称一九零四一七一零,10,分账说明1|1,140000001567,账单一九零六二七零二,10,分账个人";
        bodyMap.put("SPLIT_FORMULA", Base64.encodeBase64String(splitFormula.getBytes("utf-8")));//分账公式

        bodyMap.put("ORDER_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
        bodyMap.put("MERC_USER_NO", "z20190719110317878");
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, reqUrlPayment);
        logger.info("### 响应结果respMap:{}", respMap);
    }
}
