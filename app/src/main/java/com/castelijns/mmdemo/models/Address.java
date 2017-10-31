package com.castelijns.mmdemo.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Address implements Parcelable {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Map<String, String> geo;

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Map<String, String> getGeo() {
        return geo;
    }

    public void setGeo(Map<String, String> geo) {
        this.geo = geo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.street);
        dest.writeString(this.suite);
        dest.writeString(this.city);
        dest.writeString(this.zipcode);
        dest.writeInt(this.geo.size());

        for (Map.Entry<String, String> entry : this.geo.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    private Address(Parcel in) {
        this.street = in.readString();
        this.suite = in.readString();
        this.city = in.readString();
        this.zipcode = in.readString();
        int geoSize = in.readInt();
        this.geo = new HashMap<>(geoSize);

        for (int i = 0; i < geoSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.geo.put(key, value);
        }
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
