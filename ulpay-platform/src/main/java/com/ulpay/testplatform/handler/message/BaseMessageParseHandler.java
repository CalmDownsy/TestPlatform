package com.ulpay.testplatform.handler.message;

import com.ulpay.testplatform.common.enums.MessageType;
import com.ulpay.testplatform.domain.CaseRunResult;
import com.ulpay.testplatform.domain.TestCase;
import com.ulpay.testplatform.domain.TestCertManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangsy
 * 根据报文格式进行转换，数据库中同一为json
 */
public abstract class BaseMessageParseHandler {

    private static final Logger logger = LoggerFactory.getLogger(BaseMessageParseHandler.class);

    /**
     * 转换报文格式
     * @param message   原生json报文
     * @return  转换后报文
     */
    public abstract String parseMessage(String message, CaseRunResult caseRunResult, TestCase testCase, TestCertManagement testCertManagement) throws Exception;

    public static BaseMessageParseHandler getMsgHandlerInstance(String msgType) {
        return MessageType.getMsgHandler(msgType);
    }
}
