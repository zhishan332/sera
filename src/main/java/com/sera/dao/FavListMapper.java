package com.sera.dao;

import com.sera.entity.FavListEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 收藏列表
 * Created by wangqing on 16/7/12.
 */
@Repository
public interface FavListMapper {

    public int insert(FavListEntity favListEntity);

    public FavListEntity get(FavListEntity favListEntity);

    public FavListEntity getByUrl(FavListEntity favListEntity);

    public int delByUserIdAndId(FavListEntity favListEntity);

    public int deleteByGroup(FavListEntity favListEntity);

    public int deleteByStatus(FavListEntity favListEntity);

    public List<FavListEntity> find(Map<String,Object> param);

    public List<FavListEntity> findSimple(Map<String,Object> param);

    public long findTotal(Map<String,Object> param);

    public int updateFocus(FavListEntity favListEntity);

    public int updateHighSort(FavListEntity favListEntity);

    public int updateLowSort(FavListEntity favListEntity);

    public int updateFavGroup(FavListEntity favListEntity);

    public int updateStatus(FavListEntity favListEntity);

    public int updateTitle(FavListEntity favListEntity);
}
