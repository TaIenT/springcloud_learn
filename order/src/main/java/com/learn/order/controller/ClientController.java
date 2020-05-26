package com.learn.order.controller;


import com.learn.order.client.ProductClient;
import com.learn.order.dataobject.ProductInfo;
import com.learn.order.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

//    @Autowired
//    private LoadBalancerClient loadBalancerClient;
//
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient ;

    @GetMapping("/getProductMsg")
    public String getProductMsg(){
        //第一种方式(直接使用restTemplate,URL写死)
        //RestTemplate restTemplate = new RestTemplate();
        //String response = restTemplate.getForObject("http://localhost:8080/msg",String.class);

        //第二种方式(利用loadBalancerClient通过应用名获取url,获取url的ip地址与端口,然后在使用restTemplate)
//        RestTemplate restTemplate = new RestTemplate();
//        ServiceInstance serviceInstance = loadBalancerClient.choose("product");
//        String url =  String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort())+"/msg";
//        String response = restTemplate.getForObject(url,String.class);

        //第三种方式(利用@LoadBalanced,可在restTemplate里使用应用名字)
       // String response = restTemplate.getForObject("http://product/msg",String.class);

        String response = productClient.productMsg();

        log.info("response={}",response);
        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList(){
        List<ProductInfo> productInfoList = productClient.listForObject(Arrays.asList("157875196366162707"));
        log.info("response={}",productInfoList);
        return "OK";
    }

    @GetMapping("/productDecreaseStock")
    public String productDecreaseStock(){
        productClient.decreaseStock(Arrays.asList(new CartDTO("157875196366162707",3)));
        return "OK";
    }

}
