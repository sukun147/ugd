package com.sukun.ugd.module.tcmc.service.knowledge;

import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeNode;
import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeRelationship;
import com.sukun.ugd.module.tcmc.dal.neo4j.KnowledgeNodeRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<KnowledgeNode> findNodesByNameContaining(String name) {
        return knowledgeNodeRepository.findByNameContaining(name);
    }

    @Override
    public List<KnowledgeNode> findAllNodes() {
        return knowledgeNodeRepository.findAll();
    }

    @Override
    @Transactional
    public KnowledgeNode updateNode(String oldName, String newName) {
        if (knowledgeNodeRepository.existsByName(oldName)) {
            return knowledgeNodeRepository.updateNodeName(oldName, newName);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteNode(String name) {
        if (knowledgeNodeRepository.existsByName(name)) {
            knowledgeNodeRepository.deleteByName(name);
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
    @Transactional
    public void createRelationship(String sourceName, String targetName, String relationshipType) {
        knowledgeNodeRepository.createRelationship(sourceName, targetName, relationshipType);
    }

    @Override
    public List<KnowledgeRelationship> findOutgoingRelationships(String name) {
        List<Map<String, String>> results = knowledgeNodeRepository.findOutgoingRelationships(name);
        return convertToRelationships(results);
    }

    @Override
    public List<KnowledgeRelationship> findIncomingRelationships(String name) {
        List<Map<String, String>> results = knowledgeNodeRepository.findIncomingRelationships(name);
        return convertToRelationships(results);
    }

    @Override
    @Transactional(timeout = 6000)
    public List<KnowledgeRelationship> findAllRelationships() {
        List<Map<String, String>> results = knowledgeNodeRepository.findAllRelationships();
        return convertToRelationships(results);
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

    private List<KnowledgeRelationship> convertToRelationships(List<Map<String, String>> results) {
        return results.stream()
                .map(map -> new KnowledgeRelationship(
                        map.get("sourceName"),
                        map.get("targetName"),
                        map.get("relationType")))
                .collect(Collectors.toList());
    }

}