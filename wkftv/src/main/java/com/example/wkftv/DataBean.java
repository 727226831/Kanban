package com.example.wkftv;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class DataBean implements Parcelable {



   private  String item1;
    private  String item2;
    private  String item3;
    private  String item4;
    private  String item5;
    private  String item6;

    public String getItem110() {
        return item110;
    }

    public void setItem110(String item110) {
        this.item110 = item110;
    }

    private String item110;

    public String getItem100() {
        return item100;
    }

    public void setItem100(String item100) {
        this.item100 = item100;
    }

    private String item100;

    public String getItem99() {
        return item99;
    }

    public void setItem99(String item99) {
        this.item99 = item99;
    }

    private String item99;

    public int getItem0() {
        return item0;
    }

    public void setItem0(int item0) {
        this.item0 = item0;
    }

    private int item0;

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getItem5() {
        return item5;
    }

    public void setItem5(String item5) {
        this.item5 = item5;
    }

    public String getItem6() {
        return item6;
    }

    public void setItem6(String item6) {
        this.item6 = item6;
    }

    public String getItem7() {
        return item7;
    }

    public void setItem7(String item7) {
        this.item7 = item7;
    }

    public String getItem8() {
        return item8;
    }

    public void setItem8(String item8) {
        this.item8 = item8;
    }

    public String getItem9() {
        return item9;
    }

    public void setItem9(String item9) {
        this.item9 = item9;
    }

    public String getItem10() {
        return item10;
    }

    public void setItem10(String item10) {
        this.item10 = item10;
    }

    public String getItem11() {
        return item11;
    }

    public void setItem11(String item11) {
        this.item11 = item11;
    }

    public String getItem12() {
        return item12;
    }

    public void setItem12(String item12) {
        this.item12 = item12;
    }

    public String getItem13() {
        return item13;
    }

    public void setItem13(String item13) {
        this.item13 = item13;
    }

    public String getItem14() {
        return item14;
    }

    public void setItem14(String item14) {
        this.item14 = item14;
    }

    public String getItem15() {
        return item15;
    }

    public void setItem15(String item15) {
        this.item15 = item15;
    }

    private  String item7;
    private  String item8;
    private  String item9;
    private  String item10;
    private  String item11;
    private  String item12;
    private  String item13;
    private  String item14;
    private  String item15;


    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    private int textColor;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.item1);
        dest.writeString(this.item2);
        dest.writeString(this.item3);
        dest.writeString(this.item4);
        dest.writeString(this.item5);
        dest.writeString(this.item6);
        dest.writeString(this.item110);
        dest.writeString(this.item100);
        dest.writeString(this.item99);
        dest.writeInt(this.item0);
        dest.writeString(this.item7);
        dest.writeString(this.item8);
        dest.writeString(this.item9);
        dest.writeString(this.item10);
        dest.writeString(this.item11);
        dest.writeString(this.item12);
        dest.writeString(this.item13);
        dest.writeString(this.item14);
        dest.writeString(this.item15);
        dest.writeInt(this.textColor);
    }

    public DataBean() {
    }

    protected DataBean(Parcel in) {
        this.item1 = in.readString();
        this.item2 = in.readString();
        this.item3 = in.readString();
        this.item4 = in.readString();
        this.item5 = in.readString();
        this.item6 = in.readString();
        this.item110 = in.readString();
        this.item100 = in.readString();
        this.item99 = in.readString();
        this.item0 = in.readInt();
        this.item7 = in.readString();
        this.item8 = in.readString();
        this.item9 = in.readString();
        this.item10 = in.readString();
        this.item11 = in.readString();
        this.item12 = in.readString();
        this.item13 = in.readString();
        this.item14 = in.readString();
        this.item15 = in.readString();
        this.textColor = in.readInt();
    }

    public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
        @Override
        public DataBean createFromParcel(Parcel source) {
            return new DataBean(source);
        }

        @Override
        public DataBean[] newArray(int size) {
            return new DataBean[size];
        }
    };
}
