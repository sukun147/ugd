package com.sukun.ugd.module.tcmc.controller.app.clientuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户 APP - 密码登录 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppClientUserLoginReqVO {
    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "sukun")
    @NotEmpty(message = "用户名称不能为空")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotEmpty(message = "密码不能为空")
    private String password;
}
