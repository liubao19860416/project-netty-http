package com.saic.ebiz.webmagic;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.MultiPagePipeline;

//TargetUrl的意思是只有以下格式的URL才会被抽取出生成model对象
//这里对正则做了一点改动，'.'默认是不需要转义的，而'*'则会自动被替换成'.*'，因为这样描述URL看着舒服一点...
//继承jfinal中的Model
//实现AfterExtractor接口可以在填充属性后进行其他操作
@TargetUrl("http://my.oschina.net/flashsword/blog/*")
public class LieTouJobInfo extends Model<LieTouJobInfo> implements AfterExtractor {
 
    //用ExtractBy注解的字段会被自动抽取并填充
    //默认是xpath语法
    @ExtractBy("//title")
    private String title;
 
    //可以定义抽取语法为Css、Regex等
    @ExtractBy(value = "div.BlogContent", type = ExtractBy.Type.Css)
    private String content;
 
    //multi标注的抽取结果可以是一个List
    @ExtractBy(value = "//div[@class='BlogTags']/a/text()", multi = true)
    private List<String> tags;
    
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+)").all());
        GithubRepo githubRepo = new GithubRepo();
        githubRepo.setName(page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        githubRepo.setName(page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        githubRepo.setReadme(page.getHtml().xpath("//div[@id='readme']/tidyText()").toString());
        if (githubRepo.getName() == null) {
            //skip this page
            page.setSkip(true);
        } else {
            page.putField("repo", githubRepo);
        }
    }
 
    @Override
    public void afterProcess(Page page) {
        //jfinal的属性其实是一个Map而不是字段，没关系，填充进去就是了
        this.set("title", title);
        this.set("content", content);
        this.set("tags", StringUtils.join(tags, ","));
        //保存
        save();
    }
 
    public static void main(String[] args) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://127.0.0.1/webmagic?characterEncoding=utf-8", "root", "root");
        c3p0Plugin.start();
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.addMapping("blog", LieTouJobInfo.class);
        activeRecordPlugin.start();
        //启动webmagic
        OOSpider.create(Site
                .me()
                .setCharset("utf-8")
                .setUserAgent("Spider")
                .setTimeOut(3000)
                .setRetryTimes(3)
                .setCycleRetryTimes(3)
                .setDomain("github.com")
                .addCookie("dotcomt_user","code4craft")
                .addHeader("Referer","https://github.com")
                .setHttpProxy(new HttpHost("127.0.0.1",8080))
                .addStartUrl("http://my.oschina.net/flashsword/blog/145796"), LieTouJobInfo.class)
//        .clearPipeline()
//        .pipeline(new MultiPagePipeline())
//        .pipeline(new ConsolePipeline())
        .runAsync();
//        .run();
    }
    
}