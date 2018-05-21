package spark.java;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2017/4/2.
 */
public class PoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor p = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);
        for(int i = 0 ; i < 50 ; i ++){
            p.submit(new Runnable() {
                public void run() {
                    System.out.println("hello world");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
