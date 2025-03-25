package com.sukun.ugd.module.tcmc.controller.app.qa;

import com.sukun.ugd.framework.common.pojo.CommonResult;
import com.sukun.ugd.module.tcmc.controller.app.qa.vo.QALogSaveReqVO;
import com.sukun.ugd.module.tcmc.dal.dataobject.qalog.QALogDO;
import com.sukun.ugd.module.tcmc.service.qalog.QALogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.sukun.ugd.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 中医问诊")
@RestController
@RequestMapping("/tcmc/qa")
@Validated
public class QAController {

    @Resource
    private QALogService qALogService;

    @PostMapping("/create")
    @Operation(summary = "创建问答")
    public CommonResult<QALogDO> createQALog(@RequestBody QALogSaveReqVO reqVO) {
        return success(qALogService.createQALog(reqVO));
    }
}
