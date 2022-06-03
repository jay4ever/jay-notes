package spring.core.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 在非spring容器环境中使用JavaBean配置可以用字符串get对象
 */
public class BeanInfoExample {

    public static void main(String[] args) throws Throwable {
        Map<String, String> parameters = new HashMap<>() {
            {
                //这里的key要和Node里面的属性名一致
                put("nodeName", "曹操");
                put("user", "zhaoJun|changhe@163.com|2018-10-08 15:06:00");
            }
        };
        Node convert = convert(parameters);
        System.out.println(convert.getNodeName());
        System.out.println(convert.getUser());
    }

    public static Node convert(Map<String, String> parameters) throws Throwable {
        // 注册bean的编辑器，放到一个Map中
        PropertyEditorManager.registerEditor(User.class, StringToUserPropertyEditor.class);
        Node node = new Node();
        BeanInfo beanInfo = Introspector.getBeanInfo(Node.class, Object.class);
        Stream.of(beanInfo.getPropertyDescriptors()).forEach(it -> {
            Class<?> propertyType = it.getPropertyType();
            Method writeMethod = it.getWriteMethod();
            if (propertyType == String.class) {
                try {
                    writeMethod.invoke(node, parameters.get(it.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // setUser方法
                // fixme: 拿不到引用
                // PropertyEditor editor = PropertyEditorManager.findEditor(propertyType);
                StringToUserPropertyEditor editor = new StringToUserPropertyEditor();
                if (editor != null) {
                    editor.setAsText(parameters.get(it.getName()));
                    try {
                        writeMethod.invoke(node, editor.getValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("no editor for: " + it.getName());
                }
            }
        });
        return node;
    }

    static class StringToUserPropertyEditor extends PropertyEditorSupport {
        public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:hh:mm");

        @Override
        public void setAsText(String userText) throws IllegalArgumentException {
            String[] tokens = userText.split("\\|");
            User user = new User();
            user.setName(tokens[0]);
            user.setEmail(tokens[1]);
            try {
                user.setDate(formatter.parse(tokens[2]));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
            setValue(user);
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    static class User {
        private String name;
        private String email;
        private Date date;
    }

    @Getter
    @Setter
    static class Node {
        private String nodeName;
        private User user;
    }
}
