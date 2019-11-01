package com.http.customergatewayweb;

import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.http.NewRequestMsgUtils;
import com.interfacetest.commonutils.http.XmlReverseUtils;
import com.ruoyi.common.annotation.AutoTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * zhu.shuo
 * 特约商户入网case
 */
@AutoTest(description = "特约商户入网case")
public class TeYueCustomerTest extends NewRequestMsgUtils {

    private static final Logger logger = LoggerFactory.getLogger(TeYueCustomerTest.class);
    private RandomUtils randomUtils = new RandomUtils();
    private String req_sn;
    private String merc_order_no;
    private String merc_order_date;
    private String mercId = "800010000020019";
    private String mercId2 = "800075700120002";
    private String mercId3 = "800075700120008";

    Map<String, String> file1 = new HashMap<String, String>();
    Map<String, String> file2 = new HashMap<String, String>();
    Map<String, String> file3 = new HashMap<String, String>();
    IdentityHashMap<String, Object> files = new IdentityHashMap<>();
    Map<String, Object> details = new HashMap<String, Object>();
    Map<String, Object> detail = new HashMap<>();

    /**
     * 每次执行单元测试 都初始化新的流水号，订单号，订单日期
     */
    public void initJunit() {
        System.out.println("清空集合，初始化新的流水号，订单号，订单日期。。。。。。。。。。。。。。。");
        mapClear();
        req_sn = randomUtils.getRandomTime(32);
        merc_order_no = "Z" + randomUtils.getDateTime17();
        merc_order_date = randomUtils.getDate8();
    }

    /**
     * 1.文件上传 TODO
     */
//    @Test
//    public void TestUploadFile() {
    //initJunit();
//        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
//                + "/mercopenaccount/uploadfile";
//        infoMap.put("TRX_CODE", "100092");
//        infoMap.put("VERSION", "2.0");
//        infoMap.put("DATA_TYPE", "0");
//        infoMap.put("REQ_SN", req_sn);
//        infoMap.put("GZIP_CHARSET", "UTF-8");
//        infoMap.put("SIGNED_MSG", "");
//        bodyMap.put("MERCHANT_ID", "800010000020019");
//        bodyMap.put("MERC_ORDER_NO", merc_order_no);
//        bodyMap.put("FILE_TYPE", "");
////        bodyMap.put("FILE_TYPE", "");
//////        bodyMap.put("FILE_TYPE", "");
//        bodyMap.put("FILE_CONTENT", "");
//        bodyMap.put("FILE_SUFFIX", ".jpg");
//        Map<String, String> map = httpSend(url); 20191008150518  20191008150553 20191008150604
//
//    }


    /**
     * 2.身份鉴权   OK
     */
    @Test(description = "2.身份鉴权   OK")
    public void testIDAuth() {
        initJunit();
        String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")
                + "/newauth/idauth";
        logger.info("开始执行身份鉴权用例: url=" + url);
        infoMap.put("TRX_CODE", "100006");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", req_sn);
        //bodyMap.put("GZIP_CHARSET","UTF-8"); //utf-8 乱吗
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
        bodyMap.put("MERC_ORDER_NO", merc_order_no);
        bodyMap.put("BANK_ACCOUNT_NAME", "祝硕");
        bodyMap.put("ID_TYPE", "00");
        bodyMap.put("ID_NO", "41142219930625511X");
        bodyMap.put("MERC_USER_NO", "");
        bodyMap.put("REMARK", "");
        bodyMap.put("EXTEND1", "");
        bodyMap.put("EXTEND2", "");
        bodyMap.put("EXTEND3", "");
        respMap = httpSend(url);   //Z20191008150743237
        Assert.assertEquals("0000", respMap.get("RET_CODE"));
        //Assert.assertEquals(map.get("AUTH_STATUS"), "1");  //1 成功 2 失败    响应ok "MERC_ORDER_NO":"L20190926173214656"  通过


    }

