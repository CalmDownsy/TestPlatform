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
 * 
 * Description: 合单支付-担保撤销
 * 只能一个子单一个子单的撤销
 * 可以部分撤销+费用返还
 * 可以不扣服务费
 */

public class TestMergePayRevoke extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestMergePayRevoke.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11110/merc-gateway-web/mergepay/revoke";
	private String url=randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"mergepay/revoke";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	//	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/backNotify"
	private String orderNo = "MergeRevoke"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
	private TestQueryPayin query = new TestQueryPayin();

	public void initReqMap() throws Exception {
		infoMap.put("TRX_CODE","100040");//Y 交易代码
		infoMap.put("VERSION","2.0");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
		
		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", orderNo);
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("MERC_APPLY_ORDER_NO", "");//商户担保申请订单号
		bodyMap.put("MERC_APPLY_ORDER_DATE","");//商户担保申请订单日期
		bodyMap.put("NOTIFY_URL", "http://10.63.13.81:12790/simulator-web/mercservice/backNotify");//后台异步通知
		bodyMap.put("SVR_FEE_NOTIFY_URL", "http://10.63.13.81:12790/simulator-web/merc/notify");//服务费通知地址
        bodyMap.put("EXTEND1", "");
        bodyMap.put("EXTEND2", "");
        bodyMap.put("EXTEND3", "");
	}

	@BeforeMethod
	public void setUp() throws Exception {
		mapInit();
		initReqMap();
		respMap.clear();
	}
	@AfterMethod
	public void tearDown() throws Exception {
	}

	/**
	 * APP合单担保撤销
	 * 全部撤销
	 * 通道需要打转，否则撤销会失败，银行通道真正无数据
	 */
//	@Test
	public void testMergePayRevokeAppAll()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		TestAppPay mergApp = new TestAppPay();
		mergApp.testAppPayMergePay();
		String orig_order_no = mergApp.order_no;
		Thread.sleep(15*1000);
		String orderState = "";
		for (int j=0; j<5; j++) {
			query.queryPayin(mergApp.merc_id, mergApp.order_no, mergApp.order_date);
			orderState = query.respMap.get("ORDER_STATUS").toString();
			logger.info("orderState=" + orderState);
			if (orderState == "S" || orderState.equals("S"))
				break;
			Thread.sleep(5 * 1000);
		}
		Assert.assertEquals( orderState,"S", "支付订单未成功，无法后续流程");
		for(int i=0; i<2; i++){
			setUp();
			orderNo = "MergeRevoke"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
			bodyMap.put("MERCHANT_ID", mergApp.bodyMap.get("MERCHANT_ID"));//Y 商户编号
			bodyMap.put("MERC_ORDER_NO", orderNo);
			bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
			bodyMap.put("MERC_APPLY_ORDER_NO", orig_order_no);//商户担保申请订单号
			bodyMap.put("MERC_APPLY_ORDER_DATE", mergApp.bodyMap.get("MERC_ORDER_DATE"));//商户担保申请订单日期
			Map<String,String> TRANS_DETAILContent1 = new LinkedHashMap<String, String>();
			Map<String,Object> TRANS_DETAIL1 = new LinkedHashMap<String, Object>();
			ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();
			TRANS_DETAILContent1.put("SUB_MERC_APPLY_ORDER_NO", (i+1)+orig_order_no);//原担保申请商户子订单号  本笔单
			TRANS_DETAILContent1.put("TRANS_AMT", "150");//撤销金额
//			String returnFeeFormula1 = "1,140000000802,邹国平,1,费用返还说明1|2,700000000391402,企业商户1,2,费用返还说明1|3,700000000391404,个体工商户1,3,费用返还说明1|4,700000000391405,小微商户1,4,费用返还说明1";
//			TRANS_DETAILContent1.put("RETURN_FEE_FORMULA", Base64.encodeBase64String(returnFeeFormula1.getBytes("utf-8")));//费用返还公式，格式与分账公式相同
//			String svrFeeFormula1 = "1,140000000802,邹国平,1,服务费划扣说明1|2,700000000391402,企业商户1,2,服务费划扣说明1|3,700000000391404,个体工商户1,3,服务费划扣说明1|4,700000000391405,小微商户1,4,服务费划扣说明1";
//			TRANS_DETAILContent1.put("SVR_FEE_FORMULA", Base64.encodeBase64String(svrFeeFormula1.getBytes("utf-8")));//服务费收取公式，格式与分账公式相同
			TRANS_DETAIL1.put("TRANS_DETAIL",TRANS_DETAILContent1);
			TRANS_DETAILS.add(TRANS_DETAIL1);
			bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);
			logger.info("MERC_ORDER_NO= "+orderNo);
			respMap = httpSend(http, url, requestMap);
			Assert.assertEquals("6666", respMap.get("RET_CODE"));
			Thread.sleep(38*1000);
			String revokeOrderState = "";
			for (int j=0; j<5; j++){
				query.queryPayin(mergApp.merc_id, orderNo ,bodyMap.get("MERC_ORDER_DATE").toString() );
				revokeOrderState = query.respMap.get("ORDER_STATUS").toString();
				logger.info("revokeOrderState="+revokeOrderState);
				if(revokeOrderState == "S" || revokeOrderState.equals("S"))
					break;
				Thread.sleep(5*1000);
			}
			Assert.assertEquals( revokeOrderState,"S", "撤销订单未成功");
		}
	}

	/**
	 * APP合单担保撤销
	 * 费用返还+划服务费
	 * 通道需要打转，否则撤销会失败，银行通道真正无数据
	 */
