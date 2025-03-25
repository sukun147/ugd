package com.sukun.ugd.module.tcmc.service.clientuser;

import com.sukun.ugd.module.tcmc.controller.admin.clientuser.vo.*;
import com.sukun.ugd.module.tcmc.controller.app.clientuser.vo.*;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.ClientUserDO;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.SessionDO;
import com.sukun.ugd.framework.common.pojo.PageResult;
import com.sukun.ugd.framework.common.pojo.PageParam;
import jakarta.validation.Valid;

/**
 * 问诊用户 Service 接口
 *
 * @author sukun
 */
public interface ClientUserService {

    /**
     * 获得问诊用户分页
     *
     * @param pageReqVO 分页查询
     * @return 问诊用户分页
     */
    PageResult<ClientUserDO> getClientUserPage(ClientUserPageReqVO pageReqVO);

    /**
     * 注册问诊用户
     *
     * @param registerReqVO 注册问诊用户
     * @return 用户编号
     */
    Long register(AppClientUserRegisterReqVO registerReqVO);

    /**
     * 登录问诊用户
     *
     * @param loginReqVO 登录问诊用户
     * @return 用户令牌
     */
    AppClientUserLoginRespVO login(@Valid AppClientUserLoginReqVO loginReqVO);

    /**
     * 登出问诊用户
     *
     * @param token 令牌
     */
    void logout(String token);

    /**
     * 刷新令牌
     *
     * @param refreshToken 刷新令牌
     * @return 用户令牌
     */
    AppClientUserLoginRespVO refreshToken(String refreshToken);

    /**
     * 更新问诊用户
     *
     * @param updateReqVO 更新问诊用户
     */
    void updateClientUser(AppClientUserUpdateReqVO updateReqVO);

    /**
     * 更新问诊用户密码
     *
     * @param updateReqVO 更新问诊用户密码
     */
    void updateClientUserPassword(@Valid AppClientUserUpdatePasswordReqVO updateReqVO);

    /**
     * 登录问诊用户
     * @param id 用户id
     * @return 问诊用户
     */
    ClientUserDO getClientUser(Long id);

    // ==================== 子表（用户会话） ====================

    /**
     * 获得用户会话分页
     *
     * @param pageReqVO 分页查询
     * @param userId 用户id
     * @return 用户会话分页
     */
    PageResult<SessionDO> getSessionPage(PageParam pageReqVO, Long userId);

    /**
     * 获得用户会话
     *
     * @param sessionId 会话编号
     * @return 用户会话
     */
    SessionDO getSession(Long sessionId);

    /**
     * 更新用户会话
     *
     * @param updateReqVO 更新用户会话
     */
    void updateSession(AppClientUserUpdateSessionReqVO updateReqVO);

    /**
     * 创建会话
     *
     * @param title 会话标题
     * @return 会话编号
     */
    Long createSession(String title);

    /**
     * 删除会话
     *
     * @param sessionId 会话编号
     */
    void deleteSession(Long sessionId);

    /**
     * 检查会话是否存在
     *
     * @param id 会话编号
     * @return 是否存在
     */
    SessionDO validateSessionExists(Long id);
}