package com.chipx.kqxs;

/**
 * Created with IntelliJ IDEA
 * User: xc
 * Date: 31/01/14.
 * Time: 16:52
 */
public class ItemDataCity {
    String[] links;
    String[] displays;

    public ItemDataCity() {
    }

    public ItemDataCity(String[] links, String[] displays) {
        this.links = links;
        this.displays = displays;
    }

    public String[] getLinks() {
        return links;
    }

    public void setLinks(String[] links) {
        this.links = links;
    }

    public String[] getDisplays() {
        return displays;
    }

    public void setDisplays(String[] displays) {
        this.displays = displays;
    }
}
