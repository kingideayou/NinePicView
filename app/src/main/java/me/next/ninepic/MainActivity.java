package me.next.ninepic;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.next.ninepiclib.NinePicViewAdapter;
import me.next.ninepiclib.NinePicViewGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> imgList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            imgList.add("" + i);
        }

        NinePicViewGroup ninePicViewGroup = findViewById(R.id.nine_pic);
        ninePicViewGroup.setAdapter(mAdapter);
        ninePicViewGroup.setImagesData(imgList);


    }

    private NinePicViewAdapter<String> mAdapter = new NinePicViewAdapter<String>() {
        @Override
        protected void onBindView(Context context, FrameLayout frameLayout, int position, String s) {
            ImageView imageView = frameLayout.findViewById(R.id.image_test);
            Glide.with(getApplicationContext()).load("https://tva3.sinaimg.cn/crop.6.0.753.753.180/6db4aff6jw8et9dnyhy7oj20lc0lcabg.jpg").into(imageView);
            if (position % 2 == 0) {
                View ivTag = frameLayout.findViewById(R.id.tag);
                ivTag.setVisibility(ImageView.GONE);
            }
        }

        @Override
        protected void onItemClick(Context context, FrameLayout imageView, int index, List<String> list) {
            super.onItemClick(context, imageView, index, list);
            Toast.makeText(context, "点击第" + index + "张图片", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected boolean onItemLongClick(Context context, FrameLayout imageView, int index, List<String> list) {
            Toast.makeText(context, "长按第" + index + "张图片", Toast.LENGTH_SHORT).show();
            return super.onItemLongClick(context, imageView, index, list);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.test;
        }
    };
}
