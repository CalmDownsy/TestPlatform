package com.ruoyi.test.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.test.annotation.AutoTestAnnotation;
import com.ruoyi.test.domain.Testcase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import com.ruoyi.test.mapper.TestcaseMapper;
import com.ruoyi.test.service.ITestcaseService;
import com.ruoyi.common.core.text.Convert;
import org.testng.annotations.Test;

/**
 * 测试用例Service业务层处理
 * 
 * @author zhangsy
 * @date 2019-10-24
 */
@Service
public class TestcaseServiceImpl implements ITestcaseService 
{
    @Autowired
    private TestcaseMapper testcaseMapper;

    /**
     * 查询测试用例
     * 
     * @param testCaseId 测试用例ID
     * @return 测试用例
     */
    @Override
    public Testcase selectTestcaseById(Long testCaseId)
    {
        return testcaseMapper.selectTestcaseById(testCaseId);
    }

    /**
     * 查询测试用例列表
     * 
     * @param testcase 测试用例
     * @return 测试用例
     */
    @Override
    public List<Testcase> selectTestcaseList(Testcase testcase)
    {
        return testcaseMapper.selectTestcaseList(testcase);
    }

    /**
     * 新增测试用例
     * 
     * @param testcase 测试用例
     * @return 结果
     */
    @Override
    public int insertTestcase(Testcase testcase)
    {
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
    public int updateTestcase(Testcase testcase)
    {
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
    public int deleteTestcaseByIds(String ids)
    {
        return testcaseMapper.deleteTestcaseByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除测试用例信息
     * 
     * @param testCaseId 测试用例ID
     * @return 结果
     */
    public int deleteTestcaseById(Long testCaseId)
    {
        return testcaseMapper.deleteTestcaseById(testCaseId);
    }

    /**
     * 扫描自动化测试用例
     * @return
     */
    @Override
    public List<Testcase> scanTestcases() {
        ArrayList<Testcase> caseList = new ArrayList<>();
        // 不使用默认的过滤器
        ClassPathScanningCandidateComponentProvider classPathScanningCandidateComponentProvider =
                new ClassPathScanningCandidateComponentProvider(false);
        //扫描TestNG@Test注解
        classPathScanningCandidateComponentProvider.addIncludeFilter(new AnnotationTypeFilter(AutoTestAnnotation.class));
        // TODO: 2019/10/15  只能扫描到main下面的类，test下面扫描不到，暂时解决不了
        Set<BeanDefinition> beanDefinitions = classPathScanningCandidateComponentProvider.findCandidateComponents("demo.test");
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String classPath = beanDefinition.getBeanClassName();
            System.out.println(classPath);
            try {
                Class<?> classBean = Class.forName(classPath);
                //是否使用了指定注解
                boolean annotationPresent = classBean.isAnnotationPresent(AutoTestAnnotation.class);
                if (annotationPresent) {
                    AutoTestAnnotation annotation = classBean.getAnnotation(AutoTestAnnotation.class);
                    System.out.println(annotation.description());
                    Method[] methods = classBean.getMethods();
                    for (Method method : methods) {
                        Testcase testCase = new Testcase();
                        boolean isTestNgMethod = method.isAnnotationPresent(Test.class);
                        if (isTestNgMethod) {
                            System.out.println(method.getName());
                            testCase.setParentTitle(classPath);
                            testCase.setTestCaseTitle(method.getName());
                            //testng均为自动化脚本
                            testCase.setTestCaseType("1");
                            caseList.add(testCase);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return caseList;
    }
}
