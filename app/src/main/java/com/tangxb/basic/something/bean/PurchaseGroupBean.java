package com.tangxb.basic.something.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 采购组实体<br>
 * Created by Taxngb on 2017/12/28.
 */

public class PurchaseGroupBean {
    /**
     * id : 1512716132514
     * name : 田胜军
     */
    @Expose
    @SerializedName("id")
    private Long id;
    @Expose
    @SerializedName("name")
    private String nickname;
    /**
     * 是否被选中
     */
    @Expose
    @SerializedName("check")
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
