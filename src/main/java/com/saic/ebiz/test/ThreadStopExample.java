package com.saic.ebiz.test;

import java.util.concurrent.TimeUnit;

/**
 * 重排序例子
 * 
 * @author Liubao
 * @version 3.0
 * @Date 2016年4月10日
 * 
 */
public class ThreadStopExample {

    //解决方案：添加变量类型volatile
    //private static volatile boolean stop;
    private static boolean stop;
    
    public static void main(String[] args) throws InterruptedException {
        for(int i=0;i<10;i++){
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    int i=0;
                    /**
                     * 重排序后的样子为：很难被测试捕捉到这种情况
                     * if(!stop){
                     *  while(true){
                     *  }
                     * }
                     */
                    while(!stop){
                        i++;
                        System.out.println("run :"+i);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("run finished");
                }
            });
            //启动线程
            thread.start();
        }
        TimeUnit.SECONDS.sleep(3);
        stop=true;
        System.out.println("main finished");
    }

}
