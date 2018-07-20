package com.imooc.girl.controller;

import com.imooc.girl.properties.GirlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @Autowired
    private GirlProperties girlProperties;

    @RequestMapping(value={"/say","/hi"},method=RequestMethod.GET)
    public int say(){
        return girlProperties.getAge();
    }
}
