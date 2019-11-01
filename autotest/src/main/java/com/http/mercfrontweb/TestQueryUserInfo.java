package com.http.mercfrontweb;

import com.interfacetest.commonutils.HttpSendUtils;
import com.interfacetest.commonutils.RandomUtils;
import com.interfacetest.commonutils.RequestMsgUtils;
import com.interfacetest.constants.AutoConstant;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName TestQueryUserInfo
 * @Description 查询个人用户基本信息  本类联调环境跳过测试，原因是：发送真实图像识别通道，有识别次数限制
 * @Author owen.liu
 * @Date 2019.6.27 10:57
 * @Version 1.0
 */
public class TestQueryUserInfo extends RequestMsgUtils {
    private static Logger logger = LoggerFactory.getLogger(TestQueryUserInfo.class);
    private HttpSendUtils http = new HttpSendUtils();
    private RandomUtils randomUtils = new RandomUtils();
    private String queryUserInfoUrl = randomUtils.getProperties("url.properties").getProperty("merc-front-web") + "modifyaccount/queryuserinfo";
    private String uploadIdInfoUrl = randomUtils.getProperties("url.properties").getProperty("merc-front-web") + "modifyaccount/uploadidinfo";
    private String modifyUserInfoUrl = randomUtils.getProperties("url.properties").getProperty("merc-front-web") + "modifyaccount/modifyuserinfo";
    private String getModifyUrlUrl = randomUtils.getProperties("url.properties").getProperty("merc-front-web") + "modifyaccount/getmodifyurl";


    public void initGetModifyUrl() {
        mapInit();
        infoMap.put("TRX_CODE", "100070");
        infoMap.put("VERSION", "2.0");    //N 版本 固定值：01
        infoMap.put("DATA_TYPE", "0");   //N 数据格式 0：xml格式
        infoMap.put("REQ_SN", randomUtils.getRandomTime(19));    //N 请求流水号
        infoMap.put("SIGNED_MSG", "signedMsg");  //N 签名信息
        bodyMap.put("MERCHANT_ID", AutoConstant.MERC_ID_40001);   //Y 商户编号
        bodyMap.put("MERC_ORDER_NO", randomUtils.getDateTime17());
        bodyMap.put("MERC_ORDER_DATE", randomUtils.getDate8());
        bodyMap.put("MERC_USER_NO", "");//商户用户号
        bodyMap.put("RETURN_URL", "");//页面返回地址
        bodyMap.put("NOTIFY_URL", "");//后台通知地址
        bodyMap.put("REQUEST_IP", "10.10.10.10");//用户请求IP
        bodyMap.put("TERMINAL_TYPE", "");//终端类型
        bodyMap.put("TERMINAL_ID", "");//终端编码
    }
    //    private String mercUserNo = "U20190701155546531";
    private String mercUserNo = "U20190711101004837";
    private String mercID = AutoConstant.MERC_ID_40001;
    private String mercOrderNo = "20190703093432823";
    private String sign = "2e90db432c89ee195f9cceebe8c9911b";
    // 个人信息修改
//    @Ignore
//    @Test
    public void testGetModifyUrl() {
        logger.info("开始执行 [个人信息修改] 用例...");
        initGetModifyUrl();
        bodyMap.put("MERCHANT_ID", mercID);   //Y 商户编号
        bodyMap.put("MERC_USER_NO", mercUserNo);//商户用户号
        bodyMap.put("MERC_ORDER_NO", randomUtils.getDateTime17());
        bodyMap.put("RETURN_URL", "sadfsa");//页面返回地址
        bodyMap.put("NOTIFY_URL", "http://10.63.13.11:12790/simulator-web/mercservice/signCardNotify");//后台通知地址
        requestMap = getRequestMap();
        logger.info("请求参数：{}", requestMap);
        respMap = http.sendHttpRequest(requestMap, getModifyUrlUrl);
        logger.info("响应参数：{}", respMap);
    }

