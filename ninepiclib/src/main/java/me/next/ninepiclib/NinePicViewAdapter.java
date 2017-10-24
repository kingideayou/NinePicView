package me.next.ninepiclib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by NeXT on 17/10/11.
 */

public abstract class NinePicViewAdapter<T, V extends View> {

    private static final int[] IMG_DEFAULT_SIZE = new int[]{0, 0};
    private int imgCount;

    protected abstract void onBindView(Context context, V view, int position, T t);

    protected void onItemClick(Context context, V view, int index, List<T> list) {
    }

    protected boolean onItemLongClick(Context context, V view, int index, List<T> list) {
        return false;
    }

    V generateFrameLayout(Context context) {
        return (V) LayoutInflater.from(context).inflate(getLayoutId(), null);
    }

    public void setImgCount(int imgCount) {
        this.imgCount = imgCount;
    }

    protected int getImgCount() {
        return imgCount;
    }

    /**
     * @return result[0] : imageWidth, result[1] : imageHeight
     */
    protected int[] getImgWidthHeight(T t) {
        return IMG_DEFAULT_SIZE;
    }

    protected abstract int getLayoutId();
}
