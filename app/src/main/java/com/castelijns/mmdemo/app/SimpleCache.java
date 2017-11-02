package com.castelijns.mmdemo.app;

import java.util.List;

public class SimpleCache<T> {

    protected List<T> cache;

    public void cacheData(List<T> data) {
        cache = data;
    }
}
