package me.next.ninepic;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        List<TimeLineBean> timeLineList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            List<String> imgList = new ArrayList<>();

            int imgCount = i % 9 + 1;
            if (imgCount == 1) {
                imgList.add("http://7xs5ba.com1.z0.glb.clouddn.com/chedui.jpg");
            } else {
                for (int j = 0; j < imgCount; j++) {
                    imgList.add("http://odum9helk.qnssl.com/resource/gogopher.jpg?imageView2/1/w/200/h/200");
                }
            }
            timeLineList.add(new TimeLineBean("内容" + i, imgList));
        }

        RecyclerView recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new NinePicListAdapter(timeLineList));

    }
}
