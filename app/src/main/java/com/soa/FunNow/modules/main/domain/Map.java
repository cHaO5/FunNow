package com.soa.FunNow.modules.main.domain;

public class Map {
    private String name;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Map{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
