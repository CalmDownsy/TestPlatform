package com.http.mercpayoutgatewayweb;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.commonutils.http.XmlReverseUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestPayoutBatchPay
 * @Description 批量代付（100000）
 * @Author owen.liu
 * @Date 2019.6.13 15:37
 * @Version 1.0
 */
public class TestPayoutBatchPay extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestPayoutBatchPay.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").get("merc-payoutgateway-web") + "payout/batchpay";
    private String mercBatchNo = null;
    private List<String> mercOrderNoList = new ArrayList<String>();

    @BeforeMethod
    public void setUp() throws Exception {
        mapInit();
        mercOrderNoList.clear();
        infoMap.put("TRX_CODE", "100000");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("LEVEL", "0");
        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));
        infoMap.put("SIGNED_MSG", "signedMsg");
        transSumMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);
        mercBatchNo = randomUtils.getRandomTime(20);
        transSumMap.put("MERC_BATCH_NO", mercBatchNo);
        transSumMap.put("MERC_BATCH_DATE", randomUtils.getDate8());
        transSumMap.put("BUSINESS_CODE", "09902");
        transSumMap.put("NOTIFY_URL", "");
        transSumMap.put("EXTEND1", "");
        transSumMap.put("EXTEND2", "");
        transSumMap.put("EXTEND3", "");
    }

    @AfterMethod
    public void tearDown() throws Exception {
    }

    /**
     * 分别为：借记卡、贷记卡、存折、对公账户
     * @throws InterruptedException
     */
    @Test
    public void testBatchPaySuccess_BankCard() throws InterruptedException {
        logger.info("开始执行 批量代付（100000）（merc-payoutgateway-web/payout/batchpay） 用例...");
        transSumMap.put("TOTAL_NUM", "2");
        transSumMap.put("TOTAL_AMT", "0.04");
        //借记卡-第1笔明细
        Map<String, Object> tempTransDetailMap = new HashMap<String, Object>();
        String mercOrderNo = randomUtils.getRandomTime(19);
        mercOrderNoList.add(mercOrderNo);
        tempTransDetailMap.put("MERC_ORDER_NO", mercOrderNo);
        tempTransDetailMap.put("ACCOUNT_TYPE", "00");
        tempTransDetailMap.put("ACCOUNT_PROP", "0");
        tempTransDetailMap.put("ACCOUNT_NO", randomUtils.getCardNo("ABC"));
        tempTransDetailMap.put("ACCOUNT_NAME", randomUtils.getRandomName());
        tempTransDetailMap.put("BANK_NO", "103");
        tempTransDetailMap.put("BANK_NAME", "农业银行");
        tempTransDetailMap.put("BANK_SHORT_NAME", "农行");
        tempTransDetailMap.put("BANK_PROVINCE", "北京");
        tempTransDetailMap.put("BANK_CITY", "北京");
        tempTransDetailMap.put("TRANS_AMT", "0.02");
        tempTransDetailMap.put("CURRENCY", "CNY");
        tempTransDetailMap.put("REMARK", "借记卡批付");
        tempTransDetailMap.put("REMARK2", "备注2");
        tempTransDetailMap.put("REMARK3", "备注3");
        tempTransDetailMap.put("EXTEND1", "");
        tempTransDetailMap.put("EXTEND2", "");
        tempTransDetailMap.put("EXTEND3", "");
        String transDetail = new String("TRANS_DETAIL");
        transDetailsMap.put(transDetail, tempTransDetailMap);
        //对公账户-第2笔明细
        Map<String, Object> tempTransDetailMap3 = new HashMap<String, Object>();
        String mercOrderNo3 = randomUtils.getRandomTime(19);
        mercOrderNoList.add(mercOrderNo3);
        tempTransDetailMap3.put("MERC_ORDER_NO", mercOrderNo3);
        tempTransDetailMap3.put("ACCOUNT_TYPE", "02");
        tempTransDetailMap3.put("ACCOUNT_PROP", "0");
        tempTransDetailMap3.put("ACCOUNT_NO", randomUtils.getRandomAll(12));
        tempTransDetailMap3.put("ACCOUNT_NAME", "测试渠道");
        tempTransDetailMap3.put("BANK_NO", "105");
        tempTransDetailMap3.put("BANK_NAME", "建设银行");
        tempTransDetailMap3.put("BANK_SHORT_NAME", "建行");
        tempTransDetailMap3.put("BANK_PROVINCE", "北京");
        tempTransDetailMap3.put("BANK_CITY", "北京");
        tempTransDetailMap3.put("TRANS_AMT", "0.02");
        tempTransDetailMap3.put("CURRENCY", "CNY");
        tempTransDetailMap3.put("REMARK", "对公账户批付");
        tempTransDetailMap3.put("REMARK2", "备注2");
        tempTransDetailMap3.put("REMARK3", "备注3");
        tempTransDetailMap3.put("EXTEND1", "");
        tempTransDetailMap3.put("EXTEND2", "");
        tempTransDetailMap3.put("EXTEND3", "");
        String transDetail3 = new String("TRANS_DETAIL");
        transDetailsMap.put(transDetail3, tempTransDetailMap3);
        transDetailsXml = XmlReverseUtils.callMap2XML(transDetailsMap);
        requestMap = getRequestMap();
        Map<String, String> respMap = http.sendHttpRequest(requestMap, reqUrl);
        respMap.put("SIGNED_MSG","");
        logger.info("批量付款调用响应结果：{}", respMap);
        Assert.assertEquals(respMap.get("RET_CODE"), "0000", "批量付款受理失败！");
        Assert.assertEquals(respMap.get("RET_MSG"), "受理成功", "批量付款受理失败！");
    }
}
