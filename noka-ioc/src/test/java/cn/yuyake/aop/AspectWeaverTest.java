package cn.yuyake.aop;

import cn.yuyake.core.BeanContainer;
import cn.yuyake.inject.DependencyInjector;
import cn.yuyake.reflect.FieldCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AspectWeaverTest {

    @DisplayName("织入通用逻辑测试：doAOP")
    @Test
    public void doAOPTest() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("cn.yuyake");
        new AspectWeaver().doAOP();
        new DependencyInjector().doIoc();
        FieldCollector fieldCollector = (FieldCollector) beanContainer.getBean(FieldCollector.class);
        fieldCollector.getMethodCollector();
    }
}
