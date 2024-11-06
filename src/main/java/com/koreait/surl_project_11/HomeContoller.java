package com.koreait.surl_project_11;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeContoller {
    @GetMapping("/")
    @ResponseBody
    public String ShowMain(){
        return "Main";
    }
}
