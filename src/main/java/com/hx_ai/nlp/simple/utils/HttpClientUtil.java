package com.hx_ai.nlp.simple.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Created by dzkan on 2016/7/25.
 * HttpClient重新包装，用于方便抓取
 */
public class HttpClientUtil {

    private CloseableHttpClient httpClient;

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36";

    // 初始化，允许重定向
    public void init() {
        httpClient = HttpClients.custom()
                .setUserAgent(USER_AGENT)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setSocketTimeout(5000)
                        .setConnectTimeout(5000)
                        .build())
                .build();
    }

    // 初始化，不允许重定向
    public void initNotRedirect() {
        httpClient = HttpClients.custom()
                .setUserAgent(USER_AGENT)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setSocketTimeout(5000)
                        .setConnectTimeout(5000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
    }

    // 销毁
    public void destroy() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("----- 销毁失败 -----");
        }
    }

    // 抓取网页源码，需要先初始化
    // 可维持抓取，不用多次初始化
    public String grabHtml(String url) {
        String responseHtml = "";
        try {
            System.out.println("----- 请求网址：" + url + " -----");
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();
            responseHtml = EntityUtils.toString(entity, "UTF-8");

            EntityUtils.consume(entity);
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----- 获取源码失败 -----");
        }
        return responseHtml;
    }

    // 获取重定向链接，需要先初始化
    // 官方存在循环重定向的BUG，暂不使用
    @Deprecated
    public String grabRedirectUrl2(String url) {
        String redirectUrl = "";
        try {
            HttpClientContext context = HttpClientContext.create();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet, context);
            List<URI> redirectLocations = context.getRedirectLocations();

            response.close();
            redirectUrl = redirectLocations.get(0).toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----- 获取重定向链接失败 -----");
        }
        return redirectUrl;
    }

    // 获取重定向链接，需要先初始化，禁止重定向
    // 截取301跳转的HEADERS的location信息
    public String grabRedirectUrl(String url) {
        String redirectUrl = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            redirectUrl = response.getHeaders("location")[0].getValue();
            response.close();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("----- 获取重定向链接失败 -----");
        }
        return redirectUrl;
    }

    // 抓取页面内容，一步到位
    // 静态方法，直接调用
    public static String grabHtmlOnce(String url) {
        String responseHtml = "";
        try {
            CloseableHttpClient client = HttpClients.custom()
                    .setUserAgent(USER_AGENT)
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setSocketTimeout(5000)
                            .setConnectTimeout(5000)
                            .build())
                    .build();
            // System.out.println("----- 请求网址：" + url + " -----");
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = client.execute(httpGet);

            HttpEntity entity = response.getEntity();
            responseHtml = EntityUtils.toString(entity, "UTF-8");

            EntityUtils.consume(entity);
            response.close();
            client.close();
        } catch (Exception e) {
            System.out.println("----- 获取源代码失败 -----");
        }
        return responseHtml;
    }

    public static String grabHtmlOncePost(String url) {
        String responseHtml = "";
        try {
            CloseableHttpClient client = HttpClients.custom()
                    .setUserAgent(USER_AGENT)
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setSocketTimeout(5000)
                            .setConnectTimeout(5000)
                            .build())
                    .build();
            // System.out.println("----- 请求网址：" + url + " -----");
            HttpPost httpPost = new HttpPost(url);
            CloseableHttpResponse response = client.execute(httpPost);

            HttpEntity entity = response.getEntity();
            responseHtml = EntityUtils.toString(entity, "UTF-8");

            EntityUtils.consume(entity);
            response.close();
            client.close();
        } catch (Exception e) {
            System.out.println("----- 获取源代码失败 -----");
        }
        return responseHtml;
    }

    @Test
    public void testGrabHtml() {
        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=2&tn=baiduhome_pg&wd=httpclien%20%E8%8E%B7%E5%8F%96%20cookie&rsv_spt=1&oq=%E7%88%AC%E8%99%ABcookie&rsv_pq=bbebdb1c000069d6&rsv_t=f2a3KOoj3qTBTHDaJd%2BtuWbJx5AUucWC7PsoLvKTa9bkO2XHssQ%2F31%2F%2F3OL1OiMeCkLY&rqlang=cn&rsv_enter=1&inputT=164120&sug=%E7%88%AC%E8%99%ABcookie&rsv_sug3=60&rsv_sug1=48&rsv_sug7=100&rsv_sug2=0&rsv_sug4=164120";
        HttpClientUtil clientUtil2 = new HttpClientUtil();
        clientUtil2.init();
        for(int i = 0; i < 10; i++) {
            String html = clientUtil2.grabHtml(url);
            // System.out.println(html);
            System.out.println("--- 第" + (i+1) + "遍 ---");
            System.out.println();
        }

        clientUtil2.destroy();
    }
}
