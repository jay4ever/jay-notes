package spring.core.overview.repository;

import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import spring.core.overview.domain.User;

import java.util.Collection;

@Data
public class UserRepository {

    private Collection<User> users;

    // 容器内建依赖
    private BeanFactory beanFactory;

    // 可以通过泛型，延迟获取不同类型的bean
    private ObjectFactory<ApplicationContext> objectFactory;


}
