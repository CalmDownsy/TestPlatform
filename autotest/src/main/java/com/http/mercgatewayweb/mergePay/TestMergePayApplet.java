package com.http.mercgatewayweb.mergePay;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 小程序支付下单-合单支付、普通支付都支持
 */

public class TestMergePayApplet extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestMergePayApplet.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11110/merc-gateway-web/mergepay/applet";
	private String url=randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"mergepay/applet";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	//	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/backNotify"
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
	private ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();

	public void initReqMap_V21() throws Exception {
		infoMap.put("TRX_CODE","100041");//Y 交易代码
		infoMap.put("VERSION","2.1");//Y 版本  2.1仅支持合单
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
		bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", order_no);
		bodyMap.put("MERC_ORDER_DATE","");
        bodyMap.put("SUB_MERC_ID", "");//二级商户号
		bodyMap.put("MERC_USER_NO", order_no);
        bodyMap.put("TRANS_AMT", "100");
		bodyMap.put("PLACE_ORDER_IP", "223.104.3.243");//下单IP,手机上网ip
        bodyMap.put("CURRENCY", "CNY");
        bodyMap.put("ORDER_EXP_TIME", "20970724101010");//订单过期时间
        bodyMap.put("TERMINAL_ID", "终端ID");//终端编号
        bodyMap.put("TERMINAL_TYPE", "01");//终端类型
        bodyMap.put("DEVICE_INFO", "01");//交易设备信息
        bodyMap.put("OPEN_ID", "oUpF8uKyV1Ku-gE5GUGI3j2k9Xwk");//微信用户标识,trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。
        bodyMap.put("APPID", "wxd1234567wxd1234567wxd123456712 ");//微信分配的小程序ID   wxd678efh567hg6787&quot;
        bodyMap.put("LIMIT_PAY", "1");//目前仅当微信支付模式下可用。1-限定不能使用信用卡支付    通道方使用
        bodyMap.put("SPLIT_FLAG", "1");// 1：分账        2：不分账（默认） 本期只支持分账
        bodyMap.put("ASSURE_FLAG", "1");// 1：担保        2：不担保（默认） 本期只支持分账
        bodyMap.put("ORDER_SUBJECT", "订单&quot;标题");//订单标题
        bodyMap.put("ORDER_DESC", "订单描述");//订单描述
        bodyMap.put("OPERATOR_ID", "操作员编号");//操作员编号
        bodyMap.put("PAY_CHANNEL", "1");//支付渠道 1-微信，目前只支持微信
		bodyMap.put("NOTIFY_URL", notifyUrl);//后台异步通知
		bodyMap.put("RETURN_URL", "http://www.baidu.com");//前台通知地址
        bodyMap.put("RISK_INFO", "{\"subMercNo\": \"1000001\",\"subMercName\": \"小霸王\",\"subMercTradeCategory\": \"\",\"mercUserRegDate\": \"100\",\"mercUserActiveDays\": \"99\",\"mercUserActiveLevel\":\"1\",\"mercUserRegMobile\":\"13112345678\"}");//风控扩展字段
        bodyMap.put("EXTEND1", "备注EXTEND1");
        bodyMap.put("EXTEND2", "备注EXTEND2");
        bodyMap.put("EXTEND3", "备注EXTEND3");
	}
	
	public void initReqMap_V22() throws Exception {
		infoMap.put("TRX_CODE","100041");//Y 交易代码
		infoMap.put("VERSION","2.2");//Y 版本  2.2 所有类型交易都支持
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
		bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", order_no);
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());
        bodyMap.put("SUB_MERC_ID", "");//二级商户号
		bodyMap.put("MERC_USER_NO", order_no);
        bodyMap.put("TRANS_AMT", "100");
		bodyMap.put("PLACE_ORDER_IP", "223.104.3.243");//下单IP,手机上网ip
        bodyMap.put("CURRENCY", "CNY");
        bodyMap.put("ORDER_EXP_TIME", "20970724101010");//订单过期时间
        bodyMap.put("TERMINAL_ID", "终端ID");//终端编号
        bodyMap.put("TERMINAL_TYPE", "01");//终端类型
        bodyMap.put("DEVICE_INFO", "01");//交易设备信息
        bodyMap.put("OPEN_ID", "oUpF8uKyV1Ku-gE5GUGI3j2k9Xwk");//微信用户标识,trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。
        bodyMap.put("APPID", "wxd1234567wxd1234567wxd123456712");//微信分配的小程序ID   wxd678efh567hg6787&quot;
        bodyMap.put("LIMIT_PAY", "1");//目前仅当微信支付模式下可用。1-限定不能使用信用卡支付    通道方使用
        bodyMap.put("SPLIT_FLAG", "2");// 1：分账        2：不分账（默认） 本期只支持分账
        bodyMap.put("ASSURE_FLAG", "2");// 1：担保        2：不担保（默认） 本期只支持分账
        bodyMap.put("ORDER_SUBJECT", "订单&quot;标题");//订单标题
        bodyMap.put("ORDER_DESC", "订单描述");//订单描述
        bodyMap.put("OPERATOR_ID", "操作员编号");//操作员编号
        bodyMap.put("PAY_CHANNEL", "1");//支付渠道 1-微信，目前只支持微信
		bodyMap.put("NOTIFY_URL", notifyUrl);//后台异步通知
		bodyMap.put("RETURN_URL", "http://www.baidu.com");//前台通知地址
        bodyMap.put("RISK_INFO", "{\"subMercNo\": \"1000001\",\"subMercName\": \"小霸王\",\"subMercTradeCategory\": \"\",\"mercUserRegDate\": \"100\",\"mercUserActiveDays\": \"99\",\"mercUserActiveLevel\":\"1\",\"mercUserRegMobile\":\"13112345678\"}");//风控扩展字段
        bodyMap.put("EXTEND1", "备注EXTEND1");
        bodyMap.put("EXTEND2", "备注EXTEND2");
        bodyMap.put("EXTEND3", "备注EXTEND3");
        bodyMap.put("MERGE_FLAG", "");//1：合单        2：非合单（默认）
        bodyMap.put("SPLIT_FORMULA", "");//分账公式
        bodyMap.put("PRODUCT_DETAIL", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
	}
	@BeforeMethod
	public void setUp() throws Exception {
		mapInit();
	}
	@AfterMethod
	public void tearDown() throws Exception {
	}
	/* 新商户，入驻关系
	 * select * from PAYADM.URMTMINF_RELATION WHERE MERC_ID='800010000020019' AND SUB_MERC_ID='800010000020019' AND PAY_CHANNEL='1'
	   select * from PAYADM.URMTMINF_RELATION WHERE SUB_MERC_ID='800010000020019' AND PAY_CHANNEL='1'
	   update PAYADM.URMTMINF_RELATION set MERC_ID='800010000020019' where SUB_MERC_ID='800010000020019' AND PAY_CHANNEL='1'
	*/
	public String merc_id = "800010000020019";
	public String order_no = orderNo;
	public String order_date = randomUtils.getDate8();
	public String subMercId = "800010000020019";
	/**
	 * 小程序 2.2版本多场景支持
	 * 普通支付
	 */
