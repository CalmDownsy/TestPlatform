package com.interfacetest.commonutils;

import com.ulpay.security.center.engine.service.WestoneHsm;
import com.ulpay.security.center.exception.HSMException;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * 数据加解密工具类
 *
 * @author yingjie.liu
 * @create 2018-02-27 09:38
 **/
public class EncryptUtils {
    public String encryptEncoder(String plaintext) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        WestoneHsm westoneHsm = ac.getBean("westoneHsm", WestoneHsm.class);
        String ciphertext = null;
        try {
            ciphertext = westoneHsm.encrypt(plaintext);
        } catch (HSMException e) {
            System.out.println(e.getMessage());
        }
        return ciphertext;
    }

    public String decryptEncoder(String ciphertext) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        WestoneHsm westoneHsm = ac.getBean("westoneHsm", WestoneHsm.class);
        String plaintext = null;
        try {
            plaintext = westoneHsm.decrypt(ciphertext);
        } catch (HSMException e) {
            System.out.println(e.getMessage());
        }
        return plaintext;
    }
    public static void main(String[] args){
        EncryptUtils en = new EncryptUtils();
        String mi,ming;
        mi = "e7c5846234976635a1049ffc5cb3b352";
        System.out.println(mi+"解密==》"+en.decryptEncoder(mi));
        mi = "e7c584623497663548ad51a067a5fa69";
        System.out.println(mi+"解密==》"+en.decryptEncoder(mi));

        ming = "全渠道一";
        System.out.println(ming+"加密==》"+en.encryptEncoder(ming));
        ming = ming + " " ;
        System.out.println(ming+"加密==》"+en.encryptEncoder(ming));
    }
    /*md5加密*/
    public  String encoderByMd5(String str) throws Exception {
        String newstr="";
        if(StringUtils.isBlank(str)){
            return newstr;
        }
        try {
            //确定计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64 = new BASE64Encoder();
            //加密后的字符串
            newstr = base64.encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e) {
            System.out.println("加密MD5失败！"+e);
            //newstr=str;
            throw new Exception("MD5加密失败");
        }
        return newstr;
    }
}



