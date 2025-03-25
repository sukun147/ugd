package com.sukun.ugd.module.tcmc.dal.dataobject.clientuser;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.sukun.ugd.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户会话 DO
 *
 * @author sukun
 */
@TableName("tcmc_session")
@KeySequence("tcmc_session_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 会话标题
     */
    private String title;

}