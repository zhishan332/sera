package com.sera.service.impl;

import com.sera.config.SequenceConfig;
import com.sera.dao.FavGroupMapper;
import com.sera.dao.FavListMapper;
import com.sera.entity.FavGroupEntity;
import com.sera.entity.FavListEntity;
import com.sera.helper.IconFinder;
import com.sera.helper.SequenceHelper;
import com.sera.helper.UrlHelper;
import com.sera.service.FavService;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Resource
    private SequenceHelper sequenceHelper;
    @Resource
    private UrlHelper urlHelper;
    @Resource
    private IconFinder iconFinder;

    @Override
    public boolean addFavGroup(FavGroupEntity favGroupEntity) {
        if (favGroupEntity.getGroupId() == null || favGroupEntity.getGroupId() <= 0) {
            favGroupEntity.setGroupId(sequenceHelper.getSeq(SequenceConfig.FAV_GROUP_SEQ));
        }
        favGroupEntity.setCovert(1);
        return favGroupMapper.insert(favGroupEntity) > 0;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean delFavGroup(long userId, long groupId) {
        FavGroupEntity favGroupEntity = new FavGroupEntity();
        favGroupEntity.setUserId(userId);
        favGroupEntity.setGroupId(groupId);
        favGroupMapper.delByUserIdAndId(favGroupEntity);
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setGroupId(groupId);
        favListEntity.setUserId(userId);
        favListMapper.deleteByGroup(favListEntity);
        return true;
    }

    @Override
    public List<FavGroupEntity> findFavGroup(long userId) {
        FavGroupEntity favGroupEntity = new FavGroupEntity();
        favGroupEntity.setUserId(userId);
        return favGroupMapper.find(favGroupEntity);
    }

    @Override
    public boolean protectFavGroup(long userId, long groupId, int type) {
        FavGroupEntity favGroupEntity = new FavGroupEntity();
        favGroupEntity.setUserId(userId);
        favGroupEntity.setGroupId(groupId);
        favGroupEntity.setCovert(type);
        return favGroupMapper.updateForProtect(favGroupEntity) > 0;
    }

    @Override
    public boolean addFav(FavListEntity favListEntity) {
        if (favListEntity.getFavId() == null || favListEntity.getFavId() <= 0) {
            favListEntity.setFavId(sequenceHelper.getSeq(SequenceConfig.FAV_LIST_SEQ));
        }
        favListEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        favListEntity.setFavFocus(1);
        favListEntity.setFavSort(1);
        favListEntity.setFavStatus(1);
        if (StringUtils.isBlank(favListEntity.getGroupName())) {
            favListEntity.setGroupName(favGroupMapper.getByGroupId(favListEntity.getGroupId()).getGroupName());
        }
        if (StringUtils.isBlank(favListEntity.getFavTitle())) {
            favListEntity.setFavTitle(urlHelper.getFormatUrlTitle(favListEntity.getFavUrl()));
        }
        String icon = iconFinder.getIconUrlString(favListEntity.getFavUrl());
        if (!StringUtils.isBlank(icon)) {
            icon = Jsoup.parse(icon).text();
            icon = icon.replaceAll(" ", "");
            icon = icon.replaceAll("\"", "");
        }
        favListEntity.setFavIco(icon);
        return favListMapper.insert(favListEntity) > 0;
    }

    @Override
    public boolean delFav(long userId, long favId) {
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setUserId(userId);
        favListEntity.setFavId(favId);
        return favListMapper.delByUserIdAndId(favListEntity) > 0;
    }

    @Override
    public boolean checkFav(long userId) {
        return false;
    }

    @Override
    public boolean arrange(long userId) {
        return false;
    }

    @Override
    public boolean delExpireFav(long userId) {
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setUserId(userId);
        favListEntity.setFavStatus(2);
        favListMapper.deleteByStatus(favListEntity);
        return true;
    }

    @Override
    public int focusFav(long userId, long favId) {
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setUserId(userId);
        favListEntity.setFavId(favId);
        FavListEntity fav = favListMapper.get(favListEntity);
        if (fav == null) return 0;

        int favFocus = fav.getFavFocus();
        if (1 == favFocus) {
            favFocus = 2;
        } else {
            favFocus = 1;
        }
        favListEntity.setFavFocus(favFocus);
        favListEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int num = favListMapper.updateFocus(favListEntity);
        if (num > 0) {//修改排序
            if(favFocus==2){
                favListMapper.updateHighSort(favListEntity);
            }else{
                favListMapper.updateLowSort(favListEntity);
            }
        }
        return favFocus;
    }

    @Override
    public List<FavListEntity> findByGroup(long userId, long groupId, String searchKey, int start, int num) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("groupId", groupId);
        param.put("searchKey", searchKey);
        param.put("start", start);
        param.put("num", num);
        return favListMapper.find(param);
    }

    @Override
    public boolean updateFavGroup(long userId, long favId, long groupId) {
        FavListEntity favListEntity = new FavListEntity();
        favListEntity.setUserId(userId);
        favListEntity.setGroupId(groupId);
        favListEntity.setFavId(favId);
        favListEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        favListMapper.updateFavGroup(favListEntity);
        return true;
    }
}
