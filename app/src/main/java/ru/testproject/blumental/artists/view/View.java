package ru.testproject.blumental.artists.view;

import android.content.Context;

/**
 * Created by Maxim Blumental on 3/25/2016.
 * bvmaks@gmail.com
 */
public interface View {
    Context getContext();
    void showToast(String message);
}
