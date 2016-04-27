package com.saic.ebiz.webmagic.pck;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

import com.saic.ebiz.webmagic.LieTouJobInfo;

@Component("JobInfoDaoPipeline")
public class JobInfoDaoPipeline implements PageModelPipeline<LieTouJobInfo> {

    @Resource
    private JobInfoDAO jobInfoDAO;

    @Override
    public void process(LieTouJobInfo lieTouJobInfo, Task task) {
        //调用MyBatis DAO保存结果
        jobInfoDAO.add(lieTouJobInfo);
    }
}