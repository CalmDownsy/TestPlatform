package com.ulpay.testplatform.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author zhangsy
 * @date 2020/6/11 15:14
 * @description 扫描jar文件，反射类
 */
public class ScanJarUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScanJarUtils.class);

    public static Map<String, Class<?>> loadFacadeClass(String jarFile, String facadeName, String methodName) throws IOException, ClassNotFoundException {
        Map<String, Class<?>> clazzMap = new HashMap<String, Class<?>>(16);

        //通过将给定路径名字符串转换为抽象路径名来创建一个新File实例
        File f = new File(jarFile);
        URL url1 = f.toURI().toURL();
        URLClassLoader myClassLoader = new URLClassLoader(new URL[]{url1}, Thread.currentThread().getContextClassLoader());

        //通过jarFile和JarEntry得到所有的类
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> enumFiles = jar.entries();
        //测试此枚举是否包含更多的元素
        while (enumFiles.hasMoreElements()) {
            JarEntry entry = enumFiles.nextElement();
            if (!entry.getName().contains("META-INF") && entry.getName().indexOf(facadeName) > 0) {
                String classFullName = entry.getName();
                if (classFullName.endsWith(".class")) {
                    //去掉后缀.class
                    String className = classFullName.substring(0, classFullName.length() - 6).replace("/", ".");
                    logger.debug("className: {}", className);
                    // 不能用系统类加载器
//                        Class<?> myclass = Class.forName(className);
                    Class<?> myclass = myClassLoader.loadClass(className);
                    clazzMap.put(myclass.getSimpleName(), myclass);
                    //得到类中包含的属性
                    Method[] methods = myclass.getMethods();
                    for (Method method : methods) {
                        String mName = method.getName();
                        if (mName.equals(methodName)) {
                            logger.debug("methodName: {}", mName);
                            // 返回值
                            Class<?> returnType = method.getReturnType();
                            logger.debug("returnDtoName: {}", returnType.getSimpleName());
                            clazzMap.put(methodName + "-resp", returnType);
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            for (Class<?> clas : parameterTypes) {
                                logger.info("reqDtoName: {}", clas.getSimpleName());
                                clazzMap.put(methodName + "-req", clas);
                            }
                        }
                    }
                }
            }
        }

        return clazzMap;
    }
}

