package com.sukun.ugd.module.tcmc.controller.app.qa.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户APP - 问答日志 Response VO")
@Data
public class QALogSaveReqVO {

    @Schema(description = "会话id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long sessionId;

    @Schema(description = "问题", requiredMode = Schema.RequiredMode.REQUIRED, example = "Q")
    private String question;
}
