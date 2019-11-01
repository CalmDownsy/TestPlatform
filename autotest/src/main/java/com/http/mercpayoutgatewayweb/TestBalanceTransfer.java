package com.http.mercpayoutgatewayweb;
import java.util.Date;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test  interface 
 * @author zyh
 * @version 2.0
 * Description: 红包余额结转
 * 
 */
public class TestBalanceTransfer  extends RequestMsgUtils {
	private static final Logger logger = LoggerFactory.getLogger(TestBalanceTransfer.class);
	private RandomUtils randomUtils = new RandomUtils();
	private HttpSendUtils http = new HttpSendUtils();
	//    private String url = "http://10.63.11.81:11120/merc-payoutgateway-web/balance/transfer";
	private String url = randomUtils.getProperties("url.properties").getProperty("merc-payoutgateway-web") + "balance/transfer";
	private String notifyUrl = "http://10.63.13.81:12790/simulator-web/mercservice/signAndPayNotify";
	private String orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");

	public void initReqMap() throws Exception {
		orderNo = "ZGP"+DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
		infoMap.put("TRX_CODE","100069");//Y交易代码
		infoMap.put("VERSION","2.0");//Y版本号
		infoMap.put("DATA_TYPE","0");//Y数据格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y请求流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y签名信息
		
		bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y商户订单日期
		bodyMap.put("MERC_ORDER_NO", orderNo);//Y商户订单编号
		bodyMap.put("PAY_USER_TYPE","");//Y付款方类型  2：商户
		bodyMap.put("PAY_NO","");//Y付款方编号 商户编号
		bodyMap.put("RECV_USER_TYPE","");//Y收款方类型  1：个人
		bodyMap.put("RECV_NO","");//Y收款方编号  个人平台用户号
		bodyMap.put("TRANS_AMT","");//Y交易金额
		
		bodyMap.put("EXTEND1","");//N扩展字段
		bodyMap.put("EXTEND2","");//N扩展字段
		bodyMap.put("EXTEND3","");//N扩展字段
	}
	@BeforeMethod
	public void setUp() throws Exception {
		mapInit();
		initReqMap();
	}
	@AfterMethod
	public void tearDown() throws Exception {
	}
	 /**
	  * 余额结转 ： 商户现金转账到用户余额
     * @throws Exception
     */
	@Test
    public void testBalanceTransfer() throws Exception{
        logger.info("开始执行余额划拨成功用例-营销户->小微商户...");
		bodyMap.put("MERCHANT_ID","800010000020019");//Y商户编号
		bodyMap.put("PAY_USER_TYPE","2");//Y付款方类型  2：商户
		bodyMap.put("PAY_NO","800010000020019");//Y付款方编号 商户编号
		bodyMap.put("RECV_USER_TYPE","1");//Y收款方类型  1：个人
		bodyMap.put("RECV_NO","140000005411");//Y收款方编号  个人平台用户号
		bodyMap.put("TRANS_AMT","1.00");//Y交易金额
		respMap = httpSend(http, url, requestMap);
		Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }
}
