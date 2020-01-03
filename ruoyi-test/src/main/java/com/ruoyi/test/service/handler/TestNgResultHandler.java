package com.ruoyi.test.service.handler;

import com.ruoyi.test.domain.TestResult;
import com.ruoyi.test.service.ITestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.Set;

/**
 * @Auther: zhangsy
 * @Date: 2019/11/13 19:36
 * @Description:   处理TestNg执行结果
 */
@Component
public class TestNgResultHandler {

    @Autowired
    private ITestCaseService iTestcaseService;


    /**
     * 获取ITestContext中的测试结果，将结果插入到数据库
     *
     * @param testContext
     */
    public void handle(ITestContext testContext) {
        TestResult result = new TestResult();
        //所有测试结果
        String testName = testContext.getName();
        result.setResultId(System.currentTimeMillis());
        result.setTestTitle(testName);
        int allPassedResults = testContext.getPassedTests().getAllResults().size();
        int allFailedResults = testContext.getFailedTests().getAllResults().size();
        int allSkippedResults = testContext.getSkippedTests().getAllResults().size();
        if (allFailedResults > 0) {
            result.setTestResult("1");
        } else {
            result.setTestResult("2");
        }
        result.setCaseCount(allPassedResults + allFailedResults + allSkippedResults);
        result.setSuccessCount(allPassedResults);
        result.setFailCount(allFailedResults);
        result.setSkipCount(allSkippedResults);
        result.setDuration(1000L);
        result.setReportPath("");
    }
}
