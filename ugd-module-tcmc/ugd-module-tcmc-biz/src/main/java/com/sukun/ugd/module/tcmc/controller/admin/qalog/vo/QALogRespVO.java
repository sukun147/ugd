package com.sukun.ugd.module.tcmc.controller.admin.qalog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 问答日志 Response VO")
@Data
public class QALogRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "会话id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long sessionId;

    @Schema(description = "问题", requiredMode = Schema.RequiredMode.REQUIRED, example = "Q")
    private String question;

    @Schema(description = "答案", requiredMode = Schema.RequiredMode.REQUIRED, example = "A")
    private String answer;

}