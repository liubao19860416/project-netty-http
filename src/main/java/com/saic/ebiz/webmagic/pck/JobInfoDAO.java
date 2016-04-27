package com.saic.ebiz.webmagic.pck;

import org.apache.ibatis.annotations.Insert;

import com.saic.ebiz.webmagic.LieTouJobInfo;

public interface JobInfoDAO {

    @Insert("insert into JobInfo (`title`,`salary`,`company`,`description`,`requirement`,`source`,`url`,`urlMd5`) values (#{title},#{salary},#{company},#{description},#{requirement},#{source},#{url},#{urlMd5})")
    public int add(LieTouJobInfo jobInfo);
}