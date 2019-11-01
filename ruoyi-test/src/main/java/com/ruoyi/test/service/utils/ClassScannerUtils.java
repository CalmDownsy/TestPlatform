package com.ruoyi.test.service.utils;

import com.ruoyi.test.service.executor.ScanExecutor;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @Auther: zhangsy
 * @Date: 2019/10/24 15:50
 * @Description:
 */
public class ClassScannerUtils {

    public static Set<Class<?>> searchClasses(String moduleName, String packageName) {
        return searchClasses(moduleName, packageName, null);
    }

    public static Set<Class<?>> searchClasses(String moduleName, String packageName, Predicate predicate) {
        return ScanExecutor.getInstance().search(moduleName, packageName, predicate);
    }
}
