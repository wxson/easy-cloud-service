package com.easy.cloud.web.component.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 类工具
 *
 * @author GR
 * @date 2021-4-7 10:19
 */
public class ClassUtils {

    /**
     * 给一个接口，返回这个接口的所有实现类
     *
     * @param packageName    制定包名
     * @param interfaceClass 接口类
     * @return java.util.List<java.lang.Class < ?>>
     */
    public static List<Class<?>> getAllClassByInterface(String packageName, Class<?> interfaceClass) {

        //如果不是一个接口，则不做处理
        if (interfaceClass.isInterface()) {
            return filter(packageName, interfaceClass);
        }

        return new ArrayList<>();
    }

    /**
     * 给一个接口，返回这个接口的所有实现类
     *
     * @param interfaceClass 接口类
     * @return java.util.List<java.lang.Class>
     */
    public static List<Class<?>> getAllClassByInterface(Class<?> interfaceClass) {
        //返回结果
        List<Class<?>> returnClassList = new ArrayList<>();
        //如果不是一个接口，则不做处理
        if (interfaceClass.isInterface()) {
            //获得当前的包名
            String packageName = interfaceClass.getPackage().getName();
            return filter(packageName, interfaceClass);
        }

        return returnClassList;
    }

    /**
     * 过滤非统一接口类
     *
     * @param packageName    包名
     * @param interfaceClass 接口类
     * @return java.util.List<java.lang.Class < ?>>
     */
    private static List<Class<?>> filter(String packageName, Class<?> interfaceClass) {
        //返回结果
        List<Class<?>> returnClassList = new ArrayList<>();
        try {
            //获得当前包下以及子包下的所有类
            List<Class<?>> underPackageNameAllClass = getClasses(packageName);
            underPackageNameAllClass.forEach(oneClass -> {
                //判断是不是一个接口
                if (interfaceClass.isAssignableFrom(oneClass)) {
                    //本身不加进去
                    if (!interfaceClass.equals(oneClass)) {
                        returnClassList.add(oneClass);
                    }
                }
            });
            return returnClassList;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return returnClassList;
    }

    /**
     * 从一个包中查找出所有的类，在jar包中不能查找
     *
     * @param packageName 包名
     * @return java.util.List<java.lang.Class < ?>>
     */
    private static List<Class<?>> getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        // 获取当前线程的类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }


    /**
     * 获取目录下的所有文件类
     *
     * @param directory   目录
     * @param packageName 包名
     * @return java.util.List<java.lang.Class < ?>>
     */
    private static List<Class<?>> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (null == files) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." +
                        file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' +
                        file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
