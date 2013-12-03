package my;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Denis Zhdanov
 * @since 03/12/13 10:11
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Service service = context.getBean(Service.class);
        System.out.println("No system property is set. Storage: " + service.getStorage());

        System.setProperty("use.nosql.storage", "true");
        context = new AnnotationConfigApplicationContext(Config.class);
        service = context.getBean(Service.class);
        System.out.println("'use nosql' system property is set. Storage: " + service.getStorage());
    }
}
