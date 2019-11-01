package com.http.mercgatewayweb.mergePay;

import java.util.ArrayList;
import java.util.Date;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 合单支付-担保确认
 * 担保确认
 * 只能一个子单一个子单的确认
 */

public class TestMergePayConfirm extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestMergePayConfirm.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11110/merc-gateway-web/mergepay/confirm";
	private String url=randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"mergepay/confirm";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	//	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/backNotify"
	private String orderNo = "MergConfirm"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
	private ArrayList<Object> TRANS_DETAILS = new ArrayList<Object>();
	private TestQueryPayin query = new TestQueryPayin();

	public void initReqMap() throws Exception {
		infoMap.put("TRX_CODE","100039");//Y 交易代码
		infoMap.put("VERSION","2.0");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", orderNo);
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("MERC_APPLY_ORDER_NO", "");//商户担保申请订单号
		bodyMap.put("MERC_APPLY_ORDER_DATE", "");//商户担保申请订单日期
		bodyMap.put("ASSURE_CONFIRM_FORMULA", "");//担保确认公式:原担保申请商户子订单号1,子订单号2,子订单号3,...子订单号n
        bodyMap.put("NOTIFY_URL", notifyUrl);//后台异步通知
        bodyMap.put("EXTEND1", "111");
        bodyMap.put("EXTEND2", "222");
        bodyMap.put("EXTEND3", "333");

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
	 * APP合单担保确认
	 * 通道需要打转，否则撤销会失败，银行通道真正无数据
	 */
//	@Test
	public void testMergePayConfirmApp()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		TestAppPay app = new TestAppPay();
		app.testAppPayMergePay();
		String orig_order_no = app.order_no;
		Thread.sleep(15*1000);
		for (int j=0; j<5; j++){
			query.queryPayin(app.merc_id, app.order_no , app.order_date);
			String orderState = query.respMap.get("ORDER_STATUS").toString();
			logger.info("orderState="+orderState);
			if(orderState == "S" || orderState.equals("S")) break;
			Thread.sleep(5*1000);
		}
		for(int i=0; i<2; i++){
			setUp();
			orderNo = "MergConfirm"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
			bodyMap.put("MERCHANT_ID", app.bodyMap.get("MERCHANT_ID"));//Y 商户编号
			bodyMap.put("MERC_ORDER_NO", orderNo);
			bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
			bodyMap.put("MERC_APPLY_ORDER_NO", orig_order_no);//商户担保申请订单号
			bodyMap.put("MERC_APPLY_ORDER_DATE", app.bodyMap.get("MERC_ORDER_DATE"));//商户担保申请订单日期
			bodyMap.put("ASSURE_CONFIRM_FORMULA", (i+1)+orig_order_no);//担保确认公式:原担保申请商户子订单号1,子订单号2,子订单号3,...子订单号n
			logger.info("MERC_ORDER_NO= "+orderNo);
			respMap = httpSend(http, url, requestMap);
			Assert.assertEquals("0000", respMap.get("RET_CODE"));
		}
	}
	/**
	 * 微信小程序合单担保确认V2.1版本
	 * 通道需要打转，否则撤销会失败，银行通道真正无数据
	 */
	@Test
	public void testMergePayConfirmAppletV21()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		TestMergePayApplet applet = new TestMergePayApplet();
		applet.testAppletMergePayV21();
		String orig_order_no = applet.order_no;
		Thread.sleep(15*1000);
		for (int j=0; j<5; j++){
			query.queryPayin(applet.merc_id, applet.order_no , applet.order_date);
			String orderState = query.respMap.get("ORDER_STATUS").toString();
			logger.info("orderState="+orderState);
			if(orderState == "S" || orderState.equals("S")) break;
			Thread.sleep(5*1000);
		}
		for(int i=0; i<2; i++){
			setUp();
			orderNo = "MergConfirm"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
			bodyMap.put("MERCHANT_ID", applet.bodyMap.get("MERCHANT_ID"));//Y 商户编号
			bodyMap.put("MERC_ORDER_NO", orderNo);
			bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
			bodyMap.put("MERC_APPLY_ORDER_NO", orig_order_no);//商户担保申请订单号
			bodyMap.put("MERC_APPLY_ORDER_DATE", applet.bodyMap.get("MERC_ORDER_DATE"));//商户担保申请订单日期
			bodyMap.put("ASSURE_CONFIRM_FORMULA", (i+1)+orig_order_no);//担保确认公式:原担保申请商户子订单号1,子订单号2,子订单号3,...子订单号n
			logger.info("MERC_ORDER_NO= "+orderNo);

			respMap = httpSend(http, url, requestMap);
			Assert.assertEquals("0000", respMap.get("RET_CODE"));
		}
	}

	/**
	 * 微信小程序合单担保确认V2.2版本
	 * 通道需要打转，否则撤销会失败，银行通道真正无数据
	 */
//	@Test
	public void testMergePayConfirmAppletV22()throws Exception {
		logger.info("###"+Thread.currentThread().getStackTrace()[1].getMethodName()+" case is running");

		TestMergePayApplet applet = new TestMergePayApplet();
		applet.testAppletMergePayV22();
		String orig_order_no = applet.order_no;
		Thread.sleep(15*1000);
		for (int j=0; j<5; j++){
			query.queryPayin(applet.merc_id, applet.order_no , applet.order_date);
			String orderState = query.respMap.get("ORDER_STATUS").toString();
			logger.info("orderState="+orderState);
			if(orderState == "S" || orderState.equals("S")) break;
			Thread.sleep(5*1000);
		}
		for(int i=0; i<2; i++){
			setUp();
			orderNo = "MergConfirm"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
			bodyMap.put("MERCHANT_ID", applet.bodyMap.get("MERCHANT_ID"));//Y 商户编号
			bodyMap.put("MERC_ORDER_NO", orderNo);
			bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
			bodyMap.put("MERC_APPLY_ORDER_NO", orig_order_no);//商户担保申请订单号
			bodyMap.put("MERC_APPLY_ORDER_DATE", applet.bodyMap.get("MERC_ORDER_DATE"));//商户担保申请订单日期
			bodyMap.put("ASSURE_CONFIRM_FORMULA", (i+1)+orig_order_no);//担保确认公式:原担保申请商户子订单号1,子订单号2,子订单号3,...子订单号n
			logger.info("MERC_ORDER_NO= "+orderNo);

			respMap = httpSend(http, url, requestMap);
			Assert.assertEquals("0000", respMap.get("RET_CODE"));
		}
	}
}
