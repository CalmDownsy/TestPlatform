package com.ruoyi.test.service;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @Auther: zhangsy
 * @Date: 2019/10/24 15:31
 * @Description:
 */
public interface IClassScanService {

    String CLASS_SUFFIX = ".class";

    Set<Class<?>> search(String moduleName, String packageName, Predicate<Class<?>> predicate);

    default Set<Class<?>> search(String packageName) {
        return search(null, packageName, null);
    }
}
