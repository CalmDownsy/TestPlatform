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
 * @ClassName TestQueryBindcardIds
 * @Description 查询绑卡ID列表(200016 头条专用)
 * @Author owen.liu
 * @Date 2019.6.14 14:02
 * @Version 1.0
 */
public class TestQueryBindcardIds extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestQueryBindcardIds.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "query/bindcardids";
    private String mercId = AutoConstant.MERC_ID_40001;
    //初始化数据
    public void initData() {
        mapInit();
        infoMap.put("TRX_CODE", "200016");//Y 交易代码 定值
        infoMap.put("VERSION", "2.0");//Y 版本 定值
        infoMap.put("DATA_TYPE", "0");//Y 数据格式 定值
        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG", "signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("ID_TYPE", "00");
        bodyMap.put("ID_NO", "");
        bodyMap.put("BANK_ACCOUNT_NAME", "");
        bodyMap.put("MERC_USER_NO", "");
        bodyMap.put("EXTEND1", "备用1");
        bodyMap.put("EXTEND2", "备用1");
        bodyMap.put("EXTEND3", "备用1");
    }

    @Test
    public void testQueryBoundCardlist() throws Exception {
        logger.info("开始执行 查询绑卡ID列表(200016 头条专用)（merc-gateway-web/query/bindcardids） 用例...");
        initData();
        bodyMap.put("ID_NO", "150624196910225115");
        bodyMap.put("BANK_ACCOUNT_NAME", "郭条擐");
        bodyMap.put("MERC_USER_NO", "U20190614102634710");
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrl);
        respMap.put("SIGNED_MSG","");
        logger.info("查询绑卡ID列表响应结果：{}",respMap);
        logger.info("判断查询绑卡ID列表结果？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
    }
}
