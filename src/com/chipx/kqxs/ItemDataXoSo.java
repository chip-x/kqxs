package com.chipx.kqxs;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA
 * User: xc
 * Date: 10/01/14.
 * Time: 20:00
 */
public class ItemDataXoSo implements Serializable {
    String display;
    String[] prizes;
    String[] results;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String[] getPrizes() {
        return prizes;
    }

    public void setPrizes(String[] prizes) {
        this.prizes = prizes;
    }

    public String[] getResults() {
        return results;
    }

    public void setResults(String[] results) {
        this.results = results;
    }

    public ItemDataXoSo() {

    }

    public ItemDataXoSo(String display, String[] prizes, String[] results) {

        this.display = display;
        this.prizes = prizes;
        this.results = results;
    }

    public ArrayList<miniItem> getMiniItem() {
        ArrayList<miniItem> miniItemArrayList = new ArrayList<miniItem>();
        for (int i = 0; i < prizes.length; i++) {
            miniItemArrayList.add(new miniItem(results[i], prizes[i]));
        }
        return miniItemArrayList;
    }


}
