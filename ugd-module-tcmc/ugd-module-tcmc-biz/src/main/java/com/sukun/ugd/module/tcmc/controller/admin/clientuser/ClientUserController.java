package com.sukun.ugd.module.tcmc.controller.admin.clientuser;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;

import com.sukun.ugd.framework.common.pojo.PageParam;
import com.sukun.ugd.framework.common.pojo.PageResult;
import com.sukun.ugd.framework.common.pojo.CommonResult;
import com.sukun.ugd.framework.common.util.object.BeanUtils;
import static com.sukun.ugd.framework.common.pojo.CommonResult.success;

import com.sukun.ugd.module.tcmc.controller.admin.clientuser.vo.*;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.ClientUserDO;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.SessionDO;
import com.sukun.ugd.module.tcmc.service.clientuser.ClientUserService;

@Tag(name = "管理后台 - 问诊用户")
@RestController
@RequestMapping("/tcmc/client-user")
@Validated
public class ClientUserController {

    @Resource
    private ClientUserService clientUserService;

    @GetMapping("/page")
    @Operation(summary = "获得问诊用户分页")
    @PreAuthorize("@ss.hasPermission('tcmc:client-user:query')")
    public CommonResult<PageResult<ClientUserRespVO>> getClientUserPage(@Valid ClientUserPageReqVO pageReqVO) {
        PageResult<ClientUserDO> pageResult = clientUserService.getClientUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ClientUserRespVO.class));
    }

    // ==================== 子表（用户会话） ====================

    @GetMapping("/session/page")
    @Operation(summary = "获得用户会话分页")
    @Parameter(name = "userId", description = "用户id")
    @PreAuthorize("@ss.hasPermission('tcmc:client-user:query')")
    public CommonResult<PageResult<SessionDO>> getSessionPage(PageParam pageReqVO, @RequestParam("userId") Long userId) {
        return success(clientUserService.getSessionPage(pageReqVO, userId));
    }

}