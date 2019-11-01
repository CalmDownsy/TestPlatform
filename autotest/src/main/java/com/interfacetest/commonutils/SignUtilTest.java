package com.interfacetest.commonutils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import static com.alibaba.fastjson.util.IOUtils.DIGITS;

/**
 * 处理加签验签工具类
 * 头条模式
 */
public class SignUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(SignUtilTest.class);
    /**
     *
     * @Title: addSign
     * @Description: 签名工具类
     * @return    设定文件
     */
    public static String addSign(Map<String,String> params){

        String oriData=encode2Form(params);
        /**签名源数据串*/
//        logger.info(String.format("===plain===%s",oriData));
        /**签名源串添加固定开头和结尾，增加安全性*/
        String signSource = String.format("%s%s%s", "90HOFa232START",oriData,"^FH897w**END");
        //对串SHA-1算法加密
        String sign = doEncrypt(signSource, "MD5", "utf-8");
//        logger.info(String.format("签名核心数据原文：%s,加密串为：%s", signSource,sign));
        sign = sign.toLowerCase();
        return sign;
    }
    public  boolean validateSign(Map<String,String> params,String sign) throws Exception{
        //针对上面的关键字段进行签名处理
        String oriData=encode2Form(params);
        String signNew=addSign1(oriData);
        if(!signNew.equals(sign)){
           return false;
        }else{
            return true;
        }
    }
    public static String addSign1(String oriData){
        /**签名源数据串*/
        logger.info(String.format("===plain===%s",oriData));
        /**签名源串添加固定开头和结尾，增加安全性*/
        String signSource = String.format("%s%s%s", "90HOFa232START",oriData,"^FH897w**END");
        //对串SHA-1算法加密
        String sign = doEncrypt(signSource, "MD5", "utf-8");
        logger.info(String.format("签名核心数据原文：%s,加密串为：%s", signSource,sign));
        return sign;
    }

    public static String encode2Form(Map<String, String> params) {
        StringBuilder buf = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = "";
            try {
                value = URLEncoder.encode((String) entry.getValue(), "UTF-8");
            } catch (Exception e) {
            }
            String key = (String) entry.getKey();
            buf.append(key).append("=");
            if(StringUtils.isNotEmpty(value)){
                buf.append(value);
            }
            buf.append("&");
        }
        return buf.substring(0, buf.length() - 1);
    }

    /**
     * 对字符串进行加密
     *
     * @param text
     *            签名原文
     * @return 签名后密文
     */
    public static String doEncrypt(String text, String algorithm, String inputCharset) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "System doesn't support this algorithm.");
        }

        try {
            msgDigest.update(text.getBytes(inputCharset)); // 注意该接口是按照指定编码形式签名
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(
                    "System doesn't support your  EncodingException.");
        }

        byte[] bytes = msgDigest.digest();

        String md5Str = new String(encodeHex(bytes));

        return md5Str;
    }
    /**
     * 转成16进制
     * @param data
     * @return
     */
    public static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }

}
