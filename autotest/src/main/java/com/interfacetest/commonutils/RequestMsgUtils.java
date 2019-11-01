package com.interfacetest.commonutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * http xml格式报文封装
 *
 * @author yingjie.liu
 * @create 2018-03-05 17:27
 **/
public class RequestMsgUtils {
	private static Logger logger = LoggerFactory.getLogger(RequestMsgUtils.class);
    public Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
    public Map<String, Object> bodyMap = new LinkedHashMap<String, Object>();
    public Map<String, Object> transSumMap = new LinkedHashMap<String, Object>();
    public Map<String, Object> transDetailMap = new LinkedHashMap<String, Object>();
    public IdentityHashMap<String,Object> transDetailsMap = new IdentityHashMap<String, Object>();
    public String transDetailsXml = null;
    public Map<String, Object> aipgMap = new LinkedHashMap<String, Object>();
    public Map<String, Object> requestMap = new LinkedHashMap<String, Object>();
    public Map<String, String> respMap = new LinkedHashMap<String, String>();
    public void mapInit(){
        infoMap = new LinkedHashMap<>();
        bodyMap = new LinkedHashMap<>();
        transSumMap = new LinkedHashMap<>();
        transDetailMap = new LinkedHashMap<>();
        transDetailsMap = new IdentityHashMap<>();
        transDetailsXml = null;
        aipgMap = new LinkedHashMap<>();
        requestMap = new LinkedHashMap<>();
        respMap = new LinkedHashMap<>();
    }
    public Map<String,Object> getRequestMap(){
        if (!infoMap.isEmpty()){
            if (transSumMap.isEmpty()){
                if (transDetailMap.isEmpty()){
                    //simple msg
                    aipgMap.put("INFO", infoMap);
                    aipgMap.put("BODY", bodyMap);
                    requestMap.put("AIPG", aipgMap);
                }else {
                    //transDetail
                    aipgMap.put("INFO", infoMap);
                    bodyMap.put("TRANS_DETAIL",transDetailMap);
                    aipgMap.put("BODY", bodyMap);
                    requestMap.put("AIPG", aipgMap);
                }
            }else {
                //transSum
                aipgMap.put("INFO", infoMap);
                bodyMap.put("TRANS_SUM",transSumMap);
                bodyMap.put("TRANS_DETAILS",transDetailsXml);
                aipgMap.put("BODY", bodyMap);
                requestMap.put("AIPG", aipgMap);
            }
        }
        return requestMap;
    }

    /**
     * 统一发送请求封装
     * @param http
     * @param reqUrl
     * @param requestMap
     * @return
     * @author zouguoping
     */
    public Map<String, String> httpSend(HttpSendUtils http, String reqUrl, Map<String, Object> requestMap){
    	requestMap = getRequestMap();
    	logger.info("reqUrl msg:{}",reqUrl);
        logger.info("request msg:{}",requestMap);
        Map<String, String> responseMap = http.sendHttpRequest(requestMap,reqUrl);
        responseMap.put("SIGNED_MSG","initdata");
        logger.info("response msg:{}",responseMap);
        return responseMap;
    }

}
