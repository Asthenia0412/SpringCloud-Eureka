package com.itranswarp.exchange.provider.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：服务端控制器
 * @Author :xiaoyc
 */
@RequestMapping("test")
@RestController
public class TestController {

    @RequestMapping("getName")
    public String getName(){
        return "SpringCloud!";
    }
}

