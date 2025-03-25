package com.sukun.ugd.module.tcmc.dal.mysql.clientuser;

import java.util.*;

import com.sukun.ugd.framework.common.pojo.PageResult;
import com.sukun.ugd.framework.common.pojo.PageParam;
import com.sukun.ugd.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.sukun.ugd.framework.mybatis.core.mapper.BaseMapperX;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.SessionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户会话 Mapper
 *
 * @author sukun
 */
@Mapper
public interface SessionMapper extends BaseMapperX<SessionDO> {

    default PageResult<SessionDO> selectPage(PageParam reqVO, Long userId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SessionDO>()
            .eq(SessionDO::getUserId, userId)
            .orderByDesc(SessionDO::getId));
    }

}
