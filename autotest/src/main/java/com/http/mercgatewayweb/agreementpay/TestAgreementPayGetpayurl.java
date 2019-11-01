package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.commonutils.SignUtilTest;
import com.interfacetest.constants.AutoConstant;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

/**
 * @ClassName TestAgreementPayGetpayurl
 * @Description 该类覆盖交易：获取签约并支付URL(200014)
 * @Author zhuyh
 * @Date 2019.6.14 18:53
 * @Version 1.0
 */

public class TestAgreementPayGetpayurl extends RequestMsgUtils{
	
    private static Logger logger = LoggerFactory.getLogger(TestAgreementPayGetpayurl.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    Map<String, String> paramsSms = new LinkedHashMap<String, String>();
    Map<String, String> paramsCardPay = new LinkedHashMap<String, String>();
    public ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();
    private String reqUrlGetpayurl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/getpayurl";
    private String reqUrlPaysms = randomUtils.getProperties("url.properties").getProperty("merc-front-web") + "agreementpay/paysms";
    private String reqUrlBindcardPay = randomUtils.getProperties("url.properties").getProperty("merc-front-web") + "agreementpay/bindcardpay";

    //获取签约并支付URL初始化数据
    public void getGetPayUrlInit() throws Exception{
        mapInit();
        infoMap.put("TRX_CODE","200014");//Y 交易代码 定值
        infoMap.put("VERSION","2.2");//Y 版本 定值
        infoMap.put("DATA_TYPE","0");//Y 数据格式 定值
        infoMap.put("REQ_SN",randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MERC_USER_NO","U"+randomUtils.getDateTime17());//商户用户号
        bodyMap.put("CARD_NO","622848"+randomUtils.getRandomAll(13));//卡号
        bodyMap.put("BANK_ACCOUNT_NAME","");//银行账户名称
        bodyMap.put("ID_TYPE","00");//证件类型
        bodyMap.put("ID_NO","");//证件号
        bodyMap.put("PLACE_ORDER_IP","10.2.1.3");//下单IP
        bodyMap.put("NOTIFY_URL","http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify");//Y 后台通知地址
        bodyMap.put("RETURN_URL","http://www.baidu.com");//Y 前台通知地址
        bodyMap.put("TRANS_AMT","0.2");//交易金额
        bodyMap.put("BUSINESS_CATEGORY","100001");//业务种类
        bodyMap.put("CURRENCY","CNY");//币种CNY
        bodyMap.put("ORDER_EXP_DATE","20970724101010");//订单过期时间
        bodyMap.put("SPLIT_FLAG","2");//分账标识 1-分、2-不分
        bodyMap.put("SPLIT_FORMULA","");//分账公式
        bodyMap.put("ASSURE_FLAG","2");//担保标志 1-担保、2-不担保
        bodyMap.put("ORDER_DETAILS","3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
        bodyMap.put("TERMINAL_TYPE","01");//终端类型
        bodyMap.put("TERMINALID",randomUtils.getRandomTime(12));//终端编号
        bodyMap.put("DEVICE_INFO","10.10.0.1");//交易设备信息
        bodyMap.put("RISK_INFO", "{\"subMercNo\": \"1000001\",\"subMercName\": \"小霸王\",\"subMercTradeCategory\": \"\",\"mercUserRegDate\": \"100\",\"mercUserActiveDays\": \"99\",\"mercUserActiveLevel\":\"1\",\"mercUserRegMobile\":\"13112345678\"}");//风控扩展字段		
        bodyMap.put("PAY_PASSWORD","");//支付密码
        bodyMap.put("MERGE_FLAG","2");//合单标识
        bodyMap.put("SIGN_MERC_ID","");//签约商户号
        bodyMap.put("PAGE_TYPE","");//页面类型
        bodyMap.put("CARD_SOURCE","");//卡来源
        bodyMap.put("BIND_CARD_ID","");//绑卡id
        bodyMap.put("ALLOW_TRANS_CARD_TYPE","");//允许交易的卡类型
        bodyMap.put("BUSINESS_SCENE", "");//1：消费（默认）2：红包发放
        /*子单信息*/
//        Map<String,String> TRANS_DETAILContent1 = new LinkedHashMap<String, String>();
//    	Map<String,Object> TRANS_DETAIL1 = new LinkedHashMap<String, Object>();
//		TRANS_DETAILContent1.put("SUB_MERC_ORDER_NO", "1");//子订单编号
//		TRANS_DETAILContent1.put("BELONG_MERC_ID", "");//分账方、主商户都可以
//		TRANS_DETAILContent1.put("SUB_TRANS_AMT", "320");//子订单交易金额
//		TRANS_DETAILContent1.put("SUB_NOTIFY_URL", "http://10.63.13.31:12790/simulator-web/mercservice/backNotify");//子订单交易金额
//		TRANS_DETAILContent1.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
//		String splitFormula1 = "2,800025000100028,金融开户-金融类one,10,分账说明1|1,140000001361,邹国平一九零四三零零一,10,分账说明2|2,700000000392036,企业名称zyh1,10,分账说明3|2,700000000392039,企业名称zyh4,290,分账说明4";
//		TRANS_DETAILContent1.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula1.getBytes("utf-8")));//子订单分账公式
//		TRANS_DETAIL1.put("TRANS_DETAIL",TRANS_DETAILContent1);
//		TRANS_DETAILS.add(TRANS_DETAIL1);
        
        bodyMap.put("EXTEND1","备用1");
        bodyMap.put("EXTEND2","备用1");
        bodyMap.put("EXTEND3","备用1");
    }
    //签约并支付发送短信初始化数据
    public void signPaySmsInit(){
//        mapInit();
//        paramsSms.put("platOrderNo", randomUtils.getRandomTime(20));
//        paramsSms.put("mercUserNo", "U"+randomUtils.getDateTime17());
//        paramsSms.put("mercId", AutoConstant.MERC_ID_40001);
//        
    } 
    //绑卡签约初始化数据
    public void bindCardPayInit(){
//        mapInit();
//        paramsCardSign.put("platOrderNo", randomUtils.getRandomTime(20));
//        paramsCardSign.put("mercId", AutoConstant.MERC_ID_40001);
//        paramsCardSign.put("mercUserNo", "U"+randomUtils.getDateTime17());
//        String merchantSignCardSign = SignUtilTest.addSign(paramsCardSign);
//        paramsCardSign.put("sign", merchantSignCardSign);
//        paramsCardSign.put("smsCode", "123456");//
//        paramsCardSign.put("token", "20181022194929123100010000");//
    }    
    /*普通交易-不担保、不分账、不合单*/
    @Test
    public void testGetPayUrl_Ordinary() throws Exception {
    	logger.info("###  开始执行 获取签约并支付URL(200014)（merc-gateway-web/agreementpay/getpayurl） 用例...");
    	getGetPayUrlInit();	
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlGetpayurl);
        logger.info(" 获取签约并支付URL响应：{}",respMap.toString());
        logger.info("判断 获取签约并支付URL是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        String payUrl = respMap.get("PAY_URL").toString();
        System.out.println(payUrl);
        String platOrderNo = payUrl.substring(payUrl.indexOf("&")+13).substring(0,26);
        String userNo = payUrl.substring(payUrl.indexOf("mercUserNo=")+11).substring(0,18);
        
        System.out.println(platOrderNo);
        System.out.println(userNo);
        logger.info("###  开始执行 签约并支付发送短信（merc-gateway-web/agreementpay/paysms） 用例...");
    	/*签约发送短信请求参数*/
//        signPaySmsInit();
        paramsSms.put("platOrderNo", platOrderNo);
        paramsSms.put("mercId", AutoConstant.MERC_ID_40001);
        paramsSms.put("mercUserNo", userNo);
        String merchantSignSms = SignUtilTest.addSign(paramsSms);
        paramsSms.put("sign", merchantSignSms);

        paramsSms.put("userName", randomUtils.getRandomName());
        paramsSms.put("idNo", randomUtils.getIdNo());
        paramsSms.put("idType", "00");
        paramsSms.put("cardNo", "622848"+randomUtils.getRandomAll(13));
        paramsSms.put("bankMobileNo", randomUtils.getMobileNo());
                
        Map<String, String> responseSms = http.sendPostRequest(paramsSms, reqUrlPaysms);
        logger.info("签约发送短信响应：{}",responseSms.toString());
        logger.info("判断签约发送短信是否成功？返回码：{},返回信息：{}",responseSms.get("retCode"),responseSms.get("retMsg"));
        Assert.assertEquals(responseSms.get("retCode"),"0000");
        
        String token = responseSms.get("token");
        logger.info("###  开始执行 绑卡并支付（merc-gateway-web/agreementpay/bindcardpay） 用例...");
        /*绑卡签约请求参数*/
//        bindCardPayInit();
        System.out.println(platOrderNo);
        System.out.println(userNo);
        paramsCardPay.put("platOrderNo", platOrderNo);
        paramsCardPay.put("mercId", AutoConstant.MERC_ID_40001);
        paramsCardPay.put("mercUserNo", userNo);
        String merchantSignCardSign = SignUtilTest.addSign(paramsCardPay);
        paramsCardPay.put("sign", merchantSignCardSign);
        paramsCardPay.put("token", token);//状态
        paramsCardPay.put("smsCode", "123456");//状态
        
        Map<String, String> bindCardSignrespMap = http.sendPostRequest(paramsCardPay,reqUrlBindcardPay);
        logger.info("绑卡并支付响应：{}",bindCardSignrespMap.toString());
        logger.info("判断绑卡并支付是否成功？返回码：{},返回信息：{}",bindCardSignrespMap.get("retCode"),bindCardSignrespMap.get("retMsg"));
        Assert.assertEquals(bindCardSignrespMap.get("retCode"),"0000");
        
    }

    /*发红包交易-不担保、不分账、不合单*/
    @Test
    public void testGetPayUrl_RedPackage() throws Exception {
        logger.info("###  开始执行 获取签约并支付URL(200014)（merc-gateway-web/agreementpay/getpayurl） 用例...");
        infoMap.clear();
        bodyMap.clear();
        transSumMap.clear();
        transDetailMap.clear();
        transDetailsMap.clear();
        transDetailsXml = null;
        aipgMap.clear();
        requestMap.clear();
        respMap.clear();
        paramsSms.clear();
        paramsCardPay.clear();
        TRANS_DETAILS.clear();

        getGetPayUrlInit();
        infoMap.put("VERSION","2.3");//Y 版本 定值
        bodyMap.put("BUSINESS_SCENE", "2");//1：消费（默认）2：红包发放
        bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
        bodyMap.put("MERC_USER_NO","ZGP20190125143220262");//商户用户号
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlGetpayurl);
        logger.info(" 获取签约并支付URL响应：{}",respMap.toString());
        logger.info("判断 获取签约并支付URL是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        String payUrl = respMap.get("PAY_URL").toString();
        logger.info("payUrl="+payUrl);
        String platOrderNo = payUrl.substring(payUrl.indexOf("&")+13).substring(0,26);
        String userNo = "ZGP20190125143220262";

        logger.info("platOrderNo="+platOrderNo);
        logger.info("mercUserNo="+userNo);
        logger.info("mercOrderNo="+bodyMap.get("MERC_ORDER_NO"));

        logger.info("###  开始执行 签约并支付发送短信（merc-gateway-web/agreementpay/paysms） 用例...");
    	/*签约发送短信请求参数*/
//        signPaySmsInit();
        paramsSms.put("platOrderNo", platOrderNo);
        paramsSms.put("mercId", "800010000020019");
        paramsSms.put("mercUserNo", userNo);
        String merchantSignSms = SignUtilTest.addSign(paramsSms);
        paramsSms.put("sign", merchantSignSms);

//        paramsSms.put("userName", "全渠道一");
//        paramsSms.put("idNo", "341126197709218366");
//        paramsSms.put("idType", "00");
        paramsSms.put("cardNo", "622848"+randomUtils.getRandomAll(13));
        paramsSms.put("bankMobileNo", randomUtils.getMobileNo());

        Map<String, String> responseSms = http.sendPostRequest(paramsSms, reqUrlPaysms);
        logger.info("签约发送短信响应：{}",responseSms.toString());
        logger.info("判断签约发送短信是否成功？返回码：{},返回信息：{}",responseSms.get("retCode"),responseSms.get("retMsg"));
        Assert.assertEquals(responseSms.get("retCode"),"0000");

        String token = responseSms.get("token");
        logger.info("###  开始执行 绑卡并支付（merc-gateway-web/agreementpay/bindcardpay） 用例...");
        /*绑卡签约请求参数*/
//        bindCardPayInit();
        System.out.println(platOrderNo);
        System.out.println(userNo);
        paramsCardPay.put("platOrderNo", platOrderNo);
        paramsCardPay.put("mercId", "800010000020019");
        paramsCardPay.put("mercUserNo", userNo);
        String merchantSignCardSign = SignUtilTest.addSign(paramsCardPay);
        paramsCardPay.put("sign", merchantSignCardSign);
        paramsCardPay.put("token", token);//状态
        paramsCardPay.put("smsCode", "123456");//状态

        Map<String, String> bindCardSignrespMap = http.sendPostRequest(paramsCardPay,reqUrlBindcardPay);
        logger.info("绑卡并支付响应：{}",bindCardSignrespMap.toString());
        logger.info("判断绑卡并支付是否成功？返回码：{},返回信息：{}",bindCardSignrespMap.get("retCode"),bindCardSignrespMap.get("retMsg"));
        Assert.assertEquals(bindCardSignrespMap.get("retCode"),"0000");
    }

    /*营销交易-不担保、不分账、不合单*/
    @Test
    public void testGetPayUrl_marketing() throws Exception {
        logger.info("###  开始执行 获取签约并支付URL(200014)（merc-gateway-web/agreementpay/getpayurl） 用例...");
        infoMap.clear();
        bodyMap.clear();
        transSumMap.clear();
        transDetailMap.clear();
        transDetailsMap.clear();
        transDetailsXml = null;
        aipgMap.clear();
        requestMap.clear();
        respMap.clear();
        paramsSms.clear();
        paramsCardPay.clear();
        TRANS_DETAILS.clear();

        getGetPayUrlInit();
        infoMap.put("VERSION","2.3");//Y 版本 定值
        bodyMap.put("TRANS_AMT", "10");
        bodyMap.put("BUSINESS_SCENE", "1");//1：消费（默认）2：红包发放
        bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
        bodyMap.put("MERC_USER_NO","ZGP20190125143220262");//商户用户号
        bodyMap.put("MARKETING_AMT", "3");//营销金额  ,未上送或上送金额为0时，即非营销交易，不校验营销公式
        String marketingFormula = "多营销户测试#800010000020020,1|800010000020019,2";
        bodyMap.put("MARKETING_FORMULA", Base64.encodeBase64String(marketingFormula.getBytes("utf-8")));//营销公式

        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlGetpayurl);
        logger.info(" 获取签约并支付URL响应：{}",respMap.toString());
        logger.info("判断 获取签约并支付URL是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        String payUrl = respMap.get("PAY_URL").toString();
        logger.info("payUrl="+payUrl);
        String platOrderNo = payUrl.substring(payUrl.indexOf("&")+13).substring(0,26);
        String userNo = "ZGP20190125143220262";

        logger.info("platOrderNo="+platOrderNo);
        logger.info("mercUserNo="+userNo);
        logger.info("mercOrderNo="+bodyMap.get("MERC_ORDER_NO"));

        logger.info("###  开始执行 签约并支付发送短信（merc-gateway-web/agreementpay/paysms） 用例...");
    	/*签约发送短信请求参数*/
//        signPaySmsInit();
        paramsSms.put("platOrderNo", platOrderNo);
        paramsSms.put("mercId", "800010000020019");
        paramsSms.put("mercUserNo", userNo);
        String merchantSignSms = SignUtilTest.addSign(paramsSms);
        paramsSms.put("sign", merchantSignSms);

//        paramsSms.put("userName", "全渠道一");
//        paramsSms.put("idNo", "341126197709218366");
//        paramsSms.put("idType", "00");
        paramsSms.put("cardNo", "622848"+randomUtils.getRandomAll(13));
        paramsSms.put("bankMobileNo", randomUtils.getMobileNo());

        Map<String, String> responseSms = http.sendPostRequest(paramsSms, reqUrlPaysms);
        logger.info("签约发送短信响应：{}",responseSms.toString());
        logger.info("判断签约发送短信是否成功？返回码：{},返回信息：{}",responseSms.get("retCode"),responseSms.get("retMsg"));
        Assert.assertEquals(responseSms.get("retCode"),"0000");

        String token = responseSms.get("token");
        logger.info("###  开始执行 绑卡并支付（merc-gateway-web/agreementpay/bindcardpay） 用例...");
        /*绑卡签约请求参数*/
//        bindCardPayInit();
        System.out.println(platOrderNo);
        System.out.println(userNo);
        paramsCardPay.put("platOrderNo", platOrderNo);
        paramsCardPay.put("mercId", "800010000020019");
        paramsCardPay.put("mercUserNo", userNo);
        String merchantSignCardSign = SignUtilTest.addSign(paramsCardPay);
        paramsCardPay.put("sign", merchantSignCardSign);
        paramsCardPay.put("token", token);//状态
        paramsCardPay.put("smsCode", "123456");//状态

        Map<String, String> bindCardSignrespMap = http.sendPostRequest(paramsCardPay,reqUrlBindcardPay);
        logger.info("绑卡并支付响应：{}",bindCardSignrespMap.toString());
        logger.info("判断绑卡并支付是否成功？返回码：{},返回信息：{}",bindCardSignrespMap.get("retCode"),bindCardSignrespMap.get("retMsg"));
        Assert.assertEquals(bindCardSignrespMap.get("retCode"),"0000");
    }
}
