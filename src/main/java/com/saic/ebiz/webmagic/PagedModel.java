package com.saic.ebiz.webmagic;

import java.util.Collection;

public interface PagedModel {

    public String getPageKey();

    public Collection<String> getOtherPages();

    public String getPage();

    public PagedModel combine(PagedModel pagedModel);

}