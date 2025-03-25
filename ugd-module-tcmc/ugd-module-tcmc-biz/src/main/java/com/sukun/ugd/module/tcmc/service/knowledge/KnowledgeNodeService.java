package com.sukun.ugd.module.tcmc.service.knowledge;

import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeNode;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface KnowledgeNodeService {
    // 节点基本操作
    KnowledgeNode createNode(String name);
    Optional<KnowledgeNode> findNodeByName(String name);
    Optional<KnowledgeNode> findNodeById(Long id);
    List<KnowledgeNode> findNodesByNameContaining(String name);
    List<KnowledgeNode> findAllNodes();

    // 修改为基于ID的更新
    KnowledgeNode updateNode(Long id, String newName);

    // 修改为基于ID的删除
    void deleteNode(Long id);

    void deleteAllNodes();
    boolean existsByName(String name);
    boolean existsById(Long id);

    // 关系操作保持不变...
    void createRelationship(String sourceName, String targetName, String relationshipType);
    List<Map<String, Object>> findOutgoingRelationships(String name);
    List<Map<String, Object>> findIncomingRelationships(String name);
    List<Map<String, Object>> findAllRelationships();
    String getRelationshipType(String sourceName, String targetName);
    void deleteRelationship(String sourceName, String targetName, String relationshipType);
    void deleteAllRelationshipsBetweenNodes(String sourceName, String targetName);
    void deleteAllRelationshipsOfNode(String name);
    boolean relationshipExists(String sourceName, String targetName, String relationshipType);
}
