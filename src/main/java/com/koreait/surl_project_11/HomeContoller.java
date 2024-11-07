package com.koreait.surl_project_11;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeContoller {
    @Value("${custom.site.name}")
    private String customSiteName;

    @GetMapping("/")
    @ResponseBody
    public String ShowMain() {
        return "Main on " + customSiteName;
    }
}
