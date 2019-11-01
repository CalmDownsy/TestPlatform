package com.ruoyi.test.service.impl;

import java.lang.reflect.Method;
import java.util.*;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.annotation.AutoTest;
import com.ruoyi.test.domain.Testcase;
import com.ruoyi.test.service.utils.ClassScannerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.test.mapper.TestcaseMapper;
import com.ruoyi.test.service.ITestcaseService;
import com.ruoyi.common.core.text.Convert;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * 测试用例Service业务层处理
 *
 * @author zhangsy
 * @date 2019-10-24
 */
@Service
public class TestcaseServiceImpl implements ITestcaseService {
    private static final Logger log = LoggerFactory.getLogger(TestcaseServiceImpl.class);

    @Autowired
    private TestcaseMapper testcaseMapper;

    /**
     * 查询测试用例
     *
     * @param testCaseId 测试用例ID
     * @return 测试用例
     */
    @Override
    public Testcase selectTestcaseById(Long testCaseId) {
        return testcaseMapper.selectTestcaseById(testCaseId);
    }

    /**
     * 查询测试用例列表
     *
     * @param testcase 测试用例
     * @return 测试用例
     */
    @Override
    public List<Testcase> selectTestcaseList(Testcase testcase) {
        return testcaseMapper.selectTestcaseList(testcase);
    }

    /**
     * 新增测试用例
     *
     * @param testcase 测试用例
     * @return 结果
     */
    @Override
    public int insertTestcase(Testcase testcase) {
        testcase.setCreateTime(DateUtils.getNowDate());
        return testcaseMapper.insertTestcase(testcase);
    }

    /**
     * 修改测试用例
     *
     * @param testcase 测试用例
     * @return 结果
     */
    @Override
    public int updateTestcase(Testcase testcase) {
        testcase.setUpdateTime(DateUtils.getNowDate());
        return testcaseMapper.updateTestcase(testcase);
    }

    /**
     * 删除测试用例对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestcaseByIds(String ids) {
        return testcaseMapper.deleteTestcaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除测试用例信息
     *
     * @param testCaseId 测试用例ID
     * @return 结果
     */
    public int deleteTestcaseById(Long testCaseId) {
        return testcaseMapper.deleteTestcaseById(testCaseId);
    }

    /**
     * 扫描自动化测试用例
     *
     * @return
     */
    @Override
    public List<Testcase> scanTestcases() {
        ArrayList<Testcase> caseList = new ArrayList<>();
        Set<Class<?>> classes = ClassScannerUtils.searchClasses("autotest", "com.http");
        Iterator<Class<?>> iterator = classes.iterator();
        while (iterator.hasNext()) {
            Class<?> clazz = iterator.next();
            //是否使用了指定注解
            boolean annotationPresent = clazz.isAnnotationPresent(AutoTest.class);
            if (annotationPresent) {
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    Testcase testCase = new Testcase();
                    boolean isTestNgMethod = method.isAnnotationPresent(Test.class);
                    if (isTestNgMethod) {
                        testCase.setParentTitle(clazz.getCanonicalName());
                        testCase.setTestCaseTitle(method.getName());
                        //TestNg均为自动化脚本
                        testCase.setTestCaseType("1");
                        testCase.setDescription(method.getAnnotation(Test.class).description());
                        //根据title去重
                        if (selectTestcaseList(testCase).isEmpty()) {
                            insertTestcase(testCase);
                            caseList.add(testCase);
                        }
                    }
                }
            }
        }
        log.info("本次新增用例个数: {}", caseList.size());
        return caseList;
    }

    @Override
    public void runCase(List<Testcase> testcaseList) {
        TestNG testNG = new TestNG();
        //不适用默认报告
        testNG.setUseDefaultListeners(false);
        //suite
        XmlSuite xmlSuite = new XmlSuite();
        xmlSuite.setName("自动化测试");
        xmlSuite.addListener("com.ruoyi.test.service.utils.ExtentTestNGReporterListener");
        //class
        Map<String, List<XmlInclude>> map = new HashMap<>();
        for (Testcase testCase : testcaseList) {
            XmlInclude xmlInclude = new XmlInclude(testCase.getTestCaseTitle());
            String className = testCase.getParentTitle();
            if (map.containsKey(className)) {
                map.get(className).add(xmlInclude);
            } else {
                List<XmlInclude> methodList = new ArrayList<>();
                methodList.add(xmlInclude);
                map.put(className, methodList);
            }
        }
        for (Map.Entry<String, List<XmlInclude>> entry : map.entrySet()) {
            //test
            XmlTest xmlTest = new XmlTest(xmlSuite);
            xmlTest.setName(entry.getKey());
            //日志级别
            xmlTest.setVerbose(2);
            List<XmlClass> xmlClassList = new ArrayList<>();
            XmlClass xmlClass = new XmlClass(entry.getKey());
            xmlClass.setIncludedMethods(entry.getValue());
            xmlClassList.add(xmlClass);
            xmlTest.setXmlClasses(xmlClassList);
        }
        List<XmlSuite> xmlSuiteList = new ArrayList<>();
        xmlSuiteList.add(xmlSuite);
        testNG.setXmlSuites(xmlSuiteList);
        testNG.run();
    }
}
