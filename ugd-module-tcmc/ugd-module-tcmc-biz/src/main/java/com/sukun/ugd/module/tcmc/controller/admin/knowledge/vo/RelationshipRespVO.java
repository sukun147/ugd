package com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "关系响应")
public class RelationshipRespVO {
    @Schema(description = "源节点名称", example = "Java编程")
    private String sourceName;

    @Schema(description = "目标节点名称", example = "Spring框架")
    private String targetName;

    @Schema(description = "关系类型", example = "包含")
    private String relationType;
}