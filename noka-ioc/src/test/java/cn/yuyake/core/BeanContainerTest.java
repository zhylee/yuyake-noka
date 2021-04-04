package cn.yuyake.core;

import cn.yuyake.core.annotation.Controller;
import cn.yuyake.relfect.ConstructorCollector;
import cn.yuyake.relfect.ReflectTargetOrigin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * create by yeah on 2021/4/4 11:18
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanContainerTest {

    private static BeanContainer beanContainer;

    @BeforeAll
    static void init() {
        beanContainer = BeanContainer.getInstance();
    }

    @DisplayName("加载目标类及其实例到BeanContainer: loadBeansTest")
    @Order(1)
    @Test
    public void loadBeansTest() {
        Assertions.assertFalse(beanContainer.isLoaded());
        beanContainer.loadBeans("cn.yuyake");
        Assertions.assertEquals(4, beanContainer.size());
        Assertions.assertTrue(beanContainer.isLoaded());
    }

    @DisplayName("根据类获取其实例：getBeanTest")
    @Order(2)
    @Test
    public void getBeanTest() {
        var controller = beanContainer.getBean(ConstructorCollector.class);
        Assertions.assertTrue(controller instanceof ConstructorCollector);
    }

    @DisplayName("根据注解获取对应的实例：getClassesByAnnotation")
    @Order(3)
    @Test
    public void getClassesByAnnotationTest() {
        Assertions.assertTrue(beanContainer.isLoaded());
        var controllers = beanContainer.getClassesByAnnotation(Controller.class);
        Assertions.assertEquals(2, controllers.size());
    }

    @DisplayName("根据接口获取对应的实例：getClassesBySuper")
    @Order(4)
    @Test
    public void getClassesBySuperTest() {
        Assertions.assertTrue(beanContainer.isLoaded());
        var set = beanContainer.getClassesBySuper(ReflectTargetOrigin.class);
        Assertions.assertEquals(1, set.size());
    }
}
