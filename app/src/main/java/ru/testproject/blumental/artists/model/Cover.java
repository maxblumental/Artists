package ru.testproject.blumental.artists.model;

/**
 * POJO for URLs to covers.
 * <p/>
 * Created by Maxim Blumental on 3/24/2016.
 * bvmaks@gmail.com
 */
public class Cover {
    private String big;

    private String small;

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    @Override
    public String toString() {
        return "ClassPojo [big = " + big + ", small = " + small + "]";
    }
}