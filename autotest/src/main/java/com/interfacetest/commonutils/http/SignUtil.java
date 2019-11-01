package com.interfacetest.commonutils.http;

/**
 * 生成签名
 * @author yingjie.liu
 * @create 2017-10-25 19:40
 **/
public class SignUtil {
    /**
     * xml格式签名
     * @param orgMsg
     * @return
     */
    public static String merSignaWithMercGateWay(String orgMsg) {
        String signLabel = "<SIGNED_MSG>signedMsg</SIGNED_MSG>";
        String signLabelEmpty = "<SIGNED_MSG></SIGNED_MSG>";
        orgMsg = orgMsg.replace(signLabel, signLabelEmpty);
        UlpayRaTools ulpayRaTools = UlpayRaTools.getInstance();
        String signed = ulpayRaTools.mercSign(orgMsg);
        StringBuffer signBuffer = new StringBuffer("<SIGNED_MSG>").append(signed).append("</SIGNED_MSG>");
        return orgMsg.replace(signLabelEmpty, signBuffer.toString());
    }
}
