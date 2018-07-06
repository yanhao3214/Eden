package com.gogo.haobutler.test.singleton;

/**
 * @author: 闫昊
 * @date: 2018/7/4 0004
 * @function: 在DCL基础上，将sInstance设为volatile
 * volatile：1.保证线程本地不会有变量副本，每次都从主内存中读取；2.保证变量的写操作都先行发生在后面对于它的读操作
 * 优点：每次都从主内存中读取sInstance，将DCL中sInstance = new Singleton2()操作原子化，避免错误
 * 缺点：多少影响一点性能
 * 结论：可以使用
 */
public class Singleton3 {
    private volatile static Singleton3 sInstance;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (sInstance == null) {
            synchronized (Singleton3.class) {
                if (sInstance == null) {
                    sInstance = new Singleton3();
                }
            }
        }
        return sInstance;
    }
}
