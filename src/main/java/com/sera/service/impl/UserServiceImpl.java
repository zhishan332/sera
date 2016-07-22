package com.sera.service.impl;

import com.sera.config.SequenceConfig;
import com.sera.dao.UserInfoMapper;
import com.sera.entity.UserInfoEntity;
import com.sera.helper.MD5Helper;
import com.sera.helper.SequenceHelper;
import com.sera.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户
 * Created by wangqing on 16/7/22.
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private MD5Helper md5Helper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Value("#{config['user.login.token']}")
    private String token;
    @Resource
    private SequenceHelper sequenceHelper;


    @Override
    public UserInfoEntity login(String userName, String password) {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            return null;
        }
        password = getPassword(userName, password);
        Map<String, Object> param = new HashMap<>();
        param.put("userName", userName);
        param.put("password", password);

        return userInfoMapper.getByPassword(param);
    }

    @Override
    public UserInfoEntity regist(UserInfoEntity userInfoEntity) {
        if (userInfoEntity.getUserId() == null || userInfoEntity.getUserId() <= 0) {
            userInfoEntity.setUserId(sequenceHelper.getSeq(SequenceConfig.USER_SEQ));
        }
        userInfoEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userInfoEntity.setPassword(getPassword(userInfoEntity.getUserName(),userInfoEntity.getPassword()));
        int num = userInfoMapper.insert(userInfoEntity);

        if (num > 0) {
            return userInfoMapper.getByUserID(userInfoEntity.getUserId());
        }

        return null;
    }

    private String getPassword(String userName, String password) {

        return md5Helper.string2MD5(userName + "_" + token + "_" + password);
    }

}
