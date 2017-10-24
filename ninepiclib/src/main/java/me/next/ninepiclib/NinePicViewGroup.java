package me.next.ninepiclib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeXT on 17/10/10.
 */
public class NinePicViewGroup<T, V extends View> extends ViewGroup {

    private static final int ROW_COUNT = 3;
    private static final int COLUMN_COUNT = 3;
    private static final int MAX_IMG_SIZE = 9;
    private int mGap;
    private int mGridSize;
    private int mRowCount;
    private int singleImgWidth;
    private int singleImgHeight;
    private boolean useSpecifySize;
    private List<T> mImgDataList = new ArrayList<>();
    private List<V> mViewListList = new ArrayList<>();
    private NinePicViewAdapter<T, V> mAdapter;

    public NinePicViewGroup(Context context) {
        this(context, null);
    }

    public NinePicViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePicViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NinePic);
        this.mGap = (int) typedArray.getDimension(R.styleable.NinePic_imgGap, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int totalWidth = width - getPaddingLeft() - getPaddingRight();

        if (mImgDataList.size() == 1) {
            int[] size = mAdapter.getImgWidthHeight(mImgDataList.get(0));
            singleImgWidth = size[0];
            singleImgHeight = size[1];

            useSpecifySize = singleImgWidth > 0 && singleImgHeight > 0;
            if (useSpecifySize) {
                for (int i = 0; i < getChildCount(); i++) {
                    View childView = getChildAt(i);
                    childView.measure(MeasureSpec.EXACTLY | singleImgWidth, MeasureSpec.EXACTLY | singleImgHeight);
                }
                setMeasuredDimension(singleImgWidth, singleImgHeight);
                return;
            }
        }

        mGridSize = (totalWidth - mGap * (COLUMN_COUNT - 1)) / COLUMN_COUNT;

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(MeasureSpec.EXACTLY | mGridSize, MeasureSpec.EXACTLY | mGridSize);
        }

        int height = mGridSize * mRowCount + mGap * (mRowCount - 1) + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width, height);
    }

    /**
     * 设置图片数据
     *
     * @param lists    图片数据集合
     */
    public void setImagesData(List<T> lists) {
        if (lists == null || lists.isEmpty()) {
            this.setVisibility(GONE);
            return;
        } else {
            this.setVisibility(VISIBLE);
        }
        int imagesSize = lists.size();
        mAdapter.setImgCount(imagesSize);
        getRowCount(imagesSize);
        int newShowCount = getNeedShowCount(imagesSize);

        if (mImgDataList == null) {
            int i = 0;
            while (i < newShowCount) {
                V view = getFrameLayout(i);
                if (view == null) {
                    return;
                }
                addView(view, generateDefaultLayoutParams());
                i++;
            }
        } else {
            int oldShowCount = getNeedShowCount(mImgDataList.size());
            if (oldShowCount > newShowCount) {
                removeViews(newShowCount, oldShowCount - newShowCount);
            } else if (oldShowCount < newShowCount) {
                for (int i = oldShowCount; i < newShowCount; i++) {
                    V view = getFrameLayout(i);
                    if (view == null) {
                        return;
                    }
                    addView(view, generateDefaultLayoutParams());
                }
            }
        }
        mImgDataList = lists;
        requestLayout();
    }

    private V getFrameLayout(final int position) {
        if (position < mViewListList.size()) {
            return mViewListList.get(position);
        } else {
            if (mAdapter != null) {
                final V view = mAdapter.generateFrameLayout(getContext());
                mViewListList.add(view);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAdapter.onItemClick(getContext(), view, position, mImgDataList);
                    }
                });
                view.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return mAdapter.onItemLongClick(getContext(), view, position, mImgDataList);
                    }
                });
                return view;
            } else {
                Log.e("NinePic", "Your must set a NinePicAdapter for NinePic");
                return null;
            }
        }
    }

    private void getRowCount(int imagesSize) {
        mRowCount = imagesSize / 3 + (imagesSize % 3 == 0 ? 0 : 1);
        if (mRowCount > ROW_COUNT) {
            mRowCount = ROW_COUNT;
        }
    }

    private int getNeedShowCount(int size) {
        if (size > MAX_IMG_SIZE) {
            return MAX_IMG_SIZE;
        } else {
            return size;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildrenView();
    }

    /**
     * 根据照片数量和span类型来对子视图进行动态排版布局
     */
    private void layoutChildrenView() {
        if (mImgDataList == null) {
            return;
        }
        int showImgCount = getNeedShowCount(mImgDataList.size());
        layoutForChildrenView(showImgCount);
    }

    private void layoutForChildrenView(int childrenCount) {
        if (childrenCount <= 0) {
            return;
        }

        int row, column, left, top, right, bottom;
        if (childrenCount == 1 && useSpecifySize) {
            V childrenView = (V) getChildAt(0);
            childrenView.measure(MeasureSpec.EXACTLY | singleImgWidth, MeasureSpec.EXACTLY | singleImgHeight);

            left = getPaddingLeft();
            top = getPaddingTop();
            right = left + singleImgWidth;
            bottom = top + singleImgHeight;
            childrenView.layout(left, top, right, bottom);

            if (mAdapter != null) {
                mAdapter.onBindView(getContext(), childrenView, 0, mImgDataList.get(0));
            }
            return;
        }

        for (int i = 0; i < childrenCount; i++) {
            V childrenView = (V) getChildAt(i);
            childrenView.measure(MeasureSpec.EXACTLY | mGridSize, MeasureSpec.EXACTLY | mGridSize);
            row = i / COLUMN_COUNT;
            column = i % COLUMN_COUNT;
            left = (mGridSize + mGap) * column + getPaddingLeft();
            top = (mGridSize + mGap) * row + getPaddingTop();
            right = left + mGridSize;
            bottom = top + mGridSize;
            childrenView.layout(left, top, right, bottom);

            if (mAdapter != null) {
                mAdapter.onBindView(getContext(), childrenView, i, mImgDataList.get(i));
            }
        }
    }

    public void setAdapter(NinePicViewAdapter<T, V> adapter) {
        mAdapter = adapter;
    }
}
