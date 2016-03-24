package ru.testproject.blumental.artists.model;

import java.util.List;

import rx.Observable;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface Model {
    Observable<String> downloadPage(List<String> urls);
}
