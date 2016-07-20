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

    boolean delFavGroup(long userId, long groupId);

    List<FavGroupEntity> findFavGroup(long userId);

    boolean protectFavGroup(long userId, long groupId, int type);

    boolean addFav(FavListEntity favListEntity);

    boolean delFav(long userId, long favId);

    boolean checkFav(long userId);

    boolean arrange(long userId);

    boolean delExpireFav(long userId);

    boolean focusFav(long userId, long favId, int type);

    List<FavListEntity> findByGroup(long userId, long groupId, String searchKey, int start, int num);

}
