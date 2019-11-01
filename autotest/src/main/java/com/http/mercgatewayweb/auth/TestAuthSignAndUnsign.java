package com.http.mercgatewayweb.auth;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @ClassName TestAuthSignAndUnsign
 * @Description 请求授权签约（100064）解除授权（100065）获取明文信息（20023）
 * @Author owen.liu
 * @Date 2019.6.18 9:28
 * @Version 1.0
 */
public class TestAuthSignAndUnsign extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestAuthSignAndUnsign.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String mercId = AutoConstant.MERC_ID_40001;
    //用户授权
    private String reqUrlAuthSign = randomUtils.getProperties("url.properties").get("merc-gateway-web") + "auth/authsign";
    //解除授权
    private String reqUrlAuthUnsign = randomUtils.getProperties("url.properties").get("merc-gateway-web") + "auth/authunsign";
    //获取明文信息
    private String reqUrlGetPlainText = randomUtils.getProperties("url.properties").get("merc-gateway-web") + "auth/getplaintext";

    public void initAuthSign() {
        mapInit();
        infoMap.put("TRX_CODE", "100064");//Y 交易代码
        infoMap.put("VERSION", "2.0");//Y 版本
        infoMap.put("DATA_TYPE", "0");//N 数据格式 0：xml格式
        infoMap.put("REQ_SN", randomUtils.getDateTime14());//Y 交易流水号
        infoMap.put("SIGNED_MSG", "signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO", "Auth" + randomUtils.getDateTime17());
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("MERC_USER_NO", "");//Y 实名/签约时的商户用户号,在该商户号下保持唯一
        bodyMap.put("AUTH_TYPE", "");//Y 授权类型 1：二要素认证 2：四要素认证
        bodyMap.put("AGREEMENT_EFFECTIVE_DATE", randomUtils.getDate8());//Y 协议生效日期
        bodyMap.put("AGREEMENT_EXPIR_DATE", randomUtils.getPastDate(-3));//N 协议失效日期
        bodyMap.put("PROTOCOL_INFO", "{\"fullName\":\"合众易宝\",\"shortName\":\"合众支付\"}");//Y 协议信息
        bodyMap.put("BUSINESS_SCENE", "2");//Y 授权用途  1、理财业务 2、保险业务 3、放心借业务
        bodyMap.put("EXTEND1", "111");
        bodyMap.put("EXTEND2", "222");
        bodyMap.put("EXTEND3", "222");
    }

    public void initAuthUnsign() {
        mapInit();
        infoMap.put("TRX_CODE","100065");//Y 交易代码
        infoMap.put("VERSION","2.0");//Y 版本
        infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
        infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO", "Auth"+randomUtils.getDateTime17());
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("MERC_USER_NO", "");//Y 实名/签约时的商户用户号,在该商户号下保持唯一
        bodyMap.put("AUTH_PROTOCOL_NO", "");//Y 授权协议号
        bodyMap.put("EXTEND1", "111");
        bodyMap.put("EXTEND2", "222");
        bodyMap.put("EXTEND3", "222");
    }

    public void initGetPlainText() {
        mapInit();
        infoMap.put("TRX_CODE","20023");//Y 交易代码
        infoMap.put("VERSION","2.0");//Y 版本
        infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
        infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
        infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息

        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO", "Auth"+randomUtils.getDateTime17());
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("MERC_USER_NO", "");//Y 实名/签约时的商户用户号,在该商户号下保持唯一
        bodyMap.put("AUTH_PROTOCOL_NO", "");//Y 授权协议号
        bodyMap.put("QUERY_DATA_TYPE", "");//Y 查询数据类型 1.二要素 2.四要素
        bodyMap.put("CARD_TYPE", "");//Y 卡类型 1.借记卡 2.贷记卡 3.全部
        bodyMap.put("BIND_CARD_ID", "");//N 为空默认查询所有绑卡信息
        bodyMap.put("EXTEND1", "111");
        bodyMap.put("EXTEND2", "222");
        bodyMap.put("EXTEND3", "222");
    }


    //二要素授权、查询明文信息、解除授权  二要素用户号：ly201906180952
    @Test
    public void testAuthSignAndUnsignAndGetPlainText2() {
        String mercUserNo="ly201906180952";
        logger.info("开始执行 请求授权签约（100064）（merc-gateway-web/auth/authsign） 用例...");
        initAuthSign();
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y 实名/签约时的商户用户号,在该商户号下保持唯一
        bodyMap.put("AUTH_TYPE", "1");//Y 授权类型 1：二要素认证 2：四要素认证
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlAuthSign);
        logger.info("二要素授权签约响应：{}",respMap.toString());
        logger.info("判断二要素授权签约是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        logger.info("开始执行 获取明文信息（20023）（merc-gateway-web/auth/getplaintext） 用例...");
        //获取签约协议号
        String authProtocolNo = respMap.get("AUTH_PROTOCOL_NO");
        logger.info("授权协议号：{},商户用户号：{}",authProtocolNo,mercUserNo);
        initGetPlainText();
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y 实名/签约时的商户用户号,在该商户号下保持唯一
        bodyMap.put("AUTH_PROTOCOL_NO", authProtocolNo);//Y 授权协议号
        bodyMap.put("QUERY_DATA_TYPE", "1");//Y 查询数据类型 1.二要素 2.四要素
        bodyMap.put("CARD_TYPE", "3");//Y 卡类型 1.借记卡 2.贷记卡 3.全部
        bodyMap.put("BIND_CARD_ID", "");//N 为空默认查询所有绑卡信息
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlGetPlainText);
        respMap.put("SIGNED_MSG","");
        logger.info("二要素获取明文信息响应：{}",respMap.toString());
        logger.info("判断二要素获取明文信息是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        logger.info("开始执行 解除授权（100065）（merc-gateway-web/auth/authunsign） 用例...");
        initAuthUnsign();
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y 实名/签约时的商户用户号,在该商户号下保持唯一
        bodyMap.put("AUTH_PROTOCOL_NO", authProtocolNo);//Y 授权协议号
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlAuthUnsign);
        respMap.put("SIGNED_MSG","");
        logger.info("二要素解除授权响应：{}",respMap.toString());
        logger.info("判断二要素解除授权是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
    }

    //四要素授权、查询明文信息、解除授权  四要素用户号：ly20190618134824
    @Test
    public void testAuthSignAndUnsignAndGetPlainText4() {
        String mercUserNo="ly20190618134824";
        logger.info("开始执行 请求授权签约（100064）（merc-gateway-web/auth/authsign） 用例...");
        initAuthSign();
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y 实名/签约时的商户用户号,在该商户号下保持唯一
        bodyMap.put("AUTH_TYPE", "2");//Y 授权类型 1：二要素认证 2：四要素认证
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlAuthSign);
        respMap.put("SIGNED_MSG","");
        logger.info("四要素授权签约响应：{}",respMap.toString());
        logger.info("判断四要素授权签约是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        logger.info("开始执行 获取明文信息（20023）（merc-gateway-web/auth/getplaintext） 用例...");
        //获取签约协议号
        String authProtocolNo = respMap.get("AUTH_PROTOCOL_NO");
        logger.info("授权协议号：{},商户用户号：{}",authProtocolNo,mercUserNo);
        initGetPlainText();
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y 实名/签约时的商户用户号,在该商户号下保持唯一
        bodyMap.put("AUTH_PROTOCOL_NO", authProtocolNo);//Y 授权协议号
        bodyMap.put("QUERY_DATA_TYPE", "2");//Y 查询数据类型 1.二要素 2.四要素
        bodyMap.put("CARD_TYPE", "3");//Y 卡类型 1.借记卡 2.贷记卡 3.全部
        bodyMap.put("BIND_CARD_ID", "");//N 为空默认查询所有绑卡信息
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlGetPlainText);
        respMap.put("SIGNED_MSG","");
        logger.info("四要素获取明文信息响应：{}",respMap.toString());
        logger.info("判断四要素获取明文信息是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
        logger.info("开始执行 解除授权（100065）（merc-gateway-web/auth/authunsign） 用例...");
        initAuthUnsign();
        bodyMap.put("MERC_USER_NO", mercUserNo);//Y 实名/签约时的商户用户号,在该商户号下保持唯一
        bodyMap.put("AUTH_PROTOCOL_NO", authProtocolNo);//Y 授权协议号
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqUrlAuthUnsign);
        respMap.put("SIGNED_MSG","");
        logger.info("四要素解除授权响应：{}",respMap.toString());
        logger.info("判断四要素解除授权是否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));
        Assert.assertEquals(respMap.get("RET_CODE"),"0000");
    }

}
