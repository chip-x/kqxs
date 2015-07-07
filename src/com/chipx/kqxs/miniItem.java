package com.chipx.kqxs;

/**
 * Created with IntelliJ IDEA
 * User: xc
 * Date: 02/02/14.
 * Time: 13:19
 */
public class miniItem {
    String gt;
    String gtName;

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getGtName() {
        return gtName;
    }

    public void setGtName(String gtName) {
        this.gtName = gtName;
    }

    public miniItem() {

    }

    public miniItem(String gt, String gtName) {

        this.gt = gt;
        this.gtName = gtName;
    }
}
