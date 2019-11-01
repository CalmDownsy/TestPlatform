package com.http.mercgatewayweb;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.interfacetest.commonutils.DBConnectUtils;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
/**
 * Test merc-gateway-web interface 
 * @author liyang
 * @version 2.0
 * @date 2019  8月14日 16:48
 * Description: 包含：1、信用卡还款creditcard/repay  2、还款短信验证creditcard/confirmpay 3、还款获取短信creditcard/getsmscode
 */
public class TestCreditCardRepay extends RequestMsgUtils {
	private static Logger logger = LoggerFactory.getLogger(TestCreditCardRepay.class);
	private RandomUtils randomUtils = new RandomUtils();
    private HttpSendUtils http = new HttpSendUtils();
    private String creditCardrepayUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"creditcard/repay";
    private String creditCardconfirmPayUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"creditcard/confirmpay";
    private String creditCardGetSmsCodeUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"creditcard/getsmscode";
    private String creditQueryUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"creditcard/query";
    String orderNo="ly"+randomUtils.getDateTime17();
    String merc_id =AutoConstant.MERC_ID_40001;
  //初始化信用卡还款信息  
    public void creditRepayInitMap() throws Exception { 
    mapInit();
    infoMap.put("TRX_CODE","100077");//Y 交易代码
	infoMap.put("VERSION","2.0");//Y 版本
	infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
	infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
	infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
//	bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_19);//Y 商户编号
//	bodyMap.put("MERCHANT_ID", "800025000100028");//Y 商户编号
	bodyMap.put("MERC_ORDER_NO", orderNo);//Y商户订单号
	bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y商户订单日期
	bodyMap.put("PLACE_ORDER_IP", "127.0.0.1");//N用户下单IP
	bodyMap.put("NOTIFY_URL",AutoConstant.NOTIFY_URL);//
	bodyMap.put("SIGN_AGREEMENT_NO","");//Y签约协议号扣款银行卡签约协议号 说明：1.	仅支持借记卡2.	签约绑卡时生成的协议号
	bodyMap.put("REPAY_AGREEMENT_NO","");//C还款银行卡签约协议号  1.仅支持贷记卡、准贷记卡2.	待还款协议号与二要素（还款银行卡号和还款银行账户名称）二选其一，如都上送，以协议号中对应的信息为准.
//	bodyMap.put("CARD_NO", "622848"+randomUtils.getRandomAll(13));//卡号,C,还款银行卡号
//	bodyMap.put("BANK_ACCOUNT_NAME", "全渠道");//C还款银行账户名称
	bodyMap.put("TRANS_AMT", "0.05");//Y交易金额  还款金额
	bodyMap.put("CURRENCY", "CNY");//Y币种
	bodyMap.put("VALID_MODE", "3");//验证方式,Y,1:短验验证2:支付密码验证3:短验弱验证：若风控加验或通道验证，则下发短验做验证4、无需验证：不验证短验和支付密码	
	bodyMap.put("BUSINESS_CATEGORY", "110001");//业务种类,Y,110001水电煤缴费
	bodyMap.put("PAY_PASSWORD", "");//支付密码,N
	bodyMap.put("ORDER_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//N商品详情
	bodyMap.put("TERMINAL_TYPE", "04");//Y 01:电脑02:手机03:平板设备04:可穿戴设备05:数字电视06:条码支付受理终端99:其他
	bodyMap.put("TERMINALID", "TERMINALID");//Y终端编码
	bodyMap.put("DEVICE_INFO", "ABCD:EF01:2345:6789:ABCD:EF01:2345:6789|F0E1D2C3B4A5|352044063995403|460030912121001|898600680113F0123014|968778695A4B|116.360207,-39.921064|");//N交易设备信息
	Map<String, String> riskMsg = new HashMap<String, String>();
	//	riskMsg.put("mercTradeCategory", "");
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
	bodyMap.put("RISK_INFO", JSONObject.toJSONString(riskMsg));//风控大字段,N  
	bodyMap.put("EXTEND1", "备用域EXTEND1");
	bodyMap.put("EXTEND1", "备用域EXTEND2");
	bodyMap.put("EXTEND1", "备用域EXTEND3");
    }
    //初始化信用卡还款获取短信 接口
    public void creditGetSmsCodeInitMap() throws Exception { 
        mapInit();
        infoMap.put("TRX_CODE","100079");//Y 交易代码
    	infoMap.put("VERSION","2.0");//Y 版本
    	infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
    	infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
    	infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
//    	bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_19);//Y 商户编号
    	bodyMap.put("MERCHANT_ID", "");//Y 信用卡还款交易的商户号
    	bodyMap.put("MERC_ORDER_NO", orderNo);//Y 信用卡还款交易的订单号
    	bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y信用卡还款交易的订单日期
    }
    //初始化信用卡还款短信验证接口
    public void creditGetConfirmPayInitMap() throws Exception { 
        mapInit();
        infoMap.put("TRX_CODE","100078");//Y 交易代码
    	infoMap.put("VERSION","2.0");//Y 版本
    	infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
    	infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
    	infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
    	bodyMap.put("MERCHANT_ID", "");//Y 信用卡还款交易的商户号
    	bodyMap.put("MERC_ORDER_NO", orderNo);//Y 信用卡还款交易的订单号
    	bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y信用卡还款交易的订单日期	
    	bodyMap.put("SMS_CODE", "111111");//Y 短信验证码
    	bodyMap.put("TOKEN", orderNo);//Y 获取短信接口返回的请求TOKEN

    }
    //初始化信用卡还款订单查询
    public void creditQueryInitMap() throws Exception { 
        mapInit();
        infoMap.put("TRX_CODE","200033");//Y 交易代码
    	infoMap.put("VERSION","2.0");//Y 版本
    	infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
    	infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
    	infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
    	bodyMap.put("MERCHANT_ID", "");//Y 信用卡还款交易的商户号
    	bodyMap.put("MERC_ORDER_NO", orderNo);//Y 商户订单号
    	bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y信用卡还款交易的订单日期	

    }
    @Test//信用卡还款一步还款+ 营销     付款方式姓名卡号
	public void TestCardAndNAme_RepayAndQuery()throws Exception {
			creditRepayInitMap();
			logger.info("###开始执行信用卡还款一步还款+ 营销###");
			bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
			bodyMap.put("MERC_ORDER_NO", "1"+orderNo);//Y商户订单号
			bodyMap.put("VALID_MODE", "3");//验证方式,Y,1:短验验证2:支付密码验证3:短验弱验证：若风控加验或通道验证，则下发短验做验证4、无需验证：不验证短验和支付密码		
			bodyMap.put("SIGN_AGREEMENT_NO","44190814171532855100010010");//Y签约协议号扣款银行卡签约协议号 说明：1.	仅支持借记卡2.	签约绑卡时生成的协议号
			bodyMap.put("CARD_NO", "6259200987897678");//卡号,C,还款银行卡号
			bodyMap.put("BANK_ACCOUNT_NAME", "测试哈哈");//C还款银行账户名称
			bodyMap.put("TRANS_AMT", "0.01");//Y交易金额  还款金额
			requestMap=getRequestMap();
			Map<String, String> creditRepayRespMap = new LinkedHashMap<String, String>();
			creditRepayRespMap = httpSend(http, creditCardrepayUrl, requestMap);
			Assert.assertEquals(creditRepayRespMap.get("RET_CODE"), "0000");
			Assert.assertEquals(creditRepayRespMap.get("RET_MSG"),"受理成功");		
			String  merc_order_no=creditRepayRespMap.get("MERC_ORDER_NO");   	  
			creditQueryInitMap();
			logger.info("###开始执行信用卡还款订单查询###");
			requestMap=getRequestMap();
			bodyMap.put("MERCHANT_ID", merc_id);//Y 信用卡还款交易的商户号  
			bodyMap.put("MERC_ORDER_NO", merc_order_no);//Y 商户订单号
			bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y信用卡还款交易的订单日期	
			Map<String, String> creditQueryRespMap = httpSend(http, creditQueryUrl, requestMap);
			Assert.assertEquals(creditQueryRespMap.get("RET_CODE"), "0000");
			Assert.assertEquals(creditQueryRespMap.get("RET_MSG"),"查询成功");	
	}
	@Test//信用卡还款两步支付+订单查询   普通 协议号还款
	public void TestXy_RepayAndQuery()throws Exception {
			creditRepayInitMap();
			logger.info("###开始执行信用卡还款-两步支付 ###");
			bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
			bodyMap.put("MERC_ORDER_NO", "2"+orderNo);//Y商户订单号
			bodyMap.put("VALID_MODE", "1");//验证方式,Y,1:短验验证2:支付密码验证3:短验弱验证：若风控加验或通道验证，则下发短验做验证4、无需验证：不验证短验和支付密码	
			bodyMap.put("SIGN_AGREEMENT_NO","44190814171532855100010010");//Y签约协议号扣款银行卡签约协议号 说明：1.	仅支持借记卡2.	签约绑卡时生成的协议号
			bodyMap.put("REPAY_AGREEMENT_NO","43190814171352471100010010");//C还款银行卡签约协议号  1.仅支持贷记卡、准贷记卡2.	待还款协议号与二要素（还款银行卡号和还款银行账户名称）二选其一，如都上送，以协议号中对应的信息为准.
			bodyMap.put("TRANS_AMT", "0.4");//Y交易金额  还款金额
			bodyMap.put("MARKETING_AMT", "0.3");//营销金额,N,营销金额未上送或上送金额为0时，即非营销交易，不校验营销公式 合众测试营销#800057100050001,10.05|800057100050002,10.02
			String MARKETING_FORMULA="合众测试营销#800010000020019,0.1|800010000020020,0.2";
			bodyMap.put("MARKETING_FORMULA",Base64.encodeBase64String(MARKETING_FORMULA.getBytes("utf-8")));//营销公示
			requestMap=getRequestMap();
			Map<String, String> creditRepayRespMap = new LinkedHashMap<String, String>();
			creditRepayRespMap = httpSend(http, creditCardrepayUrl, requestMap);
			Assert.assertEquals(creditRepayRespMap.get("RET_CODE"), "0000");
			Assert.assertEquals(creditRepayRespMap.get("RET_MSG"),"发送短信成功");		
			String  merc_order_no=creditRepayRespMap.get("MERC_ORDER_NO");
		    String token=creditRepayRespMap.get("TOKEN");
		    creditGetConfirmPayInitMap();
			Map<String,String>queryMap = DBConnectUtils.queryOne("SELECT PLAT_ORDER_NO FROM BASEADM.T_B_MERC_CREDIT_CARD_ORDER WHERE MERC_ORDER_NO='" + merc_order_no + "'","baseadm");      
	        Map<String,String>queryMap1 = DBConnectUtils.queryOne("SELECT VERIFY_CODE FROM BASEADM.T_C_MERC_SMS_VERIFYCODEINF WHERE PLAT_ORDER_NO='" + queryMap.get("PLAT_ORDER_NO") + "'","baseadm");      
	        String smsCode=queryMap1.get("VERIFY_CODE");          	  
			logger.info("###开始执行信用卡还款验证短信###");
			bodyMap.put("MERCHANT_ID", merc_id);//Y 信用卡还款交易的商户号
	    	bodyMap.put("MERC_ORDER_NO",merc_order_no );//Y 信用卡还款交易的订单号
	    	bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y信用卡还款交易的订单日期		
	    	bodyMap.put("SMS_CODE", smsCode);//短信验证码
	    	bodyMap.put("TOKEN", token);//Y 获取短信接口返回的请求TOKEN
	    	requestMap=getRequestMap();
	    	Map<String, String> ConfirmPayRespMap = httpSend(http, creditCardconfirmPayUrl, requestMap);			
			Assert.assertEquals(ConfirmPayRespMap.get("RET_CODE"), "0000");
			Assert.assertEquals(ConfirmPayRespMap.get("RET_MSG"),"交易成功");
			creditQueryInitMap();
			logger.info("###开始执行信用卡还款订单查询###");
			requestMap=getRequestMap();
			bodyMap.put("MERCHANT_ID", merc_id);//Y 信用卡还款交易的商户号  
			bodyMap.put("MERC_ORDER_NO", merc_order_no);//Y 商户订单号
			bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y信用卡还款交易的订单日期	
			Map<String, String> creditQueryRespMap = httpSend(http, creditQueryUrl, requestMap);
			Assert.assertEquals(creditQueryRespMap.get("RET_CODE"), "0000");
			Assert.assertEquals(creditQueryRespMap.get("RET_MSG"),"查询成功");	
	
	}

}