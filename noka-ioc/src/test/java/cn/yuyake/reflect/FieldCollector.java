package cn.yuyake.reflect;

import cn.yuyake.core.annotation.Controller;
import cn.yuyake.inject.annotation.Autowired;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 获取成员变量并调用：
 * <p>    1. 获取批量的
 * <p>        public Field[] getFields(); 获取所有的公有字段 / 包含父类的公有
 * <p>        public Field[] getDeclaredFields(); 获取所有的字段（包括私有、受保护、默认、公有）/ 不包含父类所有字段
 * <p>    2. 获取单个的
 * <p>        public Field getField(String fieldName); 获取某个公有的字段，包含继承的字段
 * <p>        public Field getDeclareField(String fieldName); 获取某个字段，不包含继承的字段
 * <p>    3. 设置字段的值
 * <p>        Field --> public void set(Object obj, Object value);
 * <p>       参数说明：obj：要设置的字段所在的对象；value：要为字段设置的值
 * <p>
 * create by yeah on 2021/4/3 10:48
 */
@Controller
public class FieldCollector implements ReflectCollector {

    @Autowired
    private ConstructorCollector constructorCollector;
    @Autowired
    private MethodCollector methodCollector;

    public MethodCollector getMethodCollector() {
        return methodCollector;
    }

    public static void main(String[] args)
        throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 获取Class对象
        var reflectTargetClass = Class.forName("cn.yuyake.reflect.ReflectTarget");
        // 1. 获取所有公有的字段
        System.out.println("*************获取所有公有的字段*************");
        var fieldArray = reflectTargetClass.getFields();
        for (Field f : fieldArray) {
            System.out.println(f);
        }
        // 2. 获取所有字段
        System.out.println("*************获取所有字段（包括私有、受保护、默认、公有）*************");
        fieldArray = reflectTargetClass.getDeclaredFields();
        for (Field f : fieldArray) {
            System.out.println(f);
        }
        // 3. 获取单个特定公有的Field
        System.out.println("*************获取公有字段并调用*************");
        var f = reflectTargetClass.getField("name");
        System.out.println("公有的Field name：" + f);
        var reflectTarget = (ReflectTarget) reflectTargetClass.getConstructor().newInstance();
        // 4. 给获取到的Filed赋值
        f.set(reflectTarget, "待反射一号");
        // 5. 验证对应的值name
        System.out.println("验证name = "+ reflectTarget.name);
        // 6. 获取单个私有的Field
        System.out.println("*************获取私有字段targetInfo并调用*************");
        f = reflectTargetClass.getDeclaredField("targetInfo");
        System.out.println(f);
        f.setAccessible(true);
        f.set(reflectTarget, "18819499542");
        System.out.println("验证信息" + reflectTarget);
    }
}
