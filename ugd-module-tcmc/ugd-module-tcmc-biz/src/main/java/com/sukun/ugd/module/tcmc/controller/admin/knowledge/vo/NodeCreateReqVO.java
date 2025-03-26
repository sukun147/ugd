package com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "节点创建请求")
public class NodeCreateReqVO {
    @Schema(description = "节点名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "中药示例")
    private String name;
}