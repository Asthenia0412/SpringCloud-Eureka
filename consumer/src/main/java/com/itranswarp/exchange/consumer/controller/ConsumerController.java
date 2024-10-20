package com.itranswarp.exchange.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itranswarp.exchange.consumer.service.ConsumerService;

/**
 * @Description：消费端控制器
 * @Author :xiaoyc
 */
@RequestMapping("consumer")
@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;


    /**方式一：RestTemplate调用
     * @return
     */
    @RequestMapping("getName")
    public String getName(){
        return consumerService.getName();
    }



}

