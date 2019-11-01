package com.http.mercgatewayweb.openaccountandcancel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;

/**
 * @ClassName: 2019-06-21
 * @author Wendy 
 * @Description: 个人用户开户(传入密码)(100058)、密码验证（100060）、密码设置（100061）、开户和密码设置查询（200022）
 * @Date 2019.6.21 
 * @Version 1.0
 * 
 */
public class TestOpenwithPwd extends RequestMsgUtils {
	private static Logger logger = LoggerFactory.getLogger(TestOpenwithPwd.class);
	private HttpSendUtils http = new HttpSendUtils();
	private RandomUtils randomUtils = new RandomUtils();
    private String mercId = AutoConstant.MERC_ID_40001;

	//密码开户
	private String reqUrlOpenwithpw = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")
			+ "openaccount/openwithpw";
	//密码验证
	private String reqUrlTestPasswordAuth = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")
			+ "password/passwordauth";
	//密码重置
	private String reqUrlTestPasswordRest = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")
			+ "password/passwordrest";
	//订单查询
	private String reqUrlTestQueryInfo = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")
			+ "password/queryinfo";


	// 个人用户开户初始化参数
	public void getOpenwithpwInit() {
		mapInit();
		infoMap.put("TRX_CODE", "100058");// Y 交易代码
		infoMap.put("VERSION", "2.0");// Y 版本
		infoMap.put("DATA_TYPE", "0");// N 数据格式 0：xml格式
		infoMap.put("REQ_SN", randomUtils.getRandomTime(14));// Y 请求流水号
		infoMap.put("SIGNED_MSG", "signedMsg");// Y 签名信息
		// body
		bodyMap.put("MERCHANT_ID", mercId);// Y 商户编号（联调回归商户）
		bodyMap.put("MERC_ORDER_NO", "");// 商户订单号
		bodyMap.put("MERC_ORDER_DATE", "");// 商户订单日期
		bodyMap.put("MERC_USER_NO", "U" + randomUtils.getDateTime17());// 商户用户号
		bodyMap.put("RN_MERCHANT_ID", mercId);// 实名/签约时的商户编号 发起方
		bodyMap.put("AUTH_TYPE", "");// 实名认证类型  0：原单号认证(默认) 1：二要素认证  2：四要素认证
		bodyMap.put("BANK_ACCOUNT_NAME", randomUtils.getRandomName());// 银行账户名称
		bodyMap.put("ID_TYPE", "00");// 开户证件类型 00：身份证
		bodyMap.put("ID_NO", randomUtils.getIdNo());// 证件号
		bodyMap.put("REG_MOBILE_NO", randomUtils.getMobileNo());// 手机号码
		bodyMap.put("EXTEND1", "111");// 备用域1
		bodyMap.put("EXTEND2", "222");// 备用域2
		bodyMap.put("EXTEND3", "222");// 备用域3
	}
	
	
	// 密码验证初始化数据
	public void getTestPWDAuthInit() {
		mapInit();
		infoMap.put("TRX_CODE", "100058");// Y 交易代码
		infoMap.put("VERSION", "2.0");// Y 版本
		infoMap.put("DATA_TYPE", "0");// N 数据格式 0：xml格式
		infoMap.put("REQ_SN", randomUtils.getRandomTime(14));// Y 请求流水号
		infoMap.put("SIGNED_MSG", "signedMsg");// Y 签名信息
		// body
		bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);// Y 商户编号
		bodyMap.put("MERC_ORDER_NO", randomUtils.getRandomTime(20));// Y 商户订单号
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());// Y 商户订单日期
		bodyMap.put("USER_NO", "");// Y 平台用户号
		bodyMap.put("PAY_PASSWORD", "");// Y 支付密码
	}
	
	
	// 设置密码初始化数据
	public void getTestPWDRestInit() {
		mapInit();
		infoMap.put("TRX_CODE", "100058");// Y 交易代码
		infoMap.put("VERSION", "2.0");// Y 版本
		infoMap.put("DATA_TYPE", "0");// N 数据格式 0：xml格式
		infoMap.put("REQ_SN", randomUtils.getRandomTime(14));// Y 请求流水号
		infoMap.put("SIGNED_MSG", "signedMsg");// Y 签名信息
		// body
		bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);// Y 商户编号
		bodyMap.put("MERC_ORDER_NO", randomUtils.getRandomTime(20));// Y 商户订单号
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());// Y 商户订单日期

		bodyMap.put("USER_NO", "");// Y 平台用户号
		bodyMap.put("OLD_PAY_PASSWORD", "");// N 原支付密码 为空代表直接设置新密码
		bodyMap.put("PAY_PASSWORD", "");// Y 支付密码
	}
	
	// 订单查询初始化数据
	public void getTestQueryInfoInit() {
		mapInit();
		infoMap.put("TRX_CODE", "100058");// Y 交易代码
		infoMap.put("VERSION", "2.0");// Y 版本
		infoMap.put("DATA_TYPE", "0");// N 数据格式 0：xml格式
		infoMap.put("REQ_SN", randomUtils.getRandomTime(14));// Y 请求流水号
		infoMap.put("SIGNED_MSG", "signedMsg");// Y 签名信息
		// body
		bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);// Y 商户编号
		bodyMap.put("MERC_ORDER_NO", randomUtils.getRandomTime(20));// Y 商户订单号
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());// Y 商户订单日期
	}
	
	
	/*
	 * 二要素开户成功，密码验证成功，无原始密码时设置密码，开户订单查询
	 */
	@Test
	public void testOpenwithpw2() throws Exception {
		//二要素开户，开户成功
		getOpenwithpwInit();
		String mercOrderNo=randomUtils.getRandomTime(20);// 设置商户订单号
		String mercOrderDate=randomUtils.getDate8();// 设置商户订单日期
		String passWord="U" + randomUtils.getDate8();//设置密码
		
		bodyMap.put("MERC_USER_NO", "U" + randomUtils.getDateTime17());// 商户用户号
		bodyMap.put("MERC_ORDER_NO", mercOrderNo);// 商户订单号
		bodyMap.put("MERC_ORDER_DATE", mercOrderDate);// 商户订单日期
		bodyMap.put("PAY_PASSWORD", passWord);// 支付密码
		bodyMap.put("AUTH_TYPE", "1");// 实名认证类型 1：二要素认证
		bodyMap.put("BANK_ACCOUNT_NAME", randomUtils.getRandomName());// 银行账户名称
		bodyMap.put("ID_TYPE", "00");// 开户证件类型 00：身份证
		bodyMap.put("ID_NO", randomUtils.getIdNo());// 证件号
		bodyMap.put("REG_MOBILE_NO", randomUtils.getMobileNo());// 手机号码

		requestMap = getRequestMap();
		System.out.println(requestMap);
		logger.info("请求：{}", requestMap.toString());
		respMap = http.sendHttpRequest(requestMap, reqUrlOpenwithpw);
		logger.info("响应：{}", respMap.toString());
		logger.info("判断开户是否成功？返回码：{},返回信息：{}", respMap.get("RET_CODE"), respMap.get("RET_MSG"));
		Assert.assertEquals(respMap.get("RET_CODE"), "0000");
		
		//密码验证：成功
        String user_no = respMap.get("USER_NO");//设置上一步骤开户时的商户用户号
		getTestPWDAuthInit();
		bodyMap.put("USER_NO", user_no);// Y 平台用户号
		bodyMap.put("PAY_PASSWORD", passWord);// Y 支付密码
		
		requestMap = getRequestMap();
		System.out.println(requestMap);
		logger.info("请求：{}", requestMap.toString());
		respMap = http.sendHttpRequest(requestMap, reqUrlTestPasswordAuth);
		logger.info("响应：{}", respMap.toString());
		logger.info("判断密码验证是否成功？返回码：{},返回信息：{}", respMap.get("RET_CODE"), respMap.get("RET_MSG"));
		Assert.assertEquals(respMap.get("RET_CODE"), "0000");
		Assert.assertEquals(respMap.get("ORDER_STATUS"), "S");
		
		//密码设置，无原始密码直接设置密码，成功
		getTestPWDRestInit();
		bodyMap.put("USER_NO", user_no);// Y 平台用户号
		bodyMap.put("OLD_PAY_PASSWORD", "");// N 原支付密码 为空代表直接设置新密码
		bodyMap.put("PAY_PASSWORD", "12345678");// Y 支付密码
		
		requestMap = getRequestMap();
		System.out.println(requestMap);
		logger.info("请求：{}", requestMap.toString());
		respMap = http.sendHttpRequest(requestMap, reqUrlTestPasswordRest);
		logger.info("响应：{}", respMap.toString());
		logger.info("判断密码设置是否成功？返回码：{},返回信息：{}", respMap.get("RET_CODE"), respMap.get("RET_MSG"));
		Assert.assertEquals(respMap.get("RET_CODE"), "0000");
		Assert.assertEquals(respMap.get("ORDER_STATUS"), "S");
		
		//订单查询：查询本次开户订单
		getTestQueryInfoInit();
		bodyMap.put("MERC_ORDER_NO", mercOrderNo);// Y 商户订单号
		bodyMap.put("MERC_ORDER_DATE", mercOrderDate);// Y 商户订单日期
		
		requestMap = getRequestMap();
		System.out.println(requestMap);
		logger.info("请求：{}", requestMap.toString());
		respMap = http.sendHttpRequest(requestMap, reqUrlTestQueryInfo);

		logger.info("响应：{}", respMap.toString());
		logger.info("判断开户订单查询是否成功？返回码：{},返回信息：{}", respMap.get("RET_CODE"), respMap.get("RET_MSG"));
		Assert.assertEquals(respMap.get("RET_CODE"), "0000");
		Assert.assertEquals(respMap.get("ORDER_STATUS"), "S");
	}

	/*
	 * 四要素开户成功，密码验证不成功，有原始密码设置密码成功，设置密码订单查询成功
	 */
	@Test
	public void testOpenwithpw4() throws Exception {
		String passWord="U" + randomUtils.getDate8();//设置密码
		getOpenwithpwInit();
		bodyMap.put("MERC_ORDER_NO", "U" + randomUtils.getRandomTime(20));// 商户订单号
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());// 商户订单日期
		bodyMap.put("MERC_USER_NO", "U" + randomUtils.getDateTime17());// 商户用户号
		bodyMap.put("AUTH_TYPE", "2");// 实名认证类型：2 四要素认证
		bodyMap.put("BANK_ACCOUNT_NAME", randomUtils.getRandomName());// 银行账户名称
		bodyMap.put("ID_NO", randomUtils.getIdNo());// 证件号
		bodyMap.put("CARD_NO", randomUtils.getCardNo("ICBC"));// 银行卡号
		bodyMap.put("BANK_MOBILE_NO", randomUtils.getMobileNo());// 手机号码
		bodyMap.put("PAY_PASSWORD",passWord);// 支付密码
		bodyMap.put("BANK_ACCOUNT_NAME", randomUtils.getRandomName());// 银行账户名称
		bodyMap.put("ID_TYPE", "00");// 开户证件类型 00：身份证
		bodyMap.put("ID_NO", randomUtils.getIdNo());// 证件号
		bodyMap.put("REG_MOBILE_NO", randomUtils.getMobileNo());// 手机号码
		requestMap = getRequestMap();
		System.out.println(requestMap);
		logger.info("请求：{}", requestMap.toString());
		respMap = http.sendHttpRequest(requestMap, reqUrlOpenwithpw);

		logger.info("响应：{}", respMap.toString());
		logger.info("判断开户是否成功？返回码：{},返回信息：{}", respMap.get("RET_CODE"), respMap.get("RET_MSG"));
		Assert.assertEquals(respMap.get("RET_CODE"), "0000");

		//密码验证：响应成功，密码正确
		
        String user_no = respMap.get("USER_NO");//设置上一步骤开户时的商户用户号
		getTestPWDAuthInit();
		bodyMap.put("USER_NO", user_no);// Y 平台用户号
		bodyMap.put("PAY_PASSWORD", passWord);// Y 支付密码
		
		requestMap = getRequestMap();
		System.out.println(requestMap);
		logger.info("请求：{}", requestMap.toString());
		respMap = http.sendHttpRequest(requestMap, reqUrlTestPasswordAuth);
		logger.info("响应：{}", respMap.toString());
		logger.info("判断密码验证是否成功？返回码：{},返回信息：{}", respMap.get("RET_CODE"), respMap.get("RET_MSG"));
		Assert.assertEquals(respMap.get("RET_CODE"), "0000");
		
		//密码设置,有原支付密码，设置成功
		
		String mercOrderNo1=randomUtils.getRandomTime(20);// 设置商户订单号
		String mercOrderDate1=randomUtils.getDate8();// 设置商户订单号
		getTestPWDRestInit();
		bodyMap.put("MERC_ORDER_NO", mercOrderNo1);// Y 商户订单号
		bodyMap.put("MERC_ORDER_DATE", mercOrderDate1);// Y 商户订单号
		bodyMap.put("USER_NO", user_no);// Y 平台用户号
		bodyMap.put("OLD_PAY_PASSWORD", passWord);// N 原支付密码  不为空
		bodyMap.put("PAY_PASSWORD", "12345678");// Y 支付密码
		
		requestMap = getRequestMap();
		System.out.println(requestMap);
		logger.info("请求：{}", requestMap.toString());
		respMap = http.sendHttpRequest(requestMap, reqUrlTestPasswordRest);
		logger.info("响应：{}", respMap.toString());
		logger.info("判断密码设置是否成功？返回码：{},返回信息：{}", respMap.get("RET_CODE"), respMap.get("RET_MSG"));
		Assert.assertEquals(respMap.get("RET_CODE"), "0000");
		Assert.assertEquals(respMap.get("ORDER_STATUS"), "S");
		
		//订单查询：查询本次设置密码订单
		getTestQueryInfoInit();
		bodyMap.put("MERC_ORDER_NO", mercOrderNo1);// Y 商户订单号
		bodyMap.put("MERC_ORDER_DATE", mercOrderDate1);// Y 商户订单日期
		
		requestMap = getRequestMap();
		System.out.println(requestMap);
		logger.info("请求：{}", requestMap.toString());
		respMap = http.sendHttpRequest(requestMap, reqUrlTestQueryInfo);

		logger.info("响应：{}", respMap.toString());
		logger.info("判断设置密码是否成功？返回码：{},返回信息：{}", respMap.get("RET_CODE"), respMap.get("RET_MSG"));
		Assert.assertEquals(respMap.get("RET_CODE"), "0000");
		Assert.assertEquals(respMap.get("ORDER_STATUS"), "S");
	}
}