    /**
     * 3.开户   Ok
     */
    @Test(description = "3.开户   Ok")
    public void testOpenAccount() {
        initJunit();
        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
                + "/mercopenaccount/openapply";
        logger.info("开始执行开户用例: url=" + url);
        infoMap.put("TRX_CODE", "100081");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", req_sn);
        infoMap.put("GZIP_CHARSET", "GBK");//TODO
        infoMap.put("SIGNED_MSG", "");

        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", merc_order_no);
        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
        bodyMap.put("MERC_NAME", "shore02");
        bodyMap.put("MERC_SHORT_NAME", "sh02");
        bodyMap.put("MERC_MOBIL_NO", "18910720232");
        bodyMap.put("MGT_SCP", "技术服务");
        bodyMap.put("MERC_PROV", "19");
        bodyMap.put("MERC_CITY", "757");
        bodyMap.put("MERC_ADDRESS", "总部基地50区");
        bodyMap.put("INDUSTRY_NO", "12");      //行业类型
        bodyMap.put("INDUSTRY_DETAIL_NO", "1045");//行业细分
        bodyMap.put("MERC_OPEN_TYPE", "2");
        bodyMap.put("AGENT_ID", "800010000020019");         //代理商编号
        bodyMap.put("WEB_URL", "www.sina.com");//商户网站地址
        bodyMap.put("OPEN_MERCHANT_FLAG", "1");
        bodyMap.put("NOTIFY_URL", "http://10.63.11.132:16600/simulator-web/mercservice/backNotify");  //通知地址
        bodyMap.put("PAYOUT_NOTIFY_URL", "http://10.63.11.132:16600/simulator-web/mercservice/backNotify");//打款验证通知地址
        bodyMap.put("PROTOCOL_YEAR_LIMIT", "2"); //
        bodyMap.put("LEGAL_NAME", "");
        bodyMap.put("LEGAL_ID_TYPE", "");
        bodyMap.put("LEGAL_ID_NO", "");
        bodyMap.put("LEGAL_ID_EXP_DATE", "20991212");
        bodyMap.put("LEGAL_AUTH_MERC_ORDER_NO", "Z20191008150743237");
        bodyMap.put("BUS_LICENSE_NO", "91320612251980031X");
        bodyMap.put("BUS_LICENSE_EXP_DATE", "20300302");
        bodyMap.put("HOLDER_NAME", "");
        bodyMap.put("HOLDER_ID_TYPE", "");
        bodyMap.put("HOLDER_ID_NO", "");
        bodyMap.put("HOLDER_ID_EXP_DATE", "");
        bodyMap.put("AUTH_NAME", "");
        bodyMap.put("AUTH_ID_TYPE", "");
        bodyMap.put("AUTH_ID_NO", "");
        bodyMap.put("AUTH_ID_EXP_DATE", "");
        bodyMap.put("AUTH_MOBILE", "18910720232");
        bodyMap.put("AUTH_MERC_ORDER_NO", "Z20191008150743237");
        bodyMap.put("REG_FUND", "100");
        bodyMap.put("CURRENCY", "1");
        bodyMap.put("MERC_OPR_NAME", "祝硕");
        bodyMap.put("MERC_OPR_MOBILE_NO", "18910720232");
        bodyMap.put("MERC_OPR_EMAIL", "nice2007@163.com");
        bodyMap.put("INV_TITLE", "");
        bodyMap.put("INV_NO", "");
        bodyMap.put("INV_COMPANY_ADDRESS", "");
        bodyMap.put("INV_MOBILE_NO", "");
        bodyMap.put("INV_BANK_NAME", "");
        bodyMap.put("INV_CARD_NO", "");
        bodyMap.put("INV_GENERAL_FLAG", "");
        bodyMap.put("INV_EMS_RECIEVER", "");
        bodyMap.put("INV_EMS_ADDRESS", "");
        bodyMap.put("INV_EMS_MOBILE_NO", "");
        bodyMap.put("SETTLE_TYPE", "00");
        bodyMap.put("BANK_NO", "308");
        bodyMap.put("BANK_PROVINCE_CODE", "19");
        bodyMap.put("BANK_CITY_CODE", "755");
        bodyMap.put("BANK_FULL_NAME", "招商银行");
        bodyMap.put("SETTLE_ACCOUNT_TYPE", "5");
        bodyMap.put("SETTLE_ACCOUNT_NAME", "shore02");
        bodyMap.put("BANK_ACCOUNT_NO", "6231361234567891");
        bodyMap.put("IP_ADDR", "10.63.11.131");
        bodyMap.put("DOMAIN_NAME", "10.63.11.131");
        bodyMap.put("IP_ADDRESS", "10.63.11.134,10.63.11.135");
        bodyMap.put("VALID_MODE", "1");
        bodyMap.put("BRAND_NM", "品牌XX");

        detail.put("BUSI_PRODUCT_NO", "B0000011");
        detail.put("PAY_PRODUCT_NO", "P0000601");
        detail.put("ACCESS_CHANNEL", "2");
        details.put("TRANS_DETAIL", detail);
        bodyMap.put("TRANS_DETAILS", details);

        file1.put("FILE_MERC_ORDER_NO", "20191008150518");
        file2.put("FILE_MERC_ORDER_NO", "20191008150553");
        file3.put("FILE_MERC_ORDER_NO", "20191008150604");
        files.put(new String("FILE"), file1);
        files.put(new String("FILE"), file2);
        files.put(new String("FILE"), file3);
        String xml;
        xml = XmlReverseUtils.callMap2XML(files);
        bodyMap.put("FILES", xml);
        bodyMap.put("RISK_INFO", "");
        bodyMap.put("EXTEND1", "");
        bodyMap.put("EXTEND2", "");
        bodyMap.put("EXTEND3", "");
        Map<String, Object> map = constMainParamstructure();
        //httpSendWithParamters(map, url);
        respMap = httpSendWithParamters(map, url);
        Assert.assertEquals("40101030", respMap.get("RET_CODE"));
//  跑脚本： "RET_MSG":"同一代理商编号下，同一经营主体（统一信用代码）存在申请中或已入网成功的特约商户"
    }


