package com.interfacetest.commonutils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.interfacetest.commonutils.http.GZipUtil;
import com.interfacetest.commonutils.http.SignUtil;
import com.interfacetest.commonutils.http.XmlReverseUtils;
import com.interfacetest.constants.CharSet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送Http请求
 *
 * @author yingjie.liu
 * @create 2018-02-28 14:02
 **/
public class HttpSendUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpSendUtils.class);
    public static final int CONNECTION_TIMEOUT = 15000;
    public static final int SOCKET_TIMEOUT = 60000;

    private static File getFileAndCreateDir(String fileFullPath) {
        File file = new File(fileFullPath);
        if (file.exists()) {
            logger.error("文件已经存在，文件名{}", fileFullPath);
            return null;
        }
        if (fileFullPath.endsWith(File.separator)) {
            logger.error("文件名错误，{}", fileFullPath);
            return null;
        }
        // 判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录

            if (!file.getParentFile().mkdirs()) {
                logger.error("创建文件目录失败，文件名{}", fileFullPath);
                return null;
            }
        }
        return file;
    }

    private static boolean downloadFile(String url, String filePath) {
        boolean ret = false;
        File file = getFileAndCreateDir(filePath);
        if (file == null) {
            // 创建路径错误或文件已经存在
            logger.error("下载文件时，文件名错误或路径错误，{}", filePath);
            return ret;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpclient.execute(httpGet);

            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == 200) {

                FileOutputStream outputStream = new FileOutputStream(file);
                InputStream inputStream = httpResponse.getEntity().getContent();
                byte b[] = new byte[1024 * 1024];
                int j = 0;
                while ((j = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, j);
                }
                outputStream.flush();
                outputStream.close();
                ret = true;
            }
        } catch (ClientProtocolException e) {
            logger.error("HttpClient ClientProtocolException异常", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("HttpClient IO异常", e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("HttpClient 异常", e);
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("关闭httpclient连接异常", e);
                e.printStackTrace();
            }
        }
        return ret;
    }


    /**
     * @return
     * @throws GeneralSecurityException 设定文件
     * @Title: createSSLInsecureClient
     * @Description: 创建发送https请求
     */
    protected static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
        try {
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault(),
                    NoopHostnameVerifier.INSTANCE);
            final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", sslsf)
                    .build();
            final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(100);
            return HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm).build();
        } catch (GeneralSecurityException e) {
            logger.error("", e);
            throw e;
        }
    }

    /**
     * 外发POST请求
     *
     * @param keyValStr 外发对象
     * @param url       外发地址
     * @param encoding  编码
     * @return 返回字符串
     * @throws Exception
     */
    private String sendRequest(String keyValStr, String url, String encoding) {
        StringEntity httpEntity = new StringEntity(keyValStr, encoding);
        logger.debug("ss=" + httpEntity);
        logger.debug("发送请求URL==>{}, 字符编码：{}", url, encoding);
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = null;
        if (url.startsWith("https")) {
            // 执行 Https 请求.
            try {
                httpclient = createSSLInsecureClient();
            } catch (GeneralSecurityException e) {
                logger.error("createSSLInsecureClient Error ==> ", e);
            }
        } else {
            httpclient = HttpClients.createDefault();
        }
        logger.debug("HttpClient方法创建！");
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        logger.debug("Post方法创建！");
        String resMes = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
            httppost.setConfig(requestConfig);
            httppost.setEntity(httpEntity);
            logger.debug("请求URL:" + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
//                logger.info("StatusLine ==> {}", response.getStatusLine());
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resMes = EntityUtils.toString(entity, encoding);
                    logger.debug("返回数据:" + resMes);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (SocketTimeoutException e) {
            // 捕获服务器响应超时
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("Close HttpClient Error==> ", e);
            }
        }
        return resMes;
    }

    public Map<String, String> sendHttpRequest(Map<String, Object> reqMap, String url) {
        Map resultMap = new HashMap();
        try {
            String xml = XmlReverseUtils.callMap2XML(reqMap);
            logger.debug("### xml :" + xml);
            // 加签
            logger.debug("****************xml加签*********************");
            xml = SignUtil.merSignaWithMercGateWay(xml);
            logger.debug("xml签名结果：" + xml);
            // 加压加密
            logger.debug("****************xml加压加密*********************");
            String base64 = GZipUtil.gzipString(xml);
            logger.debug("xml加压加密:" + base64);
            // 发送请求获取响应
            String response = sendRequest(base64, url, CharSet.encrypt_charset);
            String res = GZipUtil.ungzipString(response);
            // 响应信息解密
            logger.debug("****************响应信息解密后*********************" + res);
            resultMap = XmlReverseUtils.xml2map(res);
        } catch (Exception e) {
            resultMap.put("SEND_ERROR", e.getMessage());
            logger.error("send request error :{}", e.getMessage());
        }
        return resultMap;
    }

    /**
     * @Title: httpPost
     * @Description: TODO(提交参数，入参为实体对象，转换为json字符串)
     * @param: @param obj 实体对象
     * @param: @return
     * @return: HashMap<String,String>
     * @throws IOException
     */
    public HashMap<String,String> sendPostRequest(Object obj,String uri){
        String serverIp = "";
        String url = uri;
        HttpClient httpClient = HttpClientBuilder.create().build();
        String params = JSON.toJSONString(obj);
        HttpPost method = new HttpPost(url);
        HttpResponse result = null;
        HashMap<String,String> map = new HashMap<String, String>();
        String resultJson="";
        try {
            if (null != params) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(params, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            //log.info(url+"post请求提交:"+params);
            result = httpClient.execute(method);
            /** 请求发送成功，并得到响应 **/
            if (result.getStatusLine().getStatusCode() == 200) {
                try {
                    /** 读取服务器返回过来的json字符串数据 **/
                    resultJson = EntityUtils.toString(result.getEntity());
//                    System.out.println(resultJson);
                    /**返回的json对象转换为Map格式**/
                    map = (HashMap) JSONObject.parseObject(resultJson, HashMap.class);
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    logger.info(serverIp+"post请求提交失败:"+params+e.getMessage());
                }
            }else if (result.getStatusLine().getStatusCode()!=200 ) {

            }
        } catch (IOException e) {
            logger.info(serverIp+"post请求提交成失败:"+params+e.getMessage());
        }
        return map;
    }
}