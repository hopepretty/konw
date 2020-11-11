package com.pc.listeners;

import com.pc.spring.importannotation.Bean1;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * spring容器上下文监听
 * @author pc
 * @Date 2020/10/20
 **/
public class ApplicationContextListener implements ApplicationListener<ApplicationEvent> {

    /**
     * 监听事件
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationStartingEvent) {
            System.out.println("执行-ApplicationStartedEvent -- 1");
        } else if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
            //环境参数设置
            System.out.println("执行-ApplicationEnvironmentPreparedEvent -- 2");
            ConfigurableEnvironment environment = ((ApplicationEnvironmentPreparedEvent) applicationEvent).getEnvironment();
            System.out.println("环境参数：" + environment.getSystemEnvironment());
        } else if (applicationEvent instanceof ApplicationPreparedEvent) {
            System.out.println("执行-ApplicationPreparedEvent  -- 3");
            ConfigurableApplicationContext applicationContext = ((ApplicationPreparedEvent) applicationEvent).getApplicationContext();
            //环境参数
            System.out.println("环境参数：" + applicationContext.getEnvironment());
            if (applicationContext.containsBean("com.pc.spring.importannotation.Bean1")) {
                Bean1 bean = applicationContext.getBean(Bean1.class);
                System.out.println("bean被加载：" + bean);
            }
        } else if (applicationEvent instanceof ApplicationStartedEvent) {
            System.out.println("执行-ApplicationStartedEvent -- 4");
            ConfigurableApplicationContext applicationContext = ((ApplicationStartedEvent) applicationEvent).getApplicationContext();
            if (applicationContext.containsBean("com.pc.spring.importannotation.Bean1")) {
                Bean1 bean = applicationContext.getBean(Bean1.class);
                System.out.println("bean被加载：" + bean);
            }
        } else if (applicationEvent instanceof ApplicationReadyEvent) {
            System.out.println("执行-ApplicationReadyEvent -- 5");
            ConfigurableApplicationContext applicationContext = ((ApplicationReadyEvent) applicationEvent).getApplicationContext();
            if (applicationContext.containsBean("com.pc.spring.importannotation.Bean1")) {
                Bean1 bean = applicationContext.getBean(Bean1.class);
                System.out.println("bean被加载：" + bean);
            }
        } else if (applicationEvent instanceof ApplicationFailedEvent) {
            System.out.println("执行-ApplicationFailedEvent -- 6");
        }
    }
}
