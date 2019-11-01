package com.interfacetest.constants;

import com.interfacetest.commonutils.RandomUtils;

/**
 * 自动化常量
 *
 * @author yingjie.liu
 * @create 2019-06-13 14:15
 **/
public class AutoConstant {
    /**
     * 关尔靖
     */
    public static final String MERC_ID_20019="800010000020019";
    /**
     * 官靖
     */
    public static final String MERC_ID_20020="800010000020020";
    /**
     * 回归商户
     */
    public static final String MERC_ID_40001="800010000140001";

    /**
     * 付款分账公式
     */
    public static String splitFormula_pay = "2,800010000100007,自动化专用,0.02,自动化主商户|2,700000000391265,自动化分账方,0.02,自动化分账";
    /**
     * 退款分账公式
     */
    public static String splitFormula_refund = "2,800010000100007,0.02,退款1|2,700000000391265,0.02,退款2";
    /**
     * 后台通知地址
     */
    public static final String NOTIFY_URL = new RandomUtils().getProperties("url.properties").getProperty("NOTIFYURL");
    /**
     * 前台通知地址
     */
    public static final String RET_URL = new RandomUtils().getProperties("url.properties").getProperty("RETURL");

}
