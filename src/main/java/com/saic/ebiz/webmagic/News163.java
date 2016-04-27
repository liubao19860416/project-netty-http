package com.saic.ebiz.webmagic;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.AfterExtractor;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.pipeline.MultiPagePipeline;
import us.codecraft.webmagic.selector.Selectable;

@TargetUrl("http://news.163.com/\\d+/\\d+/\\d+/\\w+*.html")
public class News163 extends Model<News163> implements PagedModel, AfterExtractor {

    @ExtractByUrl("http://news\\.163\\.com/\\d+/\\d+/\\d+/(\\w+)*\\.html")
    private String pageKey;

    @ExtractByUrl(value = "http://news\\.163\\.com/\\d+/\\d+/\\d+/\\w+_(\\d+)\\.html", notNull = false)
    private String page;

    private List<String> otherPage;

    @ExtractBy("//h1[@id=\"h1title\"]/text()")
    private String title;

    @ExtractBy("//div[@id=\"epContentLeft\"]")
    private String content;
    
    @Override
    public String getPageKey() {
        return pageKey;
    }

    @Override
    public Collection<String> getOtherPages() {
        return otherPage;
    }

    @Override
    public String getPage() {
        if (page == null) {
            return "1";
        }
        return page;
    }

    @Override
    public PagedModel combine(PagedModel pagedModel) {
        News163 news163 = new News163();
        News163 pagedModel1 = (News163) pagedModel;
        news163.content = this.content + pagedModel1.content;
        return news163;
    }

    @Override
    public String toString() {
        return "News163{" +
                "content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", otherPage=" + otherPage +
                '}';
    }

    public static void main(String[] args) {
        C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://127.0.0.1/webmagic?characterEncoding=utf-8", "root", "root");
        c3p0Plugin.start();
        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(c3p0Plugin);
        activeRecordPlugin.addMapping("blog", News163.class);
        activeRecordPlugin.start();
        
        OOSpider.create(Site.me()
                .addStartUrl("http://news.163.com/13/0802/05/958I1E330001124J_2.html"), News163.class)
                .clearPipeline()
                .addPipeline(new MultiPagePipeline())
                .addPipeline(new ConsolePipeline())
                .addPipeline(new JsonFilePipeline("D:\\temp\\webmagic"))
                .thread(5)
                .run();
    }

    @Override
    public void afterProcess(Page page) {
        Selectable xpath = page.getHtml().xpath("//div[@class=\"ep-pages\"]//a/@href");
        otherPage = xpath.regex("http://news\\.163\\.com/\\d+/\\d+/\\d+/\\w+_(\\d+)\\.html").all();
        //jfinal的属性其实是一个Map而不是字段，没关系，填充进去就是了
        this.set("title", title);
        this.set("content", content);
        this.set("tags", StringUtils.join(otherPage, ","));
        //保存
        save();
    }
    
}