package com.sera.service;

import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;

import java.util.List;

/**
 * 收藏夹操作
 * Created by wangqing on 16/7/12.
 */
public interface FavService {

    boolean addFavGroup();

    boolean delFavGroup();

    List<FavGroupEntity> findFavGroup();

    boolean protectFavGroup();

    boolean addFav();

    boolean delFav();

    boolean checkFav();

    boolean focusFav();

    List<FavListEntity> findByGroup(long group, int start, int limit);

}
