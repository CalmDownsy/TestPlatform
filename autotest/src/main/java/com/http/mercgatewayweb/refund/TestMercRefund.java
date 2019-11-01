package com.http.mercgatewayweb.refund;

import com.http.mercgatewayweb.agreementpay.TestAgreementPayGetpayurl;
import com.http.mercgatewayweb.agreementpay.TestAgreementpayDirectpay;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.commons.codec.binary.Base64;


/**
 * @ClassName TestMercRefund
 * @Description TODO
 * @Author owen.liu
 * @Date 2019.6.13 16:40
 * @Version 1.0
 */
public class TestMercRefund extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestMercRefund.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrlRefund = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "refund/mercrefund";
    private String reqUrlRefundQuery = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "refund/refundquery";
    private String mercId = AutoConstant.MERC_ID_40001;
    public void initRefund() {
        mapInit();
        infoMap.put("TRX_CODE","100003");//Y 交易代码 定值
        infoMap.put("VERSION","2.0");//Y 版本 定值
        infoMap.put("DATA_TYPE","0");//Y 数据格式 定值
        infoMap.put("REQ_SN",randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("ORIG_MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("ORIG_MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MERC_REFUND_NO",randomUtils.getDateTime17());//Y 商户订单日期
        bodyMap.put("MERC_REFUND_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("NOTIFY_URL","http://10.63.13.81:12790/simulator-web/mercservice/backNotify");//N 后台通知地址
        bodyMap.put("REFUND_FORMULA","00");//C 退款公式 原交易是分账交易时必传
        bodyMap.put("REFUND_AMT","0.04");//退款金额
        bodyMap.put("CURRENCY","CNY");//N 币种
        bodyMap.put("REMARK","退款备注");//N 备注
        bodyMap.put("EXTEND","备用");//N 备用
    }
    public void initRefundQuery() {
        mapInit();
        infoMap.put("TRX_CODE","200003");//Y 交易代码 定值
        infoMap.put("VERSION","2.0");//Y 版本 定值
        infoMap.put("DATA_TYPE","0");//Y 数据格式 定值
        infoMap.put("REQ_SN",randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("MERC_REFUND_NO","");//Y 商户订单日期
        bodyMap.put("MERC_REFUND_DATE","");//Y 商户订单日期
    }
    @Test
    public void testMercRefund() throws Exception {
        logger.info("开始执行 退款（100003）（merc-gateway-web/refund/mercfund） 用例...");
        TestAgreementPayGetpayurl getpayurl = new TestAgreementPayGetpayurl();
        getpayurl.testGetPayUrl_Ordinary();
        initRefund();
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("ORIG_MERC_ORDER_NO",getpayurl.bodyMap.get("MERC_ORDER_NO"));//Y 原商户订单编号
        bodyMap.put("ORIG_MERC_ORDER_DATE",randomUtils.getDate8());//Y 原商户订单日期
        bodyMap.put("REFUND_FORMULA","");//C 退款公式 原交易是分账交易时必传
        bodyMap.put("REFUND_AMT","0.02");//退款金额
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlRefund);
        respMap.put("SIGNED_MSG","");
        logger.info("退款响应：{}",respMap.toString());
        logger.info("判断退款是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"6666");
        logger.info("开始执行 退款订单查询（200003）（merc-gateway-web/refund/refundquery） 用例...");
        String refundOrderNo = respMap.get("MERC_REFUND_NO");
        String mercRefundDate = respMap.get("MERC_REFUND_DATE");
        initRefundQuery();
        bodyMap.put("MERC_REFUND_NO",refundOrderNo);//Y 商户订单日期
        bodyMap.put("MERC_REFUND_DATE",mercRefundDate);//Y 商户订单日期
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlRefundQuery);
        respMap.put("SIGNED_MSG","");
        logger.info("退款订单查询响应：{}",respMap.toString());
        logger.info("判断退款订单查询是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));

        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        Assert.assertEquals(respMap.get("REFUND_STATUS"),"S");
    }
    //退款查询接口

    /**
     * 红包退款
     */
    @Test
    public void TestRefund_RedPackage()throws Exception {
        TestAgreementpayDirectpay pay = new TestAgreementpayDirectpay();
        pay.setUp();
        pay.testAgreementpayDirectpay_RedPackage();
        initRefund();
        bodyMap.put("MERCHANT_ID", pay.bodyMap.get("MERCHANT_ID").toString());//Y 商户编号
        bodyMap.put("ORIG_MERC_ORDER_DATE", pay.bodyMap.get("MERC_ORDER_DATE").toString());
        bodyMap.put("ORIG_MERC_ORDER_NO", pay.bodyMap.get("MERC_ORDER_NO").toString());
        bodyMap.put("REFUND_AMT", pay.bodyMap.get("TRANS_AMT").toString());//
        bodyMap.put("REFUND_FORMULA","");//C 退款公式 原交易是分账交易时必传
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlRefund);
        Assert.assertEquals(respMap.get("RET_CODE"),"0000" );
    }

    /**
     * 红包退款
     */
    @Test
    public void TestRefund_marketing()throws Exception {
        TestAgreementpayDirectpay pay = new TestAgreementpayDirectpay();
        pay.setUp();
        pay.testAgreementpayDirectpay_marketing();
        initRefund();
        bodyMap.put("MERCHANT_ID", pay.bodyMap.get("MERCHANT_ID").toString());//Y 商户编号
        bodyMap.put("ORIG_MERC_ORDER_DATE", pay.bodyMap.get("MERC_ORDER_DATE").toString());
        bodyMap.put("ORIG_MERC_ORDER_NO", pay.bodyMap.get("MERC_ORDER_NO").toString());
        bodyMap.put("REFUND_AMT", pay.bodyMap.get("TRANS_AMT").toString());//
        bodyMap.put("REFUND_FORMULA","");//C 退款公式 原交易是分账交易时必传
        String marketingRetrunFormula = "800010000020020,1|800010000020019,2";
        bodyMap.put("MARKETING_RETURN_FORMULA", Base64.encodeBase64String(marketingRetrunFormula.getBytes("utf-8")));// 营销退回公式
        bodyMap.put("MARKETING_REFUND_AMT", "3");// 营销退回总金额

        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlRefund);
        Assert.assertEquals(respMap.get("RET_CODE"),"0000" );
    }
}
