package com.sukun.ugd.module.tcmc.service.clientuser;

import com.sukun.ugd.framework.common.pojo.PageParam;
import com.sukun.ugd.framework.common.pojo.PageResult;
import com.sukun.ugd.framework.common.util.object.BeanUtils;
import com.sukun.ugd.module.system.api.oauth2.OAuth2TokenApi;
import com.sukun.ugd.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import com.sukun.ugd.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.sukun.ugd.module.system.enums.oauth2.OAuth2ClientConstants;
import com.sukun.ugd.module.tcmc.controller.admin.clientuser.vo.ClientUserPageReqVO;
import com.sukun.ugd.module.tcmc.controller.app.clientuser.vo.*;
import com.sukun.ugd.module.tcmc.convert.clientuser.ClientUserConvert;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.ClientUserDO;
import com.sukun.ugd.module.tcmc.dal.dataobject.clientuser.SessionDO;
import com.sukun.ugd.module.tcmc.dal.mysql.clientuser.ClientUserMapper;
import com.sukun.ugd.module.tcmc.dal.mysql.clientuser.SessionMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

import static com.sukun.ugd.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.sukun.ugd.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.sukun.ugd.module.tcmc.enums.ErrorCodeConstants.*;

/**
 * 问诊用户 Service 实现类
 *
 * @author sukun
 */
@Service
@Validated
public class ClientUserServiceImpl implements ClientUserService {

    @Resource
    private ClientUserMapper clientUserMapper;
    @Resource
    private SessionMapper sessionMapper;
    @Resource
    private OAuth2TokenApi oauth2TokenApi;

    @Override
    public PageResult<ClientUserDO> getClientUserPage(ClientUserPageReqVO pageReqVO) {
        return clientUserMapper.selectPage(pageReqVO);
    }

    @Override
    public Long register(AppClientUserRegisterReqVO registerReqVO) {
        // 密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        registerReqVO.setPassword(encoder.encode(registerReqVO.getPassword()));
        // 插入
        ClientUserDO clientUser = BeanUtils.toBean(registerReqVO, ClientUserDO.class);
        clientUserMapper.insert(clientUser);
        // 返回
        return clientUser.getId();
    }

    @Override
    public AppClientUserLoginRespVO login(AppClientUserLoginReqVO loginReqVO) {
        // 查询用户
        ClientUserDO clientUser = clientUserMapper.selectOne(ClientUserDO::getUsername, loginReqVO.getUsername());
        if (clientUser == null) {
            throw exception(CLIENT_USER_NOT_EXISTS);
        }
        // 校验密码
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(loginReqVO.getPassword(), clientUser.getPassword())) {
            throw exception(CLIENT_USER_ERROR_PASSWORD);
        }
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.createAccessToken(new OAuth2AccessTokenCreateReqDTO()
                .setUserId(clientUser.getId()).setUserType(1)
                .setClientId(OAuth2ClientConstants.CLIENT_ID_DEFAULT));
        return ClientUserConvert.INSTANCE.convert(accessTokenRespDTO);
    }

    @Override
    public void logout(String token) {
        // 删除访问令牌
        oauth2TokenApi.removeAccessToken(token);
    }

    @Override
    public AppClientUserLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenRespDTO accessTokenDO = oauth2TokenApi.refreshAccessToken(refreshToken,
                OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return ClientUserConvert.INSTANCE.convert(accessTokenDO);
    }

    @Override
    public void updateClientUser(AppClientUserUpdateReqVO updateReqVO) {
        // 校验存在
        ClientUserDO user = validateClientUserExists(getLoginUserId());
        updateReqVO.setId(user.getId());
        // 更新
        ClientUserDO updateObj = BeanUtils.toBean(updateReqVO, ClientUserDO.class);
        clientUserMapper.updateById(updateObj);
    }

    @Override
    public void updateClientUserPassword(AppClientUserUpdatePasswordReqVO updateReqVO) {
        // 检查密码
        ClientUserDO clientUser = validateClientUserExists(getLoginUserId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(updateReqVO.getOldPassword(), clientUser.getPassword())) {
            throw exception(CLIENT_USER_ERROR_PASSWORD);
        }
        // 更新
        clientUserMapper.updateById(ClientUserDO.builder().id(clientUser.getId()).password(encoder.encode(updateReqVO.getNewPassword())).build());
    }

    private ClientUserDO validateClientUserExists(Long id) {
        ClientUserDO user = clientUserMapper.selectById(id);
        if (user == null) {
            throw exception(CLIENT_USER_NOT_EXISTS);
        }
        return user;
    }

    @Override
    public ClientUserDO getClientUser(Long id) {
        return clientUserMapper.selectById(id).setPassword(null);
    }

    // ==================== 子表（用户会话） ====================

    @Override
    public PageResult<SessionDO> getSessionPage(PageParam pageReqVO, Long userId) {
        return sessionMapper.selectPage(pageReqVO, userId);
    }

    @Override
    public SessionDO getSession(Long sessionId) {
        return validateSessionExists(sessionId);
    }

    @Override
    public void updateSession(AppClientUserUpdateSessionReqVO updateReqVO) {
        SessionDO sessionDO = validateSessionExists(updateReqVO.getId());
        if (!Objects.equals(sessionDO.getUserId(), getLoginUserId())) {
            throw exception(SESSION_NOT_BELONG_TO_USER);
        }
        sessionMapper.updateById(BeanUtils.toBean(updateReqVO, SessionDO.class));
    }

    @Override
    public Long createSession(String title) {
        SessionDO sessionDO = new SessionDO();
        sessionDO.setTitle(title);
        sessionDO.setUserId(getLoginUserId());
        sessionMapper.insert(sessionDO);
        return sessionDO.getId();
    }

    @Override
    public void deleteSession(Long sessionId) {
        SessionDO sessionDO = validateSessionExists(sessionId);
        if (!Objects.equals(sessionDO.getUserId(), getLoginUserId())) {
            throw exception(SESSION_NOT_BELONG_TO_USER);
        }
        sessionMapper.deleteById(sessionId);
    }

    @Override
    public SessionDO validateSessionExists(Long id) {
        SessionDO session = sessionMapper.selectById(id);
        if (session == null) {
            throw exception(SESSION_NOT_EXISTS);
        }
        return session;
    }

}