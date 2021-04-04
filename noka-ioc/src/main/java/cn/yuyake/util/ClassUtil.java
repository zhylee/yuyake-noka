package cn.yuyake.util;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * create by yeah on 2021/4/3 15:21
 */
public class ClassUtil {

    public static final String FILE_PROTOCOL = "file";

    public static Logger logger = LogManager.getLogger(ClassUtil.class);

    /**
     * 获取包下类集和
     *
     * @param packageName 包名
     * @return 类集和
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        // 1. 获取到类的加载器
        ClassLoader classLoader = getClassLoader();
        // 2. 通过类加载器获取到加载的资源
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            logger.warn("unable to retrieve anything from package: {}", () -> packageName);
            return null;
        }
        // 3. 依据不同的资源类型，采用不同的方式获取资源的集合
        Set<Class<?>> classSet = null;
        // 过滤出文件类型的资源
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        // TODO 此处可以加入针对其他类型资源的处理

        return classSet;
    }

    /**
     * 递归获取目标package里面所有的class文件（包括package里的class文件）
     *
     * @param emptyClassSet 装载目标类的集合
     * @param fileSource    文件或者目录
     * @param packageName   包名
     */
    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource,
        String packageName) {
        if (!fileSource.isDirectory()) {
            return;
        }
        // 如果是一个文件夹，则调用其listFiles方法获取文件夹下面的文件或文件夹
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    // 获取文件的绝对值路径
                    String absoluteFilePath = file.getAbsolutePath();
                    if (absoluteFilePath.endsWith(".class")) {
                        // 若是class文件，则直接加载
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }

            // 根据class文件的绝对值路径，获取并生成class对象，并放入classSet中
            private void addToClassSet(String absoluteFilePath) {
                // 1. 从class文件的绝对值路径里提取出包含了package的类名
                //    如 `D:/Prect/noka/noka-ioc/out/test/classes/cn/yuyake/util/ClassUtilTest.class`
                //    需要弄成 `cn.yuyake.util.ClassUtil`
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                var className = absoluteFilePath.substring(absoluteFilePath.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));
                // 2. 通过反射机制获取对应的class对象并加入到classSet里
                var targetClass = loadClass(className);
                emptyClassSet.add(targetClass);
            }
        });
        if (files != null) {
            for (File f : files) {
                // 递归调用
                extractClassFile(emptyClassSet, f, packageName);
            }
        }
    }

    /**
     * 获取Class对象
     *
     * @param className class全名 = package + 类全名
     * @return Class
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("load class error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化class
     *
     * @param clazz      Class
     * @param accessible 是否支持创建出私有class对象的实例
     * @param <T>        class的类型
     * @return 类的实例化
     */
    public static <T> T newInstance(Class<?> clazz, boolean accessible) {
        try {
            var constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            logger.error("new instance error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 classLoader
     *
     * @return 当前 ClassLoader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
