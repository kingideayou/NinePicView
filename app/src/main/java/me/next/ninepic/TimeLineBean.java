package me.next.ninepic;

import java.util.List;

/**
 * Created by NeXT on 17/10/11.
 */

public class TimeLineBean {

    public TimeLineBean(String content, List<String> imgList) {
        this.content = content;
        this.imgList = imgList;
    }

    private String content;
    private List<String> imgList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
