package com.ruoyi.test.service.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.*;

/**
 * @Auther: zhangsy
 * @Date: 2019/1/17 09:43
 * @Description:
 */
//Testng有自己的类创建工厂，不依赖Spring容器管理
public class ExtentTestNGReporterListener implements IReporter {

    //    报告路径文件名
    private static final String OUTPUT_FOLDER = "test-output/";
    private static final String FILE_NAME = "index.html";
    private ExtentReports extentReports;

    //    重写testng生成报告的方法，并在textng.xml文件listener标签下增加监听，使用修改后的报告
    public void generateReport(List<XmlSuite> xmlSuiteList, List<ISuite> iSuiteList, String outputDirectory) {
        init();
        boolean createSuiteNode = iSuiteList.size() > 1;
        for (ISuite iSuite : iSuiteList) {
            Map<String, ISuiteResult> iSuiteResults = iSuite.getResults();
            if (iSuiteResults.size() == 0) {
                continue;
            }
//            统计suite下 pass . fail . skip
            int suiteFailSize = 0;
            int suitePassSize = 0;
            int suiteSkipSize = 0;
            ExtentTest suiteTest = null;
            if (createSuiteNode) {
                suiteTest = extentReports.createTest(iSuite.getName()).assignCategory(iSuite.getName());
            }
            boolean createSuiteResultNode = iSuiteResults.size() > 1;
            for (ISuiteResult iSuiteResult : iSuiteResults.values()) {
                ExtentTest resultNode;
                ITestContext iTestContext = iSuiteResult.getTestContext();
                if (createSuiteResultNode) {
                    if (null == suiteTest) {
                        resultNode = extentReports.createTest(iTestContext.getName());
                    } else {
                        resultNode = suiteTest.createNode(iTestContext.getName());
                    }
                } else {
                    resultNode = suiteTest;
                }
                if (null != resultNode) {
                    resultNode.getModel().setName(iSuite.getName() + ": " + iTestContext.getName());
                    if (resultNode.getModel().hasCategory()) {
                        resultNode.assignCategory(iTestContext.getName());
                    } else {
                        resultNode.assignCategory(iSuite.getName(), iTestContext.getName());
                    }
                    resultNode.getModel().setStartTime(iTestContext.getStartDate());
                    resultNode.getModel().setEndTime(iTestContext.getEndDate());
//                    统计result下的数据
                    int passSize = iTestContext.getPassedTests().size();
                    int failSize = iTestContext.getFailedTests().size();
                    int skipSize = iTestContext.getSkippedTests().size();
                    suitePassSize += passSize;
                    suiteFailSize += failSize;
                    suiteSkipSize += skipSize;
                    if (failSize > 0) {
                        resultNode.getModel().setStatus(Status.FAIL);
                    }
                    resultNode.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;", passSize, failSize, skipSize));
                }
                buildNotes(resultNode, iTestContext.getPassedTests(), Status.PASS);
                buildNotes(resultNode, iTestContext.getFailedTests(), Status.FAIL);
                buildNotes(resultNode, iTestContext.getSkippedTests(), Status.SKIP);
            }
            if (null != suiteTest) {
                suiteTest.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;", suitePassSize, suiteFailSize, suiteSkipSize));
                if (suiteFailSize > 0) {
                    suiteTest.getModel().setStatus(Status.FAIL);
                }
            }
        }
        extentReports.flush();
    }

    //    初始化
    private void init() {
//        文件夹是否存在
        File outputFile = new File(OUTPUT_FOLDER);
        if (!outputFile.exists() && !outputFile.isDirectory()) {
            outputFile.mkdir();
        }
//        创建报告
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
//        设置标题、样式等
        htmlReporter.config().setDocumentTitle("AutoTestReport-API");
        htmlReporter.config().setReportName("AutoTestReport-API");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
        htmlReporter.config().setCSS(".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}");

        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentReports.setReportUsesManualConfiguration(true);
    }

    //    创建报告节点
    private void buildNotes(ExtentTest extentTest, IResultMap resultMap, Status status) {
        String[] categories = new String[0];
        if (extentTest != null) {
//            获取父节点所有标签
            List<TestAttribute> categoryList = extentTest.getModel().getCategoryContext().getAll();
            categories = new String[categoryList.size()];
            for (int index = 0; index < categoryList.size(); index++) {
//                获取节点的name 和 description
                categories[index] = categoryList.get(index).getName();
            }
        }
//        作为子节点使用
        ExtentTest test;
        if (resultMap.size() > 0) {
//            treeSet 目的应该就是为了按时间排序
            Set<ITestResult> treeSet = new TreeSet<ITestResult>(new Comparator<ITestResult>() {
                public int compare(ITestResult o1, ITestResult o2) {
                    return o1.getStartMillis() < o2.getStartMillis() ? -1 : 1;
                }
            });
//            否则直接取出来就行了
            treeSet.addAll(resultMap.getAllResults());
//            遍历获取testResult
            for (ITestResult testResult : treeSet) {
                String name = "";
                Object[] parameters = testResult.getParameters();
//               for (Object param : parameters) {
//                   name += param.toString();
//               }
//                如果有参数只取第一个参数作为test-name
                if (parameters.length > 0) {
                    name = parameters[0].toString();
                }
                if (name.length() > 0) {
                    if (name.length() > 50) {
                        name = name.substring(0, 49) + "...";
                    }
                } else {
//                    否则用方法名+描述
                    name = testResult.getMethod().getMethodName() + "---" + testResult.getMethod().getDescription();
                }
                if (extentTest == null) {
//                    作为父节点
                    test = extentReports.createTest(name);
                } else {
//                    作为子节点, 标签与父节点一致
                    test = extentTest.createNode(name).assignCategory(categories);
                }
                for (String group : testResult.getMethod().getGroups()) {
                    test.assignCategory(group);
                }
//                输出日志
                List<String> output = Reporter.getOutput(testResult);
                for (String out : output) {
                    test.debug(out);
                }
                if (testResult.getThrowable() != null) {
                    test.log(status, testResult.getThrowable());
                } else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }
                test.getModel().setStartTime(getTime(testResult.getStartMillis()));
                test.getModel().setEndTime(getTime(testResult.getEndMillis()));
            }
        }
    }

    private Date getTime(Long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
