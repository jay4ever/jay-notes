package spring.core.overview.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.core.overview.lookup.annotation.Super;
import spring.core.overview.lookup.domain.User;

import java.util.Map;

public class DependencyLookupDemo {
    public static void main(String[] args) {
        BeanFactory context = new ClassPathXmlApplicationContext("classpath:/META-INFO/dependencyLookup.xml");
        getByAnnotation(context);
        getByType(context);
        getMapBeans(context);
        getInRealtime(context);
        getInLazy(context);
    }

    private static void getByAnnotation(BeanFactory context) {
        if(context instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) context;
            Map<String, User> beansWithAnnotation = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println(beansWithAnnotation);
        }
    }

    private static void getByType(BeanFactory context) {
        User user = context.getBean(User.class);
        System.out.println(user);
    }

    private static void getMapBeans(BeanFactory context) {
        if(context instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) context;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println(beansOfType);
        }

    }

    private static void getInLazy(BeanFactory context) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) context.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟查找：" + user);
    }

    private static void getInRealtime(BeanFactory context) {
        User user = (User) context.getBean("user");
        System.out.println("实时查找" + user);
    }
}
