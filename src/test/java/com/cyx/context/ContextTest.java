package com.cyx.context;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @Description
 * @date 2021/3/5
 */

public class ContextTest {
    @Test
    public void testAop() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println(context.getBeanDefinitionNames());
    }
}
