package ru.denisfv.fullapi.jmx.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;

@EnableMBeanExport
@ComponentScan
public class JMXSpringApplication {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JMXSpringApplication.class);

        JMXSpringController controller = context.getBean(JMXSpringController.class);

        while (true) {
            Thread.sleep(1000);
            System.out.println(controller.isEnabled());
        }
    }
}
