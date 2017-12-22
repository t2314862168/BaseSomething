package com.tangxb.basic.something.mvp.model;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Tangxb on 2016/12/13.
 */

public interface BaseModel<T> {
    Observable<List<T>> getCommonList(String category, int pageSize, int pageNum);
}
