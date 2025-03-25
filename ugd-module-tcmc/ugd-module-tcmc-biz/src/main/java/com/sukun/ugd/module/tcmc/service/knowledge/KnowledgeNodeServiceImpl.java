package com.sukun.ugd.module.tcmc.service.knowledge;

import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeNode;
import com.sukun.ugd.module.tcmc.dal.neo4j.KnowledgeNodeRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class KnowledgeNodeServiceImpl implements KnowledgeNodeService {

    @Resource
    private KnowledgeNodeRepository knowledgeNodeRepository;

    @Override
    public KnowledgeNode createNode(String name) {
        KnowledgeNode node = new KnowledgeNode(name);
        return knowledgeNodeRepository.save(node);
    }

    @Override
    public Optional<KnowledgeNode> findNodeByName(String name) {
        return knowledgeNodeRepository.findByName(name);
    }

    @Override
    public Optional<KnowledgeNode> findNodeById(Long id) {
        return knowledgeNodeRepository.findById(id);
    }

    @Override
    public List<KnowledgeNode> findNodesByNameContaining(String name) {
        return knowledgeNodeRepository.findByNameContaining(name);
    }

    @Override
    public List<KnowledgeNode> findAllNodes() {
        return knowledgeNodeRepository.findAll();
    }

    @Override
    @Transactional
    public KnowledgeNode updateNode(Long id, String newName) {
        if (knowledgeNodeRepository.existsById(id)) {
            return knowledgeNodeRepository.updateNodeName(id, newName);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteNode(Long id) {
        // 删除节点及其关系
        if (knowledgeNodeRepository.existsById(id)) {
            knowledgeNodeRepository.deleteByNodeId(id);
        }
    }

    @Override
    public void deleteAllNodes() {
        knowledgeNodeRepository.deleteAll();
    }

    @Override
    public boolean existsByName(String name) {
        return knowledgeNodeRepository.existsByName(name);
    }

    @Override
    public boolean existsById(Long id) {
        return knowledgeNodeRepository.existsById(id);
    }

    // 其余关系相关方法保持不变...
    @Override
    @Transactional
    public void createRelationship(String sourceName, String targetName, String relationshipType) {
        knowledgeNodeRepository.createRelationship(sourceName, targetName, relationshipType);
    }

    @Override
    public List<Map<String, Object>> findOutgoingRelationships(String name) {
        return knowledgeNodeRepository.findOutgoingRelationships(name);
    }

    @Override
    public List<Map<String, Object>> findIncomingRelationships(String name) {
        return knowledgeNodeRepository.findIncomingRelationships(name);
    }

    @Override
    public List<Map<String, Object>> findAllRelationships() {
        return knowledgeNodeRepository.findAllRelationships();
    }

    @Override
    public String getRelationshipType(String sourceName, String targetName) {
        return knowledgeNodeRepository.getRelationshipType(sourceName, targetName);
    }

    @Override
    @Transactional
    public void deleteRelationship(String sourceName, String targetName, String relationshipType) {
        knowledgeNodeRepository.deleteRelationship(sourceName, targetName, relationshipType);
    }

    @Override
    @Transactional
    public void deleteAllRelationshipsBetweenNodes(String sourceName, String targetName) {
        knowledgeNodeRepository.deleteAllRelationshipsBetweenNodes(sourceName, targetName);
    }

    @Override
    @Transactional
    public void deleteAllRelationshipsOfNode(String name) {
        knowledgeNodeRepository.deleteAllRelationshipsOfNode(name);
    }

    @Override
    public boolean relationshipExists(String sourceName, String targetName, String relationshipType) {
        return knowledgeNodeRepository.relationshipExists(sourceName, targetName, relationshipType);
    }
}
