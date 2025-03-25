package com.sukun.ugd.module.tcmc.dal.dataobject.qalog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.sukun.ugd.framework.mybatis.core.dataobject.BaseDO;

/**
 * 问答日志 DO
 *
 * @author sukun
 */
@TableName("tcmc_q_a_log")
@KeySequence("tcmc_q_a_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QALogDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 会话id
     */
    private Long sessionId;
    /**
     * 问题
     */
    private String question;
    /**
     * 答案
     */
    private String answer;

}