    /**
     * 4 .特约商户开户结果查询  OK
     */


    @Test(description = "4 .特约商户开户结果查询  OK")
    public void TestQueryOpenAccResult() {
        initJunit();
        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
                + "/mercopenaccount/queryopenstatus";
        logger.info("开始执行特约商户开户结果查询用例: url=" + url);
        infoMap.put("TRX_CODE", "100088");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", req_sn);
        bodyMap.put("GZIP_CHARSET", "");
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_APPLY_ORDER_NO", "81191008152519764100010113");
        respMap = httpSend(url);
        Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }


    /**
     * 5 开户打款验证（验证）  oK
     */
    @Test(description = "5 开户打款验证（验证）  oK")
    public void TestPayOutVerify() {
        initJunit();
        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
                + "/mercopenaccount/payoutverify";
        logger.info("开始执行开户打款验证用例: url=" + url);
        infoMap.put("TRX_CODE", "100083");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", req_sn);
        //   bodyMap.put("GZIP_CHARSET", "UTF-8");
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", merc_order_no);
        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
        bodyMap.put("MERC_APPLY_ORDER_NO", "81191008152519764100010113");
        //1.故意输错
        //bodyMap.put("TRANS_AMT", "0.37");
        //httpSend(url);
        //响应参数response msg:{"EXTEND2":"","EXTEND3":"","REQ_SN":"20190929153111089176050235653229","MERC_ORDER_DATE":"20190929","MERC_ORDER_NO":"L20190929153111543",
        // "RET_MSG":"验证打款金额错误","SIGNED_MSG":"initdata","GZIP_CHARSET":"GBK","EXTEND1":"","TRX_CODE":"100083","RET_CODE":"40109000","VERSION":"2.0",
        // "ORDER_STATUS":"F","DATA_TYPE":"0","MERCHANT_ID":"800010000020019","APPLY_ORDER_STATUS":"9"}

        //2.输入正确的金额
        bodyMap.put("TRANS_AMT", "0.32");
        respMap = httpSend(url);
        // 响应参数response msg:{"EXTEND2":"","EXTEND3":"","REQ_SN":"20190929153804884766662724781211","MERC_ORDER_DATE":"20190929","MERC_ORDER_NO":"L20190929153804821",
        // "RET_MSG":"成功","SIGNED_MSG":"initdata","GZIP_CHARSET":"GBK","EXTEND1":"","TRX_CODE":"100083","RET_CODE":"0000","VERSION":"2.0","ORDER_STATUS":"S","DATA_TYPE":"0",
        // "MERCHANT_ID":"800010000020019","APPLY_ORDER_STATUS":"8"}
        Assert.assertEquals("40101011", respMap.get("RET_CODE"));
        Assert.assertEquals("当前申请状态不允许此操作", respMap.get("RET_MSG"));

    }

