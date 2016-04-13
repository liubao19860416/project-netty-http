package com.saic.ebiz.test;

import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class ConcurrentLinkedDequeTest {
    
    public static void main(String[] args) {
        String str="gghhjjkkkjhggfddfyyuiiioooiuugffffffffggggggggggghhhhhhggg\ngggyyyyyyyyhuuuu\n";
        System.out.println(str.length());
    }

    @Test
    public void testName() throws Exception {
        Collection<String> concurrentLinkedDeque = new ConcurrentLinkedDeque<String>();
        concurrentLinkedDeque.add("3");
        concurrentLinkedDeque.add("4");
        concurrentLinkedDeque.add("3");
        concurrentLinkedDeque.add("1");
        System.out.println(JSON.toJSONString(concurrentLinkedDeque));
        
        Deque<Runnable> concurrentLinkedDeque2 = new ConcurrentLinkedDeque<Runnable>();
        BlockingQueue<Runnable> concurrentLinkedDeque3 = new LinkedBlockingQueue<Runnable>();
        
    }

}
