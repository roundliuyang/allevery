package com.yly.commons.lang3.CollectionUtils;

/**
 * Author: yly
 * Date: 2021/10/20 9:19
 */
public class Address {
    private String locality;
    private String city;

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Address(String locality, String city) {
        this.locality = locality;
        this.city = city;
    }
}