package com.ulpay.testplatform.utils.client;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.client.HessianProxyFactory;
import com.ruoyi.common.config.Global;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ulpay.testplatform.domain.TestFacadeJar;
import com.ulpay.testplatform.service.ITestFacadeJarService;
import com.ulpay.testplatform.utils.ScanJarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author zhangsy
 * @date 2020/6/10 17:46
 * @description
 */
@Component
public class DubboClientUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DubboClientUtils.class);
    private static final int READ_TIME_OUT = 30 * 1000;
    public static DubboClientUtils dubboClientUtils;

    @Autowired
    private ITestFacadeJarService iTestFacadeJarService;

    @PostConstruct
    public void init() {
        dubboClientUtils = this;
        dubboClientUtils.iTestFacadeJarService = this.iTestFacadeJarService;
    }

    // 扫描指定路径下jar包
    public Map sendHessianRequest(String message, String reqUrl, String appName)
            throws BusinessException, IOException, ClassNotFoundException {
        // 拆解url 分别取出facede、methodName、req和resp
        String[] urlArr = reqUrl.split("/");
        // 方法名
        String methodName = urlArr[urlArr.length - 1];
        // facadeName
        String facadeName = urlArr[urlArr.length - 2];
        facadeName = facadeName.substring(facadeName.lastIndexOf(".") + 1);
        // 扫描jar包获取响应类
        TestFacadeJar testFacadeJar = new TestFacadeJar();
        testFacadeJar.setAppName(appName);
        List<TestFacadeJar> testFacadeJars = dubboClientUtils.iTestFacadeJarService.selectTestFacadeJarList(testFacadeJar);
        if (testFacadeJars.size() == 0) {
            throw new BusinessException(appName + "没有上传对应facade包");
        }
        String jarFilePath = FileUploadUtils.getFileFullPath(Global.getProfile(), testFacadeJars.get(0).getJarFilepath());
        Map<String, Class<?>> classMap = ScanJarUtils.loadFacadeClass(jarFilePath, facadeName, methodName);
        Class<?> facedeClass = classMap.get(facadeName);
        Class<?> reqClass = classMap.get(methodName + "-req");
        JSONObject jsonObject = JSONObject.parseObject(message);
        Object request = JSONObject.toJavaObject(jsonObject, reqClass);
        Method declaredMethod = null;
        try {
            declaredMethod = facedeClass.getDeclaredMethod(methodName, reqClass);
        } catch (NoSuchMethodException e) {
            LOGGER.error("{} 不存在", methodName);
        }
        // 需要指定同一个类加载器，否则动态加载失败
        HessianProxyFactory factory = new HessianProxyFactory(facedeClass.getClassLoader());
        factory.setReadTimeout(READ_TIME_OUT);
        factory.setChunkedPost(false);
        Object facade = factory.create(facedeClass, reqUrl.replace(facadeName, facedeClass.getName()).replace("/" + methodName, ""));
        // 利用反射执行
        Object resp = null;
        try {
            resp = declaredMethod.invoke(facade, request);
        } catch (Exception e) {
            LOGGER.error("反射调用 {} 方法失败", methodName, e);
        }
        return BeanUtils.bean2Map(resp);
    }
}
