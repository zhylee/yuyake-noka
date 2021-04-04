package cn.yuyake.core;

import cn.yuyake.core.annotation.Component;
import cn.yuyake.core.annotation.Controller;
import cn.yuyake.core.annotation.Repository;
import cn.yuyake.core.annotation.Service;
import cn.yuyake.util.ClassUtil;
import cn.yuyake.util.ValidationUtil;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * create by yeah on 2021/4/3 20:13
 */
public class BeanContainer {

    public static Logger logger = LogManager.getLogger(BeanContainer.class);

    private BeanContainer() {
    }

    // 容器是否已经加载过bean
    private boolean loaded = false;
    // 存放所有被配置标记的目标对象的Map
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();
    // 加载bean的注解列表
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION =
        Arrays.asList(Component.class, Controller.class, Repository.class, Service.class);

    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder() {
            this.instance = new BeanContainer();
        }
    }

    /**
     * 获取Bean容器实例
     *
     * @return BeanContainer
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    /**
     * 是否加载过Bean
     *
     * @return 是否已加载
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Bean实例数量
     *
     * @return 数量
     */
    public int size() {
        return beanMap.size();
    }

    /**
     * 扫描加载所有Bean
     *
     * @param basePackage 包名
     */
    public synchronized void loadBeans(String basePackage) {
        // 判断bean容器是否被加载过
        if (isLoaded()) {
            logger.warn("BeanContainer has been loaded");
            return;
        }
        var classSet = ClassUtil.extractPackageClass(basePackage);
        if (ValidationUtil.isEmpty(classSet)) {
            logger.warn("extract nothing from package name " + basePackage);
            return;
        }
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                // 如果类上面标记了定义的注解
                if (clazz.isAnnotationPresent(annotation)) {
                    // 将目标类本身作为键，目标类的实例作为值，放入到beanMap中
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }
        loaded = true;
    }

    /**
     * 添加一个Class对象及其Bean实例
     *
     * @param clazz Class对象
     * @param bean  Bean实例
     * @return 原有的Bean实例，没有则返回null
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     * 移除一个IOC容器管理的对象
     *
     * @param clazz Class对象
     * @return 删除的Bean实例，没有则返回null
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 根据Class对象获取Bean实例
     *
     * @param clazz Class对象
     * @return Bean实例
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * 获取容器管理的所有Class对象集合
     *
     * @return Class集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取所有Bean集合
     *
     * @return Bean集合
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * 根据注解筛选出Bean的Class集合
     *
     * @param annotation 注解
     * @return Class集合
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        // 1. 获取beanMap的所有Class对象
        var keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            logger.warn("nothing in beanMap");
            return null;
        }
        // 2. 通过注解筛选被注解标记的Class对象，并添加到classSet里
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            // 类是否有相关的注解标记
            if (clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

    /**
     * 通过接口或者父类获取实现类或者子类的Class集合，不包括其本身
     *
     * @param interfaceOrClass 接口Class或者父类Class
     * @return Class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
        // 1. 获取beanMap的所有Class对象
        var keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            logger.warn("nothing in beanMap");
            return null;
        }
        // 2. 判断keySet里的元素是否是传入的接口或者类的子类，如果是，就将其添加到classSet里
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            // 判断keySet里的元素是否是传入的接口或者类的子类
            if (interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }
}
