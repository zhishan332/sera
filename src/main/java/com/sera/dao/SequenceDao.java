package com.sera.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *  sequence获取
 *
 * @author wangqing
 * @since 14-10-30 下午3:02
 */
@Repository
public class SequenceDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Long getSeq(final String tableName,final String seqName) {
        String sql = "replace into "+tableName+" (seq_name) VALUES(?)";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, seqName);
            }
        });
        return jdbcTemplate.queryForLong("select seq from "+tableName+" where seq_name=?", seqName);
    }
}