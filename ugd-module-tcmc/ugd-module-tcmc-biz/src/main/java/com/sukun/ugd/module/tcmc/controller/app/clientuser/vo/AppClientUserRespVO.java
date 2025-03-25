package com.sukun.ugd.module.tcmc.controller.app.clientuser.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 问诊用户 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppClientUserRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "用户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "sukun")
    @ExcelProperty("用户名称")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @ExcelProperty("密码")
    private String password;

    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "1477264431@qq.com")
    @ExcelProperty("邮箱")
    private String email;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}