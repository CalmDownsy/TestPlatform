package com.ulpay.testplatform.handler.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ulpay.testplatform.domain.CaseRunResult;
import com.ulpay.testplatform.domain.TestCase;
import com.ulpay.testplatform.domain.TestCertManagement;
import com.ulpay.testplatform.utils.mercsign.UlpayRaTools;
import com.ulpay.testplatform.utils.random.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by lizhezhe on 2020/2/4.
 * Json报文转换
 */
public class JsonMessageParseHandler extends BaseMessageParseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonMessageParseHandler.class);
    private static JsonMessageParseHandler jsonMessageParseHandler;

    private JsonMessageParseHandler() {}

    public static JsonMessageParseHandler getInstance() {
        if (jsonMessageParseHandler == null) {
            jsonMessageParseHandler = new JsonMessageParseHandler();
        }
        return jsonMessageParseHandler;
    }

    @Override
    public String parseMessage(String message, CaseRunResult caseRunResult, TestCase testCase, TestCertManagement testCertManagement) throws UnsupportedEncodingException {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        Map<String,Map<String,String>> secMap = new HashMap<String,Map<String,String>>();
        Map<String,Map<String,String>> thirdMap = new HashMap<String,Map<String,String>>();
        Set<String> secNode = new HashSet<String>();
        Set<String> thirdNode = new HashSet<String>();

        JSONArray jsonArray = JSON.parseArray(message);
        RandomUtils randomUtils = new RandomUtils();
        for(int i = 0 ; i < jsonArray.size() ; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String value = jsonObject.getString("value");
            // 替换
            if(!StringUtils.isEmpty(value)){
                if(value.equals("${random}")){
                    jsonObject.put("value",randomUtils.getDateTime17());
                }
                if(value.equals("${random8}")){
                    jsonObject.put("value",randomUtils.getDate8());
                }
            }
            if(StringUtils.isEmpty(jsonObject.getString("parentNode"))){
                paramMap.put(jsonObject.getString("id"),jsonObject.getString("value"));
            }else if(paramMap.containsKey(jsonObject.getString("parentNode"))){
                secNode.add(jsonObject.getString("parentNode"));
            }else if(secNode.contains(jsonObject.getString("parentNode"))){
                thirdNode.add(jsonObject.getString("parentNode"));
            }
        }

        if(secNode.size() > 0){
            nodeMap(secNode,jsonArray,secMap);
        }
        if(thirdNode.size() > 0){
            nodeMap(thirdNode,jsonArray,thirdMap);
        }
        if(!thirdMap.isEmpty() && !secMap.isEmpty()){
            Iterator<String> iterator = thirdNode.iterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                secMap.put(key,thirdMap.get(key));
            }
        }
        if(!secMap.isEmpty()){
            Iterator<String> iterator = secNode.iterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                paramMap.put(key,secMap.get(key));
            }
        }

        caseRunResult.setRequestMsg(JSONObject.toJSONString(paramMap));

        if(paramMap.containsKey("bizContent")){
            String bizConString = JSONObject.toJSONString(paramMap.get("bizContent"));
            paramMap.put("bizContent", org.apache.commons.codec.binary.Base64.encodeBase64String(bizConString.getBytes("utf-8")));
        }

        List<String> keylist = new ArrayList<String>(paramMap.keySet());
        Collections.sort(keylist);
        StringBuffer sbf = new StringBuffer();
        for(String key: keylist){
            sbf.append(paramMap.get(key));
        }

        String requestParam = JSONObject.toJSONString(paramMap);
        return requestParam;
    }

    private void nodeMap(Set nodeSet,JSONArray jsonArray,Map<String,Map<String,String>> map){
        Iterator<String> nodeSetIterator = nodeSet.iterator();
        while(nodeSetIterator.hasNext()){
            String nodeId = nodeSetIterator.next();
            Map<String,String> nodeMap = new HashMap<String,String>();
            for(int i = 0 ; i < jsonArray.size() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.getString("parentNode").equals(nodeId)){
                    nodeMap.put(jsonObject.getString("id"),jsonObject.getString("value"));
                }
            }
            map.put(nodeId,nodeMap);
        }
    }
}
