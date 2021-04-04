package cn.yuyake.inject;

import cn.yuyake.core.BeanContainer;
import cn.yuyake.reflect.ConstructorCollector;
import cn.yuyake.reflect.FieldCollector;
import cn.yuyake.reflect.MethodCollector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * create by yeah on 2021/4/4 15:22
 */
public class DependencyInjectorTest {

    @DisplayName("依赖注入doIoc")
    @Test
    public void doIocTest() {
        var beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("cn.yuyake.reflect");
        Assertions.assertTrue(beanContainer.isLoaded());
        var fieldController = (FieldCollector) beanContainer.getBean(FieldCollector.class);
        Assertions.assertNotNull(fieldController);
        Assertions.assertNull(fieldController.getMethodCollector());
        new DependencyInjector().doIoc();
        Assertions.assertNotNull(fieldController.getMethodCollector());
        var constructorCollector = (ConstructorCollector) beanContainer.getBean(ConstructorCollector.class);
        var reflectCollector = constructorCollector.getReflectCollector();
        Assertions.assertNotNull(reflectCollector);
        Assertions.assertTrue(reflectCollector instanceof MethodCollector);

    }
}
