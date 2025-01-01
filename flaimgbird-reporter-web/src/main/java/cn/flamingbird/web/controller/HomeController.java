package cn.flamingbird.web.controller;

import cn.flamingbird.reporter.Reporter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HomeController {

    @Resource
    Reporter reporter;

    @GetMapping("/")
    public String index(){
        reporter.sendMessage("reporter","error");
        return "hello world!";
    }
}
