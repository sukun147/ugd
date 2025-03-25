SET NAMES utf8mb4;

DROP TABLE IF EXISTS `tcmc_client_user`;
CREATE TABLE `tcmc_client_user`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username`    varchar(255) NOT NULL COMMENT '用户名称',
    `password`    varchar(255) NOT NULL COMMENT '密码',
    `email`       varchar(255) NOT NULL COMMENT '邮箱',
    `creator`     varchar(255) NULL DEFAULT '' COMMENT '创建人',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(255) NULL DEFAULT '' COMMENT '更新人',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     tinyint      NOT NULL DEFAULT 0 COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci COMMENT ='问诊用户表';

INSERT INTO `tcmc_client_user` (username, password, email) VALUE ('sukun', '$2a$10$Wx1/a5Bfbui58nC8IhhBxOL9WmcGqqcHxzfYOb5Myk25IyE6XOQUO', '1477264431@qq.com');

DROP TABLE IF EXISTS `tcmc_session`;
CREATE TABLE `tcmc_session`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`     BIGINT       NOT NULL COMMENT '用户id',
    `title`       varchar(255) NOT NULL COMMENT '会话标题',
    `creator`     varchar(255) NULL DEFAULT '' COMMENT '创建人',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(255) NULL DEFAULT '' COMMENT '更新人',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     tinyint      NOT NULL DEFAULT 0 COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci COMMENT ='用户会话表';

INSERT INTO `tcmc_session` (user_id, title) VALUE (1, '测试会话');

DROP TABLE IF EXISTS `tcmc_q_a_log`;
CREATE TABLE `tcmc_q_a_log`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `session_id`  BIGINT       NOT NULL COMMENT '会话id',
    `question`    varchar(255) NOT NULL COMMENT '问题',
    `answer`      TEXT         NOT NULL COMMENT '答案',
    `creator`     varchar(255) NULL DEFAULT '' COMMENT '创建人',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(255) NULL DEFAULT '' COMMENT '更新人',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     tinyint      NOT NULL DEFAULT 0 COMMENT '删除标识',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci COMMENT ='问答日志表';

INSERT INTO `tcmc_q_a_log` (session_id, question, answer) VALUE (1, 'Q', 'A');
