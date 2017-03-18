package cn.cs.scu.domain;

/**
 * 省份热门top3广告
 * <p>
 * Created by Wanghan on 2017/3/18.
 * Copyright © Wanghan SCU. All Rights Reserved
 */
public class ProvinceTop3Ad implements Cloneable {
    String province = null;
    Ad top1 = null;
    Ad top2 = null;
    Ad top3 = null;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Ad getTop1() {
        return top1;
    }

    public void setTop1(Ad top1) {
        this.top1 = top1;
    }

    public Ad getTop2() {
        return top2;
    }

    public void setTop2(Ad top2) {
        this.top2 = top2;
    }

    public Ad getTop3() {
        return top3;
    }

    public void setTop3(Ad top3) {
        this.top3 = top3;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        String str = province + "\n" + "ad_id\tclick_number\n";
        if (top1 != null) {
            str += top1.getAdId() + "\t" + top1.getClickNumber() + "\n";
        }
        if (top2 != null) {
            str += top2.getAdId() + "\t" + top2.getClickNumber() + "\n";
        }
        if (top3 != null) {
            str += top3.getAdId() + "\t" + top3.getClickNumber() + "\n";
        }
        return str;
    }
}
