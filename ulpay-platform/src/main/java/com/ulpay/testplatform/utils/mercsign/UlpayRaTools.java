package com.ulpay.testplatform.utils.mercsign;

import cfca.sadk.algorithm.common.Mechanism;
import cfca.sadk.algorithm.common.PKIException;
import cfca.sadk.lib.crypto.JCrypto;
import cfca.sadk.lib.crypto.Session;
import cfca.sadk.util.CertUtil;
import cfca.sadk.util.KeyUtil;
import cfca.sadk.util.Signature;
import cfca.sadk.x509.certificate.X509Cert;
import com.itrus.cryptorole.Recipient;
import com.itrus.cryptorole.Sender;
import com.itrus.cryptorole.bc.RecipientBcImpl;
import com.itrus.cryptorole.bc.SenderBcImpl;
import com.itrus.cvm.CVM;
import com.itrus.util.Base64;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ulpay.testplatform.common.contants.CharSet;
import com.ulpay.testplatform.domain.TestCertManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author lihr
 * @ClassName: UlpayRaTools
 * @Description: 签名验签工具类
 * @date 2015年8月7日 下午5:38:22
 */
public class UlpayRaTools {
    public static final String CA_SERIAL_NUMBER = "ca_serial_number";
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    private  String CvmPath;
    //商户证书路径
    public  String mercCertPath;
    //商户证书密钥
    public  String mercCertPass;
    private  String chooseCert;
    private  String mercGmCertPath;
    private  String mercGmCertPass;
    private  String mercGmTrustPath;
    Logger log = LoggerFactory.getLogger(UlpayRaTools.class);
    public UlpayRaTools(){}
    public UlpayRaTools(TestCertManagement testCertManagement) {
        mercCertPath = FileUploadUtils.getFileFullPath(Global.getProfile(), testCertManagement.getCertFilepath());
        log.info("certPath:{}",mercCertPath);
        mercCertPass = testCertManagement.getCertPass();

    }

    /**
     * @Title: mercSign
     * @Description: 商户加签
     */
    public String mercSign(String oriData,String certType) {
        String signData = "";
        if (certType.equals("CFCA")) {
            log.info("### 加签方式：CFCA证书 》》》》");
            try {
                signData = mercSignCFCA(oriData);
            } catch (Exception e) {
                log.error("数据签名失败：", e);
            }
        } else {
            log.info("### 加签方式：天威诚信证书 》》》》");
            Sender s = new SenderBcImpl();
            try {
                s.initCertWithKey(mercCertPath, mercCertPass.toCharArray());
                byte[] p7dtach = s.signMessage(oriData.getBytes(CharSet.encrypt_charset));
                signData = Base64.encode(p7dtach);
            } catch (Exception e) {
                log.error("商户数据签名失败：", e);
                return null;
            }
        }
        return signData;
    }

    public String mercSignCFCA(String plainText) throws Exception {
        String signTag = "";
        try {
            JCrypto.getInstance().initialize("JSOFT_LIB", null);
            Session session = JCrypto.getInstance().openSession("JSOFT_LIB");
            PrivateKey privateKey = KeyUtil.getPrivateKeyFromSM2(mercGmCertPath, mercGmCertPass);
            X509Cert cert = CertUtil.getCertFromSM2(mercGmCertPath);
            System.err.println(cert.getIssuer());
            byte[] sourceData = plainText.getBytes("UTF-8");
            Signature signature = new Signature();
            String signAlg = Mechanism.SM3_SM2;
            byte[] base64P7SignedData = signature.p7SignMessageDetach(signAlg, sourceData, privateKey, cert, session);
            signTag = new String(base64P7SignedData);
        } catch (Exception e) {
            throw new Exception("加签失败", e);
        }
        return signTag;
    }


