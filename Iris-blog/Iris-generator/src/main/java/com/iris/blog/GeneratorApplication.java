package com.iris.blog;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lstar
 * @create 2023-04
 * @description:
 */
@SpringBootApplication(scanBasePackages = "com.iris.blog")
@MapperScan("com.iris.blog.dao")
@Slf4j
public class GeneratorApplication
{
    public static void main( String[] args ) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(GeneratorApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("=====================SpringBoot Start Success Iris-generator=====================");
        log.info("\n----------------------------------------------------------\n\t" +
                "blog is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "Knife4j-ui: \thttp://" + ip + ":" + port+ "/doc.html\n\t" +
                "----------------------------------------------------------");
    }
}
