package com.http.mercgatewayweb.openaccountandcancel;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.interfacetest.commonutils.DBConnectUtils;
import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.commonutils.SignUtilTest;
import com.interfacetest.constants.AutoConstant;
/**
 * @ClassName TestOpenAccountUserAccount
 * @Description 该类覆盖交易：头条红包个人云账户开户（200100）,销户销户获取URL（100053）、销户发送短信、确认销户、销户结果查询
 * @author liyang
 * @Date 2019.6.16 11:22
 * @Version 1.0
 */
public class TestOpenAccountUserAccountAndCancel extends RequestMsgUtils{
	private static Logger logger = LoggerFactory.getLogger(TestOpenAccountUserAccountAndCancel.class);
	private RandomUtils randomUtils = new RandomUtils();
    private HttpSendUtils http = new HttpSendUtils();													
    private String reqUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"openaccount/useraccount";
    private String reqGetCanceUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+ "/accountcancel/getcancelurl";
    private String reqCancelSmsUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+ "/accountcancel/cancelsms";
    private String reqCancelUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"/accountcancel/cancel";
    private String reqQueryCancelUrl = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")+"/accountcancel/querycancel";  
    String mercId =AutoConstant.MERC_ID_40001;
    //云账户开户初始化数据
    public void openAccountInit() throws Exception {
    	mapInit();
    	infoMap.put("TRX_CODE","200100");//Y 交易代码
		infoMap.put("VERSION","2.1");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime14());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
		bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO", randomUtils.getDateTime17());//Y商户订单号
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y商户订单日期
		bodyMap.put("OPEN_TYPE", "1");//N开户类型默认为协议号开户  1、协议号开户；2、实名信息开户
		bodyMap.put("MERC_USER_NO", "");//N商户用户号  实名信息开户必传；协议号开户非必传
		bodyMap.put("REG_MOBILE_NO", "");//N注册手机号  实名信息开户必传；协议号开户非必传
		bodyMap.put("SIGN_AGREEMENT_NO", "");//N签约协议号,与银行卡四要素不可同时为空//
    }
    //云账户销户获取URL初始化数据
    public void getCancelUrlInit() throws Exception { 
    	mapInit();
    	infoMap.put("TRX_CODE","100053");//Y 交易代码
		infoMap.put("VERSION","2.0");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime12());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
		bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO",randomUtils.getDateTime17());//Y商户订单号
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y商户订单日期
		bodyMap.put("MERC_USER_NO", "");//N商户用户号
		bodyMap.put("USER_NO", "");//N 平台用户号   商户用户号和平台用户二选一
		bodyMap.put("REG_MOBILE_NO", "");//N开户手机号
		bodyMap.put("NOTIFY_URL", AutoConstant.NOTIFY_URL);//Y后天通知地址
		bodyMap.put("RETURN_URL", AutoConstant.RET_URL);//Y前台通知地址
    }
    //云账户销户查询初始化数据
    public void queryCancelInit() throws Exception { 
    	mapInit();
    	infoMap.put("TRX_CODE","200021");//Y 交易代码
		infoMap.put("VERSION","2.0");//Y 版本
		infoMap.put("DATA_TYPE","0");//N 数据格式 0：xml格式
		infoMap.put("REQ_SN",randomUtils.getDateTime12());//Y 交易流水号
		infoMap.put("SIGNED_MSG","signedMsg");//Y 签名信息
		bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
		bodyMap.put("MERC_ORDER_NO",randomUtils.getDateTime17());//Y商户订单号
		bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());//Y商户订单日期
    }
    @Test //协议号开户
    public void openAndCancelAccount_Xy()throws Exception{
    	openAccountInit();
    	logger.info("### 开始红包个人云账户开户(200100)传注册手机号 用例执行...");
     	bodyMap.put("OPEN_TYPE", "1");//1、协议号开户；2、实名信息开户   默认1
		bodyMap.put("MERC_USER_NO", "");//实名信息开户必传；协议号开户非必传		
		bodyMap.put("REG_MOBILE_NO", randomUtils.getMobileNo());//Y,注册手机号 在该商户号下保持唯一
    	bodyMap.put("SIGN_AGREEMENT_NO", "80190617193636851100010010");//签约协议号,N,与银行卡四要素不可同时为空
     	Map<String,String>openAccountrespMap = httpSend(http, reqUrl, requestMap);	
        Assert.assertEquals(openAccountrespMap.get("RET_CODE"), "0000");
        Assert.assertEquals(openAccountrespMap.get("RET_MSG"), "开户成功");	
        logger.info("判判断开户是否成功？返回码：{},返回信息：{}",openAccountrespMap.get("RET_CODE"),openAccountrespMap.get("RET_MSG"));
        String mercUserNo="ly20190617193549";
        getCancelUrlInit();
		logger.info("### 开始 获取销户URL(100053)-成功 用例执行...");
		bodyMap.put("MERC_USER_NO", mercUserNo);//N商户用户号
		bodyMap.put("USER_NO", "");//N 平台用户号   商户用户号和平台用户二选一
		bodyMap.put("REG_MOBILE_NO", "");//N开户手机号
	    Map<String,String>	getCancelResp = httpSend(http, reqGetCanceUrl, requestMap);
		Assert.assertEquals(getCancelResp.get("RET_CODE"), "0000");	
		Assert.assertEquals(getCancelResp.get("RET_MSG"), "交易成功");
        logger.info("判断销户获取url是否成功？返回码：{},返回信息：{}",getCancelResp.get("RET_CODE"),getCancelResp.get("RET_MSG"));
        String platOrderNo=getCancelResp.get("CANCEL_URL").substring(getCancelResp.get("CANCEL_URL").indexOf("=")+1,getCancelResp.get("CANCEL_URL").indexOf("&"));
        Map<String, String> getSmsMap = new LinkedHashMap<String, String>();
        logger.info("### 开始 获取销户短信-成功 用例执行...");
        //留着以后查身份证号用
        /*Map<String, String> queryMap4 = DBConnectUtils.queryOne("SELECT ID_NO FROM BASEFMU.T_C_USER_PLAT_INFO where PLAT_USER_NO='mercUserNo'","basefmu");
        String idNo =queryMap4.get("ID_NO");*/
        getSmsMap.put("platOrderNo",platOrderNo);
        getSmsMap.put("mercId",mercId);//Y 
        getSmsMap.put("mercUserNo",mercUserNo);
        getSmsMap.put("sign", SignUtilTest.addSign(getSmsMap));
        getSmsMap.put("idType", "00");//00-身份证，02-户口本05：港、澳居通行证06：台湾居民来往大陆通行证07：护照
        getSmsMap.put("idNo","11010119900807373x");//证件号可以写死，因为开户使用的是一个协议号
        HashMap<String,String> smsResp = http.sendPostRequest(getSmsMap, reqCancelSmsUrl);
        Assert.assertEquals(smsResp.get("retCode"), "0000");
        Assert.assertEquals(smsResp.get("retMsg"), "发送短信成功");
        logger.info("判断销户获取短信是否成功？返回码：{},返回信息：{}",smsResp.get("retCode"),smsResp.get("retMsg"));
        String token=smsResp.get("token");
        Map<String,String>queryMap = DBConnectUtils.queryOne("SELECT VERIFY_CODE FROM BASEADM.T_C_MERC_SMS_VERIFYCODEINF WHERE PLAT_ORDER_NO='" + platOrderNo + "'","baseadm");      
        String smsCode=queryMap.get("VERIFY_CODE"); 
        logger.info("### 开始 销户-成功 用例执行...");
        Map<String, String> getCancelMap = new LinkedHashMap<String, String>();
        getCancelMap.put("platOrderNo", platOrderNo);
        getCancelMap.put("mercId", mercId);//Y 
        getCancelMap.put("mercUserNo", mercUserNo);
        getCancelMap.put("sign", SignUtilTest.addSign(getCancelMap));
        getCancelMap.put("smsCode", smsCode);//
        getCancelMap.put("token", token);//Y
        HashMap<String,String> CancelResp = http.sendPostRequest(getCancelMap, reqCancelUrl);
        Assert.assertEquals(CancelResp.get("retCode"), "0000");
        Assert.assertEquals(CancelResp.get("retMsg"), "销户成功");
        logger.info("判断销户是否成功？返回码：{},返回信息：{}",CancelResp.get("retCode"),CancelResp.get("retMsg"));        
        logger.info("### 开始 销户-查询 用例执行...");
        queryCancelInit();
        String mercOrderNo="20190618113840750";//商户订单号写死，每次都查同一笔销户记录
        bodyMap.put("MERCHANT_ID", mercId);//Y 商户编号
        bodyMap.put("MERC_ORDER_NO",mercOrderNo );//Y 商户订单号
        bodyMap.put("MERC_ORDER_DATE", "20190618");
        requestMap = getRequestMap();
        respMap = http.sendHttpRequest(requestMap,reqQueryCancelUrl);
        Assert.assertEquals(respMap.get("ERR_CODE"), "0000");
        Assert.assertEquals(respMap.get("ERR_MSG"), "销户成功");
        logger.info("判断销户订单查询否成功？返回码：{},返回信息：{}",respMap.get("RET_CODE"),respMap.get("RET_MSG"));  
    }
    @Test //实名信息开户  做H5签约并支付获取的商户用户号BASEADM.T_B_MERC_IDENTITY_AUTH
    public void openAndCancelAccount_Sm()throws Exception{
    	openAccountInit();
    	String mercUserNo="ly201906181603";
    	logger.info("### 开始红包个人云账户开户实名信息开户(200100) 用例执行...");
     	bodyMap.put("OPEN_TYPE", "2");//1、协议号开户；2、实名信息开户   默认1
		bodyMap.put("MERC_USER_NO", mercUserNo);//实名信息开户必传；协议号开户非必传		
		bodyMap.put("REG_MOBILE_NO", randomUtils.getMobileNo());//Y,注册手机号 在该商户号下保持唯一
     	Map<String,String>openAccountrespMap = httpSend(http, reqUrl, requestMap);	
        Assert.assertEquals(openAccountrespMap.get("RET_CODE"), "0000");
        Assert.assertEquals(openAccountrespMap.get("RET_MSG"), "开户成功");	
        logger.info("判判断开户是否成功？返回码：{},返回信息：{}",openAccountrespMap.get("RET_CODE"),openAccountrespMap.get("RET_MSG"));
        getCancelUrlInit();
		logger.info("### 开始 获取销户URL(100053)-成功 用例执行...");
		bodyMap.put("MERC_USER_NO", mercUserNo);//N商户用户号
		bodyMap.put("USER_NO", "");//N 平台用户号   商户用户号和平台用户二选一
		bodyMap.put("REG_MOBILE_NO", "");//N开户手机号
	    Map<String,String>	getCancelResp = httpSend(http, reqGetCanceUrl, requestMap);
		Assert.assertEquals(getCancelResp.get("RET_CODE"), "0000");	
		Assert.assertEquals(getCancelResp.get("RET_MSG"), "交易成功");
        logger.info("判断销户获取url是否成功？返回码：{},返回信息：{}",getCancelResp.get("RET_CODE"),getCancelResp.get("RET_MSG"));
        String platOrderNo=getCancelResp.get("CANCEL_URL").substring(getCancelResp.get("CANCEL_URL").indexOf("=")+1,getCancelResp.get("CANCEL_URL").indexOf("&"));
        Map<String, String> getSmsMap = new LinkedHashMap<String, String>();
        logger.info("### 开始 获取销户短信-成功 用例执行...");
        //留着以后查身份证号用
        /*Map<String, String> queryMap4 = DBConnectUtils.queryOne("SELECT ID_NO FROM BASEFMU.T_C_USER_PLAT_INFO where PLAT_USER_NO='mercUserNo'","basefmu");
        String idNo =queryMap4.get("ID_NO");*/
        getSmsMap.put("platOrderNo",platOrderNo);
        getSmsMap.put("mercId",mercId);//Y 
        getSmsMap.put("mercUserNo",mercUserNo);
        getSmsMap.put("sign", SignUtilTest.addSign(getSmsMap));
        getSmsMap.put("idType", "00");//00-身份证，02-户口本05：港、澳居通行证06：台湾居民来往大陆通行证07：护照
        getSmsMap.put("idNo","110101199003072738");//证件号可以写死，因为开户使用的是一个协议号
        HashMap<String,String> smsResp = http.sendPostRequest(getSmsMap, reqCancelSmsUrl);
        Assert.assertEquals(smsResp.get("retCode"), "0000");
        Assert.assertEquals(smsResp.get("retMsg"), "发送短信成功");
        logger.info("判断销户获取短信是否成功？返回码：{},返回信息：{}",smsResp.get("retCode"),smsResp.get("retMsg"));
        String token=smsResp.get("token");
        Map<String,String>queryMap = DBConnectUtils.queryOne("SELECT VERIFY_CODE FROM BASEADM.T_C_MERC_SMS_VERIFYCODEINF WHERE PLAT_ORDER_NO='" + platOrderNo + "'","baseadm");      
        String smsCode=queryMap.get("VERIFY_CODE"); 
        logger.info("### 开始 销户-成功 用例执行...");
        Map<String, String> getCancelMap = new LinkedHashMap<String, String>();
        getCancelMap.put("platOrderNo", platOrderNo);
        getCancelMap.put("mercId", mercId);//Y 
        getCancelMap.put("mercUserNo", mercUserNo);
        getCancelMap.put("sign", SignUtilTest.addSign(getCancelMap));
        getCancelMap.put("smsCode", smsCode);//
        getCancelMap.put("token", token);//Y
        HashMap<String,String> CancelResp = http.sendPostRequest(getCancelMap, reqCancelUrl);
        Assert.assertEquals(CancelResp.get("retCode"), "0000");
        Assert.assertEquals(CancelResp.get("retMsg"), "销户成功");
        logger.info("判断销户是否成功？返回码：{},返回信息：{}",getCancelMap.get("retCode"),CancelResp.get("retMsg"));        
       }
}
 

