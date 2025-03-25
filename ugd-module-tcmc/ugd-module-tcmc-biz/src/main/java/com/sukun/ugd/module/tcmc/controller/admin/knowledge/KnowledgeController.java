package com.sukun.ugd.module.tcmc.controller.admin.knowledge;

import com.sukun.ugd.framework.common.pojo.CommonResult;
import com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo.NodeCreateReqVO;
import com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo.NodeUpdateReqVO;
import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeNode;
import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.RelationshipInfo;
import com.sukun.ugd.module.tcmc.service.knowledge.KnowledgeNodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.sukun.ugd.module.tcmc.enums.ErrorCodeConstants.*;

@Tag(name = "管理后台 - 知识图谱")
@RestController
@RequestMapping("/tcmc/knowledge")
@Validated
public class KnowledgeController {
    @Resource
    private KnowledgeNodeService knowledgeNodeService;

    @Operation(summary = "获取所有节点", description = "获取知识图谱中的所有节点列表，包含节点ID")
    @GetMapping("/node/list")
    public CommonResult<List<KnowledgeNode>> getAllNodes() {
        List<KnowledgeNode> nodes = knowledgeNodeService.findAllNodes();
        return CommonResult.success(nodes);
    }

    @Operation(summary = "根据ID获取节点", description = "根据节点ID获取单个节点")
    @GetMapping("/node/getById")
    public CommonResult<KnowledgeNode> getNodeById(@Parameter(description = "节点ID", required = true) Long id) {
        return knowledgeNodeService.findNodeById(id)
                .map(CommonResult::success)
                .orElse(CommonResult.error(NODE_NOT_EXISTS, "节点不存在"));
    }

    @Operation(summary = "根据名称获取节点", description = "根据节点名称获取单个节点，返回包含ID")
    @GetMapping("/node/getByName")
    public CommonResult<KnowledgeNode> getNodeByName(@Parameter(description = "节点名称", required = true) String name) {
        return knowledgeNodeService.findNodeByName(name)
                .map(CommonResult::success)
                .orElse(CommonResult.error(NODE_NOT_EXISTS, "节点不存在"));
    }

    @Operation(summary = "搜索节点", description = "根据部分名称搜索匹配的节点，返回包含ID")
    @GetMapping("/node/search")
    public CommonResult<List<KnowledgeNode>> searchNodes(@Parameter(description = "节点名称关键字", required = true) String name) {
        List<KnowledgeNode> nodes = knowledgeNodeService.findNodesByNameContaining(name);
        return CommonResult.success(nodes);
    }

    @Operation(summary = "添加节点", description = "创建新的知识图谱节点，返回包含ID")
    @PostMapping("/node/add")
    public CommonResult<KnowledgeNode> addNode(@Parameter(description = "节点信息", required = true) @RequestBody NodeCreateReqVO reqVO) {
        String name = reqVO.getName();
        if (name == null || name.trim().isEmpty()) {
            return CommonResult.error(NODE_NOT_COMPLETE, "节点名称不能为空");
        }

        if (knowledgeNodeService.existsByName(name)) {
            return CommonResult.error(NODE_CONFLICT, "节点已存在");
        }

        KnowledgeNode createdNode = knowledgeNodeService.createNode(name);
        return CommonResult.success(createdNode);
    }

    @Operation(summary = "更新节点", description = "根据节点ID更新节点名称")
    @PutMapping("/node/update")
    public CommonResult<KnowledgeNode> updateNode(@Parameter(description = "节点更新信息", required = true) @RequestBody NodeUpdateReqVO reqVO) {
        Long id = reqVO.getId();
        String newName = reqVO.getNewName();

        if (id == null || newName == null || newName.trim().isEmpty()) {
            return CommonResult.error(NODE_NOT_COMPLETE, "节点ID和新节点名称不能为空");
        }

        KnowledgeNode updatedNode = knowledgeNodeService.updateNode(id, newName);
        if (updatedNode != null) {
            return CommonResult.success(updatedNode);
        }
        return CommonResult.error(NODE_NOT_EXISTS, "节点不存在");
    }

    @Operation(summary = "删除节点", description = "根据节点ID删除节点及其所有关系")
    @GetMapping("/node/delete")
    public CommonResult<Boolean> deleteNode(@Parameter(description = "节点ID", required = true) Long id) {
        if (id == null) {
            return CommonResult.error(NODE_NOT_COMPLETE, "节点ID不能为空");
        }

        if (knowledgeNodeService.existsById(id)) {
            knowledgeNodeService.deleteNode(id);
            return CommonResult.success(true);
        }

        return CommonResult.success(false);
    }

    @Operation(summary = "删除所有节点", description = "删除知识图谱中的所有节点和关系")
    @GetMapping("/node/deleteAll")
    public CommonResult<Boolean> deleteAllNodes() {
        knowledgeNodeService.deleteAllNodes();
        return CommonResult.success(true);
    }

    @Operation(summary = "获取所有关系", description = "获取知识图谱中的所有关系列表")
    @GetMapping("/relationship/list")
    public CommonResult<List<Map<String, Object>>> getAllRelationships() {
        List<Map<String, Object>> relationships = knowledgeNodeService.findAllRelationships();
        return CommonResult.success(relationships);
    }

    @Operation(summary = "获取节点的出向关系", description = "获取指定节点的所有出向关系")
    @GetMapping("/relationship/outgoing")
    public CommonResult<List<Map<String, Object>>> getOutgoingRelationships(
            @Parameter(description = "节点名称", required = true) @RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return CommonResult.error(NODE_NOT_COMPLETE, "节点名称不能为空");
        }

