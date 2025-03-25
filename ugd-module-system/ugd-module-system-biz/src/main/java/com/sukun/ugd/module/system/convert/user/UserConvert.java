package com.sukun.ugd.module.system.convert.user;

import com.sukun.ugd.framework.common.util.collection.CollectionUtils;
import com.sukun.ugd.framework.common.util.object.BeanUtils;
import com.sukun.ugd.module.system.controller.admin.permission.vo.role.RoleSimpleRespVO;
import com.sukun.ugd.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import com.sukun.ugd.module.system.controller.admin.user.vo.user.UserRespVO;
import com.sukun.ugd.module.system.controller.admin.user.vo.user.UserSimpleRespVO;
import com.sukun.ugd.module.system.dal.dataobject.permission.RoleDO;
import com.sukun.ugd.module.system.dal.dataobject.user.AdminUserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    default List<UserRespVO> convertList(List<AdminUserDO> list) {
        return CollectionUtils.convertList(list, this::convert);
    }

    default UserRespVO convert(AdminUserDO user) {
        return BeanUtils.toBean(user, UserRespVO.class);
    }

    default List<UserSimpleRespVO> convertSimpleList(List<AdminUserDO> list) {
        return CollectionUtils.convertList(list, user -> BeanUtils.toBean(user, UserSimpleRespVO.class));
    }

    default UserProfileRespVO convert(AdminUserDO user, List<RoleDO> userRoles) {
        UserProfileRespVO userVO = BeanUtils.toBean(user, UserProfileRespVO.class);
        userVO.setRoles(BeanUtils.toBean(userRoles, RoleSimpleRespVO.class));
        return userVO;
    }

}
