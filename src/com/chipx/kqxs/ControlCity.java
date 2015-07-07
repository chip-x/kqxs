package com.chipx.kqxs;

/**
 * Created with IntelliJ IDEA
 * User: xc
 * Date: 31/01/14.
 * Time: 16:50
 */
public class ControlCity {
    ItemDataCity bac;
    ItemDataCity nam;
    ItemDataCity trung;

    public ItemDataCity getBac() {
        return bac;
    }

    public void setBac(ItemDataCity bac) {
        this.bac = bac;
    }

    public ItemDataCity getNam() {
        return nam;
    }

    public void setNam(ItemDataCity nam) {
        this.nam = nam;
    }

    public ItemDataCity getTrung() {
        return trung;
    }

    public void setTrung(ItemDataCity trung) {
        this.trung = trung;
    }

    public ControlCity() {
        bac = new ItemDataCity();
        trung = new ItemDataCity();
        nam = new ItemDataCity();

    }

    public ControlCity(ItemDataCity bac, ItemDataCity nam, ItemDataCity trung) {

        this.bac = bac;
        this.nam = nam;
        this.trung = trung;
    }
}
