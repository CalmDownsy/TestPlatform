package com.http.mercpayoutgatewayweb.query;

import com.http.mercgatewayweb.query.TestQueryBoundcardList;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @ClassName TestQueryBankCard
 * @Description TODO
 * @Author owen.liu
 * @Date 2019.6.14 14:50
 * @Version 1.0
 */
public class TestQueryBankCard extends RequestMsgUtils{
    private static Logger logger = LoggerFactory.getLogger(TestQueryBoundcardList.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-payoutgateway-web") + "query/bankcard";
    private String mercId = AutoConstant.MERC_ID_40001;
    //初始化数据
    public void initData() {
        mapInit();
        infoMap.put("TRX_CODE", "200017");//Y 交易代码 定值
        infoMap.put("VERSION", "2.0");//Y 版本 定值
        infoMap.put("DATA_TYPE", "0");//Y 数据格式 定值
        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG", "signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("MARK_TYPE", "");//标记化类型 1：协议号 2：绑卡ID
        bodyMap.put("MARK_ID", "");
        bodyMap.put("MERC_USER_NO", "");
        bodyMap.put("EXTEND1", "备用1");
        bodyMap.put("EXTEND2", "备用1");
        bodyMap.put("EXTEND3", "备用1");
    }

    @Test
    public void testQueryBoundCardList() throws Exception {
        logger.info("开始执行 银行卡及姓名查询(200017)（merc-gateway-web/query/bankcard） 用例...");
        initData();
        bodyMap.put("MARK_TYPE", "1");//标记化类型 1：协议号 2：绑卡ID
        bodyMap.put("MARK_ID", "37190618110536703100010010");
        bodyMap.put("MERC_USER_NO", "C20190618110419");
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrl);
        respMap.put("SIGNED_MSG","");
        logger.info("银行卡及姓名查询响应结果：{}",respMap);
        logger.info("判断银行卡及姓名查询结果？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
    }
}
