package com.ulpay.testplatform.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ulpay.testplatform.common.contants.UrlProtocol;
import com.ulpay.testplatform.common.enums.RequestProtocol;
import com.ulpay.testplatform.common.enums.RunStatus;
import com.ulpay.testplatform.domain.*;
import com.ulpay.testplatform.handler.message.BaseMessageParseHandler;
import com.ulpay.testplatform.service.*;
import com.ulpay.testplatform.utils.DBConnectUtils;
import com.ulpay.testplatform.utils.client.DubboClientUtils;
import com.ulpay.testplatform.utils.client.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.TestCaseMapper;
import com.ruoyi.common.core.text.Convert;
import org.testng.Assert;

/**
 * 用例实体Service业务层处理
 *
 * @author zhangsy
 * @date 2020-01-02
 */
@Service
public class TestCaseServiceImpl implements ITestCaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCaseServiceImpl.class);
    @Autowired
    private TestCaseMapper testCaseMapper;

    @Autowired
    private ITestInterfaceInfoService testInterfaceInfoService;

    @Autowired
    private ICaseRunResultService caseRunResultService;

    @Autowired
    private ITestMessageService testMessageService;

    @Autowired
    private ITestCertManagementService testCertManagementService;

    @Autowired
    private DBConnectUtils dbConnectUtils;

    @Autowired
    private ITestEnvironmentConfigService environmentConfigService;

    @Autowired
    private ITOutbaseConfService outbaseConfService;

    @Autowired
    private ITestReportService testReportService;

    @Autowired
    private ITestCaseBoxService testCaseBoxService;

    /**
     * 查询用例实体
     *
     * @param caseId 用例实体ID
     * @return 用例实体
     */
    @Override
    public TestCase selectTestCaseById(Long caseId) {
        return testCaseMapper.selectTestCaseById(caseId);
    }

    /**
     * 查询用例实体列表
     *
     * @param testCase 用例实体
     * @return 用例实体
     */
    @Override
    public List<TestCase> selectTestCaseList(TestCase testCase) {
        return testCaseMapper.selectTestCaseList(testCase);
    }

    /**
     * 新增用例实体
     *
     * @param testCase 用例实体
     * @return 结果
     */
    @Override
    public int insertTestCase(TestCase testCase) {
        testCase.setCreateTime(DateUtils.getNowDate());
        return testCaseMapper.insertTestCase(testCase);
    }

    /**
     * 修改用例实体
     *
     * @param testCase 用例实体
     * @return 结果
     */
    @Override
    public int updateTestCase(TestCase testCase) {
        testCase.setUpdateTime(DateUtils.getNowDate());
        return testCaseMapper.updateTestCase(testCase);
    }

    /**
     * 删除用例实体对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestCaseByIds(String ids) {
        return testCaseMapper.deleteTestCaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用例实体信息
     *
     * @param caseId 用例实体ID
     * @return 结果
     */
    @Override
    public int deleteTestCaseById(Long caseId) {
        return testCaseMapper.deleteTestCaseById(caseId);
    }

    /**
     * 执行单个用例
     *
     * @param caseRunData 执行信息
     * @return 执行结果
     */
    @Override
    public CaseRunResult runCase(CaseRunData caseRunData) throws Exception {
        CaseRunResult caseRunResult = new CaseRunResult();
        caseRunResult.setCaseId(caseRunData.getCaseId());

        LOGGER.info("runCase->caseRunData.caseId:" + caseRunData.getCaseId());
        //获取用例执行信息
        TestCase testCase = selectTestCaseById(caseRunData.getCaseId());
        //参数
        String paramsJson = testCase.getParameterJson();
        LOGGER.debug("请求报文为: {}", paramsJson);
        if (StringUtils.isEmpty(paramsJson)) {
            caseRunResult.setResultMsg("测试数据为空");
            caseRunResult.setConsumingTime(0L);
            caseRunResult.setRunStatus(RunStatus.FAIL.getCode());
            caseRunResultService.insertCaseRunResult(caseRunResult);
            return caseRunResult;
        }

        //1 前置用例 2 sql 3 等待指定时间 4 无
        switch (testCase.getPreActionId()) {
            case 1:
                executePreCase(caseRunData, testCase, caseRunResult);
                break;
            case 2:
                executePreSql(testCase, caseRunResult);
                break;
            case 3:
                executePreDelay(testCase, caseRunResult);
                break;
            default:
                LOGGER.info("无前置动作");
                break;
        }
        if (RunStatus.FAIL.getCode().equals(caseRunResult.getRunStatus())) {
            caseRunResultService.insertCaseRunResult(caseRunResult);
            return caseRunResult;
        }

        //接口信息
        TestInterfaceInfo testInterfaceInfo =
                testInterfaceInfoService.selectTestInterfaceInfoById(testCase.getInterfaceId());

        //匹配执行环境信息
        TestEnvironmentConfig environmentConfig = new TestEnvironmentConfig();
        environmentConfig.setEnvFlag(caseRunData.getEnvConf());
        environmentConfig.setEnvName(testInterfaceInfo.getEnvNameLink());
        List<TestEnvironmentConfig> envList = environmentConfigService.selectTestEnvironmentConfigList(environmentConfig);
        if (envList.size() == 0) {
            throw new BusinessException("所选执行环境未维护，环境匹配异常");
        }
        environmentConfig = envList.get(0);

        //请求地址
        String reqUrl = environmentConfig.getHost() + ":" + environmentConfig.getPort() + "/" + testInterfaceInfo.getEnvNameLink() + testInterfaceInfo.getRequestUrl();
        if (testInterfaceInfo.getInterfaceProtocol().equals(RequestProtocol.HTTPS.getCode())) {
            reqUrl = UrlProtocol.URL_PROTOCOL_HTTPS + reqUrl;
        } else {
            // 默认http
            reqUrl = UrlProtocol.URL_PROTOCOL_HTTP + reqUrl;
        }
        LOGGER.info("请求地址: {}", reqUrl);
        caseRunResult.setRequestUrl(reqUrl);

        //报文处理
        TestMessage testMessage = testMessageService.selectTestMessageById(testCase.getMessageId());
        caseRunResult.setMessageType(testMessage.getMessageType());
        BaseMessageParseHandler messageHandler = BaseMessageParseHandler.getMsgHandlerInstance(testMessage.getMessageType());
        if (messageHandler == null) {
            throw new BusinessException("获取报文处理器失败");
        }

        //获取用例对应的证书
        TestCertManagement testCertManagement = testCertManagementService.selectTestCertManagementById(testCase.getCertId());
        String message = messageHandler.parseMessage(paramsJson, caseRunResult, testCase, testCertManagement);

        // TODO: 2020/6/17  Client 还得优化....
        Map responseMap;
        if ("1".equals(testMessage.getMessageType())) {
            HttpClientUtils httpClientUtils = new HttpClientUtils();
            responseMap = httpClientUtils.sendHttpRequest(message, reqUrl);
        } else {
            DubboClientUtils dubboClientUtils = new DubboClientUtils();
            responseMap = dubboClientUtils.sendHessianRequest(message, reqUrl, testInterfaceInfo.getEnvNameLink());
        }
        LOGGER.info("响应结果: {}", responseMap.toString());
        caseRunResult.setResponseMsg(JSONObject.toJSONString(responseMap));
        caseRunResult.setRunStatus(RunStatus.SUCCESS.getCode());

        // 校验
        if (!StringUtils.isEmpty(testCase.getCheckExpression())) {
            assertCaseResult(testCase, responseMap, caseRunResult);
        }

        // 单跑即调试，不持久化到数据库
        caseRunResultService.insertCaseRunResult(caseRunResult);
        return caseRunResult;
    }

    @Override
    public int[] batchRunCases(Long boxId, String env) throws Exception {
        TestCaseBox testCaseBox = testCaseBoxService.selectTestCaseBoxById(boxId);
        String caseArrayStr = testCaseBox.getCaseBox();
        List<CaseRunData> caseRunDataList = JSONArray.parseArray(caseArrayStr, CaseRunData.class);
        Set<CaseRunResult> caseRunResults = new HashSet<CaseRunResult>();
        //用例数量 分别是finishCount和totalCount,用于比较是否执行结束
        int[] count = new int[]{0, caseRunDataList.size()};
        if (StringUtils.isEmpty(caseArrayStr) || caseRunDataList.size() == 0) {
            return null;
        }
        // 不考虑场景嵌套，单纯的CASE集合，即使A依赖B，也只将A视为一个原子

        // 测试报告
        TestReport testReport = new TestReport();
        testReport.setFinishFlag("N");
        testReport.setCaseboxId(testCaseBox.getId());
        testReport.setCaseboxName(testCaseBox.getBoxName());
        testReportService.insertTestReport(testReport);

        // TODO: 2020/5/15 后续需要加入用例执行优先级并排序执行
        for (CaseRunData caseRunData : caseRunDataList) {
            caseRunData.setEnvConf(env);
            CaseRunResult caseRunResult = runCase(caseRunData);
            caseRunResult.setReportId(testReport.getReportId());
            caseRunResults.add(caseRunResult);
            caseRunResultService.updateCaseRunResult(caseRunResult);

            count[0]++;
            // 如果finshCount == totalCount 说明执行结束
            if (count[0] == count[1]) {
                testReport.setCaseRunResults(caseRunResults);
                testReport.setFinishFlag("Y");
                // 设置报告详情
                testReport.setResultDetails(testReportService.setReportDetails(testReport));
                testReportService.updateTestReport(testReport);
            }
        }

        return new int[]{testReport.getReportId().intValue(), count[1]};
    }

    @Override
    public CaseRunResult cronRunCase(String caseId, String runEnv) {
        CaseRunData caseRunData = new CaseRunData();
        caseRunData.setCaseId(Long.parseLong(caseId));
        caseRunData.setEnvConf(runEnv);
        CaseRunResult caseRunResult = null;
        try {
            LOGGER.info("传入的参数列表:caseId->{},runEnv->{}", caseId, runEnv);
            caseRunResult = runCase(caseRunData);
        } catch (Exception e) {
            caseRunResult.setRunStatus(RunStatus.FAIL.getCode());
            caseRunResult.setCaseId(Long.parseLong(caseId));
            caseRunResult.setResultMsg("定时任务执行失败：" + e.getMessage());
            caseRunResultService.insertCaseRunResult(caseRunResult);
            e.printStackTrace();
        }
        return caseRunResult;
    }

    /**
     * 执行前置用例
     *
     * @param testCase
     */
    private void executePreCase(CaseRunData currentRunData, TestCase testCase, CaseRunResult caseRunResult) throws Exception {
        String actionDetail = testCase.getActionDetail();
        JSONObject preActionObject = JSONObject.parseObject(actionDetail);

        String preCaseId = preActionObject.getString("preCaseId");
        //支持多字段替换值
        String targetKey = preActionObject.getString("targetKey");
        if (StringUtils.isEmpty(actionDetail)) {
            throw new BusinessException("未选择前置用例");
        }
        List<String> targetKeyList = new ArrayList<String>(Arrays.<String>asList(targetKey.split(",")));

        // 执行前置用例
        CaseRunData preCaseRunData = new CaseRunData();
        preCaseRunData.setCaseId(Long.parseLong(preCaseId));
        preCaseRunData.setEnvConf(currentRunData.getEnvConf());
        LOGGER.info("等待前置动作,执行用例： {}", selectTestCaseById(Long.parseLong(preCaseId)).getCaseName());
        //递归调用自身，获取到需要的目标字段结果并进行替换
        CaseRunResult preCaseRunResult = runCase(preCaseRunData);
        //当前置用例执行成功时再进行结果获取，否则直接返回执行失败
        if (preCaseRunResult.getRunStatus().equals(RunStatus.SUCCESS.getCode())) {
            LOGGER.info("前置用例执行成功，获取前置用例结果");
            JSONArray jsonArray = JSON.parseArray(testCase.getParameterJson());
            // 遍历当前用例参数，匹配需要替换的字段
            for (Object object : jsonArray) {
                JSONObject paramObject = (JSONObject) object;
                for (String s : targetKeyList) {
                    if (s.equals(paramObject.get("id"))) {
                        String targetVal = JSONObject.parseObject(preCaseRunResult.getResponseMsg()).getString(s);
                        LOGGER.info("获取需要替换的字段：" + targetKey + "-->" + targetVal);
                        paramObject.put("value", targetVal);
                    }
                }
            }
            testCase.setParameterJson(jsonArray.toJSONString());
            LOGGER.info("参数替换成功");
        } else {
            caseRunResult.setRunStatus(RunStatus.FAIL.getCode());
            //用于前端回显失败原因
            caseRunResult.setResultMsg("前置用例执行失败:" + preCaseRunResult.getResultMsg());
        }
    }

    /**
     * 执行前置sql
     *
     * @param testCase
     * @param caseRunResult
     * @return
     */
    private void executePreSql(TestCase testCase, CaseRunResult caseRunResult) throws BusinessException {
        String actionDetail = testCase.getActionDetail();
        JSONObject preActionObject = JSONObject.parseObject(actionDetail);

        String targetBase = preActionObject.getString("targetBase");
        String targetSql = preActionObject.getString("sql");
        String targetSqlKey = preActionObject.getString("targetSqlKey");
        if (StringUtils.isEmpty(targetSql)) {
            throw new BusinessException("SQL语句格式错误");
        }
        LOGGER.info("等待前置动作,执行sql: {}", targetSql);
        TOutbaseConf outbaseConf = new TOutbaseConf();
        outbaseConf.setBaseTag(targetBase);
        List<TOutbaseConf> list = outbaseConfService.selectTOutbaseConfList(outbaseConf);
        if (list.size() > 1) {
            LOGGER.info("根据数据标签匹配到的数据库不只一个，默认取第一个值");
        }
        outbaseConf = list.get(0);
        try {
            // 查询数据库
            Map<String, String> queryResult = dbConnectUtils.queryOne(targetSql, outbaseConf);

            //执行完sql后，将指定字段的值更新到用例中
            JSONArray jsonArray = JSON.parseArray(testCase.getParameterJson());
            for (Object object : jsonArray) {
                JSONObject paramObject = (JSONObject) object;
                if (targetSqlKey.equals(paramObject.get("id"))) {
                    paramObject.put("value", queryResult.get(targetSqlKey));
                }
            }
            testCase.setParameterJson(jsonArray.toJSONString());
            LOGGER.info("参数替换成功");
        } catch (Exception e) {
            caseRunResult.setRunStatus(RunStatus.FAIL.getCode());
            //用于前端回显失败原因
            caseRunResult.setResultMsg("前置sql执行失败:" + e.getMessage());
        }
    }

    /**
     * 执行前置延时
     *
     * @param testCase
     * @param caseRunResult
     */
    private void executePreDelay(TestCase testCase, CaseRunResult caseRunResult) throws BusinessException {
        String actionDetail = testCase.getActionDetail();
        JSONObject preActionObject = JSONObject.parseObject(actionDetail);

        String targetTime = preActionObject.getString("targetTime");
        if (StringUtils.isEmpty(targetTime)) {
            throw new BusinessException("等待时间格式错误");
        }

        try {
            LOGGER.info("等待前置动作,延时: {}秒", targetTime);
            long st = Long.parseLong(targetTime);
            Thread.sleep(st * 1000);
        } catch (Exception e) {
            //等待任务执行异常时直接返回前台失败结果
            caseRunResult.setRunStatus(RunStatus.FAIL.getCode());
            caseRunResult.setResultMsg("前置等待执行失败：" + e.getMessage());
        }
    }

    /**
     * 用例执行校验
     *
     * @param testCase
     * @param responseMap
     * @param caseRunResult
     */
    private void assertCaseResult(TestCase testCase, Map responseMap, CaseRunResult caseRunResult) {
        try {
            String checkExpression = testCase.getCheckExpression();
            LOGGER.info("进行如下校验: {}", checkExpression);
            if (checkExpression.contains(",")) {
                String[] checks = checkExpression.split(",");
                for (String check : checks) {
                    String[] checking = check.split("=");
                    if (!responseMap.isEmpty() && !responseMap.containsKey("SEND_ERROR")) {
                        Assert.assertEquals(responseMap.get(checking[0]), checking[1]);
                    }
                }
            } else {
                String[] check = checkExpression.split("=");
                if (!responseMap.isEmpty() && !responseMap.containsKey("SEND_ERROR")) {
                    Assert.assertEquals(responseMap.get(check[0]), check[1]);
                }
            }
            LOGGER.info("校验成功");
        } catch (AssertionError error) {
            LOGGER.info("校验失败", error);
            caseRunResult.setRunStatus(RunStatus.FAIL.getCode());
            caseRunResult.setResultMsg(error.getMessage());
        }
    }
}
