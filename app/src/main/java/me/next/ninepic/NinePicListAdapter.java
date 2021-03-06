package me.next.ninepic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.next.ninepiclib.NinePicViewAdapter;
import me.next.ninepiclib.NinePicViewGroup;

/**
 * Created by NeXT on 17/10/11.
 */
public class NinePicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<TimeLineBean> mTimeLineList = new ArrayList<>();

    public NinePicListAdapter(List<TimeLineBean> timeLineList) {
        mTimeLineList = timeLineList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeLineViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_nine_pic, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TimeLineViewHolder)holder).onBindViewHolder(mTimeLineList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTimeLineList.size();
    }

    private class TimeLineViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;
        NinePicViewGroup<String, FrameLayout> ninePicViewGroup;

        public TimeLineViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            ninePicViewGroup = itemView.findViewById(R.id.nine_pic);
            ninePicViewGroup.setAdapter(mAdapter);
        }

        public void onBindViewHolder(TimeLineBean timeLineBean) {
            tvContent.setText(timeLineBean.getContent());
            ninePicViewGroup.setImagesData(timeLineBean.getImgList());
        }
    }

    private NinePicViewAdapter<String, FrameLayout> mAdapter = new NinePicViewAdapter<String, FrameLayout>() {
        @Override
        protected void onBindView(Context context, FrameLayout frameLayout, int position, String s) {
            ImageView imageView = frameLayout.findViewById(R.id.image_test);
            if (position % 2 != 0) {
                View ivTag = frameLayout.findViewById(R.id.tag);
                ivTag.setVisibility(ImageView.GONE);
            }
            Glide.with(imageView.getContext()).load(s).into(imageView);

        }

        @Override
        protected void onItemClick(Context context, FrameLayout frameLayout, int index, List<String> list) {
            super.onItemClick(context, frameLayout, index, list);
            Toast.makeText(context, "点击第" + index + "张图片", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected boolean onItemLongClick(Context context, FrameLayout frameLayout, int index, List<String> list) {
            Toast.makeText(context, "长按第" + index + "张图片", Toast.LENGTH_SHORT).show();
            return super.onItemLongClick(context, frameLayout, index, list);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.item_with_tag;
        }

        @Override
        protected int[] getImgWidthHeight(String s) {
            return getImgWidthHeightFromUrl(s);
        }
    };

    private int[] getImgWidthHeightFromUrl(String imgUrl) {
        return new int[]{558, 314};
    }
}
