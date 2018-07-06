package com.gogo.haobutler.model.user;

import cn.bmob.v3.BmobUser;

/**
 * @author: 闫昊
 * @date: 2018/4/17
 * @function:
 */

public class User extends BmobUser {
    private int age;
    private String description;
    private int gender;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
