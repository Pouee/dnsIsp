package com.cocofhu.dnsisp.controller;

import com.cocofhu.dnsisp.DnsIspApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @RequestMapping("/hello")
    @ResponseBody
    @CrossOrigin
    public Object hello(HttpServletResponse response, String name) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        Map<String,String> data = new HashMap<>();
        data.put("ip", DnsIspApplication.data.get(name));
        if((System.currentTimeMillis() & 255) == 0) data.clear();
        return data;
    }
}
