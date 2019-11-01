package com.http.mercpayoutgatewayweb;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @ClassName TestPayoutMarkSinglePay
 * @Description 单笔标记化代付（100049）
 * @Author owen.liu
 * @Date 2019.6.13 15:37
 * @Version 1.0
 */
public class TestPayoutMarkSinglePay extends RequestMsgUtils{
    private static Logger logger = LoggerFactory.getLogger(TestPayoutSinglePay.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-payoutgateway-web")+"payout/marksinglepay";
    @BeforeMethod
    public void setUp() throws Exception {
        mapInit();
        infoMap.put("TRX_CODE","100049");//Y 交易代码 定值
        infoMap.put("VERSION","2.0");//Y 版本 定值
        infoMap.put("DATA_TYPE","0");//Y 数据格式 定值
        infoMap.put("REQ_SN",randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("BUSINESS_CODE","04900");//Y 业务代码
        bodyMap.put("NOTIFY_URL","http://10.63.13.81:12790/simulator-web/mercservice/payOutBackNotify");//N 后台通知地址
        bodyMap.put("MARK_ID","");
        bodyMap.put("MARK_TYPE","1");//1：协议号 2:绑卡id 3:二要素
        bodyMap.put("MERC_USER_NO","");//Y 商户用户号
        bodyMap.put("ACCOUNT_TYPE","");//Y 标记类型为3时，必传,00：银行卡，01：存折，02对公账户
        bodyMap.put("ACCOUNT_NO","");//Y 标记类型为3时，必传
        bodyMap.put("ACCOUNT_NAME","");//Y 标记类型为3时，必传
        bodyMap.put("BANK_NO","");//C
        bodyMap.put("BANK_NAME","");//C
        bodyMap.put("BANK_SHORT_NAME","");//C
        bodyMap.put("BANK_PROVINCE","");//C
        bodyMap.put("BANK_CITY","");//C
        bodyMap.put("TERMINAL_TYPE","01");
        bodyMap.put("TERMINALID",randomUtils.getRandomTime(20));
        bodyMap.put("DEVICE_INFO","ABCD:EF01:2345:6789:ABCD:EF01:2345:6789|F0E1D2C3B4A5|352044063995403|460030");
        bodyMap.put("TRANS_AMT","0.04");//Y 交易金额 注：单位元，保留两位小数
        bodyMap.put("CURRENCY","CNY");//N 币种
        bodyMap.put("REMARK","单笔付款备注1");//N 备注1
        bodyMap.put("REMARK2","单笔付款备注2");//N 备注2
        bodyMap.put("REMARK3","单笔付款备注3");//N 备注3
        bodyMap.put("EXTEND1","");//N 备用域1
        bodyMap.put("EXTEND2","");//N 备用域2
        bodyMap.put("EXTEND3","");//N 备用域3
    }

    /**
     * 单笔标记化代付-协议号
     * @throws Exception
     */
    @Test
    public void testMarkSinglePay_SUCEESS() throws Exception {
        logger.info("###  开始执行 单笔标记化代付（100049）（merc-payoutgateway-web/payout/marksinglepay） 用例...");
        bodyMap.put("MARK_TYPE", "1");//1：协议号支付
        bodyMap.put("MARK_ID", "19190614102739856100010010");//协议号   ICBC卡
        bodyMap.put("MERC_USER_NO", "U20190614102634710");//商户订单编号
        bodyMap.put("TRANS_AMT", "0.02");//交易金额
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrl);
        respMap.put("SIGNED_MSG","");
        logger.info("响应结果：{}",respMap);
        logger.info("判断查询结果？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        //受理成功
    }
}
