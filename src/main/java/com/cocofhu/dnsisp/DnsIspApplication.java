package com.cocofhu.dnsisp;

import com.cocofhu.dnsisp.dns.DNSServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class DnsIspApplication {
    public static Map<String,String> data = new ConcurrentHashMap<String,String>();
    public static void main(String[] args) {
        SpringApplication.run(DnsIspApplication.class, args);
        new Thread(new DNSServer(data)).start();
    }

}
