package com.ulpay.testplatform.handler.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ulpay.testplatform.domain.CaseRunResult;
import com.ulpay.testplatform.domain.TestCase;
import com.ulpay.testplatform.domain.TestCertManagement;
import com.ulpay.testplatform.utils.GZipUtils;
import com.ulpay.testplatform.utils.XmlReverseUtils;
import com.ulpay.testplatform.utils.mercsign.SignUtil;
import com.ulpay.testplatform.utils.random.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author zhangsy
 * @date 2020/1/16 16:56
 * @description xml报文转换
 */
public class XmlMessageParseHandler extends BaseMessageParseHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(XmlMessageParseHandler.class);
    private static XmlMessageParseHandler xmlMessageParseHandler;

    private XmlMessageParseHandler() {}

    public static XmlMessageParseHandler getInstance() {
        if (xmlMessageParseHandler == null) {
            xmlMessageParseHandler = new XmlMessageParseHandler();
        }
        return xmlMessageParseHandler;
    }

    @Override
    public String parseMessage(String message, CaseRunResult caseRunResult,TestCase testCase, TestCertManagement testCertManagement) throws Exception {
        //通用xml报文处理代码片段
        Map<String,Object> topMap = new LinkedHashMap<String,Object>();
        Map<String,Map<String,String>> secMap = new LinkedHashMap<String,Map<String,String>>();
        Map<String,Map<String,String>> thirdMap = new LinkedHashMap<String,Map<String,String>>();
        Map<String,Map<String,String>> forthMap = new LinkedHashMap<String,Map<String,String>>();
        //通用xml报文处理代码片段 收集父节点信息，用于判断(根节点只有一个，二三四级节点可以有多个)
        String baseNode = "";
        Set<String> secNode = new HashSet<String>();
        Set<String> thirdNode = new HashSet<String>();
        Set<String> forthNode = new HashSet<String>();
        Set<String> nodesSet = new HashSet<String>();

        JSONArray jsonArray = JSON.parseArray(message);
        RandomUtils randomUtils = new RandomUtils();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String value = jsonObject.getString("value");
            //参数化替换
            if(!StringUtils.isEmpty(value)){
                switch (jsonObject.getString("value")) {
                    case "${random}":
                        jsonObject.put("value", randomUtils.getDateTime17()); break;
                    case "${date8}":
                        jsonObject.put("value", randomUtils.getDate8()); break;
                    default:
                }
            }

            //通用xml报文处理代码片段 找出各级别根节点
            if(StringUtils.isEmpty(jsonObject.getString("parentNode"))){
                baseNode = jsonObject.getString("id");
            }else if(!StringUtils.isEmpty(baseNode) && jsonObject.getString("parentNode").equals(baseNode)){
                secNode.add(jsonObject.getString("id"));
            }else if(secNode.contains(jsonObject.getString("parentNode"))){
                thirdNode.add(jsonObject.getString("id"));
            }else if(thirdNode.contains(jsonObject.getString("parentNode"))){
                forthNode.add(jsonObject.getString("id"));
            }

            nodesSet.add(jsonObject.getString("parentNode"));
        }


        //通用xml报文处理代码片段 二级节点报文整理
        if(!secNode.isEmpty()){
            checkparentNode(secNode,nodesSet);
            nodeMap(secNode,jsonArray,secMap);
        }

        //通用xml报文处理代码片段 三级节点报文整理
        if(!thirdNode.isEmpty()){
            checkparentNode(thirdNode,nodesSet);
            nodeMap(thirdNode,jsonArray,thirdMap);
        }

        //通用xml报文处理代码片段 四级节点报文整理
        if(!forthNode.isEmpty()){
            checkparentNode(forthNode,nodesSet);
            nodeMap(forthNode,jsonArray,forthMap);
        }

        if(!forthMap.isEmpty() && !thirdMap.isEmpty()){
            //四级节点放入三级节点中
            Iterator<String> forthdkey = forthNode.iterator();
            while(forthdkey.hasNext()){
                String key = forthdkey.next();
                thirdMap.put(key,forthMap.get(key));
            }
        }
        if(!thirdMap.isEmpty() && !secMap.isEmpty()){
            //三级节点放入二级节点中
            Iterator<String> thirdkey = thirdNode.iterator();
            while(thirdkey.hasNext()){
                String key = thirdkey.next();
                secMap.put(key,thirdMap.get(key));
            }
        }
        if(!secMap.isEmpty()){
            topMap.put(baseNode,secMap);
        }

        caseRunResult.setRequestMsg(JSON.toJSONString(topMap));
        String xml = XmlReverseUtils.callMap2XML(topMap);
        LOGGER.info("### xml :" + xml);
        // 加签
        if(testCase.getIsSign().equals("1")){
            LOGGER.debug("****************xml加签*********************");
            SignUtil signUtil = new SignUtil();
            xml = signUtil.merSignaWithMercGateWay(xml, testCertManagement);
            LOGGER.debug("xml签名结果：" + xml);
        }
        // 加压加密
        if(testCase.getIsEncrypted().equals("1")){
            LOGGER.debug("****************xml加压加密*********************");
            xml = GZipUtils.gzipString(xml);
            LOGGER.debug("xml加压加密:" + xml);
        }
        return xml;
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

    private void checkparentNode(Set nodeSet,Set nodesSet){
        Iterator<String> iterator = nodeSet.iterator();
        while(iterator.hasNext()){
            String node = iterator.next();
            if(!nodesSet.contains(node)){
                iterator.remove();
            }
        }
    }


}
