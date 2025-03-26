package com.sukun.ugd.module.tcmc.service.knowledge;

import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeNode;
import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeRelationship;

import java.util.List;
import java.util.Optional;

public interface KnowledgeNodeService {
    // 节点基本操作
    KnowledgeNode createNode(String name);
    Optional<KnowledgeNode> findNodeByName(String name);
    List<KnowledgeNode> findNodesByNameContaining(String name);
    List<KnowledgeNode> findAllNodes();

    // 基于名称的更新
    KnowledgeNode updateNode(String oldName, String newName);

    // 基于名称的删除
    void deleteNode(String name);

    void deleteAllNodes();
    boolean existsByName(String name);

    // 关系操作
    void createRelationship(String sourceName, String targetName, String relationshipType);
    List<KnowledgeRelationship> findOutgoingRelationships(String name);
    List<KnowledgeRelationship> findIncomingRelationships(String name);
    List<KnowledgeRelationship> findAllRelationships();
    String getRelationshipType(String sourceName, String targetName);
    void deleteRelationship(String sourceName, String targetName, String relationshipType);
    void deleteAllRelationshipsBetweenNodes(String sourceName, String targetName);
    void deleteAllRelationshipsOfNode(String name);
    boolean relationshipExists(String sourceName, String targetName, String relationshipType);
}