package com.sukun.ugd.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import com.sukun.ugd.framework.common.enums.UserTypeEnum;
import com.sukun.ugd.framework.common.pojo.CommonResult;
import com.sukun.ugd.framework.datapermission.core.annotation.DataPermission;
import com.sukun.ugd.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import com.sukun.ugd.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import com.sukun.ugd.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import com.sukun.ugd.module.system.convert.user.UserConvert;
import com.sukun.ugd.module.system.dal.dataobject.permission.RoleDO;
import com.sukun.ugd.module.system.dal.dataobject.user.AdminUserDO;
import com.sukun.ugd.module.system.service.permission.PermissionService;
import com.sukun.ugd.module.system.service.permission.RoleService;
import com.sukun.ugd.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.List;

import static com.sukun.ugd.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.sukun.ugd.framework.common.pojo.CommonResult.success;
import static com.sukun.ugd.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.sukun.ugd.module.infra.enums.ErrorCodeConstants.FILE_IS_EMPTY;

@Tag(name = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class UserProfileController {

    @Resource
    private AdminUserService userService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;

    @GetMapping("/get")
    @Operation(summary = "获得登录用户信息")
    @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public CommonResult<UserProfileRespVO> getUserProfile() {
        // 获得用户基本信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        // 获得用户角色
        List<RoleDO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        return success(UserConvert.INSTANCE.convert(user, userRoles));
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户个人密码")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @RequestMapping(value = "/update-avatar",
            method = {RequestMethod.POST, RequestMethod.PUT}) // 解决 uni-app 不支持 Put 上传文件的问题
    @Operation(summary = "上传用户个人头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }

}
