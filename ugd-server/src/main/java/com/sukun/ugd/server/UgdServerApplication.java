package com.sukun.ugd.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 项目的启动类
 */
@EnableTransactionManagement
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${ugd.info.base-package}
@SpringBootApplication(scanBasePackages = {"${ugd.info.base-package}.server", "${ugd.info.base-package}.module"})
public class UgdServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UgdServerApplication.class, args);
    }

}
