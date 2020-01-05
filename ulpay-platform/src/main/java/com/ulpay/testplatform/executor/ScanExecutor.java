package com.ulpay.testplatform.executor;


import com.ulpay.testplatform.service.IClassScanService;
import com.ulpay.testplatform.service.impl.FileScanServiceImpl;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @Auther: zhangsy
 * @Date: 2019/10/24 15:42
 * @Description: 委派模式
 */
public class ScanExecutor implements IClassScanService {

    private volatile static ScanExecutor instance;

    @Override
    public Set<Class<?>> search(String moduleName, String packageName, Predicate<Class<?>> predicate) {
        IClassScanService fileSc = new FileScanServiceImpl();
        Set<Class<?>> fileSearch = fileSc.search(moduleName, packageName, predicate);
//        IClassScanService jarScanner = new JarScanServiceImpl();
//        Set<Class<?>> jarSearch = jarScanner.search(packageName, predicate);
//        fileSearch.addAll(jarSearch);
        return fileSearch;
    }

    private ScanExecutor() {
    }

    public static ScanExecutor getInstance() {
        if (instance == null) {
            synchronized (ScanExecutor.class) {
                if (instance == null) {
                    instance = new ScanExecutor();
                }
            }
        }
        return instance;
    }
}
