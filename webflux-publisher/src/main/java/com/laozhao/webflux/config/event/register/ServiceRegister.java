package com.laozhao.webflux.config.event.register;

import com.laozhao.webflux.config.event.InstanceEvent;
import com.laozhao.webflux.config.event.publisher.InMemoryEventStore;
import com.laozhao.webflux.config.event.publisher.InstanceEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.client.discovery.event.ParentHeartbeatEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.ws.soap.Addressing;
import java.util.stream.Collectors;

/**
 * Created by viruser on 2018/11/1.
 */

public class ServiceRegister {
    private DiscoveryClient  discoveryClient;
    private InstanceEventPublisher  instanceEventPublisher;
    public ServiceRegister(DiscoveryClient  discoveryClient,InstanceEventPublisher instanceEventPublisher){
        this.discoveryClient=discoveryClient;
        this.instanceEventPublisher=instanceEventPublisher;
    }


    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        System.out.println("onApplicationReady");
        discover();
    }

    @EventListener
    public void onInstanceRegistered(InstanceRegisteredEvent<?> event) {
        System.out.println("onInstanceRegistered");
        discover();
    }

    @EventListener
    public void onParentHeartbeat(ParentHeartbeatEvent event) {
        System.out.println("onParentHeartbeat");
        discover();
    }

    @EventListener
    public void onApplicationEvent(HeartbeatEvent event) {
        System.out.println("----------HeartbeatEvent----------");
        discover();
    }
    public void discover(){
        Flux.fromIterable(discoveryClient.getServices())
                .flatMapIterable(discoveryClient::getInstances)
                .collect(Collectors.toList())
                .flatMap(instanceEventPublisher::appendEvents)
        .subscribe();
    }



}
