package com.chipx.kqxs;

/**
 * Created with IntelliJ IDEA
 * User: xc
 * Date: 12/01/14.
 * Time: 10:45
 */
public class TinhThanhPho {
    String district;
    String region;
    String display;

    public TinhThanhPho(String district, String region, String display) {
        this.district = district;
        this.region = region;
        this.display = display;
    }

    public TinhThanhPho() {
        district = "";
        region = "";
        display = "";
    }

    public String getId() {
        return district;
    }

    public void setId(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