    /**
     * 6. 结算账户修改  OK
     */
    @Test(description = "6. 结算账户修改  OK")
    public void TestModifySettleAccount() {
        initJunit();
        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
                + "/mercopenaccount/modifysettleaccount";
        logger.info("开始执行结算账户修改用例: url=" + url);
        infoMap.put("TRX_CODE", "100084");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", req_sn);
        //   bodyMap.put("GZIP_CHARSET", "UTF-8");
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", merc_order_no);
        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
        bodyMap.put("MERC_APPLY_ORDER_NO", "81191008152519764100010113");
        bodyMap.put("SETTLE_TYPE", "00");
        bodyMap.put("BANK_NO", "308");
        bodyMap.put("BANK_PROVINCE_CODE", "19");
        bodyMap.put("BANK_CITY_CODE", "755");
        bodyMap.put("BANK_FULL_NAME", "招商银行");
        bodyMap.put("SETTLE_ACCOUNT_TYPE", "5");
        bodyMap.put("BANK_ACCOUNT_NAME", "shore02");
        bodyMap.put("CARD_NO", "6231361234567000");
        respMap = httpSend(url);
        Assert.assertEquals("40101011", respMap.get("RET_CODE"));
        Assert.assertEquals("当前申请状态不允许此操作", respMap.get("RET_MSG"));

    }

    /**
     * 7 .合众协议签署-协议预览       OK
     */
    //@Test
//    public void TestViewAss() {
//        initJunit();
//        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
//                + "/mercopenaccount/viewprotocol";
//        infoMap.put("TRX_CODE", "100091");
//        infoMap.put("VERSION", "2.0");
//        infoMap.put("DATA_TYPE", "0");
//        infoMap.put("REQ_SN", req_sn);
//        //   bodyMap.put("GZIP_CHARSET", "UTF-8");
//        infoMap.put("SIGNED_MSG", "");
//        bodyMap.put("MERCHANT_ID", mercId);
//        bodyMap.put("MERC_ORDER_NO", merc_order_no);
//        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
//        bodyMap.put("MERC_APPLY_ORDER_NO", "81191008152519764100010113");
//        respMap = httpSend(url);
//        Assert.assertEquals("0000", respMap.get("RET_CODE"));
//        //        响应参数response msg:{"REQ_SN":"20190929153949984654753380330960","MERC_ORDER_DATE":"20190929","MERC_ORDER_NO":"L20190929153949986","RET_MSG":"成功","SIGNED_MSG":
//        // "initdata","GZIP_CHARSET":"GBK","AUTH_MOBILE":"189****0232","TRX_CODE":"100091","RET_CODE":"0000","VERSION":"2.0","DATA_TYPE":"0",
//        // "PROTOCOL_INFO":"4a564245526930784c6a634b4a654c6a7a394d4b4d5341774947396961676f38504339556558426c4c316850596d706c59335176556d567a623356795932--------------------......
//        // "MERCHANT_ID":"800010000020019"}
    // }

    /**
     * 8. 合众协议签署-获取短验  OK
     */
//    // @Test
//    public void TestGetSms() {
//        initJunit();
//        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
//                + "/mercopenaccount/getsignsms";
//        infoMap.put("TRX_CODE", "100085");
//        infoMap.put("VERSION", "2.0");
//        infoMap.put("DATA_TYPE", "0");
//        infoMap.put("REQ_SN", req_sn);
//        //   bodyMap.put("GZIP_CHARSET", "UTF-8");
//        infoMap.put("SIGNED_MSG", "");
//        bodyMap.put("MERCHANT_ID", mercId);
//        bodyMap.put("MERC_ORDER_NO", merc_order_no);
//        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
//        bodyMap.put("MERC_APPLY_ORDER_NO", "81191008152519764100010113");
//        respMap = httpSend(url);
//        // Assert.assertEquals("0000", respMap.get("RET_CODE"));
//        //收到验证码 387069
//        //        响应参数response msg:{"REQ_SN":"20190929154424161102752257313946","MERC_ORDER_DATE":"20190929","MERC_ORDER_NO":"L20190929154424911","RET_MSG":"成功",
//        // "SIGNED_MSG":"initdata","GZIP_CHARSET":"GBK","TRX_CODE":"100085","RET_CODE":"0000","VERSION":"2.0","ORDER_STATUS":"S","DATA_TYPE":"0","MERCHANT_ID":"800010000020019",
//        // "APPLY_ORDER_STATUS":"10"}
//    }

