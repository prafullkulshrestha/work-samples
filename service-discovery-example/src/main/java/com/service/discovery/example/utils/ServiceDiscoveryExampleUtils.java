package com.service.discovery.example.utils;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;


@Component
public class ServiceDiscoveryExampleUtils {
	
	
	@Autowired
    private DiscoveryClient discoveryClient;
 
    public Optional<URI> serviceUrl(String instanceId) {
        return discoveryClient.getInstances(instanceId)
          .stream()
          .findFirst() 
          .map(si -> {
        	 return si.getUri();
        	  
          });
    }

}
