package com.sera.service;

import com.sera.entity.UserInfoEntity;

/**
 * 用户服务
 * Created by wangqing on 16/7/22.
 */
public interface UserService {

    public UserInfoEntity login(String userName, String password);


    public UserInfoEntity regist(UserInfoEntity userInfoEntity);


}
