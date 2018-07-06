package com.gogo.haobutler.test;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: 闫昊
 * @date: 2018/6/21 0021
 * @function:
 */
public class Wade implements Parcelable {
    private String name;
    private int age;

    protected Wade(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }


    public static final Creator<Wade> CREATOR = new Creator<Wade>() {
        @Override
        public Wade createFromParcel(Parcel in) {
            return new Wade(in);
        }

        @Override
        public Wade[] newArray(int size) {
            return new Wade[size];
        }
    };
}
