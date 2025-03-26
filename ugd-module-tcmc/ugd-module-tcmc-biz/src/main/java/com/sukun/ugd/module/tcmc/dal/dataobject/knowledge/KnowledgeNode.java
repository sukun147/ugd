package com.sukun.ugd.module.tcmc.dal.dataobject.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("中药")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "知识图谱节点")
public class KnowledgeNode {
    @Id
    @GeneratedValue
    @Schema(description = "节点ID", example = "4:6a28ec50-aa0d-47c4-abed-d533e83f9b67:37")
    private String id;

    @Schema(description = "节点名称", example = "Java编程")
    private String name;

    public KnowledgeNode(String name) {
        this.name = name;
    }
}
