package com.example.demo;

import com.example.demo.service.IServantService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Created by lenovo on 2019/5/30.
 */
public class SpringTest {

    @BeforeClass
    public static void beforeTest() {
        System.out.println("============测试开始================");
    }

    @AfterClass
    public static void afterTest() {
        System.out.println("============测试结束================");
    }

    @Test
    public void XmlBeanFactoryTest() {
        Resource resource = new ClassPathResource("classpath.xml");
        BeanFactory beanFactory = new XmlBeanFactory(resource);
    }

    @Test
    public void classPathXmlApplicationContextTest() {
        // 从classpath获取配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("helloworld.xml");
        IServantService service = context.getBean("hello", IServantService.class);
    }

    @Test
    public void fileSystemXmlApplicationContextTest() {
        // 从文件系统获取配置文件。
        ApplicationContext context = new FileSystemXmlApplicationContext("helloworld.xml");
    }

}
