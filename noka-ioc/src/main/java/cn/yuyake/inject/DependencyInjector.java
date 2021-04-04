package cn.yuyake.inject;

import cn.yuyake.core.BeanContainer;
import cn.yuyake.inject.annotation.Autowired;
import cn.yuyake.util.ClassUtil;
import cn.yuyake.util.ValidationUtil;
import java.lang.reflect.Field;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * create by yeah on 2021/4/4 14:49
 */
public class DependencyInjector {

    // Bean容器
    private BeanContainer beanContainer;
    // 日志源
    public static Logger logger = LogManager.getLogger(DependencyInjector.class);

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行IoC
     */
    public void doIoc() {
        // 1. 遍历Bean容器中所有的Class对象
        if (ValidationUtil.isEmpty(beanContainer.getClasses())) {
            logger.warn("empty class set in beanContainer");
            return;
        }
        for (Class<?> clazz : beanContainer.getClasses()) {
            // 2. 遍历Class对象的所有成员变量
            var fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                // 3. 找出被Autowired标记的成员变量
                if (field.isAnnotationPresent(Autowired.class)) {
                    var autowired = field.getAnnotation(Autowired.class);
                    // 4. 获取这些成员变量的类型
                    var fieldClass = field.getType();
                    // 5. 获取这些成员变量的类型在容器里对应的实例
                    var fieldValue = getFieldInstance(fieldClass, autowired.value());
                    if (fieldValue == null) {
                        throw new RuntimeException(
                            "unable to inject relevant type, target fieldClass is " +
                                fieldClass.getName() + " " + autowired.value());
                    } else {
                        // 6. 通过反射将对应的成员变量实例注入到成员变量所在的实例里
                        var targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field, targetBean, fieldValue, true);
                    }
                }
            }
        }
    }

    /**
     * 根据Class在BeanContainer里获取其实例或者实现类
     *
     * @param fieldClass     field的Class类型
     * @param autowiredValue 注解autowired里的value值
     * @return 对应的实例或实现类
     */
    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        var fieldValue = beanContainer.getBean(fieldClass);
        if (fieldValue != null) {
            return fieldValue;
        }
        var implementedClass = getImplementClass(fieldClass, autowiredValue);
        if (implementedClass != null) {
            return beanContainer.getBean(implementedClass);
        }
        return null;
    }

    /**
     * 获取接口的实现类
     *
     * @param fieldClass     field的Class类型
     * @param autowiredValue 注解autowired里的value值
     * @return 对应的实现类
     */
    private Class<?> getImplementClass(Class<?> fieldClass, String autowiredValue) {
        var classSet = beanContainer.getClassesBySuper(fieldClass);
        if (!ValidationUtil.isEmpty(classSet)) {
            if (ValidationUtil.isEmpty(autowiredValue)) {
                if (classSet.size() == 1) {
                    return classSet.iterator().next();
                } else {
                    // 如果多于两个实现类且用户未指定其中一个实现类，则抛出异常
                    throw new RuntimeException(
                        "multiple implemented classes for " + fieldClass.getName()
                            + " please set @Autowired's value to pick one");
                }
            } else {
                for (Class<?> clazz : classSet) {
                    if (autowiredValue.equals(clazz.getSimpleName())) {
                        return clazz;
                    }
                }
            }
        }
        return null;
    }
}
