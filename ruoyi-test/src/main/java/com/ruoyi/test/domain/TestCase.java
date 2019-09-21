package com.ruoyi.test.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Created By: zhangsy
 * Description: 用例实体类
 * 2019/7/29 22:07
 */
public class TestCase extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用例Id
     */
    private Long testCaseId;
    /**
     * 父用例Id
     */
    private Long parentId;
    /**
     * 祖级列表
     */
    private String ancestors;
    /**
     * 工单Id
     */
    private Long workOrderId;
    /**
     * 用例标题
     */
    private String testCaseTitle;
    /**
     * 父用例标题
     */
    private String parentTitle;



}
