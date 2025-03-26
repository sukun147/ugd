package com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "节点响应")
public class NodeRespVO {
    @Schema(description = "节点名称", example = "Java编程")
    private String name;
}