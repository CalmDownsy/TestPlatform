package com.http.mercgatewayweb.query;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @ClassName TestQueryBankCardInfo
 * @Description 银行卡掩码信息查询(200018 头条专用)
 * @Author owen.liu
 * @Date 2019.6.14 14:26
 * @Version 1.0
 */
public class TestQueryBankCardInfo extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestQueryBankCardInfo.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "query/bankcardinfo";
    private String mercId = AutoConstant.MERC_ID_40001;
    //初始化数据
    public void initData() {
        mapInit();
        infoMap.put("TRX_CODE", "200018");//Y 交易代码 定值
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
    public void testQueryBoundCardlist() throws Exception {
        logger.info("开始执行 银行卡掩码信息查询(200018 头条专用)（merc-gateway-web/query/bankcardinfo） 用例...");
        initData();
        bodyMap.put("MARK_TYPE", "1");//标记化类型 1：协议号 2：绑卡ID
        bodyMap.put("MARK_ID", "19190614102739856100010010");
        bodyMap.put("MERC_USER_NO", "U20190614102634710");
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrl);
        respMap.put("SIGNED_MSG","");
        logger.info("银行卡掩码信息查询响应结果：{}",respMap);
        logger.info("判断银行卡掩码信息查询结果？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
    }
}
