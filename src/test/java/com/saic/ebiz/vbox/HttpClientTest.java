package com.saic.ebiz.vbox;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("deprecation")
public class HttpClientTest {

    
    @Test
    @SuppressWarnings({ "resource" })
    public void testName1() throws Exception {
        String url="http://www.baidu.com";
        //String url="http://jh-dev.j1health.net/jh-statistics/record/list_uuid/2?uuid=uuid";
        //String url="http://180.153.254.195:8082/jh-statistics/record/list_uuid/2?uuid=uuid";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        //httpGet.setHeader("ContentType", "application/json;charset=utf-8");
        HttpResponse httpResponse = httpClient.execute(httpGet);
        //System.out.println(EntityUtils.toString(httpResponse.getEntity().getContent())); 
        System.out.println(JSON.toJSONString(httpResponse.getEntity().getContent())); 
    }
    
    @Test
    public void testName2() throws Exception {
        String text="";
        Html html=new Html(text);
        List<String> links = html.links()
                .regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").all();
        System.out.println(JSON.toJSONString(links)); 
    }
    
}
