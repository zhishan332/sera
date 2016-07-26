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

    boolean addCheckFavTask(long userId);

    boolean checkFav(long userId);

    FavListEntity isDuplicate(long userId,String url);

    boolean arrange(long userId);

    boolean delExpireFav(long userId);

    int focusFav(long userId, long favId);

    List<FavListEntity> findByGroup(long userId, long groupId, String searchKey, int start, int num);

    public boolean updateFavGroup(long userId, long favId,long groupId);

}
