package com.laozhao.webflux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

/**
 * Created by viruser on 2018/11/1.
 */
public class TestFlux {
/*
    1.创建一个热数据源
    2.新启一个线程定时发布数据
    3.等待2s后，启动第一个订阅者，这个订阅者可以拿到所有数据，因为之前没有订阅者，发布的数据都是缓存着的。
    4.再启动一个订阅者，这个订阅者只能拿到发布者新发布的数据了。
    5.数据源进行了取消，所以之后发布的数据对会丢弃掉，不会传递给订阅者。

 */
    public  void testFlu() throws InterruptedException {
        UnicastProcessor<String> hotSource = UnicastProcessor.create();

        Flux<String> flux = hotSource.publish()
                .autoConnect()
                .map(String::toUpperCase);//1

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    hotSource.onNext(i + "");
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();//2

        Thread.sleep(2000L);

        flux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: " + d));//3

        flux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: " + d));//4

        Thread.sleep(3000L);
        hotSource.cancel();//5
        hotSource.onNext("cancel_after");

        hotSource.onComplete();

    }


}