    /**
     * 9. 合众协议签署-签约  OK
     */
    @Test(description = "9. 合众协议签署-签约  OK")
    public void TestSign() {
        initJunit();
        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
                + "/mercopenaccount/sign";
        logger.info("开始执行签约用例: url=" + url);
        infoMap.put("TRX_CODE", "100086");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", req_sn);
        //   bodyMap.put("GZIP_CHARSET", "UTF-8");
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", merc_order_no);
        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
        bodyMap.put("MERC_APPLY_ORDER_NO", "81191008152519764100010113");
        bodyMap.put("SMS_CODE", "431570");
        bodyMap.put("NOTIFY_URL", "http://10.63.11.132:16600/simulator-web/mercservice/backNotify");
        respMap = httpSend(url);
        Assert.assertEquals("40101011", respMap.get("RET_CODE"));
        Assert.assertEquals("当前申请状态不允许此操作", respMap.get("RET_MSG"));
        // 签约成功，开户成功  加密得用CFCA
    }

    /**
     * 10协议下载   需要用商户自己的证书  OK
     */
////    @Test
//    public void TestDownLoadAss() {
//        initJunit();
//
//        String downLoadAssUrl = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
//                + "/mercopenaccount/downloadprotocol";
//        infoMap.put("TRX_CODE", "100087");
//        infoMap.put("VERSION", "2.0");
//        infoMap.put("DATA_TYPE", "0");
//        infoMap.put("REQ_SN", req_sn);
//        //   bodyMap.put("GZIP_CHARSET", "UTF-8");
//        infoMap.put("SIGNED_MSG", "");
//        bodyMap.put("MERCHANT_ID", "800075700120002");
//        bodyMap.put("MERC_ORDER_NO", merc_order_no);
//        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
//        respMap = httpSend(downLoadAssUrl);
//        Assert.assertEquals("40100005", respMap.get("RET_CODE"));
//        Assert.assertEquals("商户证书序列号不匹配", respMap.get("RET_MSG"));
//
//        //:"成功","SIGNED_MSG":"initdata","DATA_TYPE":"0",
//        // "PROTOCOL_INFO":4a564245526930784c6a634b4a654c6a7a394d4b4d5341774947--------.................
//    }

    /**
     * 11. 特约商户开户订单查询  OK
     */
    @Test(description = "11. 特约商户开户订单查询  OK")
    public void TestDownQueryOrder() {
        initJunit();
        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
                + "/mercopenaccount/queryopenorder";
        logger.info("开始执行开户订单查询用例: url=" + url);

        infoMap.put("TRX_CODE", "100089");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", req_sn);
        //   bodyMap.put("GZIP_CHARSET", "UTF-8");
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", "36191008153101149100110113");//73190929153504768100110265   几个单子都可查
        bodyMap.put("MERC_ORDER_DATE", "20191008");
        respMap = httpSend(url);
        Assert.assertEquals("0000", respMap.get("RET_CODE"));
    }

