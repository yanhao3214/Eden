package com.gogo.haobutler.test.singleton;

/**
 * @author: 闫昊
 * @date: 2018/7/4 0004
 * @function:传统懒汉模式
 * 优点：第一次调用getInstance()时才初始化，synchronized保证线程安全
 * 缺点：每次调用getInstance()时都会同步，即使sInstance已经初始化过了，造成不必要同步开销
 * 结论：不推荐
 */
public class Singleton1 {
    private static Singleton1 sInstance;

    private Singleton1() {
    }

    public static synchronized Singleton1 getInstance() {
        if (sInstance == null) {
            sInstance = new Singleton1();
        }
        return sInstance;
    }
}
