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
 * @ClassName TestPayoutSinglePay
 * @Description 单笔代付（100030）
 * @Author owen.liu
 * @Date 2019.6.13 15:37
 * @Version 1.0
 */
public class TestPayoutSinglePay extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestPayoutSinglePay.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-payoutgateway-web")+"payout/singlepay";
    @BeforeMethod
    public void setUp() throws Exception {
        mapInit();
        infoMap.put("TRX_CODE","100030");//Y 交易代码 定值
        infoMap.put("VERSION","2.0");//Y 版本 定值
        infoMap.put("DATA_TYPE","0");//Y 数据格式 定值
        infoMap.put("REQ_SN",randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO",randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE",randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("BUSINESS_CODE","04900");//Y 业务代码
        bodyMap.put("NOTIFY_URL","http://10.63.13.141:12790/simulator-web/mercservice/payOutBackNotify");//N 后台通知地址
        bodyMap.put("ACCOUNT_TYPE","00");//Y 账号类型 00：银行卡  01：存折 02：对公账户 03：合众支付账号
        bodyMap.put("ACCOUNT_PROP","0");//N 账号属性 0：私人 1：公司  当账号类型为03是必传
        bodyMap.put("ACCOUNT_NO",randomUtils.getCardNo("ICBC"));//Y 银行卡、存折号或者对公账号或者合众支付账号
        bodyMap.put("ACCOUNT_NAME","测试吖");//Y 账户名称 银行卡、存折或者账户持有人姓名，如果为合众账号则为个人姓名或商户名称
        bodyMap.put("BANK_NO","103");//N 银行代码 注：账号类型为01,02时必填
        bodyMap.put("BANK_NAME","农业银行");//N 开户行全称
        bodyMap.put("BANK_SHORT_NAME","农业银行");//N 开户行简称 注：银行代码为313、314、317、320、401、402时必填
        bodyMap.put("BANK_PROVINCE","北京");//N 开户行所在省
        bodyMap.put("BANK_CITY","北京");//N 开户行所在市
        bodyMap.put("TRANS_AMT","0.04");//Y 交易金额 注：单位元，保留两位小数
        bodyMap.put("CURRENCY","CNY");//N 币种
        bodyMap.put("REMARK","单笔付款");//N 备注1
        bodyMap.put("REMARK2","备注2");//N 备注2
        bodyMap.put("REMARK3","备注3");//N 备注3
        bodyMap.put("EXTEND1","");//N 备用域1
        bodyMap.put("EXTEND2","");//N 备用域2
        bodyMap.put("EXTEND3","");//N 备用域3        
    }

    @Test(description = "单笔付款成功用例-付款至借记卡")
    public void testSinglePay(){
        logger.info("开始执行 单笔代付（100030）（merc-payoutgateway-web/payout/singlepay） 用例...");
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrl);
        respMap.put("SIGNED_MSG","");
        logger.info("响应结果：{}",respMap);
        logger.info("判断查询结果？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
    }

}
