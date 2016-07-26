package com.sera.dao;

import com.sera.entity.WorkTaskEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务表
 *
 * @author wangqing
 * @since 14-11-23 下午2:10
 */
@Repository
public interface WorkTaskMapper {

    public int insertTask(WorkTaskEntity workTaskEntity);

    public List<WorkTaskEntity> findTask(@Param(value = "sqlCon") String sqlCon);

    public WorkTaskEntity getTaskByKeyA(String keyA);

    public WorkTaskEntity getTaskByRefId(String refId);

    public int deleteById(long id);

    public int updateTask(WorkTaskEntity workTaskEntity);

    public int updateAddExeNum(long id);

    public int updateToRun(long id);
}
