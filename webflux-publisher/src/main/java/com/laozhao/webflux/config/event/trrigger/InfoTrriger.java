package com.laozhao.webflux.config.event.trrigger;

import com.laozhao.webflux.config.event.publisher.InstanceEventPublisher;
import org.springframework.cloud.client.ServiceInstance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.retry.Retry;


/**
 * Created by viruser on 2018/11/1.
 */
public class InfoTrriger  {
    private InstanceEventPublisher instanceEventPublisher;
    public  InfoTrriger(InstanceEventPublisher instanceEventPublisher){
        this.instanceEventPublisher=instanceEventPublisher;
    }

    public void handleEvent(){
        System.out.println("InfoTrriger  handleEvent");
        Flux.from(instanceEventPublisher)
                .doOnSubscribe(subscription -> System.out.println("Subscribed to InfoTrriger events"))
                .compose(this::showInfo)
                .retryWhen(Retry.any()
                        .retryMax(Integer.MAX_VALUE)
                        .doOnRetry(
                                ctx -> System.out.println("Resubscribing after uncaught error")))
                .subscribe();
    }
    public Mono showInfo(Flux<ServiceInstance> item){
        return item.subscribeOn(Schedulers.newSingle("status-updater"))
             .flatMap(this::realShowInfo).then() ;
       // return Mono.empty();
    }
    public Mono realShowInfo(ServiceInstance item){
        System.out.println("InfoTrriger-->"+item.getMetadata()+" hashcode:"+item.hashCode());
        return Mono.empty();
    }
}
