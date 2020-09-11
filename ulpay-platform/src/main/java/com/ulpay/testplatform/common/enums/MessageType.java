package com.ulpay.testplatform.common.enums;

import com.ulpay.testplatform.handler.message.BaseMessageParseHandler;
import com.ulpay.testplatform.handler.message.JsonMessageParseHandler;
import com.ulpay.testplatform.handler.message.XmlMessageParseHandler;

/**
 * @author zhangsy
 */
public enum MessageType {

    /**
     * xml
     */
    XML("1", XmlMessageParseHandler.getInstance()),

    /**
     * json
     */
    JSON("2", JsonMessageParseHandler.getInstance()),

    /**
     * url
     */
    URL("3", null),

    /**
     * javabean 暂时也使用json处理器
     */
    JAVABEAN("4", JsonMessageParseHandler.getInstance());

    private String code;
    private BaseMessageParseHandler msgHandler;

    MessageType(String code, BaseMessageParseHandler msgHandler) {
        this.code = code;
        this.msgHandler = msgHandler;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BaseMessageParseHandler getMsgHandler() {
        return msgHandler;
    }

    public void setMsgHandler(BaseMessageParseHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    public static BaseMessageParseHandler getMsgHandler(String msgType) {
        for (MessageType messageType : values()) {
            if (msgType.equals(messageType.getCode())) {
                return messageType.getMsgHandler();
            }
        }
        return null;
    }
}
