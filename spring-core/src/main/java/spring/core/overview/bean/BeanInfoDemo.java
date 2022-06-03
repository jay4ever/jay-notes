package spring.core.overview.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * PropertyEditor的基本api
 */
public class BeanInfoDemo {
    public static void main(String[] args) throws IntrospectionException {
        // 这里可以使用stopClass参数，将父类的property信息给排除掉
        User user = new User();
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class, Object.class);
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(it -> {
                    if (it.getName().equals("age")) {
                        // 为age添加属性编辑器, 可以set String类型的值
                        it.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
                        it.createPropertyEditor(user);
                    }
                });

    }

    static class StringToIntegerPropertyEditor extends PropertyEditorSupport {
        public void setAsText(String text) throws java.lang.IllegalArgumentException {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }

    @Getter
    @NoArgsConstructor
    static class User {
        private String name;
        private Integer age;
    }
}
