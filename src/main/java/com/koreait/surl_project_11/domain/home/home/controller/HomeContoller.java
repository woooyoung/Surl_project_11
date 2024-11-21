package com.koreait.surl_project_11.domain.home.home.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Transactional(readOnly = true)
public class HomeContoller {
    @Value("${custom.site.name}")
    private String customSiteName;
    @Value("${custom.secret.key}")
    private String secretKey;

    @GetMapping("/")
    @ResponseBody
    public String ShowMain() {
        return "Main on " + customSiteName + "!!!!!!";
    }

    @GetMapping("/secretKey")
    @ResponseBody
    public String ShowSecretKey() {
        return "secretKey : " + secretKey;
    }
}
