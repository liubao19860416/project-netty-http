package com.saic.ebiz.vbox;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/spring-context.xml" })
public class BaseGRRSServerTest {
    
    @Before
    public void setUp() throws Exception {
        System.out.println("junit测试启动setUp...");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("junit测试结束tearDown...");
    }
    
    @Test
    public void testName() throws Exception {
        Assert.assertTrue(true);
    }
}
