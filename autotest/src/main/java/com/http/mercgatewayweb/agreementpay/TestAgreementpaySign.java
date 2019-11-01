package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @ClassName TestAgreementpaySign
 * @Description 该类覆盖交易：协议支付-签约获取短信（100021），协议支付-签约（100022），协议支付-解约（100023）
 * @Author owen.liu
 * @Date 2019.6.13 16:53
 * @Version 1.0
 */
public class TestAgreementpaySign extends RequestMsgUtils{

    private static Logger logger = LoggerFactory.getLogger(TestAgreementpaySign.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrlIdentityauth = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/identityauth";
    private String reqUrlSign = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/sign";
    private String reqUrlUnsign = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/unsign";
    //签约获取短验初始化数据
    public void getSignSmsInit(){
        mapInit();
        infoMap.put("TRX_CODE","100021");//Y 交易代码 定值
        infoMap.put("VERSION","2.1");//Y 版本 定值
        infoMap.put("DATA_TYPE","0");//Y 数据格式 定值
        infoMap.put("REQ_SN",randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MERC_USER_NO","U"+randomUtils.getDateTime17());//商户用户号
        bodyMap.put("CARD_NO","622848"+randomUtils.getRandomAll(13));//卡号
        bodyMap.put("BANK_ACCOUNT_NAME",randomUtils.getRandomName());//银行账户名称
        bodyMap.put("ID_TYPE","00");//证件类型
        bodyMap.put("ID_NO",randomUtils.getIdNo());//证件号
        bodyMap.put("BANK_MOBILE_NO",randomUtils.getMobileNo());//银行预留手机号
        bodyMap.put("EXTEND1","备用1");
        bodyMap.put("EXTEND2","备用1");
        bodyMap.put("EXTEND3","备用1");
    }
    //签约初始化数据
    public void signInit(){
        mapInit();
        infoMap.put("TRX_CODE","100022");//Y 交易代码 定值
        infoMap.put("VERSION","2.1");//Y 版本 定值
        infoMap.put("DATA_TYPE","0");//Y 数据格式 定值
        infoMap.put("REQ_SN",randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MBL_CAPTCHA","123456");//短信验证码
        bodyMap.put("EXTEND1","备用1");
        bodyMap.put("EXTEND2","备用1");
        bodyMap.put("EXTEND3","备用1");
    }
    //解约初始化数据
    public void unsignInit(){
        mapInit();
        infoMap.put("TRX_CODE","100023");//Y 交易代码 定值
        infoMap.put("VERSION","2.1");//Y 版本 定值
        infoMap.put("DATA_TYPE","0");//Y 数据格式 定值
        infoMap.put("REQ_SN",randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("SIGN_AGREEMENT_NO","");//签约协议号
        bodyMap.put("EXTEND1","备用1");
        bodyMap.put("EXTEND2","备用1");
        bodyMap.put("EXTEND3","备用1");
    }

    @Test
    public void testSignAndUnsign() throws Exception {
        logger.info("开始执行 协议支付-签约获取短信（100021）（merc-gateway-web/agreementpay/identityauth） 用例...");
        getSignSmsInit();
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlIdentityauth);
        respMap.put("SIGNED_MSG","");
        logger.info("签约获取短验响应：{}",respMap.toString());
        logger.info("判断签约获取短验是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        logger.info("开始执行 协议支付-签约（100022）（merc-gateway-web/agreementpay/sign） 用例...");
        String mercOrderNo = respMap.get("MERC_ORDER_NO");
        String mercOrderDate = respMap.get("MERC_ORDER_DATE");
        signInit();
        bodyMap.put("MERC_ORDER_NO",mercOrderNo);//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",mercOrderDate);//Y 商户订单日期
        bodyMap.put("MBL_CAPTCHA","123456");//短信验证码
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlSign);
        respMap.put("SIGNED_MSG","");
        logger.info("签约响应：{}",respMap.toString());
        logger.info("判断签约是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        logger.info("开始执行 协议支付-解约（100023）（merc-gateway-web/agreementpay/unsign） 用例...");
        String signAgreementNo = respMap.get("SIGN_AGREEMENT_NO");
        unsignInit();
        bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("SIGN_AGREEMENT_NO",signAgreementNo);//签约协议号
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlUnsign);
        respMap.put("SIGNED_MSG","");
        logger.info("解约响应：{}",respMap.toString());
        logger.info("判断解约是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
    }
}
