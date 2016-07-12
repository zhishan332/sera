package com.sera.service;

import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;

import java.util.List;

/**
 * 收藏夹操作
 * Created by wangqing on 16/7/12.
 */
public interface FavService {

    boolean addFavGroup(FavGroupEntity favGroupEntity);

    boolean delFavGroup(long groupId);

    List<FavGroupEntity> findFavGroup(long userId);

    boolean protectFavGroup(long groupId, int type);

    boolean addFav(FavListEntity favListEntity);

    boolean delFav(long favId);

    boolean checkFav(long userId);

    boolean focusFav(long favId);

    List<FavListEntity> findByGroup(long groupId, int start, int limit);

}
