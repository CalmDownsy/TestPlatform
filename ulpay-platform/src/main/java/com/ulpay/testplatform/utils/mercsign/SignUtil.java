package com.ulpay.testplatform.utils.mercsign;

import com.ulpay.testplatform.domain.TestCase;
import com.ulpay.testplatform.domain.TestCertManagement;

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
    public String merSignaWithMercGateWay(String orgMsg, TestCertManagement testCertManagement) {
        String signLabel = "<SIGNED_MSG>signedMsg</SIGNED_MSG>";
        String signLabelEmpty = "<SIGNED_MSG></SIGNED_MSG>";
        orgMsg = orgMsg.replace(signLabel, signLabelEmpty);
        UlpayRaTools ulpayRaTools = new UlpayRaTools(testCertManagement);
        String signed = ulpayRaTools.mercSign(orgMsg,testCertManagement.getCertType());
        StringBuffer signBuffer = new StringBuffer("<SIGNED_MSG>").append(signed).append("</SIGNED_MSG>");
        return orgMsg.replace(signLabelEmpty, signBuffer.toString());
    }
}
