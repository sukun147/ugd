package com.sukun.ugd.module.tcmc.controller.app.clientuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 APP - 问诊用户修改密码 Request VO")
@Data
public class AppClientUserUpdatePasswordReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED,  example = "1")
    private Long id;

    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED,  example = "123456")
    private String oldPassword;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED,  example = "123456")
    private String newPassword;
}
