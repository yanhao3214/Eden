package com.gogo.haobutler.test.singleton;

/**
 * @author: 闫昊
 * @date: 2018/7/4 0004
 * @function: 静态内部类单例模式
 * 优点：懒加载、线程安全、保证单例对象的唯一性（静态内部类只会被加载一次）
 * 缺点：无
 * 结论：最佳选择
 */
public class Singleton4 {
    private Singleton4() {
    }

    private static class Holder {
        private static final Singleton4 INSTANCE = new Singleton4();
    }

    public static Singleton4 getInstance() {
        return Holder.INSTANCE;
    }
}
