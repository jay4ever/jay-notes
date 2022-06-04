package spring.core.overview.container;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import spring.core.overview.domain.User;

public class ApplicationContextDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ApplicationContextDemo.class);
        // 容器启动
        applicationContext.refresh();
        User bean = applicationContext.getBean(User.class);
        System.out.println(bean);
    }

    @Bean
    public User user() {
        User user = new User();
        user.setId(1L);
        user.setName("lisi");
        return user;
    }
}
