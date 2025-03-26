package com.sukun.ugd.module.tcmc.controller.admin.knowledge;

import com.sukun.ugd.framework.common.pojo.CommonResult;
import com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo.NodeCreateReqVO;
import com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo.NodeRespVO;
import com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo.NodeUpdateReqVO;
import com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo.RelationshipRespVO;
import com.sukun.ugd.module.tcmc.convert.knowledge.KnowledgeNodeConvert;
import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeNode;
import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeRelationship;
import com.sukun.ugd.module.tcmc.service.knowledge.KnowledgeNodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sukun.ugd.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 知识图谱节点")
@RestController
@RequestMapping("/tcmc/knowledge")
@Validated
public class KnowledgeNodeController {

    @Resource
    private KnowledgeNodeService knowledgeNodeService;

    @PostMapping("/node/add")
    @Operation(summary = "创建节点")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:create')")
    public CommonResult<NodeRespVO> createNode(@Valid @RequestBody NodeCreateReqVO createReqVO) {
        KnowledgeNode node = knowledgeNodeService.createNode(createReqVO.getName());
        return success(KnowledgeNodeConvert.INSTANCE.convert(node));
    }

    @GetMapping("/node/get")
    @Operation(summary = "获得节点")
    @Parameter(name = "name", description = "节点名称", required = true, example = "Java编程")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:query')")
    public CommonResult<NodeRespVO> getNode(@RequestParam("name") String name) {
        return success(KnowledgeNodeConvert.INSTANCE.convert(
                knowledgeNodeService.findNodeByName(name).orElse(null)));
    }

    @GetMapping("/node/list")
    @Operation(summary = "获得所有节点列表")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:query')")
    public CommonResult<List<NodeRespVO>> getNodeList() {
        List<KnowledgeNode> list = knowledgeNodeService.findAllNodes();
        return success(KnowledgeNodeConvert.INSTANCE.convertList(list));
    }

    @PutMapping("/node/update")
    @Operation(summary = "更新节点")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:update')")
    public CommonResult<NodeRespVO> updateNode(@Valid @RequestBody NodeUpdateReqVO updateReqVO) {
        KnowledgeNode node = knowledgeNodeService.updateNode(updateReqVO.getOldName(), updateReqVO.getNewName());
        return success(KnowledgeNodeConvert.INSTANCE.convert(node));
    }

    @GetMapping("/node/delete")
    @Operation(summary = "删除节点")
    @Parameter(name = "name", description = "节点名称", required = true, example = "Java编程")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:delete')")
    public CommonResult<Boolean> deleteNode(@RequestParam("name") String name) {
        knowledgeNodeService.deleteNode(name);
        return success(true);
    }

    @GetMapping("/node/search")
    @Operation(summary = "搜索节点")
    @Parameter(name = "name", description = "节点名称", required = true, example = "Java")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:query')")
    public CommonResult<List<NodeRespVO>> searchNodes(@RequestParam("name") String name) {
        List<KnowledgeNode> nodes = knowledgeNodeService.findNodesByNameContaining(name);
        return success(KnowledgeNodeConvert.INSTANCE.convertList(nodes));
    }

    // 关系相关接口

    @PostMapping("/relationship/add")
    @Operation(summary = "创建关系")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:create')")
    public CommonResult<Boolean> createRelationship(@RequestParam("sourceName") String sourceName,
                                                    @RequestParam("targetName") String targetName,
                                                    @RequestParam("relationshipType") String relationshipType) {
        knowledgeNodeService.createRelationship(sourceName, targetName, relationshipType);
        return success(true);
    }

    @GetMapping("/relationship/outgoing")
    @Operation(summary = "获取出向关系")
    @Parameter(name = "name", description = "节点名称", required = true)
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:query')")
    public CommonResult<List<RelationshipRespVO>> getOutgoingRelationships(@RequestParam("name") String name) {
        List<KnowledgeRelationship> relationships = knowledgeNodeService.findOutgoingRelationships(name);
        return success(KnowledgeNodeConvert.INSTANCE.convertRelationshipList(relationships));
    }

    @GetMapping("/relationship/incoming")
    @Operation(summary = "获取入向关系")
    @Parameter(name = "name", description = "节点名称", required = true)
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:query')")
    public CommonResult<List<RelationshipRespVO>> getIncomingRelationships(@RequestParam("name") String name) {
        List<KnowledgeRelationship> relationships = knowledgeNodeService.findIncomingRelationships(name);
        return success(KnowledgeNodeConvert.INSTANCE.convertRelationshipList(relationships));
    }

    @GetMapping("/relationship/list")
    @Operation(summary = "获取所有关系")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:query')")
    public CommonResult<List<RelationshipRespVO>> getAllRelationships() {
        List<KnowledgeRelationship> relationships = knowledgeNodeService.findAllRelationships();
        return success(KnowledgeNodeConvert.INSTANCE.convertRelationshipList(relationships));
    }

    @GetMapping("/relationship/delete")
    @Operation(summary = "删除关系")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:delete')")
    public CommonResult<Boolean> deleteRelationship(@RequestParam("sourceName") String sourceName,
                                                    @RequestParam("targetName") String targetName,
                                                    @RequestParam("relationshipType") String relationshipType) {
        knowledgeNodeService.deleteRelationship(sourceName, targetName, relationshipType);
        return success(true);
    }

    @GetMapping("/relationship/between")
    @Operation(summary = "删除节点之间的所有关系")
    @PreAuthorize("@ss.hasPermission('tcmc:knowledge-node:delete')")
    public CommonResult<Boolean> deleteAllRelationshipsBetweenNodes(@RequestParam("sourceName") String sourceName,
                                                                    @RequestParam("targetName") String targetName) {
        knowledgeNodeService.deleteAllRelationshipsBetweenNodes(sourceName, targetName);
        return success(true);
    }
}