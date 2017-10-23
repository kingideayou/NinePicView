package me.next.ninepiclib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by NeXT on 17/10/11.
 */

public abstract class NinePicViewAdapter<T, V extends View> {

    protected abstract void onBindView(Context context, V view, int position, T t);

    protected void onItemClick(Context context, V view, int index, List<T> list) {
    }

    protected boolean onItemLongClick(Context context, V view, int index, List<T> list) {
        return false;
    }

    V generateFrameLayout(Context context) {
        return (V) LayoutInflater.from(context).inflate(getLayoutId(), null);
    }

    protected abstract int getLayoutId();
}
