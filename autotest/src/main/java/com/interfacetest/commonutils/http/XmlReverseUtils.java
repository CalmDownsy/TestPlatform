package com.interfacetest.commonutils.http;

import org.dom4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Xml和Map互转工具类
 *
 * @author yingjie.liu
 * @create 2018-02-28 14:04
 **/
public class XmlReverseUtils {
    private static final Logger logger = LoggerFactory.getLogger(XmlReverseUtils.class);
    private static int flag = 1;

    public static String callMap2XML(Map<String, Object> map) {
//		logger.info("将Map转成Xml, Map：{}",map.toString());
        StringBuffer sb = new StringBuffer();
        //sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        map2XML(map, sb);
//		logger.info("将Map转成Xml, Xml：{}",sb.toString());
        return sb.toString();
    }

    /**
     * <p>Title: </p>
     * <p>Description: 将map转化成xml，支持格式为：map.put(String,Object)/map.put(String,Map<String,Object>)
     * map.put(String,Arraylist<Map<String,Object>);</p>
     *
     * @param map
     * @param sb
     */
    @SuppressWarnings("unchecked")
    private static void map2XML(Map<String, Object> map, StringBuffer sb) {
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (null == value) {
                    value = "";
                }
                if (value.getClass().getName().equals("java.util.ArrayList")) {
                    ArrayList<Object> list = (ArrayList<Object>) map.get(key);
                    sb.append("<" + key + ">");
                    for (int i = 0; i < list.size(); i++) {
                        HashMap<String, Object> temp = (HashMap<String, Object>) list.get(i);
                        map2XML(temp, sb);
                    }
                    sb.append("</" + key + ">");

                } else {
                    if (value instanceof HashMap) {
                        sb.append("<" + key + ">");
                        map2XML((HashMap<String, Object>) value, sb);
                        sb.append("</" + key + ">");
                    } else {
                        sb.append("<" + key + ">" + value + "</" + key + ">");
                    }
                }
            }
        } catch (Exception e) {
            sb.append("");
            logger.error("解析Map异常：" + e);
        }
    }

    public static Map xml2map(String xml) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);
        Element rootElement = doc.getRootElement();
        Map<String, Object> map = new HashMap<String, Object>();
        ele2map(map, rootElement);
        return map;
    }

    /***
     * 核心方法，里面有递归调用
     *
     * @param map
     * @param ele
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void ele2map(Map map, Element ele) {
        /**
         *  获得当前节点的子节点
         */
        List<Element> elements = ele.elements();
        if (elements.size() == 0) {
            /**
             *  没有子节点说明当前节点是叶子节点，直接取值即可
             */
            map.put(ele.getName(), ele.getText());
        } else if (elements.size() == 1) {
            /**
             *  只有一个子节点说明不用考虑list的情况，直接继续递归即可
             */
            Map<String, Object> tempMap = new HashMap<String, Object>();
            ele2map(tempMap, elements.get(0));
            putAllNew(map, tempMap);
        } else {
            /**
             *  多个子节点的话就得考虑list的情况了，比如多个子节点有节点名称相同的
             *  构造一个map用来去重
             */
            Map<String, Object> tempMap = new HashMap<String, Object>();
            for (Element element : elements) {
                tempMap.put(element.getName(), null);
            }
            Set<String> keySet = tempMap.keySet();
            for (String string : keySet) {
                Namespace namespace = elements.get(0).getNamespace();
                List<Element> elements2 = ele.elements(new QName(string, namespace));
                /**
                 * 如果同名的数目大于1则表示要构建list
                 */
                if (elements2.size() > 1) {
                    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                    for (Element element : elements2) { //子节点有相同的元素处理（相同的元素用别名标识）
                        Map<String, Object> tempMap1 = new HashMap<String, Object>();
                        ele2map(tempMap1, element);
                        list.add(tempMap1);
                    }
                    putAllNew(map, list);

                } else {
                    /**
                     * 同名的数量不大于1则直接递归去
                     */
                    Map<String, Object> tempMap1 = new HashMap<String, Object>();
                    ele2map(tempMap1, elements2.get(0));
                    putAllNew(map, tempMap1);
                }
            }
        }
    }

    /**
     * <p>Title: </p>
     * <p>Description:如果有重复的元素将当前元素改成name+"1" 保存 </p>
     *
     * @param map
     * @param tempMap
     */
    private static void putAllNew(Map<String, Object> map, Map<String, Object> tempMap) {
        for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (map.containsKey(name)) {
                map.put(name + flag, value);
                flag++;
            } else {
                map.put(name, value);
            }
        }
    }

    /**
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param map
     */
    private static void putAllNew(Map<String, Object> map, List<Map<String, Object>> list) {
        for (Map<String, Object> newMap : list) {
            putAllNew(map, newMap);
        }
    }
}
