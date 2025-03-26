package com.sukun.ugd.module.tcmc.dal.dataobject.knowledge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeRelationship {
    private String sourceName;
    private String targetName;
    private String relationType;
}
