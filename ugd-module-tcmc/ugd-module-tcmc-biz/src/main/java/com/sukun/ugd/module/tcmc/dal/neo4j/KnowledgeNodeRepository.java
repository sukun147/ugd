package com.sukun.ugd.module.tcmc.dal.neo4j;

import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface KnowledgeNodeRepository extends Neo4jRepository<KnowledgeNode, String> {

    // 基于名称查询节点
    @Query("MATCH (n:中药) WHERE n.name = $name RETURN n")
    Optional<KnowledgeNode> findByName(@Param("name") String name);

    // 基于名称模糊查询节点
    @Query("MATCH (n:中药) WHERE n.name CONTAINS $name RETURN n")
    List<KnowledgeNode> findByNameContaining(@Param("name") String name);

    // 根据名称更新节点名称
    @Query("MATCH (n:中药) WHERE n.name = $oldName SET n.name = $newName RETURN n")
    KnowledgeNode updateNodeName(@Param("oldName") String oldName, @Param("newName") String newName);

    // 根据名称删除节点
    @Query("MATCH (n:中药) WHERE n.name = $name DETACH DELETE n")
    void deleteByName(@Param("name") String name);

    // 创建关系
    @Query("MATCH (a), (b) " +
            "WHERE a.name = $sourceName AND b.name = $targetName " +
            "CREATE (a)-[r:$relationshipType]->(b)")
    void createRelationship(@Param("sourceName") String sourceName,
                            @Param("targetName") String targetName,
                            @Param("relationshipType") String relationshipType);

    // 查找与指定节点有出向关系的节点
    @Query("MATCH (n:中药 {name: $name})-[r]->(related) " +
            "RETURN {sourceName: n.name, relationType: type(r), targetName: related.name} as result")
    List<Map<String, String>> findOutgoingRelationships(@Param("name") String name);

    // 查找与指定节点有入向关系的节点
    @Query("MATCH (related)-[r]->(n:中药 {name: $name}) " +
            "RETURN {sourceName: related.name, relationType: type(r), targetName: n.name} as result")
    List<Map<String, String>> findIncomingRelationships(@Param("name") String name);

    // 查找所有关系
    @Query("MATCH (a:中药)-[r]->(b) " +
            "RETURN {sourceName: a.name, relationType: type(r), targetName: b.name} as result")
    List<Map<String, String>> findAllRelationships();

    // 获取节点之间的关系类型
    @Query("MATCH (a {name: $sourceName})-[r]->(b {name: $targetName}) " +
            "RETURN type(r) as relationType")
    String getRelationshipType(@Param("sourceName") String sourceName, @Param("targetName") String targetName);

    // 删除指定关系
    @Query("MATCH (a {name: $sourceName})-[r:$relationshipType]->(b {name: $targetName}) " +
            "DELETE r")
    void deleteRelationship(@Param("sourceName") String sourceName,
                            @Param("targetName") String targetName,
                            @Param("relationshipType") String relationshipType);

    // 删除两个节点之间的所有关系
    @Query("MATCH (a {name: $sourceName})-[r]->(b {name: $targetName}) " +
            "DELETE r")
    void deleteAllRelationshipsBetweenNodes(@Param("sourceName") String sourceName,
                                            @Param("targetName") String targetName);

    // 删除节点的所有关系
    @Query("MATCH (n {name: $name})-[r]-() " +
            "DELETE r")
    void deleteAllRelationshipsOfNode(@Param("name") String name);

    // 检查节点是否存在
    @Query("MATCH (n:中药) WHERE n.name = $name RETURN count(n) > 0")
    boolean existsByName(@Param("name") String name);

    // 检查关系是否存在
    @Query("MATCH (a {name: $sourceName})-[r:$relationshipType]->(b {name: $targetName}) " +
            "RETURN count(r) > 0")
    boolean relationshipExists(@Param("sourceName") String sourceName,
                               @Param("targetName") String targetName,
                               @Param("relationshipType") String relationshipType);

    // 删除所有节点和关系
    @Query("MATCH (n:中药) DETACH DELETE n")
    void deleteAll();
}