package me.next.ninepiclib;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by NeXT on 17/10/11.
 */

public abstract class NinePicViewAdapter<T> {

    protected abstract void onBindView(Context context, FrameLayout frameLayout, int position, T t);

    protected void onItemClick(Context context, FrameLayout frameLayout, int index, List<T> list) {
    }

    protected boolean onItemLongClick(Context context, FrameLayout frameLayout, int index, List<T> list) {
        return false;
    }

    FrameLayout generateFrameLayout(Context context) {
        return (FrameLayout) LayoutInflater.from(context).inflate(getLayoutId(), null);
    }

    protected abstract int getLayoutId();
}
