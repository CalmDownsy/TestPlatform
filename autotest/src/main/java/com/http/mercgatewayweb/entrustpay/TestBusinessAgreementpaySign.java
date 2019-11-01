package com.http.mercgatewayweb.entrustpay;

import com.http.mercgatewayweb.agreementpay.TestAgreementpayGetsignurl;
import com.interfacetest.commonutils.DBConnectUtils;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/*
 * 业务协议签约
 * 2019-06-27
 * zxb
 */
public class TestBusinessAgreementpaySign extends RequestMsgUtils{
	private static final Logger logger = LoggerFactory.getLogger(TestBusinessAgreementpaySign.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	private String urlsign = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "entrustpay/businessagreementsign";
    private String urlUnsign = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "entrustpay/businessagreementunsign";
    private TestAgreementpayGetsignurl getsignurl = new TestAgreementpayGetsignurl();
	public void testSign() {
        logger.info("1.签约初始化");
		mapInit();
		infoMap.put("TRX_CODE","100062");//Y 交易代码
		infoMap.put("VERSION","2.0");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

		bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
		bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
		bodyMap.put("MARK_TYPE", "");//标记化类型  1：支付协议号 2：绑卡id
		bodyMap.put("MARK_ID", "");//标记化id
		bodyMap.put("MERC_USER_NO", "");//商户用户号
		bodyMap.put("BUSINESS_TYPE", "");//业务类型  1:订阅
		bodyMap.put("BUSINESS_SCENE", "");//业务场景 1:保险缴费2:会员续订3:贷款还款4:主播守护
		bodyMap.put("AGREEMENT_EFFECTIVE_DATE", "");//协议生效日期
		bodyMap.put("AGREEMENT_EXPIR_DATE", "");//N协议失效日期
		bodyMap.put("AGREEMENT_MESSAGE", "");//委托扣款协议信息
		bodyMap.put("OUTSIDE_PRODUCT_NO", "");//N外部产品编号
		bodyMap.put("OUTSIDE_PRODUCT_INFO", "");//N外部产品说明信息

		bodyMap.put("EXTEND1", "备用域EXTEND1");
		bodyMap.put("EXTEND2", "备用域EXTEND2");
		bodyMap.put("EXTEND3", "备用域EXTEND3");
	}
    public void setUpUnsign() throws Exception {
        logger.info("2.解约初始化");
        mapInit();
        infoMap.put("TRX_CODE","100063");//Y 交易代码
        infoMap.put("VERSION","2.0");//Y 版本
        infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
        infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", "800010000020019");//Y 商户编号
        bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MERC_USER_NO", "");//Y 商户用户号
        bodyMap.put("BUSINESS_AGREEMENT_NO", "");//Y 委托扣款协议号
        bodyMap.put("BUSINESS_TYPE", "");//Y 业务类型  1：订阅

        bodyMap.put("EXTEND1", "备用域EXTEND1");
        bodyMap.put("EXTEND2", "备用域EXTEND2");
        bodyMap.put("EXTEND3", "备用域EXTEND3");
    }


    /**
	 *
	 * 支付协议号签约
	 */
	@Test
	public void testsign()throws Exception {
		getsignurl.testGetsignurl();
		Map<String, String> signDb = DBConnectUtils.queryOne("SELECT BUSI_AGREEMENT_NO,MERC_USER_NO,MERC_ID FROM BASEADM.T_B_MERC_SIGN_AGREEMENT WHERE MERC_USER_NO='"+getsignurl.bodyMap.get("MERC_USER_NO")+"'","baseadm");
        testSign();
		bodyMap.put("MERCHANT_ID", signDb.get("MERC_ID"));//Y 商户编号
		bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
		bodyMap.put("MARK_TYPE", "1");//标记化类型  1：支付协议号 2：绑卡id
		bodyMap.put("MARK_ID", signDb.get("BUSI_AGREEMENT_NO"));//标记化id
		bodyMap.put("MERC_USER_NO", signDb.get("MERC_USER_NO"));//商户用户号
		bodyMap.put("BUSINESS_TYPE", "1");//业务类型  1:订阅
		bodyMap.put("BUSINESS_SCENE", "2");//业务场景 1:保险缴费2:会员续订3:贷款还款4:主播守护
		bodyMap.put("AGREEMENT_EFFECTIVE_DATE", randomUtils.getDate8());//协议生效日期
		bodyMap.put("AGREEMENT_EXPIR_DATE", "");//N协议失效日期
		bodyMap.put("AGREEMENT_MESSAGE", "关尔靖");//委托扣款协议信息
		bodyMap.put("OUTSIDE_PRODUCT_NO", "");//N外部产品编号
		bodyMap.put("OUTSIDE_PRODUCT_INFO", "");//N外部产品说明信息
		requestMap = getRequestMap();
		respMap = http.sendHttpRequest(requestMap, urlsign);
		logger.info("respMap:{}",respMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));

		//解约
        Map<String, String> unsignsignDb = DBConnectUtils.queryOne("SELECT MERC_ID,MERC_USER_NO,PROTOCOL_NO FROM BASEADM.T_B_MERC_ENTRUST_USER_PROTOCOL WHERE MERC_USER_NO='"+ signDb.get("MERC_USER_NO")+"'","baseadm");
        setUpUnsign();
        bodyMap.put("MERCHANT_ID", unsignsignDb.get("MERC_ID"));//Y 商户编号
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MERC_USER_NO", unsignsignDb.get("MERC_USER_NO"));//Y 商户用户号
        bodyMap.put("BUSINESS_AGREEMENT_NO", unsignsignDb.get("PROTOCOL_NO"));//Y 委托扣款协议号
        bodyMap.put("BUSINESS_TYPE", "1");//Y 业务类型  1：订阅

        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, urlUnsign);
        logger.info("respMap:{}",respMap);
        Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }

}
