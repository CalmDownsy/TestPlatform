package com.ulpay.testplatform.service.impl;

import com.ruoyi.common.exception.base.BaseException;
import com.ulpay.testplatform.service.IClassScanService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Auther: zhangsy
 * @Date: 2019/10/24 15:38
 * @Description: 从jar文件中扫描class
 */
@Service
public class JarScanServiceImpl implements IClassScanService {

    @Override
    public Set<Class<?>> search(String moduleName, String packageName, Predicate<Class<?>> predicate) {

        Set<Class<?>> classes = new HashSet<>();

        try {
            //通过当前线程得到类加载器从而得到URL的枚举
            Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".", "/"));
            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();//得到的结果大概是：jar:file:/C:/Users/ibm/.m2/repository/junit/junit/4.12/junit-4.12.jar!/org/junit
                String protocol = url.getProtocol();//大概是jar
                if ("jar".equalsIgnoreCase(protocol)) {
                    //转换为JarURLConnection
                    JarURLConnection connection = (JarURLConnection) url.openConnection();
                    if (connection != null) {
                        JarFile jarFile = connection.getJarFile();
                        if (jarFile != null) {
                            //得到该jar文件下面的类实体
                            Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                            while (jarEntryEnumeration.hasMoreElements()) {
                            /*entry的结果大概是这样：
                                    org/
                                    org/junit/
                                    org/junit/rules/
                                    org/junit/runners/*/
                                JarEntry entry = jarEntryEnumeration.nextElement();
                                String jarEntryName = entry.getName();
                                //这里我们需要过滤不是class文件和不在basePack包名下的类
                                if (jarEntryName.contains(".class") && jarEntryName.replaceAll("/", ".").startsWith(packageName)) {
                                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
                                    Class cls = Class.forName(className);
                                    if (predicate == null || predicate.test(cls)) {
                                        classes.add(cls);
                                    }
                                }
                            }
                        }
                    }
                } else if ("file".equalsIgnoreCase(protocol)) {
                    //从maven子项目中扫描
                    FileScanServiceImpl fileScanner = new FileScanServiceImpl();
                    fileScanner.setDefaultClassPath(url.getPath().replace(packageName.replace(".", "/"), ""));
                    classes.addAll(fileScanner.search(moduleName, packageName, predicate));
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new BaseException(e.getMessage());
        }
        return classes;
    }
}
