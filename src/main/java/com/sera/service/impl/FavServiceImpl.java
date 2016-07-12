package com.sera.service.impl;

import com.sera.dao.FavGroupMapper;
import com.sera.dao.FavListMapper;
import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;
import com.sera.service.FavService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 收藏夹操作
 * Created by wangqing on 16/7/12.
 */
@Service
public class FavServiceImpl implements FavService {
    @Resource
    private FavListMapper favListMapper;
    @Resource
    private FavGroupMapper favGroupMapper;

    @Override
    public boolean addFavGroup(FavGroupEntity favGroupEntity) {
        return false;
    }

    @Override
    public boolean delFavGroup(long groupId) {
        return false;
    }

    @Override
    public List<FavGroupEntity> findFavGroup(long userId) {
        return null;
    }

    @Override
    public boolean protectFavGroup(long groupId, int type) {
        return false;
    }

    @Override
    public boolean addFav(FavListEntity favListEntity) {
        return false;
    }

    @Override
    public boolean delFav(long favId) {
        return false;
    }

    @Override
    public boolean checkFav(long userId) {
        return false;
    }

    @Override
    public boolean focusFav(long favId) {
        return false;
    }

    @Override
    public List<FavListEntity> findByGroup(long groupId, int start, int limit) {
        return null;
    }
}
