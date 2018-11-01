package com.laozhao.webflux.config;

import com.laozhao.webflux.config.event.publisher.InMemoryEventStore;
import com.laozhao.webflux.config.event.publisher.InstanceEventPublisher;
import com.laozhao.webflux.config.event.register.ServiceRegister;
import com.laozhao.webflux.config.event.trrigger.HostTrriger;
import com.laozhao.webflux.config.event.trrigger.InfoTrriger;
import com.laozhao.webflux.config.event.trrigger.UriTrriger;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by viruser on 2018/11/1.
 */
@Configuration
public class FluxConfig {
    @Bean
    public InMemoryEventStore inMemoryEventStore(){
        return new InMemoryEventStore();
    }

    @Bean
    public ServiceRegister serviceRegister(DiscoveryClient discoveryClient, InstanceEventPublisher instanceEventPublisher){
        return  new ServiceRegister(discoveryClient,instanceEventPublisher);
    }

    @Bean(initMethod = "handleEvent")
    public HostTrriger hostTrriger(InstanceEventPublisher instanceEventPublisher){
        return  new HostTrriger(instanceEventPublisher);
    }
    @Bean(initMethod = "handleEvent")
    public InfoTrriger infoTrriger(InstanceEventPublisher instanceEventPublisher){
        return  new InfoTrriger(instanceEventPublisher);
    }

    @Bean(initMethod = "handleEvent")
    public UriTrriger uriTrriger(InstanceEventPublisher instanceEventPublisher){
        return  new UriTrriger(instanceEventPublisher);
    }
}
