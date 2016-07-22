package com.sera.dao;

import com.sera.entity.UserInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 用户主表
 * Created by wangqing on 16/7/12.
 */
@Repository
public interface UserInfoMapper {

    public UserInfoEntity getByPassword(Map<String, Object> param);


    public int insert(UserInfoEntity entity);


    public UserInfoEntity getByUserID(long userId);
}
