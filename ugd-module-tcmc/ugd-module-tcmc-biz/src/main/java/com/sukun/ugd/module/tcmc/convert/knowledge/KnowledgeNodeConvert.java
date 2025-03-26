package com.sukun.ugd.module.tcmc.convert.knowledge;

import com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo.NodeRespVO;
import com.sukun.ugd.module.tcmc.controller.admin.knowledge.vo.RelationshipRespVO;
import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeNode;
import com.sukun.ugd.module.tcmc.dal.dataobject.knowledge.KnowledgeRelationship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface KnowledgeNodeConvert {

    KnowledgeNodeConvert INSTANCE = Mappers.getMapper(KnowledgeNodeConvert.class);

    NodeRespVO convert(KnowledgeNode bean);

    List<NodeRespVO> convertList(List<KnowledgeNode> list);

    RelationshipRespVO convertRelationship(KnowledgeRelationship bean);

    List<RelationshipRespVO> convertRelationshipList(List<KnowledgeRelationship> list);
}