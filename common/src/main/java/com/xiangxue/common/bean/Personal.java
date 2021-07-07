package com.xiangxue.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Personal implements Parcelable {

    private String name;
    private int age;

    public Personal(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    public void readFromParcel(Parcel source) {
        this.name = source.readString();
        this.age = source.readInt();
    }

    protected Personal(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<Personal> CREATOR = new Creator<Personal>() {
        @Override
        public Personal createFromParcel(Parcel source) {
            return new Personal(source);
        }

        @Override
        public Personal[] newArray(int size) {
            return new Personal[size];
        }
    };


    @Override
    public String toString() {
        return "Personal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
