package com.tangxb.basic.something.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 通知实体
 * Created by yy520 on 2017-12-2.
 */
public class NoticeBean {
    @Expose
    @SerializedName("id")
    private Long id;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("create_time")
    private String create_time;
    @Expose
    @SerializedName("isShow")
    private Integer isShow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public boolean isNeedShow() {
        return isShow == 1;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
}
