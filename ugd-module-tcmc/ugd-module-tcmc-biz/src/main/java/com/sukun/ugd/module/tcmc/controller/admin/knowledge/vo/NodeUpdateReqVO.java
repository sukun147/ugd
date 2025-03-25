package com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "节点更新请求")
public class NodeUpdateReqVO {
    @Schema(description = "节点ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
    private Long id;

    @Schema(description = "新节点名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "Java高级编程")
    private String newName;
}