//	@Test
	public void testAppletCommonPay() throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		initReqMap_V22();
        bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", order_no);
		bodyMap.put("MERC_ORDER_DATE", order_date);
        bodyMap.put("SUB_MERC_ID", subMercId);//二级商户号
		bodyMap.put("MERGE_FLAG", "");//1：合单        2：非合单（默认）
        bodyMap.put("ASSURE_FLAG", "2");// 1：担保       2：不担保（默认）
        bodyMap.put("SPLIT_FLAG", "2");// 1：分账        2：不分账（默认）
        bodyMap.put("TRANS_AMT", "100");//
		logger.info("MERC_ORDER_NO= "+order_no);
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
		//回调
//		new PayBack().payback(merc_id,order_no,order_date);
		//查补
//		new PayBack().innerQuery(merc_id,order_no,order_date);
	}
	/**
	 * 小程序 2.2版本多场景支持
	 * 合单支付新的
	 */
	@Test
	public void testAppletMergePayV22() throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		initReqMap_V22();
        bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
		System.out.println("#####  order_no:"+order_no);
		bodyMap.put("MERC_ORDER_NO", order_no);
		bodyMap.put("MERC_ORDER_DATE", order_date);
        bodyMap.put("SUB_MERC_ID", subMercId);//二级商户号
		bodyMap.put("MERGE_FLAG", "1");//1：合单        2：非合单（默认）
        bodyMap.put("SPLIT_FLAG", "1");// 1：分账        2：不分账（默认） 本期只支持分账
        bodyMap.put("ASSURE_FLAG", "1");// 1：担保        2：不担保（默认） 本期只支持分账
        bodyMap.put("SPLIT_FORMULA", "");
        bodyMap.put("TRANS_AMT", "300");//5笔单总和

        Map<String,String> TRANS_DETAILContent1 = new LinkedHashMap<String, String>();
    	Map<String,Object> TRANS_DETAIL1 = new LinkedHashMap<String, Object>();
		TRANS_DETAILContent1.put("SUB_MERC_ORDER_NO", "1"+order_no);//子订单编号
		TRANS_DETAILContent1.put("BELONG_MERC_ID", merc_id);//分账方、主商户都可以
		TRANS_DETAILContent1.put("SUB_TRANS_AMT", "150");//子订单交易金额
		TRANS_DETAILContent1.put("SUB_NOTIFY_URL", notifyUrl);//子订单交易金额
		TRANS_DETAILContent1.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
		String splitFormula1 = "2,800010000020019,关尔靖,50,分账说明5|1,140000000802,邹国平,10,分账说明1|2,700000000391402,企业商户1,20,分账说明2|3,700000000391404,个体工商户1,30,分账说明3|4,700000000391405,小微商户1,40,分账说明4";
		TRANS_DETAILContent1.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula1.getBytes("utf-8")));//子订单分账公式
		TRANS_DETAIL1.put("TRANS_DETAIL",TRANS_DETAILContent1);

		Map<String,String> TRANS_DETAILContent2 = new LinkedHashMap<String, String>();
		Map<String,Object> TRANS_DETAIL2 = new LinkedHashMap<String, Object>();
		TRANS_DETAILContent2.put("SUB_MERC_ORDER_NO", "2"+order_no);//子订单编号
		TRANS_DETAILContent2.put("BELONG_MERC_ID", merc_id);//分账方、主商户都可以
		TRANS_DETAILContent2.put("SUB_TRANS_AMT", "150");//子订单交易金额
		TRANS_DETAILContent2.put("SUB_NOTIFY_URL", notifyUrl);//子订单交易金额
		TRANS_DETAILContent2.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
		String splitFormula2 = "2,800010000020019,关尔靖,50,分账说明5|1,140000000802,邹国平,10,分账说明1|2,700000000391402,企业商户1,20,分账说明2|3,700000000391404,个体工商户1,30,分账说明3|4,700000000391405,小微商户1,40,分账说明4";
		TRANS_DETAILContent2.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula2.getBytes("utf-8")));//子订单分账公式
		TRANS_DETAIL2.put("TRANS_DETAIL",TRANS_DETAILContent2);

		TRANS_DETAILS.add(TRANS_DETAIL1);
		TRANS_DETAILS.add(TRANS_DETAIL2);
		bodyMap.put("TRANS_AMT", "300");//5笔单总和
		bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);

		logger.info("MERC_ORDER_NO= "+order_no);
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
		//回调
