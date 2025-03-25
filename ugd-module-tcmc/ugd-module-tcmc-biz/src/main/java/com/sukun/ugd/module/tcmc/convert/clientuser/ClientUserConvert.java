package com.sukun.ugd.module.tcmc.convert.clientuser;

import com.sukun.ugd.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.sukun.ugd.module.tcmc.controller.app.clientuser.vo.AppClientUserLoginRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientUserConvert {
    ClientUserConvert INSTANCE = Mappers.getMapper(ClientUserConvert.class);

    AppClientUserLoginRespVO convert(OAuth2AccessTokenRespDTO accessTokenRespDTO);
}
