package com.ruoyi.test;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.test.domain.TestMessage;
import com.ruoyi.test.domain.TestCase;
import com.ruoyi.test.mapper.TestMessageMapper;
import com.ruoyi.test.service.ITestMessageService;
import com.ruoyi.test.service.utils.ClassScannerUtils;
import com.ruoyi.test.service.ITestCaseService;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.reporters.XMLConstants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Auther: zhangsy
 * @Date: 2019/10/24 14:30
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuoYiApplication.class)
public class Test001 {

    @Autowired
    private ITestCaseService iTestcaseService;

    @Autowired
    private TestMessageMapper testMessageMapper;



    @Test
    public void test2() {
        Set<Class<?>> autotest = ClassScannerUtils.searchClasses("autotest", "com.http");
        Iterator<Class<?>> iterator = autotest.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            System.out.println(next.getCanonicalName());
        }

    }

    @Test
    public void saveMessage() {
        TestMessage testMessage = new TestMessage();
        testMessage.setInterfaceId(1L);
        testMessage.setMessageName("测试报文123");
        testMessage.setMessageType("3");
        testMessage.setParameterJson("<AIPG>\n" +
                "<INFO>\n" +
                "    <TRX_CODE>100030</TRX_CODE>\n" +
                "    <VERSION>2.0</VERSION>\n" +
                "    <DATA_TYPE>0</DATA_TYPE>\n" +
                "    <LEVEL>0</LEVEL>\n" +
                "    <REQ_SN>2019121220151335559</REQ_SN>\n" +
                "    <SIGNED_MSG>signedMsg</SIGNED_MSG>\n" +
                "</INFO>\n" +
                "<BODY>\n" +
                "    <BUSINESS_CODE>04900</BUSINESS_CODE>\n" +
                "    <DEVICE_INFO>\n" +
                "        ABCD:EF01:2345:6789:ABCD:EF01:2345:6789|F0E1D2C3B4A5|352044063995403|460030912121001|898600680113F0123014|968778695A4B|116.360207,-39.921064|\n" +
                "    </DEVICE_INFO>\n" +
                "    <REMARK>张思远做的单笔付款</REMARK>\n" +
                "    <TERMINALID>1111111111122233</TERMINALID>\n" +
                "    <BANK_SHORT_NAME>平安银行</BANK_SHORT_NAME>\n" +
                "    <ACCOUNT_TYPE>00</ACCOUNT_TYPE>\n" +
                "    <MERC_ORDER_NO>zsy20191212201514820167</MERC_ORDER_NO>\n" +
                "    <TRANS_AMT>0.01</TRANS_AMT>\n" +
                "    <BANK_PROVINCE>北京</BANK_PROVINCE>\n" +
                "    <MERC_ORDER_DATE>20191212</MERC_ORDER_DATE>\n" +
                "    <NOTIFY_URL>http://10.63.13.21:12790/simulator-web/mercservice/payOutBackNotify</NOTIFY_URL>\n" +
                "    <BANK_NAME>平安银行</BANK_NAME>\n" +
                "    <BANK_NO>105</BANK_NO>\n" +
                "    <BANK_CITY>北京</BANK_CITY>\n" +
                "    <TERMINAL_TYPE>01</TERMINAL_TYPE>\n" +
                "    <CURRENCY>CNY</CURRENCY>\n" +
                "    <ACCOUNT_PROP>0</ACCOUNT_PROP>\n" +
                "    <MERCHANT_ID>800010000020019</MERCHANT_ID>\n" +
                "    <REMARK2>备注2</REMARK2>\n" +
                "    <REMARK3>备注3</REMARK3>\n" +
                "    <EXTEND1></EXTEND1>\n" +
                "    <EXTEND2></EXTEND2>\n" +
                "    <EXTEND3></EXTEND3>\n" +
                "    <ACCOUNT_NO>9559794382528934794</ACCOUNT_NO>\n" +
                "    <ACCOUNT_NAME>张思远</ACCOUNT_NAME>\n" +
                "</BODY>\n" +
                "</AIPG>");
        testMessageMapper.insertTestMessage(testMessage);
    }
}
