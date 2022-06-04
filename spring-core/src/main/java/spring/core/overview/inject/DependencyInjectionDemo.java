package spring.core.overview.inject;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.core.overview.repository.UserRepository;

public class DependencyInjectionDemo {
    public static void main(String[] args) {
        BeanFactory context = new ClassPathXmlApplicationContext("classpath:/META-INFO/dependencyInjection.xml");
        UserRepository userRepository = (UserRepository) context.getBean("userRepository");
//        System.out.println("实时查找" + userRepository);
        // 依赖注入和依赖查找的bean源并不相同，下面：依赖注入能找到BeanFactory，但是依赖查找找不到
//        System.out.println(userRepository.getBeanFactory());
//        System.out.println(context.getBean(BeanFactory.class));
        System.out.println(userRepository.getObjectFactory().getObject());
    }

}
