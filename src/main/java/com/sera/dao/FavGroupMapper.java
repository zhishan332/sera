package com.sera.dao;

import com.sera.entity.FavGroupEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 收藏组
 * Created by wangqing on 16/7/12.
 */
@Repository
public interface FavGroupMapper {

    public int insert(FavGroupEntity entity);

    public int delByUserIdAndId(FavGroupEntity entity);

    public int updateForProtect(FavGroupEntity entity);

    FavGroupEntity getByGroupId(long groupId);

    public List<FavGroupEntity> find(FavGroupEntity entity);

    public List<FavGroupEntity> findForDiscovery(FavGroupEntity entity);
}
