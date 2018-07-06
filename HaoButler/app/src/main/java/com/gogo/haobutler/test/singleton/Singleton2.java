package com.gogo.haobutler.test.singleton;

/**
 * @author: 闫昊
 * @date: 2018/7/4 0004
 * @function: getInstance()方法进行了两次判空，第一次是为了避免不必要的同步，第二次则是在null的情况下创建实例。
 * 看起来很好但是存在问题：因为sInstance = new Singleton2()这句不是原子操作（可能会被打断），它可分为3步：
 * 1.给sInstance分配内存
 * 2.调用构造函数初始化成员变量
 * 3.将sInstance指向分配的内存空间（执行完本操作后，sInstance != null）
 * 假设：线程A执行到sInstance = new Singleton2()这步，走的顺序为1->3->2，当执行完3后，线程B直接取走了非空的sInstance（但并没有初始化），使用时就会出错，导致DCL（Double Check Lock）失效
 * 优点：懒加载，第一次执行getInstance()时单例对象才会实例化，资源利用率高、效率高；
 * 缺点：高并发时有风险（小概率发生错误）；第一次加载稍慢
 * 结论：能满足绝大部分使用场景，推荐使用
 */
public class Singleton2 {
    private static Singleton2 sInstance;

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        if (sInstance == null) {
            synchronized(Singleton2.class) {
                if (sInstance == null) {
                    sInstance = new Singleton2();
                }
            }
        }
        return sInstance;
    }
}
