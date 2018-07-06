package com.gogo.haobutler.test;

import java.io.Serializable;

/**
 * @author: 闫昊
 * @date: 2018/6/21 0021
 * @function:
 */
public class James implements Serializable {
    private String name;
    private transient int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "James{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
