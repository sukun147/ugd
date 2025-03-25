package com.sukun.ugd.module.tcmc.controller.admin.qalog;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import com.sukun.ugd.framework.common.pojo.CommonResult;
import com.sukun.ugd.framework.common.util.object.BeanUtils;
import static com.sukun.ugd.framework.common.pojo.CommonResult.success;

import com.sukun.ugd.module.tcmc.controller.admin.qalog.vo.*;
import com.sukun.ugd.module.tcmc.dal.dataobject.qalog.QALogDO;
import com.sukun.ugd.module.tcmc.service.qalog.QALogService;

import java.util.List;

@Tag(name = "管理后台 - 问答日志")
@RestController
@RequestMapping("/tcmc/QA-log")
@Validated
public class QALogController {

    @Resource
    private QALogService qALogService;

    @GetMapping("/list")
    @Operation(summary = "获得问答日志分页")
    @Parameter(name = "sessionId", description = "会话编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('tcmc:QA-log:query')")
    public CommonResult<List<QALogRespVO>> getQALogList(@RequestParam Long sessionId) {
        List<QALogDO> result = qALogService.getQALogList(sessionId);
        return success(BeanUtils.toBean(result, QALogRespVO.class));
    }

}