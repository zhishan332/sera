package com.sera.helper;

import com.sera.dao.SequenceDao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 获取增长的序列值
 * Created by wangqing on 16/7/12.
 */
@Component
public class SequenceHelper {
    @Resource
    private SequenceDao sequenceDao;



    public long getSeq(String name) {
        return sequenceDao.getSeq("sequence",name);
    }
}