//	@Test
	public void testMergePayRevokeAppPart()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		TestAppPay mergApp = new TestAppPay();
		mergApp.testAppPayMergePay();
		String orig_order_no = mergApp.order_no;
		Thread.sleep(15*1000);
		String orderState = "";
		for (int j=0; j<5; j++) {
			query.queryPayin(mergApp.merc_id, mergApp.order_no, mergApp.order_date);
			orderState = query.respMap.get("ORDER_STATUS").toString();
			logger.info("orderState=" + orderState);
			if (orderState == "S" || orderState.equals("S"))
				break;
			Thread.sleep(5 * 1000);
		}
		Assert.assertEquals(orderState,"S","支付订单未成功，无法后续流程" );
		for(int i=0; i<2; i++){
			setUp();
			orderNo = "MergeRevoke"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
			bodyMap.put("MERCHANT_ID", mergApp.bodyMap.get("MERCHANT_ID"));//Y 商户编号
			bodyMap.put("MERC_ORDER_NO", orderNo);
			bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
			bodyMap.put("MERC_APPLY_ORDER_NO", orig_order_no);//商户担保申请订单号
			bodyMap.put("MERC_APPLY_ORDER_DATE", mergApp.bodyMap.get("MERC_ORDER_DATE"));//商户担保申请订单日期
			Map<String,String> TRANS_DETAILContent1 = new LinkedHashMap<String, String>();
			Map<String,Object> TRANS_DETAIL1 = new LinkedHashMap<String, Object>();
			ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();
			TRANS_DETAILContent1.put("SUB_MERC_APPLY_ORDER_NO", (i+1)+orig_order_no);//原担保申请商户子订单号  本笔单
			TRANS_DETAILContent1.put("TRANS_AMT", "140");//撤销金额
			String returnFeeFormula1 = "1,140000000802,邹国平,1,费用返还说明1|2,700000000391402,企业商户1,2,费用返还说明1|3,700000000391404,个体工商户1,3,费用返还说明1|4,700000000391405,小微商户1,4,费用返还说明1";
			TRANS_DETAILContent1.put("RETURN_FEE_FORMULA", Base64.encodeBase64String(returnFeeFormula1.getBytes("utf-8")));//费用返还公式，格式与分账公式相同
			String svrFeeFormula1 = "1,140000000802,邹国平,1,服务费划扣说明1|2,700000000391402,企业商户1,2,服务费划扣说明1|3,700000000391404,个体工商户1,3,服务费划扣说明1|4,700000000391405,小微商户1,4,服务费划扣说明1";
			TRANS_DETAILContent1.put("SVR_FEE_FORMULA", Base64.encodeBase64String(svrFeeFormula1.getBytes("utf-8")));//服务费收取公式，格式与分账公式相同
			TRANS_DETAIL1.put("TRANS_DETAIL",TRANS_DETAILContent1);
			TRANS_DETAILS.add(TRANS_DETAIL1);
			bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);
			logger.info("MERC_ORDER_NO= "+orderNo);
			respMap = httpSend(http, url, requestMap);
			Assert.assertEquals("6666", respMap.get("RET_CODE"));
			Thread.sleep(38*1000);
			String revokeOrderState = "";
			for (int j=0; j<5; j++){
				query.queryPayin(mergApp.merc_id, orderNo ,bodyMap.get("MERC_ORDER_DATE").toString() );
				revokeOrderState = query.respMap.get("ORDER_STATUS").toString();
				logger.info("revokeOrderState="+revokeOrderState);
				if(revokeOrderState == "S" || revokeOrderState.equals("S"))
					break;
				Thread.sleep(5*1000);
			}
			Assert.assertEquals(revokeOrderState,"S", "撤销订单未成功");
		}
	}
	/**
	 * 微信小程序合单担保撤销
	 * 全部撤销
	 * 通道需要打转，否则撤销会失败，银行通道真正无数据
	 */
