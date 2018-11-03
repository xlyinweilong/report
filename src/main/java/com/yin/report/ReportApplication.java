package com.yin.report;

import com.yin.report.service.SaleDailyChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReportApplication {


    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }
}
