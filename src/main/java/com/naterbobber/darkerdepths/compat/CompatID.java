package com.naterbobber.darkerdepths.compat;

public class CompatID {
    private final String id;

    public CompatID(String id) {
        this.id = id;
    }

    public static CompatID createCompatID(String id) {
        return new CompatID(id);
    }

    public String toString() {
        return id;
    }
}
