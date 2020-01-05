package com.ulpay.testplatform.service.impl;

import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.utils.StringUtils;
import com.ulpay.testplatform.service.IClassScanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @Auther: zhangsy
 * @Date: 2019/10/24 15:32
 * @Description: 从文件中扫描class
 */
@Service
public class FileScanServiceImpl implements IClassScanService {

    private static final Logger log = LoggerFactory.getLogger(FileScanServiceImpl.class);

    private String defaultClassPath = FileScanServiceImpl.class.getResource("/").getPath();

    public String getDefaultClassPath() {
        return defaultClassPath;
    }

    public void setDefaultClassPath(String defaultClassPath) {
        this.defaultClassPath = defaultClassPath;
    }

    public FileScanServiceImpl(String defaultClassPath) {
        this.defaultClassPath = defaultClassPath;
    }

    public FileScanServiceImpl() {
    }

    private static class ClassSearcher {
        private Set<Class<?>> classPaths = new HashSet<>();

        private Set<Class<?>> doPath(File file, String packageName, Predicate<Class<?>> predicate, boolean flag) {

            if (file.isDirectory()) {
                //文件夹我们就递归
                File[] files = file.listFiles();
                if (!flag) {
                    packageName = packageName + "." + file.getName();
                }

                for (File f1 : files) {
                    doPath(f1, packageName, predicate, false);
                }
            } else {//标准文件
                //标准文件我们就判断是否是class文件
                if (file.getName().endsWith(CLASS_SUFFIX)) {
                    //如果是class文件我们就放入我们的集合中。
                    try {
                        Class<?> clazz = Class.forName(packageName + "." + file.getName().substring(0, file.getName().lastIndexOf(".")));
                        if (predicate == null || predicate.test(clazz)) {
                            classPaths.add(clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new BaseException(e.getMessage());
                    }
                }
            }
            return classPaths;
        }
    }

    /**
     * @param moduleName  模块名
     * @param packageName 包路径
     * @param predicate   指定扫描类
     * @return 扫描结果
     */
    @Override
    public Set<Class<?>> search(String moduleName, String packageName, Predicate<Class<?>> predicate) {
        //先把包名转换为路径,首先得到项目的classpath
        String classpath = StringUtils.isEmpty(moduleName) ? defaultClassPath : replaceModuleName(moduleName);
        //然后把我们的包名basPack转换为路径名
//        String basePackPath = packageName.replace(".", File.separator);
        String basePackPath = packageName.replace(".", "/");
        String searchPath = classpath + basePackPath;
        log.info("当前扫描的文件路径为: {}", searchPath);
        return new ClassSearcher().doPath(new File(searchPath), packageName, predicate, true);
    }

    /**
     * 替换模块名，以便找到指定路径
     *
     * @param moduleName 模块名
     * @return 替换后的文件路径
     */
    private String replaceModuleName(String moduleName) {
        return defaultClassPath.replaceAll("TestPlatform/.+?/target/test-classes",
                "TestPlatform/" + moduleName + "/target/classes");
    }
}
