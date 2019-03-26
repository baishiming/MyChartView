package com.xietong.mychartview;

/**
 * Author : baishiming
 * Time : 2019/3/22
 * e_mail :
 * function:
 */
public class ChartViewBean {


    /**
     * key : 专家种类
     * isRise : 0
     * rate : 35.5
     * lastYear : 35
     * thisYear : 40
     * unit : 个
     * max : 40
     */

    private String key;
    private int isRise;
    private double rate;
    private int lastYear;
    private int thisYear;
    private String unit;
    private int max;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIsRise() {
        return isRise;
    }

    public void setIsRise(int isRise) {
        this.isRise = isRise;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getLastYear() {
        return lastYear;
    }

    public void setLastYear(int lastYear) {
        this.lastYear = lastYear;
    }

    public int getThisYear() {
        return thisYear;
    }

    public void setThisYear(int thisYear) {
        this.thisYear = thisYear;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
