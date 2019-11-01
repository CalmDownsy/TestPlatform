package com.ruoyi.test;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.test.domain.Testcase;
import com.ruoyi.test.service.utils.ClassScannerUtils;
import com.ruoyi.test.service.ITestcaseService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
public class Test {

    @Autowired
    private ITestcaseService iTestcaseService;

    @org.junit.Test
    public void test1() {
        iTestcaseService.scanTestcases();
    }

    @org.testng.annotations.Test
    public void test2() {
        Set<Class<?>> autotest = ClassScannerUtils.searchClasses("autotest", "com.http");
        Iterator<Class<?>> iterator = autotest.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            System.out.println(next.getCanonicalName());
        }

    }

    @org.junit.Test
    public void runcase() {
        Testcase b = iTestcaseService.selectTestcaseById(3L);
        Testcase c = iTestcaseService.selectTestcaseById(9L);
        List<Testcase> a = new ArrayList<>();
        a.add(b);
        a.add(c);
        iTestcaseService.runCase(a);
    }
}
