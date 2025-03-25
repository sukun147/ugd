package com.sukun.ugd.module.tcmc.dal.mysql.qalog;

import com.sukun.ugd.framework.mybatis.core.mapper.BaseMapperX;
import com.sukun.ugd.module.tcmc.dal.dataobject.qalog.QALogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 问答日志 Mapper
 *
 * @author sukun
 */
@Mapper
public interface QALogMapper extends BaseMapperX<QALogDO> {

    default List<QALogDO> selectList(Long sessionId) {
        return selectList(QALogDO::getSessionId, sessionId);
    }

}