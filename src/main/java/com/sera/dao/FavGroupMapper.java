package com.sera.dao;

import com.sera.entity.FavGroupEntity;
import org.springframework.stereotype.Repository;

/**
 * 收藏组
 * Created by wangqing on 16/7/12.
 */
@Repository
public interface FavGroupMapper {

    public int insert(FavGroupEntity entity);

    public int delByPk(long id);

    public int insert(FavGroupEntity entity);
}
