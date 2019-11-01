package com.http.mercgatewayweb.agreementpay;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TestAgreementAndGuranteed
 * @Description 协议支付相关接口--覆盖协议支付下单（100025）、获取短信（100029）、确认支付（100026）
 * @author felix
 * @Date 2019-06-19
 * @Version 1.0
 */

public class TestAgreementAndGuranteed extends RequestMsgUtils {
	 private static Logger logger = LoggerFactory.getLogger(TestAgreementAndGuranteed.class);
	    private HttpSendUtils http = new HttpSendUtils();
	    private RandomUtils randomUtils = new RandomUtils();
	    private String reqCreateOrderUrl = randomUtils.getProperties("url.properties").get("merc-gateway-web") + "agreementpay/createorder";
	    private String reqGetSmsCodeUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"agreementpay/getsmscode";
	    private String reqConfirmPayUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+ "agreementpay/confirmpay";
	    private String reqGuranteedConfirmUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+ "guaranteed/confirm";
	    private String reqGuranteedRevokeUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"guaranteed/revoke";  
	    String mercId =AutoConstant.MERC_ID_40001;

	    public void createOrderInit() throws Exception {
	        mapInit();
	        infoMap.put("TRX_CODE", "100025");
	        infoMap.put("VERSION", "2.2");//Y，固定值，填其他值交易不识别
	        infoMap.put("DATA_TYPE", "0");//Y，固定值，0：xml格式
	        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));
	        infoMap.put("SIGNED_MSG", "signedMsg");
	        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
	        bodyMap.put("MERC_ORDER_NO", randomUtils.getDateTime17());//Y商户订单号
	        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y商户订单日期
	        bodyMap.put("TRANS_AMT", "1.01");//Y交易金额，单位元，保留两位小数
	        bodyMap.put("BUSINESS_CATEGORY", "100001");//Y业务种类
	        bodyMap.put("VALID_MODE", "1");//Y，1：短信校验 2：支付密码校验 3：无需校验
	        bodyMap.put("CURRENCY", "");//N，币种，默认CNY
	        bodyMap.put("SIGN_AGREEMENT_NO", "");//N，签约协议号，与银行卡四要素不可同时为空
	        bodyMap.put("ID_TYPE", "");//N,证件类型
	        /*
	         * 00：身份证
	         * 02：户口本
	         * 03：军人身份证
	         * 05：港、澳居民往来内地通行证
	         * 06：台湾居民来往大陆通行证
	         * 07：护照
	         * 08：工商营业执照 
	         * 09：法人证书
	         * 10：组织机构代码证
	         * 11：其他
	         * 不传默认：00
	         */
	        bodyMap.put("MERC_USER_NO", "testwf00001");//N，商户用户号，填四要素时必填
	        bodyMap.put("BANK_ACCOUNT_NAME", "吴鸿宇");//N，银行账户名称，银行卡四要素如果其中一个有值，其余必须有值
	        bodyMap.put("BANK_MOBILE_NO", "18410872101");//N，银行预留手机号，银行卡四要素如果其中一个有值，其余必须有值
	        bodyMap.put("CARD_NO", "6226200102850001");//N，卡号，银行卡四要素如果其中一个有值，其余必须有值
	        bodyMap.put("ID_NO", "110101199003079593");//N，证件号，银行卡四要素如果其中一个有值，其余必须有值
	        bodyMap.put("PLACE_ORDER_IP", "");//N，下单IP，用户下单时使用的IP
	        bodyMap.put("NOTIFY_URL", "");//N，后台异步通知，合众支付系统完成交易后，后台操作的方式调用此地址通知商户处理结果
	        bodyMap.put("ORDER_EXP_DATE", "");//N，订单过期时间
	        bodyMap.put("SPLIT_FLAG", "");//N，分账标志,1：分账 2：不分账（默认）
	        bodyMap.put("SPLIT_FORMULA", "");//N，分账公式
	        bodyMap.put("ASSURE_FLAG", "");//N，担保标志,1：担保 2：不担保（默认）
	        bodyMap.put("ORDER_DETAILS", "");//N，商品详情
	        bodyMap.put("PAY_PASSWORD", "");//N，支付密码
	        bodyMap.put("EXTEND1", "");//N，备用域1
	        bodyMap.put("EXTEND2", "");//N，备用域2
	        bodyMap.put("EXTEND3", "");//N，备用域3
	    }

	    public void getSmsCodeInit() throws Exception {
	        mapInit();
	        infoMap.put("TRX_CODE", "100029");
	        infoMap.put("VERSION", "2.2");//Y，固定值，填其他值交易不识别
	        infoMap.put("DATA_TYPE", "0");//Y，固定值，0：xml格式
	        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));
	        infoMap.put("SIGNED_MSG", "signedMsg");
	        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
	        bodyMap.put("MERC_ORDER_NO", "");//Y商户下单时订单号
	        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y下单时商户订单日期
	        bodyMap.put("SIGN_AGREEMENT_NO", "");//N，签约协议号，与银行卡四要素不可同时为空
	        bodyMap.put("ID_TYPE", "");//N,证件类型
	        /*
	         * 00：身份证
	         * 02：户口本
	         * 03：军人身份证
	         * 05：港、澳居民往来内地通行证
	         * 06：台湾居民来往大陆通行证
	         * 07：护照
	         * 08：工商营业执照 
	         * 09：法人证书
	         * 10：组织机构代码证
	         * 11：其他
	         * 不传默认：00
	         */
	        bodyMap.put("MERC_USER_NO", "testwf00001");//N，商户用户号，填四要素时必填
	        bodyMap.put("BANK_ACCOUNT_NAME", "吴鸿宇");//N，银行账户名称，银行卡四要素如果其中一个有值，其余必须有值
	        bodyMap.put("BANK_MOBILE_NO", "18410872101");//N，银行预留手机号，银行卡四要素如果其中一个有值，其余必须有值
	        bodyMap.put("CARD_NO", "6226200102850001");//N，卡号，银行卡四要素如果其中一个有值，其余必须有值
	        bodyMap.put("ID_NO", "110101199003079593");//N，证件号，银行卡四要素如果其中一个有值，其余必须有值
	    }
	    
	    public void confirmPayInit() throws Exception {
	        mapInit();
	        infoMap.put("TRX_CODE", "100026");
	        infoMap.put("VERSION", "2.2");//Y，固定值，填其他值交易不识别
	        infoMap.put("DATA_TYPE", "0");//Y，固定值，0：xml格式
	        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));
	        infoMap.put("SIGNED_MSG", "signedMsg");
	        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
	        bodyMap.put("MERC_ORDER_NO", "");//Y商户下单时的订单号
	        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y商户下单时的订单日期
	        bodyMap.put("MBL_CAPTCHA", "123456");//N手机验证码，下单时如果返回需要短验，则需传入
	        bodyMap.put("TRANS_JRN_NO", "");//Y请求TOKEN，下单接口或获取短信接口返回的请求TOKEN
	        bodyMap.put("TERMINAL_TYPE", "01");//Y,终端类型
	        /*
	         * 01:电脑
	         * 02:手机
	         * 03:平板设备
	         * 04:可穿戴设备
	         * 05:数字电视
	         * 06:条码支付受理终端
	         * 99:其他
	         */
	        bodyMap.put("TERMINALID", "");//N，终端编码，标识交易终端的唯一、固定的编码
	        bodyMap.put("DEVICE_INFO", "");//N，交易设备信息
	        bodyMap.put("RISK_INFO", "");//N，风控扩展信息
	        bodyMap.put("EXTEND1", "");//N，备用域1
	        bodyMap.put("EXTEND2", "");//N，备用域2
	        bodyMap.put("EXTEND3", "");//N，备用域3
	    }
	    
	    public void guaranteedInit() throws Exception {
	        mapInit();
	        infoMap.put("TRX_CODE", "100018");
	        infoMap.put("VERSION", "2.2");//Y，固定值，填其他值交易不识别
	        infoMap.put("DATA_TYPE", "0");//Y，固定值，0：xml格式
	        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));
	        infoMap.put("SIGNED_MSG", "signedMsg");
	        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
	        bodyMap.put("MERC_ORDER_NO", randomUtils.getDateTime17());//Y商户订单号,担保确认、撤销订单号
	        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y商户订单日期,担保确认、撤日期
	        bodyMap.put("MERC_APPLY_ORDER_NO", "");//Y商户担保申请订单号,为担保申请主订单号
	        bodyMap.put("MERC_APPLY_ORDER_DATE", randomUtils.getDate8());//Y商户担保申请订单日期
	        bodyMap.put("TRANS_AMT", "1.01");//Y确认金额，单位元，保留两位小数
	        bodyMap.put("SPLIT_FORMULA", "");//N，分账公式
	        bodyMap.put("NOTIFY_URL", "");//N，后台通知地址
	        bodyMap.put("EXTENDS1", "");//N，备用域1
	        bodyMap.put("EXTENDS2", "");//N，备用域2
	        bodyMap.put("EXTENDS3", "");//N，备用域3
	        Map<String, Object> tempTransDetailMap = new HashMap<String, Object>();
	        tempTransDetailMap.put("SUB_MERC_APPLY_ORDER_NO", "");//C,原担保申请商户子订单号,如果申请单为合单则必输
	        tempTransDetailMap.put("SUB_SPLIT_FORMULA", "");//C,子订单分账公式,如果申请单为合单则必输
