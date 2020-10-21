package com.pc;

import com.pc.spring.importannotation.ImportLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动类
 * 1、目前排除数据库配置
 * @author pc
 * @Date 2020/10/20
 **/
@ImportLoader
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class);
    }

}
