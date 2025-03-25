package com.sukun.ugd.module.tcmc.dal.neo4j;

import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface KnowledgeNodeRepository extends Neo4jRepository<KnowledgeNode, Long> {

    // 基于名称查询节点（保留）
    Optional<KnowledgeNode> findByName(String name);

    // 基于名称模糊查询节点
    List<KnowledgeNode> findByNameContaining(String name);

    // 根据 ID 查询节点
    @Query("MATCH (n:KnowledgeNode) WHERE ID(n) = $id RETURN n")
    Optional<KnowledgeNode> findByNodeId(@Param("id") Long id);

    // 根据 ID 更新节点名称
    @Query("MATCH (n:KnowledgeNode) WHERE ID(n) = $id SET n.name = $newName RETURN n")
    KnowledgeNode updateNodeName(@Param("id") Long id, @Param("newName") String newName);

    // 根据 ID 删除节点
    @Query("MATCH (n:KnowledgeNode) WHERE ID(n) = $id DETACH DELETE n")
    void deleteByNodeId(@Param("id") Long id);

    // 创建关系
    @Query("MATCH (a:KnowledgeNode), (b:KnowledgeNode) " +
            "WHERE a.name = $sourceName AND b.name = $targetName " +
            "CREATE (a)-[r:$relationshipType]->(b)")
    void createRelationship(@Param("sourceName") String sourceName,
                            @Param("targetName") String targetName,
                            @Param("relationshipType") String relationshipType);

    // 查找与指定节点有出向关系的节点
    @Query("MATCH (n:KnowledgeNode {name: $name})-[r]->(related) " +
            "RETURN related.name as targetName, type(r) as relationType")
    List<Map<String, Object>> findOutgoingRelationships(@Param("name") String name);

    // 查找与指定节点有入向关系的节点
    @Query("MATCH (related)-[r]->(n:KnowledgeNode {name: $name}) " +
            "RETURN related.name as sourceName, type(r) as relationType")
    List<Map<String, Object>> findIncomingRelationships(@Param("name") String name);

    // 查找所有关系
    @Query("MATCH (a:KnowledgeNode)-[r]->(b:KnowledgeNode) " +
            "RETURN ID(a) as sourceId, a.name as sourceName, " +
            "ID(b) as targetId, b.name as targetName, " +
            "type(r) as relationType")
    List<Map<String, Object>> findAllRelationships();

    // 获取节点之间的关系类型
    @Query("MATCH (a:KnowledgeNode {name: $sourceName})-[r]->(b:KnowledgeNode {name: $targetName}) " +
            "RETURN type(r) as relationType")
    String getRelationshipType(@Param("sourceName") String sourceName, @Param("targetName") String targetName);

    // 删除指定关系
    @Query("MATCH (a:KnowledgeNode {name: $sourceName})-[r:$relationshipType]->(b:KnowledgeNode {name: $targetName}) " +
            "DELETE r")
    void deleteRelationship(@Param("sourceName") String sourceName,
                            @Param("targetName") String targetName,
                            @Param("relationshipType") String relationshipType);

    // 删除两个节点之间的所有关系
    @Query("MATCH (a:KnowledgeNode {name: $sourceName})-[r]->(b:KnowledgeNode {name: $targetName}) " +
            "DELETE r")
    void deleteAllRelationshipsBetweenNodes(@Param("sourceName") String sourceName,
                                            @Param("targetName") String targetName);

    // 删除节点的所有关系
    @Query("MATCH (n:KnowledgeNode {name: $name})-[r]-() " +
            "DELETE r")
    void deleteAllRelationshipsOfNode(@Param("name") String name);

    // 检查节点是否存在
    @Query("MATCH (n:KnowledgeNode {name: $name}) RETURN count(n) > 0")
    boolean existsByName(@Param("name") String name);

    // 检查关系是否存在
    @Query("MATCH (a:KnowledgeNode {name: $sourceName})-[r:$relationshipType]->(b:KnowledgeNode {name: $targetName}) " +
            "RETURN count(r) > 0")
    boolean relationshipExists(@Param("sourceName") String sourceName,
                               @Param("targetName") String targetName,
                               @Param("relationshipType") String relationshipType);
}
