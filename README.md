# NinePicView [![TagCloudView](https://img.shields.io/badge/NeXT___-NinePicView-brightgreen.svg?style=flat)](https://kingideayou.github.io)

九宫格图片，支持单图设置宽高，支持通过 xml 自定义 Item 样式，轻松实现[gif]、[长图]标记

# How to use 如何使用

XML 文件中添加如下视图

      <me.next.ninepiclib.NinePicViewGroup
              android:id="@+id/nine_pic"
              custom:imgGap="6dp"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:padding="6dp"/>

实现抽象类：NinePicViewAdapter [https://github.com/kingideayou/NinePicView/blob/master/app/src/main/java/me/next/ninepic/NinePicListAdapter.java](https://github.com/kingideayou/NinePicView/blob/master/app/src/main/java/me/next/ninepic/NinePicListAdapter.java)

        private NinePicViewAdapter<String> mAdapter = new NinePicViewAdapter<String>() {
                @Override
                protected void onBindView(Context context, FrameLayout frameLayout, int position, String s) {
                    ImageView imageView = frameLayout.findViewById(R.id.image_test);
                    Glide.with(imageView.getContext()).load("https://tva3.sinaimg.cn/crop.6.0.753.753.180/6db4aff6jw8et9dnyhy7oj20lc0lcabg.jpg").into(imageView);
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

        NinePicViewGroup ninePicViewGroup = findViewById(R.id.nine_pic);
        ninePicViewGroup.setAdapter(mAdapter);
        ninePicViewGroup.setImagesData(imageDataList);


### 效果图

![](http://ww1.sinaimg.cn/mw690/6db4aff6ly1fkt7277qgkj20wg198wl8.jpg)
![](http://ww1.sinaimg.cn/mw690/6db4aff6ly1fkt6zfkpf5g20gv0omu0x.gif)

### 感谢：[https://github.com/laobie/NineGridImageView](https://github.com/laobie/NineGridImageView)

## License

    Copyright 2015 NeXT

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

