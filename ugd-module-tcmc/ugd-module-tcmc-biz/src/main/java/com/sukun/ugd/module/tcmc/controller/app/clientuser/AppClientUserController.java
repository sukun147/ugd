package com.sukun.ugd.module.tcmc.controller.app.clientuser;

import cn.hutool.core.util.StrUtil;
import com.sukun.ugd.framework.common.pojo.PageParam;
import com.sukun.ugd.framework.common.pojo.PageResult;
import com.sukun.ugd.framework.security.config.SecurityProperties;
import com.sukun.ugd.framework.security.core.util.SecurityFrameworkUtils;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.SessionDO;
import com.sukun.ugd.module.tcmc.dal.dataobject.qalog.QALogDO;
import com.sukun.ugd.module.tcmc.service.qalog.QALogService;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.*;

import com.sukun.ugd.framework.common.pojo.CommonResult;
import com.sukun.ugd.framework.common.util.object.BeanUtils;
import static com.sukun.ugd.framework.common.pojo.CommonResult.success;
import static com.sukun.ugd.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import com.sukun.ugd.module.tcmc.controller.app.clientuser.vo.*;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.ClientUserDO;
import com.sukun.ugd.module.tcmc.service.clientuser.ClientUserService;

import java.util.List;

@Tag(name = "用户 APP - 问诊用户个人中心")
@RestController
@RequestMapping("/tcmc/client-user")
@Validated
public class AppClientUserController {

    @Resource
    private ClientUserService clientUserService;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private QALogService qALogService;

    @PostMapping("/register")
    @Operation(summary = "注册问诊用户")
    @PermitAll
    public CommonResult<Long> registerClientUser(@Valid @RequestBody AppClientUserRegisterReqVO createReqVO) {
        return success(clientUserService.register(createReqVO));
    }

    @PostMapping("/login")
    @Operation(summary = "登录问诊用户")
    @PermitAll
    public CommonResult<AppClientUserLoginRespVO> loginClientUser(@Valid @RequestBody AppClientUserLoginReqVO loginReqVO) {
        AppClientUserLoginRespVO respVo = clientUserService.login(loginReqVO);
        return success(respVo);
    }

    @GetMapping("/logout")
    @Operation(summary = "登出问诊用户")
    @PermitAll
    public CommonResult<Boolean> logoutClientUser(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotBlank(token)) {
            clientUserService.logout(token);
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌", required = true)
    @PermitAll
    public CommonResult<AppClientUserLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(clientUserService.refreshToken(refreshToken));
    }

    @PutMapping("/update")
    @Operation(summary = "更新问诊用户个人信息")
    public CommonResult<Boolean> updateClientUser(@Valid @RequestBody AppClientUserUpdateReqVO updateReqVO) {
        clientUserService.updateClientUser(updateReqVO);
        return success(true);
    }

    @PostMapping("/updatePassword")
    @Operation(summary = "更新问诊用户密码")
    public CommonResult<Boolean> updateClientUserPassword(@Valid @RequestBody AppClientUserUpdatePasswordReqVO updateReqVO) {
        clientUserService.updateClientUserPassword(updateReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得问诊用户")
    public CommonResult<AppClientUserRespVO> getClientUser() {
        ClientUserDO clientUser = clientUserService.getClientUser(getLoginUserId());
        return success(BeanUtils.toBean(clientUser, AppClientUserRespVO.class));
    }

    // ==================== 子表（用户会话） ====================

    @GetMapping("/session/page")
    @Operation(summary = "获得用户会话分页")
    public CommonResult<PageResult<SessionDO>> getSessionPage(PageParam pageReqVO) {
        return success(clientUserService.getSessionPage(pageReqVO, getLoginUserId()));
    }

    @GetMapping("/session/get")
    @Operation(summary = "获得用户会话")
    @Parameter(name = "sessionId", description = "会话编号", required = true, example = "1")
    public CommonResult<SessionDO> getSession(@RequestParam("sessionId") Long sessionId) {
        return success(clientUserService.getSession(sessionId));
    }

    @GetMapping("/session/detail")
    @Operation(summary = "获得用户会话下的问答列表")
    @Parameter(name = "sessionId", description = "会话编号", required = true, example = "1")
    public CommonResult<List<QALogDO>> getSessionQALogList(@RequestParam("sessionId") Long sessionId) {
        return success(qALogService.getQALogList(sessionId));
    }

    @PostMapping("/session/update")
    @Operation(summary = "更新用户会话")
    public CommonResult<Boolean> updateSession(@Valid @RequestBody AppClientUserUpdateSessionReqVO updateReqVO) {
        clientUserService.updateSession(updateReqVO);
        return success(true);
    }

    @GetMapping("/session/delete")
    @Operation(summary = "删除用户会话")
    @Parameter(name = "sessionId", description = "会话编号", required = true, example = "1")
    public CommonResult<Boolean> deleteSession(@RequestParam("sessionId") Long sessionId) {
        clientUserService.deleteSession(sessionId);
        return success(true);
    }
}