        if (!knowledgeNodeService.existsByName(name)) {
            return CommonResult.error(NODE_NOT_EXISTS, "节点不存在");
        }

        List<Map<String, Object>> relationships = knowledgeNodeService.findOutgoingRelationships(name);
        return CommonResult.success(relationships);
    }

    @Operation(summary = "获取节点的入向关系", description = "获取指定节点的所有入向关系")
    @GetMapping("/relationship/incoming")
    public CommonResult<List<Map<String, Object>>> getIncomingRelationships(
            @Parameter(description = "节点名称", required = true) @RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return CommonResult.error(NODE_NOT_COMPLETE, "节点名称不能为空");
        }

        if (!knowledgeNodeService.existsByName(name)) {
            return CommonResult.error(NODE_NOT_EXISTS, "节点不存在");
        }

        List<Map<String, Object>> relationships = knowledgeNodeService.findIncomingRelationships(name);
        return CommonResult.success(relationships);
    }

    @Operation(summary = "获取关系类型", description = "获取两个节点之间的关系类型")
    @GetMapping("/relationship/getType")
    public CommonResult<String> getRelationshipType(
            @Parameter(description = "源节点名称", required = true) @RequestParam String sourceName,
            @Parameter(description = "目标节点名称", required = true) @RequestParam String targetName) {

        if (sourceName == null || targetName == null ||
                sourceName.trim().isEmpty() || targetName.trim().isEmpty()) {
            return CommonResult.error(NODE_NOT_COMPLETE, "源节点和目标节点名称不能为空");
        }

        if (!knowledgeNodeService.existsByName(sourceName) ||
                !knowledgeNodeService.existsByName(targetName)) {
            return CommonResult.error(NODE_NOT_EXISTS, "节点不存在");
        }

        String relationType = knowledgeNodeService.getRelationshipType(sourceName, targetName);
        return CommonResult.success(relationType);
    }

    @Operation(summary = "添加关系", description = "在两个节点之间创建新的关系")
    @PostMapping("/relationship/add")
    public CommonResult<Boolean> addRelationship(
            @Parameter(description = "关系信息", required = true)
            @RequestBody RelationshipInfo relationshipInfo) {
        String sourceName = relationshipInfo.getSourceName();
        String targetName = relationshipInfo.getTargetName();
        String type = relationshipInfo.getType();

        CommonResult<Boolean> validResult = validRelationShip(sourceName, targetName, type);
        if (validResult != null) return validResult;

        if (knowledgeNodeService.relationshipExists(sourceName, targetName, type)) {
            return CommonResult.error(RELATIONSHIP_ALREADY_EXISTS, "关系已存在");
        }

        knowledgeNodeService.createRelationship(sourceName, targetName, type);
        return CommonResult.success(true);
    }

    @Operation(summary = "删除关系", description = "删除两个节点之间的指定类型关系")
    @GetMapping("/relationship/delete")
    public CommonResult<Boolean> deleteRelationship(
            @Parameter(description = "源节点名称", required = true) @RequestParam String sourceName,
            @Parameter(description = "目标节点名称", required = true) @RequestParam String targetName,
            @Parameter(description = "关系类型", required = true) @RequestParam String type) {

        CommonResult<Boolean> validResult = validRelationShip(sourceName, targetName, type);
        if (validResult != null) return validResult;

        knowledgeNodeService.deleteRelationship(sourceName, targetName, type);
        return CommonResult.success(true);
    }

    @Operation(summary = "删除两个节点间的所有关系", description = "删除两个节点之间的所有关系，不论类型")
    @GetMapping("/relationship/deleteBetween")
    public CommonResult<Boolean> deleteAllRelationshipsBetweenNodes(
            @Parameter(description = "源节点名称", required = true) @RequestParam String sourceName,
            @Parameter(description = "目标节点名称", required = true) @RequestParam String targetName) {

        if (sourceName == null || targetName == null ||
                sourceName.trim().isEmpty() || targetName.trim().isEmpty()) {
            return CommonResult.error(NODE_NOT_COMPLETE, "节点名称不能为空");
        }

        if (!knowledgeNodeService.existsByName(sourceName) ||
                !knowledgeNodeService.existsByName(targetName)) {
            return CommonResult.error(NODE_NOT_EXISTS, "节点不存在");
        }

        knowledgeNodeService.deleteAllRelationshipsBetweenNodes(sourceName, targetName);
        return CommonResult.success(true);
    }

    @Operation(summary = "删除节点的所有关系", description = "删除指定节点的所有入向和出向关系")
    @GetMapping("/relationship/deleteForNode")
    public CommonResult<Boolean> deleteAllRelationshipsOfNode(
            @Parameter(description = "节点名称", required = true) @RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return CommonResult.error(NODE_NOT_COMPLETE, "节点名称不能为空");
        }

        if (!knowledgeNodeService.existsByName(name)) {
            return CommonResult.error(NODE_NOT_EXISTS, "节点不存在");
        }

        knowledgeNodeService.deleteAllRelationshipsOfNode(name);
        return CommonResult.success(true);
    }

    private CommonResult<Boolean> validRelationShip(String sourceName, String targetName, String type) {
        if (sourceName == null || targetName == null || type == null ||
                sourceName.trim().isEmpty() || targetName.trim().isEmpty() || type.trim().isEmpty()) {
            return CommonResult.error(RELATIONSHIP_NOT_COMPLETE, "关系信息不完整");
        }

        if (!knowledgeNodeService.existsByName(sourceName) ||
                !knowledgeNodeService.existsByName(targetName)) {
            return CommonResult.error(NODE_NOT_EXISTS, "节点不存在");
        }
        return null;
    }
}