//	@Test
	public void testMergePayRevokeAppletAll()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		TestMergePayApplet mergApp = new TestMergePayApplet();
		mergApp.testAppletMergePayV21();
		String orig_order_no = mergApp.order_no;
		Thread.sleep(15*1000);
		String orderState = "";
		for (int j=0; j<5; j++) {
			query.queryPayin(mergApp.merc_id, mergApp.order_no, mergApp.order_date);
			orderState = query.respMap.get("ORDER_STATUS").toString();
			logger.info("orderState=" + orderState);
			if (orderState == "S" || orderState.equals("S"))
				break;
			Thread.sleep(5 * 1000);
		}
		Assert.assertEquals(orderState,"S","支付订单未成功，无法后续流程" );
		for(int i=0; i<1; i++){
			setUp();
			orderNo = "MergeRevoke"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
			bodyMap.put("MERCHANT_ID", mergApp.bodyMap.get("MERCHANT_ID"));//Y 商户编号
			bodyMap.put("MERC_ORDER_NO", orderNo);
			bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
			bodyMap.put("MERC_APPLY_ORDER_NO", orig_order_no);//商户担保申请订单号
			bodyMap.put("MERC_APPLY_ORDER_DATE", mergApp.bodyMap.get("MERC_ORDER_DATE"));//商户担保申请订单日期
			Map<String,String> TRANS_DETAILContent1 = new LinkedHashMap<String, String>();
			Map<String,Object> TRANS_DETAIL1 = new LinkedHashMap<String, Object>();
			ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();
			TRANS_DETAILContent1.put("SUB_MERC_APPLY_ORDER_NO", (i+1)+orig_order_no);//原担保申请商户子订单号  本笔单
			TRANS_DETAILContent1.put("TRANS_AMT", "150");//撤销金额
//			String returnFeeFormula1 = "1,140000000802,邹国平,1,费用返还说明1|2,700000000391402,企业商户1,2,费用返还说明1|3,700000000391404,个体工商户1,3,费用返还说明1|4,700000000391405,小微商户1,4,费用返还说明1";
//			TRANS_DETAILContent1.put("RETURN_FEE_FORMULA", Base64.encodeBase64String(returnFeeFormula1.getBytes("utf-8")));//费用返还公式，格式与分账公式相同
//			String svrFeeFormula1 = "1,140000000802,邹国平,1,服务费划扣说明1|2,700000000391402,企业商户1,2,服务费划扣说明1|3,700000000391404,个体工商户1,3,服务费划扣说明1|4,700000000391405,小微商户1,4,服务费划扣说明1";
//			TRANS_DETAILContent1.put("SVR_FEE_FORMULA", Base64.encodeBase64String(svrFeeFormula1.getBytes("utf-8")));//服务费收取公式，格式与分账公式相同
			TRANS_DETAIL1.put("TRANS_DETAIL",TRANS_DETAILContent1);
			TRANS_DETAILS.add(TRANS_DETAIL1);
			bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);
			logger.info("MERC_ORDER_NO= "+orderNo);
			respMap = httpSend(http, url, requestMap);
			Assert.assertEquals("6666", respMap.get("RET_CODE"));
			Thread.sleep(38*1000);
			String revokeOrderState = "";
			for (int j=0; j<5; j++){
				query.queryPayin(mergApp.merc_id, orderNo ,bodyMap.get("MERC_ORDER_DATE").toString() );
				revokeOrderState = query.respMap.get("ORDER_STATUS").toString();
				logger.info("revokeOrderState="+revokeOrderState);
				if(revokeOrderState == "S" || revokeOrderState.equals("S"))
					break;
				Thread.sleep(5*1000);
			}
			Assert.assertEquals(revokeOrderState,"S", "撤销订单未成功");
		}
	}
	/**
	 * APP合单担保撤销
	 * 费用返还+划服务费
	 * 通道需要打转，否则撤销会失败，银行通道真正无数据
	 */
	@Test
	public void testMergePayRevokeAppletPart()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		TestMergePayApplet mergApp = new TestMergePayApplet();
		mergApp.testAppletMergePayV21();
		String orig_order_no = mergApp.order_no;
		Thread.sleep(15*1000);
		String orderState = "";
		for (int j=0; j<5; j++) {
			query.queryPayin(mergApp.merc_id, mergApp.order_no, mergApp.order_date);
			orderState = query.respMap.get("ORDER_STATUS").toString();
			logger.info("orderState=" + orderState);
			if (orderState == "S" || orderState.equals("S"))
				break;
			Thread.sleep(5 * 1000);
		}
		Assert.assertEquals(orderState,"S","支付订单未成功，无法后续流程" );
		for(int i=0; i<1; i++){
			setUp();
			orderNo = "MergeRevoke"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
			bodyMap.put("MERCHANT_ID", mergApp.bodyMap.get("MERCHANT_ID"));//Y 商户编号
			bodyMap.put("MERC_ORDER_NO", orderNo);
			bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
			bodyMap.put("MERC_APPLY_ORDER_NO", orig_order_no);//商户担保申请订单号
			bodyMap.put("MERC_APPLY_ORDER_DATE", mergApp.bodyMap.get("MERC_ORDER_DATE"));//商户担保申请订单日期
			Map<String,String> TRANS_DETAILContent1 = new LinkedHashMap<String, String>();
			Map<String,Object> TRANS_DETAIL1 = new LinkedHashMap<String, Object>();
			ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();
			TRANS_DETAILContent1.put("SUB_MERC_APPLY_ORDER_NO", (i+1)+orig_order_no);//原担保申请商户子订单号  本笔单
			TRANS_DETAILContent1.put("TRANS_AMT", "140");//撤销金额
			String returnFeeFormula1 = "1,140000000802,邹国平,1,费用返还说明1|2,700000000391402,企业商户1,2,费用返还说明1|3,700000000391404,个体工商户1,3,费用返还说明1|4,700000000391405,小微商户1,4,费用返还说明1";
			TRANS_DETAILContent1.put("RETURN_FEE_FORMULA", Base64.encodeBase64String(returnFeeFormula1.getBytes("utf-8")));//费用返还公式，格式与分账公式相同
			String svrFeeFormula1 = "1,140000000802,邹国平,1,服务费划扣说明1|2,700000000391402,企业商户1,2,服务费划扣说明1|3,700000000391404,个体工商户1,3,服务费划扣说明1|4,700000000391405,小微商户1,4,服务费划扣说明1";
			TRANS_DETAILContent1.put("SVR_FEE_FORMULA", Base64.encodeBase64String(svrFeeFormula1.getBytes("utf-8")));//服务费收取公式，格式与分账公式相同
			TRANS_DETAIL1.put("TRANS_DETAIL",TRANS_DETAILContent1);
			TRANS_DETAILS.add(TRANS_DETAIL1);
			bodyMap.put("TRANS_DETAILS", TRANS_DETAILS);
			logger.info("MERC_ORDER_NO= "+orderNo);
			respMap = httpSend(http, url, requestMap);
			Assert.assertEquals("6666", respMap.get("RET_CODE"));
			Thread.sleep(38*1000);
			String revokeOrderState = "";
			for (int j=0; j<5; j++){
				query.queryPayin(mergApp.merc_id, orderNo ,bodyMap.get("MERC_ORDER_DATE").toString() );
				revokeOrderState = query.respMap.get("ORDER_STATUS").toString();
				logger.info("revokeOrderState="+revokeOrderState);
				if(revokeOrderState == "S" || revokeOrderState.equals("S"))
					break;
				Thread.sleep(5*1000);
			}
			Assert.assertEquals(revokeOrderState,"S","撤销订单未成功" );
		}
	}
}
