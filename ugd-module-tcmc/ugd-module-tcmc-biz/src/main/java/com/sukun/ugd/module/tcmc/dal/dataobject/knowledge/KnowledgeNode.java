package com.sukun.ugd.module.tcmc.dal.dataobject.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "知识图谱节点")
public class KnowledgeNode {
    // Neo4j自动生成ID
    @Id
    @GeneratedValue
    @Schema(description = "节点ID，由Neo4j自动生成", example = "1234")
    private Long id;

    @Schema(description = "节点名称", example = "Java编程")
    private String name;

    public KnowledgeNode(String name) {
        this.name = name;
    }
}