    /**
     * 验签方法
     *
     * @param signData 签名结果
     * @param oriData  原文信息
     */
    public Map<String, String> verify(String signData, String oriData) {

        if (CvmPath == null || "".equals(CvmPath)) {
            throw new IllegalArgumentException("cvmPath IS NOT NULL");
        }
        Map<String, String> res = new HashMap<String, String>();
        if (chooseCert.contentEquals("CFCA")) {
            try {
                res = mercVerifyCFCA(oriData, signData);
            } catch (Exception e) {
                log.info("验证报文信息失败。", e);
                res.put(FAILED, "验签异常");
            }
        } else {
            Recipient r = new RecipientBcImpl();
            String status = FAILED;
            String resInfo = "未知";
            try {
                /**
                 *  验证签名结果有效性
                 */
                X509Certificate signer = r.verifySignature(oriData.getBytes("UTF-8"), Base64.decode(signData));

                com.itrus.cert.X509Certificate itrusCert = com.itrus.cert.X509Certificate.getInstance(signer);
                /**
                 * 获取证书商户编号与证书用途
                 */
                String icaSerialNumber = itrusCert.getICASerialNumber();

                log.debug("证书编号为【{}】", icaSerialNumber);
                res.put(CA_SERIAL_NUMBER, icaSerialNumber);
                /**
                 * 初始化证书校验信息
                 */
                CVM.config(CvmPath);
                /**
                 * 校验证书的颁发机构
                 */
                int ret = CVM.verifyCertificate(itrusCert);

                if (ret != CVM.VALID) {
                    switch (ret) {
                        case CVM.CVM_INIT_ERROR:
                            resInfo = "CVM初始化错误，请检查配置文件或给CVM增加支持的CA。";
                            break;
                        case CVM.CRL_UNAVAILABLE:
                            resInfo = "CRL不可用，未知状态。";
                            break;
                        case CVM.EXPIRED:
                            resInfo = "证书已过期。";
                            break;
                        case CVM.ILLEGAL_ISSUER:
                            resInfo = "非法颁发者。";
                            break;
                        case CVM.REVOKED:
                            resInfo = "证书已吊销。";
                            break;
                        case CVM.UNKNOWN_ISSUER:
                            resInfo = "不支持的颁发者。请检查cvm.xml配置文件";
                            break;
                        case CVM.REVOKED_AND_EXPIRED:
                            resInfo = "证书被吊销且已过期。";
                            break;
                        default:
                            resInfo = "验证证书出现未知错误，请联系证书管理人员。";
                    }
                } else {
                    status = SUCCESS;
                    resInfo = "当前签名证书是有效的！";
                }
            } catch (Exception e) {
                log.info("验证报文信息失败。", e);
                res.put(FAILED, "验签异常");
            }
            res.put("retCode", status);
            res.put("retMsg", resInfo);
        }

        log.info("验签结果==>{}", res);
        return res;
    }


    public Map<String, String> mercVerifyCFCA(String signData, String oriData) throws PKIException, UnsupportedEncodingException {
        Map<String, String> res = new HashMap<String, String>();
        String status = "failed";
        res.put("retCode", status);
        res.put("retMsg", "验签失败");
        boolean verifyResult = false;

        JCrypto.getInstance().initialize("JSOFT_LIB", null);
        Session session = JCrypto.getInstance().openSession("JSOFT_LIB");
        Signature signature = new Signature();
        X509Cert userCert = signature.getSignerX509CertFromP7SignData(signData.getBytes());
        X509Cert trustCert = new X509Cert(mercGmTrustPath);
        // 校验证书签名的有效性
        boolean certVeri = signature.p1VerifyMessage(userCert.getSignatureAlgName(), userCert.getTBSCertificate(), userCert.getSignature(), trustCert.getPublicKey(), session);
        if (!certVeri) {
            res.put("retMsg", "无效证书");
            return res;
        }
        Date now = new Date();
        // 校验证书失效时间（晚于当前时间即有效）
        if (now.after(userCert.getNotAfter())) {
            res.put("retMsg", "用户证书已过期");
            return res;
        }
        // 校验证书生效时间（早于当前时间即有效）
        if (now.before(userCert.getNotBefore())) {
            res.put("retMsg", "用户证书未生效");
            return res;
        }
        verifyResult = signature.p7VerifyMessageDetach(oriData.getBytes("UTF-8"), signData.getBytes("UTF-8"), session);
        if (verifyResult) {
            res.put("retCode", "success");
            res.put("retMsg", "验签通过");
            log.info("###  验签通过");
        }
        return res;
    }
}
