package com.ulpay.testplatform.utils.client;

import com.alibaba.fastjson.JSONObject;
import com.ulpay.testplatform.common.contants.CharSet;
import com.ulpay.testplatform.utils.GZipUtils;
import com.ulpay.testplatform.utils.XmlReverseUtils;
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
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送Http请求
 *
 * @author yingjie.liu
 * @create 2018-02-28 14:02
 **/
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
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

            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {


                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }

                @Override
                public void verify(String s, SSLSocket sslSocket) throws IOException {

                }

                @Override
                public void verify(String s, java.security.cert.X509Certificate x509Certificate) throws SSLException {

                }

                @Override
                public void verify(String s, String[] strings, String[] strings1) throws SSLException {

                }
            }
            );
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
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    if(response.getStatusLine().getStatusCode() == 200){
                        resMes = EntityUtils.toString(entity, encoding);
                        logger.debug("返回数据:" + resMes);
                    }else{
                        logger.error("外部服务异常，状态码："+response.getStatusLine());
                        logger.error("外部服务异常，返回信息："+response.toString());
                        resMes = "外部服务异常";
                    }
                }
            } finally {
                response.close();
            }
        }  // 捕获服务器响应超时
        catch (IOException e) {
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

    public Map<String, String> sendHttpRequest(String reqMessage, String url) {
        Map resultMap = new HashMap();
        try {
            // 发送请求获取响应
            String response = sendRequest(reqMessage, url, CharSet.encrypt_charset);
            logger.info("外部服务返回结果：{}",response);
            if(!response.equals("外部服务异常")){
                String res = GZipUtils.ungzipString(response);
                // 响应信息解密
                logger.debug("****************响应信息解密后*********************" + res);
                resultMap = XmlReverseUtils.xml2map(res);
            }else{
                resultMap.put("SEND_ERROR", "外部服务异常");
            }
        } catch (Exception e) {
            resultMap.put("SEND_ERROR", e.getMessage());
            logger.error("send request error :{}", e.getMessage());
        }
        return resultMap;
    }

    public HashMap<String, String> sendPostRequest(String obj, String url) {
        String encoding = "UTF-8";
        logger.debug("发送请求URL==>{}, 字符编码：{}", url, encoding);
        HashMap<String, String> map = new HashMap<>();
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = null;
        if (url.startsWith("https")) {
            // 执行 Https 请求.
            httpclient = (CloseableHttpClient) wrapClient(new DefaultHttpClient());
        } else {
            httpclient = HttpClients.createDefault();
        }
        logger.info("HttpClient方法创建！");
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        String resMes = null;
        StringEntity strEntity = new StringEntity(obj, encoding);
        strEntity.setContentEncoding("UTF-8");
        strEntity.setContentType("application/json");
        try {

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
            httppost.setConfig(requestConfig);
            httppost.setEntity(strEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                logger.info("StatusLine ==> {}", response.getStatusLine());
                HttpEntity entity = response.getEntity();
                int status = response.getStatusLine().getStatusCode();
                if ( status == 200 && entity != null) {
                    resMes = EntityUtils.toString(entity, encoding);
                    /**返回的json对象转换为Map格式**/
                    map = (HashMap) JSONObject.parseObject(resMes, HashMap.class);
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

        return map;
    }

    public static HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
            ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
            return new DefaultHttpClient(mgr, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}