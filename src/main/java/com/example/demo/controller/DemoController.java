package com.example.demo.controller;

import com.example.demo.mapper.TreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class DemoController {
    @Autowired
    private TreeMapper treeMapper;

    @RequestMapping("/test")
    public String test(){
       treeMapper.selectById(3);
        return "hello world!";
    }
}
