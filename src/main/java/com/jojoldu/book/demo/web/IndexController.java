package com.jojoldu.book.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//페이지와 관련된 컨트롤러
@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
        //src/main/resources/templates + /index + .mustache 자동 주소 완성
    }

    @GetMapping("/posts/save")
    public String postSave(){
        return "posts-save";
    }

}
