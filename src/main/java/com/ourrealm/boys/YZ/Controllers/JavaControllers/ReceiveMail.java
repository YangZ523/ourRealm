package com.ourrealm.boys.YZ.Controllers.JavaControllers;

import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 定时器，不间断拉取我的邮箱邮件
 *
 * 1.@PostConstruct说明
 *
 *      被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法。被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
 *
 * 2.@PreConstruct说明
 *
 *      被@PreConstruct修饰的方法会在服务器卸载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的destroy()方法。被@PreConstruct修饰的方法会在destroy()方法之后运行，在Servlet被彻底卸载之前。
 *
 */

@Component//将此类注入到spring容器中
@Configuration//定义配置类,可替换xml文件，被注解的类包含一个或多个@Bean注解的方法，这些方法才可以被AnnotationConfigWebApplicationContext扫描，并初始化spring容器
@EnableScheduling//spring自带的定时类类注解，下面的注解有定义定时规则Scheduled
public class ReceiveMail {

//    @Scheduled(cron = "0 */30 * * * ?")//每半小时拉取一次邮箱，此处的正则时间，还没琢磨透，待学习
//    @Scheduled(cron = "*/30 * * * * *")//每隔30秒执行一次
    @PostConstruct//项目启动调用一次的注解
    public void  readMail() {
        System.out.println("ReceiveMail定时方法被调用一次");

        

    }

}
