package com.hottop.core.feature;

import java.util.ArrayList;

public interface ICabinet<T> {
    ArrayList<T> items();

    ICabinet<T> register(T... items);

    T getItemByName(String name) throws Exception;
}