//		new PayBack().payback(merc_id,order_no,order_date);
		//查补
//		new PayBack().innerQuery(merc_id,order_no,order_date);
	}
	/**
	 * 小程序 2.1版本  仅支持合单支付老的
	 */
//	@Test
	public void testAppletMergePayV21() throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		initReqMap_V21();
        bodyMap.put("MERCHANT_ID", merc_id);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", order_no);
		bodyMap.put("MERC_ORDER_DATE", order_date);
        bodyMap.put("SUB_MERC_ID", subMercId);//二级商户号
		bodyMap.put("NOTIFY_URL", notifyUrl);//后台异步通知
		bodyMap.put("RETURN_URL", "http://www.baidu.com");//前台通知地址
        bodyMap.put("TRANS_AMT", "300");//5笔单总和

        Map<String,String> TRANS_DETAILContent1 = new LinkedHashMap<String, String>();
    	Map<String,Object> TRANS_DETAIL1 = new LinkedHashMap<String, Object>();
		TRANS_DETAILContent1.put("SUB_MERC_ORDER_NO", "1"+order_no);//子订单编号
		TRANS_DETAILContent1.put("BELONG_MERC_ID", merc_id);//子订单编号
		TRANS_DETAILContent1.put("SUB_TRANS_AMT", "150");//子订单交易金额
		TRANS_DETAILContent1.put("SUB_NOTIFY_URL", notifyUrl);//子订单交易金额
		TRANS_DETAILContent1.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
		String splitFormula1 = "2,800010000020019,关尔靖,50,分账说明5|1,140000000802,邹国平,10,分账说明1|2,700000000391402,企业商户1,20,分账说明2|3,700000000391404,个体工商户1,30,分账说明3|4,700000000391405,小微商户1,40,分账说明4";
		TRANS_DETAILContent1.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula1.getBytes("utf-8")));//子订单分账公式
		TRANS_DETAIL1.put("TRANS_DETAIL",TRANS_DETAILContent1);
		
		Map<String,String> TRANS_DETAILContent2 = new LinkedHashMap<String, String>();
		Map<String,Object> TRANS_DETAIL2 = new LinkedHashMap<String, Object>();
		TRANS_DETAILContent2.put("SUB_MERC_ORDER_NO", "2"+order_no);//子订单编号
		TRANS_DETAILContent2.put("BELONG_MERC_ID", merc_id);//子订单编号
		TRANS_DETAILContent2.put("SUB_TRANS_AMT", "150");//子订单交易金额
		TRANS_DETAILContent2.put("SUB_NOTIFY_URL", notifyUrl);//子订单交易金额
		TRANS_DETAILContent2.put("PRODUCT_DETAILS", "3#3#小苹果^10.01^4#大苹果^3.03^3#老苹果^2.5^2");//商品详情
		String splitFormula2 = "2,800010000020019,关尔靖,50,分账说明5|1,140000000802,邹国平,10,分账说明1|2,700000000391402,企业商户1,20,分账说明2|3,700000000391404,个体工商户1,30,分账说明3|4,700000000391405,小微商户1,40,分账说明4";
		TRANS_DETAILContent2.put("SUB_SPLIT_FORMULA", Base64.encodeBase64String(splitFormula2.getBytes("utf-8")));//子订单分账公式
		TRANS_DETAIL2.put("TRANS_DETAIL",TRANS_DETAILContent2);
		
		TRANS_DETAILS.add(TRANS_DETAIL1);
		TRANS_DETAILS.add(TRANS_DETAIL2);
        bodyMap.put("TRANS_AMT", "300");//5笔单总和
		bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);

		logger.info("MERC_ORDER_NO= "+order_no);
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
		//回调
//		new PayBack().payback(merc_id,order_no,order_date);
		//查补
//		new PayBack().innerQuery(merc_id,order_no,order_date);
	}

}
