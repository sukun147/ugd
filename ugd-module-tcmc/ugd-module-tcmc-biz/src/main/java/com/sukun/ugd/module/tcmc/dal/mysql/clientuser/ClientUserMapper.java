package com.sukun.ugd.module.tcmc.dal.mysql.clientuser;

import com.sukun.ugd.framework.common.pojo.PageResult;
import com.sukun.ugd.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.sukun.ugd.framework.mybatis.core.mapper.BaseMapperX;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.ClientUserDO;
import org.apache.ibatis.annotations.Mapper;
import com.sukun.ugd.module.tcmc.controller.admin.clientuser.vo.*;

/**
 * 问诊用户 Mapper
 *
 * @author sukun
 */
@Mapper
public interface ClientUserMapper extends BaseMapperX<ClientUserDO> {

    default PageResult<ClientUserDO> selectPage(ClientUserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ClientUserDO>()
                .likeIfPresent(ClientUserDO::getUsername, reqVO.getUsername())
                .likeIfPresent(ClientUserDO::getEmail, reqVO.getEmail())
                .betweenIfPresent(ClientUserDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ClientUserDO::getId));
    }

}