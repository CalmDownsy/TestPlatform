package com.interfacetest.commonutils.http;

import com.alibaba.fastjson.JSON;
import com.interfacetest.commonutils.HttpSendUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * http xml格式报文封装 重构改造
 *
 * @author Shore
 * @create 2019-08-27
 **/
public class NewRequestMsgUtils {

    private static Logger logger = LoggerFactory.getLogger(NewRequestMsgUtils.class);
    public Map<String, Object> infoMap = new LinkedHashMap<String, Object>();
    public Map<String, Object> bodyMap = new LinkedHashMap<String, Object>();
    public Map<String, Object> transSumMap = new LinkedHashMap<String, Object>();
    public Map<String, Object> transDetailMap = new LinkedHashMap<String, Object>();
    public IdentityHashMap<String, Object> transDetailsMap = new IdentityHashMap<String, Object>();
    public String transDetailsXml = null;
    public Map<String, Object> aipgMap = new LinkedHashMap<String, Object>();
    public Map<String, Object> requestMap = new LinkedHashMap<String, Object>();
    public Map<String, String> respMap = new LinkedHashMap<String, String>();
    private HttpSendUtils http = new HttpSendUtils();
    Map<String, String> riskMsg = new HashMap<String, String>();


    public void mapClear() {
        infoMap.clear();
        bodyMap.clear();
        transSumMap.clear();
        transDetailMap.clear();
        transDetailsMap.clear();
        transDetailsXml = null;
        aipgMap.clear();
        requestMap.clear();
        respMap.clear();
        riskMsg.clear();
    }


    /**
     * 组装map
     *
     * @return
     */
    public Map<String, Object> getRequestMap() {
        if (infoMap.isEmpty()) {
            return requestMap;
        }
        if (!transSumMap.isEmpty()) {
            bodyMap.put("TRANS_SUM", transSumMap);
            bodyMap.put("TRANS_DETAILS", transDetailsXml);
        } else if (!transDetailMap.isEmpty()) {
            bodyMap.put("TRANS_DETAIL", transDetailMap);
        }
        return  constMainParamstructure();
    }

    /**
     * 统一发送请求封装
     *
     * @param reqUrl
     * @return
     * @author zouguoping
     */
    public Map<String, String> httpSend(String reqUrl) {
        requestMap = getRequestMap();
        logger.info("请求reqUrl msg:{}", JSON.toJSONString(reqUrl));
        logger.info("请求参数request msg:{}", JSON.toJSONString(requestMap));
        Map<String, String> responseMap = http.sendHttpRequest(requestMap, reqUrl);
        responseMap.put("SIGNED_MSG", "initdata");
        logger.info("响应参数response msg:{}", JSON.toJSONString(responseMap));
        return responseMap;
    }

    public Map<String, Object> constMainParamstructure() {
        aipgMap.put("INFO", infoMap);
        aipgMap.put("BODY", bodyMap);
        requestMap.put("AIPG", aipgMap);
        logger.info("### 请求参数map:{}", requestMap);
        return requestMap;
    }

    public Map<String, String> httpSendWithParamters(Map<String, Object> map,String reqUrl) {
        logger.info("请求reqUrl msg:{}", JSON.toJSONString(reqUrl));
        logger.info("请求参数request msg:{}", JSON.toJSONString(map));
        Map<String, String> responseMap = http.sendHttpRequest(map, reqUrl);
        responseMap.put("SIGNED_MSG", "initdata");
        logger.info("响应参数response msg:{}", JSON.toJSONString(responseMap));
        return responseMap;
    }



}
