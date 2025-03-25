package com.sukun.ugd.module.tcmc.controller.app.clientuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;

@Schema(description = "用户 APP - 问诊用户注册 Request VO")
@Data
public class AppClientUserRegisterReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "sukun")
    @NotEmpty(message = "用户名称不能为空")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "1477264431@qq.com")
    @NotEmpty(message = "邮箱不能为空")
    private String email;

}