    /**
     * 12.商户余额结算  OK  用自己的证书做
     */

////    @Test
//    public void TestBalanceSettle() {
//        initJunit();
//        String url = randomUtils.getProperties("url.properties").getProperty("merc-payoutgateway-web")
//                + "/settlepay/balancesettle";
//        infoMap.put("TRX_CODE", "100090");
//        infoMap.put("VERSION", "2.0");
//        infoMap.put("DATA_TYPE", "0");
//        infoMap.put("REQ_SN", req_sn);
//        infoMap.put("GZIP_CHARSET", "");
//        infoMap.put("SIGNED_MSG", "");
//        bodyMap.put("MERCHANT_ID", "800075700120002");
//        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
//        bodyMap.put("MERC_ORDER_NO", merc_order_no);
//        bodyMap.put("TRANS_AMT", "1");
//        bodyMap.put("DRAW_TYP", "1");
//        bodyMap.put("REQUEST_IP", "120.0.0.1");
//        bodyMap.put("RISK_INFO", "");
//        bodyMap.put("NOTIFY_URL", "www.baidu.com");
//        bodyMap.put("REEX_NOTIFY_URL", "www.sina.com");
//        bodyMap.put("TERMINAL_TYPE", "");
//        bodyMap.put("TERMINALID", "");
//        bodyMap.put("DEVICE_INFO", "");
//        respMap = httpSend(url);
//        Assert.assertEquals("0000", respMap.get("RET_CODE"));
//    }
//
//    /**
//     * 13. 余额查询  OK   OK  用自己的证书做
//     */
//    // @Test
//    public void TestQueryBalance() {
//        initJunit();
//        String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")
//                + "/balance/query";
//        infoMap.put("TRX_CODE", "200031");
//        infoMap.put("VERSION", "2.0");
//        infoMap.put("DATA_TYPE", "0");
//        infoMap.put("REQ_SN", req_sn);
//        infoMap.put("GZIP_CHARSET", "");
//        infoMap.put("SIGNED_MSG", "");
//        bodyMap.put("MERCHANT_ID", "800075700120002");
//        bodyMap.put("CUST_TYPE", "2");
//        bodyMap.put("CUST_ID", mercId);  //TODO  "RET_MSG":"客户编号与发起方不是同一主体"   是三证合一则 统一社会信用代码 相同
//        //  TODO   不是   则营业执照编号  一样，才是同一主体，商户IP非法   入网白名单IP 不对
//        // :"商户IP非法","
//        httpSend(url);
//        //       response msg:{"CUST_ID":"","REQ_SN":"20190930134418270096977515059047","BALANCE_AMT":"9999","RET_MSG":"交易成功",
//        // "FROZEN_AMT":"0","SIGNED_MSG":"initdata","CUST_TYPE":"","GZIP_CHARSET":"GBK","UNSETTLE_AMT":"10000","TRX_CODE":"200031",
//        // "RET_CODE":"0000","VERSION":"2.0","DATA_TYPE":"0","MERCHANT_ID":"800075700120008"}
//    }

//    /**
//     * 14. 账户明细查询      OK
//     */
//    //@Test
//    public void TestQueryAccDetail() {
//        initJunit();
//        String url = randomUtils.getProperties("url.properties").getProperty("merc-gateway-web")
//                + "acmtcdd/query";
//        infoMap.put("TRX_CODE", "200007");
//        infoMap.put("VERSION", "2.0");
//        infoMap.put("DATA_TYPE", "0");
//        infoMap.put("REQ_SN", req_sn);
//        bodyMap.put("GZIP_CHARSET", "");
//        infoMap.put("SIGNED_MSG", "");
//        bodyMap.put("MERCHANT_ID", "800075700120002");
//        bodyMap.put("START_TIME", "20190928121212");
//        bodyMap.put("END_TIME", "20191028181818");
//        bodyMap.put("ACCOUNT_TYPE", "");
//        httpSend(url);
////        响应参数response msg:{"REQ_SN":"20190930135016532609129458319176","TOTAL_NUM":"","MERC_ORDER_NO":"","TOTAL_DR_NUM":"",
////                "RET_MSG":"未查到相关记录","SIGNED_MSG":"initdata","TOTAL_DR_AMT":"","GZIP_CHARSET":"GBK","TRANS_AMT":"","ACCOUNT_TIME":"",
////                "TRX_CODE":"200007","RET_CODE":"40069000",
////                "TOTAL_CR_NUM":"","ACCOUNT_FLAG":"","TOTAL_CR_AMT":"","VERSION":"2.0","ACCOUNT_TYPE_NAME":"","DATA_TYPE":"0"}
//    }

    /**
     * 15 开户打款（验证）  oK
     */
    @Test(description = "15 开户打款（验证）  oK")
    public void TestPayOut() {
        initJunit();
        String url = randomUtils.getProperties("url.properties").getProperty("customer-gateway-web")
                + "/mercopenaccount/repayout";
        logger.info("开始执行开户打款用例: url=" + url);

        infoMap.put("TRX_CODE", "100082");
        infoMap.put("VERSION", "2.0");
        infoMap.put("DATA_TYPE", "0");
        infoMap.put("REQ_SN", req_sn);
        //   bodyMap.put("GZIP_CHARSET", "UTF-8");
        infoMap.put("SIGNED_MSG", "");
        bodyMap.put("MERCHANT_ID", mercId);
        bodyMap.put("MERC_ORDER_NO", merc_order_no);
        bodyMap.put("MERC_ORDER_DATE", merc_order_date);
        bodyMap.put("MERC_APPLY_ORDER_NO", "81191008152519764100010113");
        respMap = httpSend(url);
        Assert.assertEquals("40101011", respMap.get("RET_CODE"));
        Assert.assertEquals("当前申请状态不允许此操作", respMap.get("RET_MSG"));
    }

}
