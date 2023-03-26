package com.example.proj;

import android.util.Log;

public class params {
    public String class_name;
    public String lect_in_a_day;
     public String no_working_days;
    public String no_of_Sub;


    public void setClass_name(String class_name) {
        this.class_name = class_name;
        Log.d("TAG", "setClass_name: "+this.class_name);
    }
    public String getClass_name() {
        return class_name;
    }

    public String getLect_in_a_day() {
        return lect_in_a_day;
    }

    public void setLect_in_a_day(String lect_in_a_day) {
        this.lect_in_a_day = lect_in_a_day;
        Log.d("TAG", "setLect_in_a_day: "+lect_in_a_day);
    }

    public String getNo_working_days() {
        return no_working_days;
    }

    public void setNo_working_days(String no_working_days) {
        this.no_working_days = no_working_days;
        Log.d("TAG", "setNo_working_days: "+no_working_days);
    }

    public String getNo_of_Sub() {
        return no_of_Sub;
    }

    public void setNo_of_Sub(String no_of_Sub) {
        this.no_of_Sub = no_of_Sub;
        Log.d("TAG", "setNo_of_Sub: "+no_of_Sub);
    }


}
