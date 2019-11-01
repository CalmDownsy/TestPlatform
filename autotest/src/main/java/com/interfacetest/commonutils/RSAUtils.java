package com.interfacetest.commonutils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @className RSAUtils
 * @Description RSA公钥/私钥/签名工具包
 * @Author fengyw@ulpay.com
 * @Date 2019/1/10-15:43
 **/
public class RSAUtils {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return new String(Base64Utils.encode(signature.sign()));
    }

    /**
     * 校验数字签名
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign.getBytes()));
    }

    public static void main(String[] args) throws Exception {
        String strData = "jkhgsjkhhjkfdshkkdshjkgfdhkjkjadfsfkjhfklhjdkljshklfghiauhoheohfroihvhdkjhfkjhakljhgkjhakghjakhgoahdogihoahofg";
        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVOt0u8IFdOQWFcQew/I3p+euhSI3M8vdf5ILbkmXVimmRM1mNSNp4ETZRHd1g6+RdPl7ZS+/5gdf9YEkWdkAbcyvBsAQwKYI7IPNhk7YhONF8WpBp4szrM4G45zH+v6JbuA92eSCG66nErp/OWuBid6wl+7kPRs9PnXENF0kHwf3nzDdM6adcIx89y9qIXhJjXG3av8E9HmUDxxPoJJB2BIIjnLxSiISvk5+eNyP+zXTOuvYcRPpn0sXodFiwllR9bmbBYNi6/M30mKtVKMNzsolQ6kMWsi/SkachTP1Dvi4gySQmUZlkl64W2skUMUBJz6Omz5MbUzPCvU4xTHx3AgMBAAECggEAXdYn4y5EilQizqgmh/onWqtWqZIv6GIXf2r3hg5mjoJ8o1tAwoC/L6TTHOrEm/95F9DyX31KpwAoyyDSlJdC9H/VUfPWjlprk7bCk+cILZfG0oU4Lfz5kiRPO+/VFjV2aGoy/vJnq69gj1anEmE+m2xZCYFh+uMx1QRRZEPyHje8QMImeKYtLUKaphe6xqW2EKeH9p4p5+6o0i/9m3fNiQ32OZjg5f4R8q41eWv932/QaC0xSStMlKu72FIOzPtcgIwZ6nqDPG0UqFtELAd8dAV1D2JWc/kvuTCLOKhoSWKH2fgFqzqxIWF8dPu0tnnNm24GDQOSA/z6LnNLHuJEMQKBgQDc/H5Lc5Is7F241L6YtIx8K9RGDHtOf8O4bbSIo4xTUZz5wONoNVScNA8BRYSNrE9Ue8nWqyv4exO2EuTNake9BGteTaTazc/LPTwyhmk8buy6GPNhMpA35U6ckyuuPGW39i4bESDk0PKkAL1mGySoR5uFiXpnT+p2GVmIKDuD/QKBgQCs39WZFi3IqaTsgAWPqZktN87FR/lHWpdJeS5jSlNtdFg7DEUMNcJjjTi0tleFvVFQMcJpSe7dxUkEQSrDv5poPJCEPi76txdpIdY5uNiyHwNaIMWvBlBWXE60eRxYeOwXHlGiv+I4PDZEOoQ/ibm2LeOmRusy7cSqjo6KTIVagwKBgQCJaNtjMT7Qfj0cw/Sr4YCFaX95+yLlQd7CnDyoxgFogZP5XK5KyvaMlnZKFR8CNQWzA7ISsKcLwAQWsBLjg9WNu+TDY8ZBY750ouP+isN/F35NjVwwsQ8qIoTKKVz834Nka0hp/jGU9jQaNHeQuQoHNVeIJl8dRH0G6GZamqG/RQKBgHZyx3+k2qnq0r3Y2fadVf7NhjdWIU7qm04UYdxWSONuHCYZOhYrn41mbbwkU8Om+9fewD40Eh6R1n27yPlmhv10wfBaSwYt/TRjoszaAl/JMBrI6aoKwXDqvmKZuPpFoD4W4AiLQQ0W9He7QHHog/53YsvDO9QhZecW1kOcOqLRAoGAJQ0GZh7H7OZCHS2HJPsOH64eZah3pZ7DlDEeQXfLiGps2BHsFu9cxpTPLlSmjddYG1Fov7m7yWv/xDR43u/WnaIDqXh8otU0C/pNOWIaWY/fSexWMxSjwXw1pYM01UsyHWBlORLAojYmCLO0x4Vsk8aduVjm/e/oTnEgz9lTkcI=";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi8IBcgqMSKJj8RdYQOk5wZHK7jHlo42GCFwH82umijum2SoBkMAThifkspBWY6L8fPFhsk+h5YqCIRdaQknvxIkBFLuqNweLDxIRlSm3XhUTEE+Dg3d7Me6JU8E0h2FHN0VW0Kc9ErrrQW/Hz5qS/NZb4vqiy44i7ZzQ/tMrBfuFD8bwKgBAFs1RwDYLXcDNZZS+M57jRdtAxoOIeyUj2uu+CGrQhUBNoca1RPz4z9Ayln153pzyFP8/VkR6M5rT/ocRS4qMiEwoImwtKaSg6JPXcdqrZYkGyVBJzd9zkv/ZdEUBglw0XI+TAFmfEjRtQaAJBfFwA4B4bll09BbQRQIDAQAB";
        byte[] encodedData = strData.getBytes();
        String sign = RSAUtils.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtils.verify(strData.getBytes("UTF-8"), publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }
}
