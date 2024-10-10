package com.iris.blog;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lstar
 * @create 2023-04
 * @description:
 */
@SpringBootApplication(scanBasePackages = "com.iris.blog")
@MapperScan("com.iris.blog.dao")
@EnableAsync
@Slf4j
public class AdminApplication
{
    public static void main( String[] args ) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(AdminApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        log.info("=====================SpringBoot Start Success Iris-admin=====================");
        log.info("\n----------------------------------------------------------\n\t" +
                "blog is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "Knife4j-ui: \thttp://" + ip + ":" + port + path + "/doc.html\n\t" +
                "----------------------------------------------------------");
    }
}