    // 查询个人用户基本信息
//    @Ignore
//    @Test
    public void testQueryUserInfo() {
        logger.info("开始执行 [查询个人用户基本信息] 用例...");
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("mercUserNo", mercUserNo);
        reqMap.put("mercId", mercID);
        reqMap.put("mercOrderNo", mercOrderNo);
        reqMap.put("sign", sign);
//        reqMap.put("mercOrderNo", "20190627164157456");
//        reqMap.put("sign", "c354b4d5af09c36cb4dcecf069422ae8");
        logger.info("请求参数：{}", reqMap);
        respMap = http.sendPostRequest(reqMap, queryUserInfoUrl);
        logger.info("响应参数：{}", respMap);
    }

    // 地址职业信息修改
//    @Ignore
//    @Test
    public void testModifyUserInfo() {
        logger.info("开始执行 [地址职业信息修改] 用例...");
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("mercUserNo", mercUserNo);
        reqMap.put("mercId", mercID);
        reqMap.put("mercOrderNo", mercOrderNo);
        reqMap.put("sign", sign);
        reqMap.put("profession", "5");//职业类别
        /*
         1:党的机关、国家机关、群众团体和社会组织、企事业单位负责人
         2:专业技术人员
         3:办事人员和有关人员
         4:社会生产服务和生活服务人员
         5:农、林、牧、渔业生产及辅助人员
         6:生产制造及有关人员
         7:军人
         8:不便分类的其他从业人员
         */
//        reqMap.put("provCode", "110000");//省代码  例：北京传递110000
//        reqMap.put("cityCode", "110100");//市代码  例：北京市传递110100
//        reqMap.put("areaCode", "110107");//区代码 例：海淀区传递110108
        reqMap.put("permAddr", "测试大厦5");//详细地址  前端需校验字符数不得超过100个字符
        reqMap.put("sex", "");//性别  M：男  F：女
        logger.info("请求参数：{}", reqMap);
        respMap = http.sendPostRequest(reqMap, modifyUserInfoUrl);
        logger.info("响应参数：{}", respMap);
    }

    // 上传证件影印件
//    @Ignore
//    @Test
    public void testUploadIdInfo() {
        logger.info("开始执行 [上传证件影印件] 用例...");
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("mercUserNo", mercUserNo);
        reqMap.put("mercId", mercID);
        reqMap.put("mercOrderNo", mercOrderNo);
        reqMap.put("sign", sign);
        reqMap.put("step", "1");//上传步骤  1、表示单张上传  2、表示确认提交证件，此项时要求idPicFrontOrderNo，idPicReverseOrderNo均不可为空
//        reqMap.put("idPicFront", ImageToBase64("E://测试业务相关//2019工单//PJ2019061801-头条反洗钱改造项目//李立霞.jpg"));//证件正面 D://1d744bc474b3410c9763fb5e0aa476f.jpg base64,人像面,图片的文件大小小于10MB，前端需校验
//        reqMap.put("idPicFront", "");//证件正面 D://1d744bc474b3410c9763fb5e0aa476f.jpg base64,人像面,图片的文件大小小于10MB，前端需校验
//        reqMap.put("idPicReverse", ImageToBase64("E://测试业务相关//2019工单//PJ2019061801-头条反洗钱改造项目//zhangtingting2.jpg"));//证件背面 D://1d744bc474b3410c9763fb5e0aa476f.jpg base64,人像面,图片的文件大小小于10MB，前端需校验
        reqMap.put("idPicReverse", ImageToBase64("E://测试业务相关//2019工单//PJ2019061801-头条反洗钱改造项目//微信图片_20190627140357.jpg"));//证件背面 D://1d744bc474b3410c9763fb5e0aa476f.jpg base64,人像面,图片的文件大小小于10MB，前端需校验
//        reqMap.put("idPicReverse", "");//证件背面 D://1d744bc474b3410c9763fb5e0aa476f.jpg base64,人像面,图片的文件大小小于10MB，前端需校验
        reqMap.put("idPicFrontOrderNo", "32190701112638674100010000");//证件正面请求流水 42190628151928209100010000 当step=1且idPicFront不为空时返回中的idPicorderNo参数值
        reqMap.put("idPicReverseOrderNo", "83190701113014162100010000");//证件背面请求流水  当step=1且idPicReverse不为空时返回中的idPicorderNo参数值
        logger.info("请求参数：{}", reqMap);
        respMap = http.sendPostRequest(reqMap, uploadIdInfoUrl);
        logger.info("响应参数：{}", respMap);
    }

    public String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(Objects.requireNonNull(data));

    }

}
