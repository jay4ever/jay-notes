package spring.core.overview.container;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import spring.core.overview.domain.User;

public class BeanFactoryDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        int beansCount = reader.loadBeanDefinitions("classpath:/META-INFO/dependencyInjection.xml");
        System.out.println(beansCount);
        User bean = beanFactory.getBean(User.class);
        System.out.println(bean);
    }
}
