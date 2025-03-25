package com.sukun.ugd.module.tcmc.controller.app.clientuser.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户 APP - 更新用户会话 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppClientUserUpdateSessionReqVO {

    @Schema(description = "会话编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "标题")
    private String title;
}