//	        String transDetail = new String("TRANS_DETAIL");
	        transDetailsMap.put("TRANS_DETAIL", tempTransDetailMap);
	    }

	    
//	    @AfterMethod
//	    public void tearDown() throws Exception {
//	    }

	    /**
	     * 测试下单、获取短信、确认支付接口
	     * @throws Exception 
	     */
	    @Test
	    public void testAgreementPay() throws Exception {
	        logger.info("开始执行 下单（100025）（/merc-gateway-web/agreementpay/createorder） 用例...");
	        createOrderInit();
	        requestMap = getRequestMap();
	        Map<String, String> respMap = http.sendHttpRequest(requestMap, reqCreateOrderUrl);
	        logger.info("下单响应结果：{}", respMap);
	        Assert.assertEquals(respMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respMap.get("RET_MSG"), "交易成功");
	        Assert.assertEquals(respMap.get("SMS_SEND_FLAG"), "1");
	        String oriOrderNo = respMap.get("MERC_ORDER_NO");
	        logger.info("开始执行 获取短信（100029）（/merc-gateway-web/agreementpay/getsmscode） 用例...");
	        getSmsCodeInit();
	        bodyMap.put("MERC_ORDER_NO", oriOrderNo);
	        requestMap = getRequestMap();
	        Map<String, String> respGetSmsCodeMap = http.sendHttpRequest(requestMap, reqGetSmsCodeUrl);
	        logger.info("获取短信响应结果：{}", respGetSmsCodeMap);
	        Assert.assertEquals(respGetSmsCodeMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respGetSmsCodeMap.get("RET_MSG"), "交易成功");
	        String token = respGetSmsCodeMap.get("TRANS_JRN_NO");
//	        String token = respMap.get("TRANS_JRN_NO");//错误，下发短信的情况下，需要使用短信下发时响应回来的值
	        logger.info("开始执行 获取短信（100026）（/merc-gateway-web/agreementpay/confirmpay） 用例...");
	        confirmPayInit();
	        bodyMap.put("MERC_ORDER_NO", oriOrderNo);
	        bodyMap.put("TRANS_JRN_NO", token);
	        requestMap = getRequestMap();
	        Map<String, String> respConfirmMap = http.sendHttpRequest(requestMap, reqConfirmPayUrl);
	        logger.info("确认支付响应结果：{}", respConfirmMap);
	        Assert.assertEquals(respConfirmMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respConfirmMap.get("RET_MSG"), "交易成功");
	    }
	    /**
	     * 测试担保确认接口
	     * @throws Exception 
	     */
	    @Test
	    public void testGuranteedConfirm() throws Exception {
	        logger.info("开始执行 下单（100025）（/merc-gateway-web/agreementpay/createorder） 用例...");
	        createOrderInit();
	        bodyMap.put("ASSURE_FLAG", "1");//N，担保标志,1：担保 2：不担保（默认）
	        requestMap = getRequestMap();
	        Map<String, String> respMap = http.sendHttpRequest(requestMap, reqCreateOrderUrl);
	        logger.info("下单响应结果：{}", respMap);
	        Assert.assertEquals(respMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respMap.get("RET_MSG"), "交易成功");
	        Assert.assertEquals(respMap.get("SMS_SEND_FLAG"), "1");
	        String oriOrderNo = respMap.get("MERC_ORDER_NO");
	        logger.info("开始执行 获取短信（100029）（/merc-gateway-web/agreementpay/getsmscode） 用例...");
	        getSmsCodeInit();
	        bodyMap.put("MERC_ORDER_NO", oriOrderNo);
	        requestMap = getRequestMap();
	        Map<String, String> respGetSmsCodeMap = http.sendHttpRequest(requestMap, reqGetSmsCodeUrl);
	        logger.info("获取短信响应结果：{}", respGetSmsCodeMap);
	        Assert.assertEquals(respGetSmsCodeMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respGetSmsCodeMap.get("RET_MSG"), "交易成功");
	        String token = respGetSmsCodeMap.get("TRANS_JRN_NO");
//	        String token = respMap.get("TRANS_JRN_NO");//错误，下发短信的情况下，需要使用短信下发时响应回来的值
	        logger.info("开始执行 获取短信（100026）（/merc-gateway-web/agreementpay/confirmpay） 用例...");
	        confirmPayInit();
	        bodyMap.put("MERC_ORDER_NO", oriOrderNo);
	        bodyMap.put("TRANS_JRN_NO", token);
	        requestMap = getRequestMap();
	        Map<String, String> respConfirmMap = http.sendHttpRequest(requestMap, reqConfirmPayUrl);
	        logger.info("确认支付响应结果：{}", respConfirmMap);
	        Assert.assertEquals(respConfirmMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respConfirmMap.get("RET_MSG"), "交易成功");
	        logger.info("开始执行担保确认（100018）（/merc-gateway-web/guaranteed/confirm） 用例...");
	        guaranteedInit();
	        bodyMap.put("MERC_APPLY_ORDER_NO", oriOrderNo);
	        requestMap = getRequestMap();
	        Map<String, String> respGuranteedConfirmMap = http.sendHttpRequest(requestMap, reqGuranteedConfirmUrl);
	        logger.info("担保确认响应结果：{}", respGuranteedConfirmMap);
	        Assert.assertEquals(respGuranteedConfirmMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respGuranteedConfirmMap.get("RET_MSG"), "交易成功");	        
	    }
	    /**
	     * 测试担保撤销接口
	     * @throws Exception 
	     */
	    @Test
	    public void testGuranteedRevoke() throws Exception {
	        logger.info("开始执行 下单（100025）（/merc-gateway-web/agreementpay/createorder） 用例...");
	        createOrderInit();
	        bodyMap.put("ASSURE_FLAG", "1");//N，担保标志,1：担保 2：不担保（默认）
	        requestMap = getRequestMap();
	        Map<String, String> respMap = http.sendHttpRequest(requestMap, reqCreateOrderUrl);
	        logger.info("下单响应结果：{}", respMap);
	        Assert.assertEquals(respMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respMap.get("RET_MSG"), "交易成功");
	        Assert.assertEquals(respMap.get("SMS_SEND_FLAG"), "1");
	        String oriOrderNo = respMap.get("MERC_ORDER_NO");
	        logger.info("开始执行 获取短信（100029）（/merc-gateway-web/agreementpay/getsmscode） 用例...");
	        getSmsCodeInit();
	        bodyMap.put("MERC_ORDER_NO", oriOrderNo);
	        requestMap = getRequestMap();
	        Map<String, String> respGetSmsCodeMap = http.sendHttpRequest(requestMap, reqGetSmsCodeUrl);
	        logger.info("获取短信响应结果：{}", respGetSmsCodeMap);
	        Assert.assertEquals(respGetSmsCodeMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respGetSmsCodeMap.get("RET_MSG"), "交易成功");
	        String token = respGetSmsCodeMap.get("TRANS_JRN_NO");
//	        String token = respMap.get("TRANS_JRN_NO");//错误，下发短信的情况下，需要使用短信下发时响应回来的值
	        logger.info("开始执行 获取短信（100026）（/merc-gateway-web/agreementpay/confirmpay） 用例...");
	        confirmPayInit();
	        bodyMap.put("MERC_ORDER_NO", oriOrderNo);
	        bodyMap.put("TRANS_JRN_NO", token);
	        requestMap = getRequestMap();
	        Map<String, String> respConfirmMap = http.sendHttpRequest(requestMap, reqConfirmPayUrl);
	        logger.info("确认支付响应结果：{}", respConfirmMap);
	        Assert.assertEquals(respConfirmMap.get("RET_CODE"), "0000");
	        Assert.assertEquals(respConfirmMap.get("RET_MSG"), "交易成功");
	        logger.info("开始执行担保撤销（100019）（/merc-gateway-web/guaranteed/revoke） 用例...");
	        guaranteedInit();
	        infoMap.put("TRX_CODE", "100019");
	        bodyMap.put("MERC_APPLY_ORDER_NO", oriOrderNo);	        
	        requestMap = getRequestMap();
	        Map<String, String> respGuranteedRevokeMap = http.sendHttpRequest(requestMap, reqGuranteedRevokeUrl);
	        logger.info("担保撤销响应结果：{}", respGuranteedRevokeMap);
	        Assert.assertEquals(respGuranteedRevokeMap.get("RET_CODE"), "6666");
	        Assert.assertEquals(respGuranteedRevokeMap.get("RET_MSG"), "交易处理中，请稍后查询");	        
	    }


}
