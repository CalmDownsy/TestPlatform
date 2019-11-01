package com.http.mercpayoutgatewayweb;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.commonutils.http.XmlReverseUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TestPayoutMarkBatchPay
 * @Description 批量标记化代付（100051）
 * @Author owen.liu
 * @Date 2018.12.18 10:29
 * @Version 1.0
 */
public class TestPayoutMarkBatchPay extends RequestMsgUtils {

    private static Logger logger = LoggerFactory.getLogger(TestPayoutMarkBatchPay.class);
    private RandomUtils randomUtils = new RandomUtils();
    private HttpSendUtils http = new HttpSendUtils();
    private String reqMarkbatchpayUrl = randomUtils.getProperties("url.properties").getProperty("merc-payoutgateway-web") + "payout/markbatchpay";
//    private String mercBatchNo = null;
    @BeforeMethod
    public void setUp() throws Exception {
        mapInit();
        infoMap.put("TRX_CODE", "100051");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("LEVEL", "0");
        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));
        infoMap.put("SIGNED_MSG", "signedMsg");
        transSumMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);
        transSumMap.put("MERC_BATCH_NO", randomUtils.getRandomTime(20));
        transSumMap.put("MERC_BATCH_DATE", randomUtils.getDate8());
        transSumMap.put("BUSINESS_CODE", "09902");
        transSumMap.put("NOTIFY_URL", "http://10.63.13.21:12790/simulator-web/mercservice/payOutBackNotify");
        transSumMap.put("TERMINAL_TYPE", "01");
        transSumMap.put("TERMINALID", "T"+randomUtils.getRandomTime(18));
        transSumMap.put("DEVICE_INFO", "912121001|898600680113F0123014|968778695A4B|116.360207,-39.921064|");
        transSumMap.put("EXTEND1", "");
        transSumMap.put("EXTEND2", "");
        transSumMap.put("EXTEND3", "");
    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testMarkbatchpay() throws InterruptedException {
        logger.info("###  开始执行 批量标记化代付（100051）（merc-payoutgateway-web/payout/markbatchpay） 用例...");
        transSumMap.put("TOTAL_NUM", "1");
        transSumMap.put("TOTAL_AMT", "0.08");
        //借记卡-第1笔明细
        Map<String, Object> tempTransDetailMap = new HashMap<String, Object>();
        String mercOrderNo = randomUtils.getRandomTime(19);
        tempTransDetailMap.put("MERC_ORDER_NO", mercOrderNo);
        tempTransDetailMap.put("MARK_TYPE", "1");//1-协议号，2-绑卡id
        tempTransDetailMap.put("MARK_ID", "19190614102739856100010010");//标记化id
        tempTransDetailMap.put("MERC_USER_NO", "U20190614102634710");
        tempTransDetailMap.put("TRANS_AMT", "0.08");
        tempTransDetailMap.put("CURRENCY", "CNY");
        tempTransDetailMap.put("REMARK", "借记卡批付");
        tempTransDetailMap.put("REMARK2", "备注2");
        tempTransDetailMap.put("REMARK3", "备注3");
        tempTransDetailMap.put("EXTEND1", "");
        tempTransDetailMap.put("EXTEND2", "");
        tempTransDetailMap.put("EXTEND3", "");
        String transDetail = new String("TRANS_DETAIL");
        transDetailsMap.put(transDetail, tempTransDetailMap);
        transDetailsXml = XmlReverseUtils.callMap2XML(transDetailsMap);
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap, reqMarkbatchpayUrl);
        respMap.put("SIGNED_MSG","");
        logger.info("批量标记化付款调用结果：{}", respMap);
        Assert.assertNotNull(respMap.get("RET_CODE"));
        Assert.assertEquals(respMap.get("RET_CODE"), "0000", "批量付款受理失败！");
        Assert.assertEquals(respMap.get("RET_MSG"), "受理成功", "批量付款受理失败！");
    }
}
