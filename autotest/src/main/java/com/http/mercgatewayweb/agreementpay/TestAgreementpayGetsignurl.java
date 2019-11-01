package com.http.mercgatewayweb.agreementpay;

import com.alibaba.fastjson.JSONObject;
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName TestAgreementpayGetsignurl
 * @Description 该类覆盖交易：获取签约URL(200013)，协议支付-签约（100022），协议支付-解约（100023）
 * @Author zhuyh
 * @Date 2019.6.14 13:53
 * @Version 1.0
 */
public class TestAgreementpayGetsignurl extends RequestMsgUtils{

    private static Logger logger = LoggerFactory.getLogger(TestAgreementpayGetsignurl.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    Map<String, String> paramsSms = new LinkedHashMap<String, String>();
    Map<String, String> paramsCardSign = new LinkedHashMap<String, String>();
    private String reqUrlGetsignurl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "agreementpay/getsignurl";
    private String reqUrlSignsms = randomUtils.getProperties("url.properties").getProperty("merc-front-web") + "agreementpay/signsms";
    private String reqUrlBindcardsign = randomUtils.getProperties("url.properties").getProperty("merc-front-web") + "agreementpay/bindcardsign";
    //签约(H5页面模式-头条专用)初始化数据
    public void getSignURLInit(){
        mapInit();
        infoMap.put("TRX_CODE","200013");//Y 交易代码 定值
        infoMap.put("VERSION","2.2");//Y 版本 定值 2.2(头条密码前置流程) 2.3(头条密码后置流程)
        infoMap.put("DATA_TYPE","0");//Y 数据格式 定值
        infoMap.put("REQ_SN",randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MERC_USER_NO","U"+randomUtils.getDateTime17());//Y 商户用户号
        bodyMap.put("BANK_ACCOUNT_NAME","");//N 银行账户名称
        bodyMap.put("ID_TYPE","00");//N 证件类型
        bodyMap.put("ID_NO","");//N 证件号 
        bodyMap.put("NOTIFY_URL","http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify");//Y 后台通知地址
        bodyMap.put("RETURN_URL","http://www.baidu.com");//Y 前台通知地址
        bodyMap.put("PAGE_TYPE","1");//N 页面类型	1：四要素同一个页面展示（默认） 2：四要素两个页面展示
        bodyMap.put("CARD_SOURCE","1");//N 卡来源	1：新卡（默认）2：存量卡
        bodyMap.put("BIND_CARD_ID","");//C 绑卡ID	CARD_SOURCE为2时必传	
		Map<String, String> riskMsg = new HashMap<String, String>();
		riskMsg.put("subMercNo", "1000001");
		riskMsg.put("subMercName", "小霸王");
		riskMsg.put("subMercTradeCategory", "");
		riskMsg.put("mercUserRegDate", "100");
		riskMsg.put("mercUserActiveDays", "99");
		riskMsg.put("mercUserActiveLevel", "1");
		riskMsg.put("mercUserRegMobile", "13800138000");
		riskMsg.put("mercUserRegEmail", "13800138000@139.com");
		riskMsg.put("mercUserIdNo", "1234567890");
		riskMsg.put("mercUserRegIp", "127.0.0.1");
		riskMsg.put("mercUserRegDeviceId", "1234567890");
		riskMsg.put("mercUserRealNameFlag", "1");
		riskMsg.put("orderRealFlag", "1");
		riskMsg.put("orderRealIdNo", "1234567890");
		riskMsg.put("rechargeMobile", "13800138000");
		riskMsg.put("receiveUserName", "小熊猫");
		riskMsg.put("receiveUserMobile", "13800138000");
		riskMsg.put("receiveAddrProv", "北京");
		riskMsg.put("receiveAddrCity", "北京");
		riskMsg.put("receiveAddrArea", "海淀");
		riskMsg.put("receiveAddrDetail", "四季青桥");
		riskMsg.put("payFrontUrlReferrer", "www.baidu.com");
        bodyMap.put("RISK_INFO",JSONObject.toJSONString(riskMsg));//N 风控扩展信息       
        bodyMap.put("ALLOW_TRANS_CARD_TYPE","1");//N 允许交易的卡类型 1：仅支持借记卡,2: 仅支持贷记卡 说明:1.为空默认借贷都支持.2.该字段本期暂不支持 
        bodyMap.put("EXTEND1","备用1");
        bodyMap.put("EXTEND2","备用1");
        bodyMap.put("EXTEND3","备用1");      
    }
    //签约发送短信初始化数据
    public void signSmsInit(){
//        mapInit();
//        paramsSms.put("platOrderNo", randomUtils.getRandomTime(20));
//        paramsSms.put("mercUserNo", "U"+randomUtils.getDateTime17());
//        paramsSms.put("mercId", AutoConstant.MERC_ID_40001);
//        
    }
    //绑卡签约初始化数据
    public void bindCardSignInit(){
//        mapInit();
//        paramsCardSign.put("platOrderNo", randomUtils.getRandomTime(20));
//        paramsCardSign.put("mercId", AutoConstant.MERC_ID_40001);
//        paramsCardSign.put("mercUserNo", "U"+randomUtils.getDateTime17());
//        String merchantSignCardSign = SignUtilTest.addSign(paramsCardSign);
//        paramsCardSign.put("sign", merchantSignCardSign);
//        paramsCardSign.put("smsCode", "123456");//状态
//        paramsCardSign.put("token", "20181022194929123100010000");//状态
    }

    @Test
    public void testGetsignurl() throws Exception {
    	logger.info("###  开始执行 获取签约URL(200013)（merc-gateway-web/agreementpay/getsignurl） 用例...");
    	getSignURLInit();
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlGetsignurl);
        logger.info("获取签约URL：{}",respMap.toString());
        logger.info("判断获取签约URL是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        String payUrl = respMap.get("PAY_URL").toString();
        String platOrderNo = payUrl.substring(payUrl.indexOf("=")+1).substring(0,26);
        String userNo = payUrl.substring(payUrl.indexOf("mercUserNo=")+11).substring(0,18);
        
        logger.info("###  开始执行 签约发送短信（merc-gateway-web/agreementpay/signsms） 用例...");
    	/*签约发送短信请求参数*/
//        signSmsInit();
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
                
        Map<String, String> responseSms = http.sendPostRequest(paramsSms, reqUrlSignsms);
        logger.info("签约发送短信响应：{}",responseSms.toString());
        logger.info("判断签约发送短信是否成功？返回码：{},返回信息：{}",responseSms.get("retCode"),responseSms.get("retMsg"));
        Assert.assertEquals(responseSms.get("retCode"),"0000");
        
        String token = responseSms.get("token");
        logger.info("###  开始执行 绑卡签约（merc-gateway-web/agreementpay/bindcardsign） 用例...");
        /*绑卡签约请求参数*/
//        bindCardSignInit();
        System.out.println(platOrderNo);
        System.out.println(userNo);
        paramsCardSign.put("platOrderNo", platOrderNo);
        paramsCardSign.put("mercId", AutoConstant.MERC_ID_40001);//MERC_ID_40001
        paramsCardSign.put("mercUserNo", userNo);
        String merchantSignCardSign = SignUtilTest.addSign(paramsCardSign);
        paramsCardSign.put("sign", merchantSignCardSign);
        paramsCardSign.put("token", token);//状态
        paramsCardSign.put("smsCode", "123456");//状态
        
        Map<String, String> bindCardSignrespMap = http.sendPostRequest(paramsCardSign,reqUrlBindcardsign);
        logger.info("绑卡签约响应：{}",bindCardSignrespMap.toString());
        logger.info("判断绑卡签约是否成功？返回码：{},返回信息：{}",bindCardSignrespMap.get("retCode"),bindCardSignrespMap.get("retMsg"));
        Assert.assertEquals(bindCardSignrespMap.get("retCode"),"0000");
    }
   
}
