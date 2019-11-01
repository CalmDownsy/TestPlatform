package com.http.mercgatewayweb.query;

import com.interfacetest.commonutils.EncryptUtils;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * @ClassName TestRealCompareInfo
 * @Description TODO 实名信息对比查询
 * @Author owen.liu
 * @Date 2019.7.4 13:28
 * @Version 1.0
 */
public class TestRealCompareInfo extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestRealCompareInfo.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private EncryptUtils encryptUtils = new EncryptUtils();
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web") + "query/realcompareinfo";

    public void init() {
        mapInit();
        infoMap.put("TRX_CODE", "200032");//Y 交易代码 定值
        infoMap.put("VERSION", "2.0");//Y 版本 定值 2.2(头条密码前置流程) 2.3(头条密码后置流程)
        infoMap.put("DATA_TYPE", "0");//Y 数据格式 定值
        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));//Y 请求流水号
        infoMap.put("SIGNED_MSG", "signedMsg");//Y 签名信息
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO", randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MERC_USER_NO", "U" + randomUtils.getDateTime17());//Y 商户用户号
        bodyMap.put("ID_TYPE", "00");//N 证件类型
        bodyMap.put("ID_NO", "");//N 证件号 需使用Md5加密
        bodyMap.put("BANK_ACCOUNT_NAME", "");//N 银行账户名称 需使用Md5加密
    }
    //    实名信息对比查询一致
    @Test
    public void testGetsignurl() throws Exception {
        init();
        String mercUserNo = "U20190704192724152";
        String idNo = encryptUtils.encoderByMd5("420205199210250596");
        String acountName = encryptUtils.encoderByMd5("秘蘼刹");
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO", randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y 商户用户号
        bodyMap.put("ID_TYPE", "00");//N 证件类型
        bodyMap.put("ID_NO", idNo);//N 证件号  需使用Md5加密
        bodyMap.put("BANK_ACCOUNT_NAME", acountName);//N 银行账户名称  需使用Md5加密
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrl);
        logger.info("respMap:{}",respMap);
    }
    //    实名信息对比查询不一致
    @Test
    public void testGetsignurl2() throws Exception {
        init();
        String mercUserNo = "U20190704192724152";
        String idNo = encryptUtils.encoderByMd5("420205199210250596");
        String acountName = encryptUtils.encoderByMd5("测试");
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO", randomUtils.getRandomTime(20));//Y 商户订单编号
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y 商户订单日期
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y 商户用户号
        bodyMap.put("ID_TYPE", "00");//N 证件类型
        bodyMap.put("ID_NO", idNo);//N 证件号  需使用Md5加密
        bodyMap.put("BANK_ACCOUNT_NAME", acountName);//N 银行账户名称  需使用Md5加密
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrl);
        logger.info("respMap:{}",respMap);
    }
}
