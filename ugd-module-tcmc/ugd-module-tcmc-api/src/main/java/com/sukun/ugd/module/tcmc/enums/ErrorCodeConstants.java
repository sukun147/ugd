package com.sukun.ugd.module.tcmc.enums;

import com.sukun.ugd.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    ErrorCode CLIENT_USER_NOT_EXISTS = new ErrorCode(1_003_000_001, "问诊用户不存在");
    ErrorCode CLIENT_USER_ERROR_PASSWORD = new ErrorCode(1_003_000_002, "问诊用户密码错误");
    ErrorCode SESSION_NOT_EXISTS = new ErrorCode(1_003_000_003, "用户会话不存在");
    ErrorCode SESSION_NOT_BELONG_TO_USER = new ErrorCode(1_003_000_004, "用户会话不属于当前用户");

    ErrorCode NODE_NOT_EXISTS = new ErrorCode(1_003_001_001, "节点不存在");
    ErrorCode NODE_NOT_COMPLETE = new ErrorCode(1_003_001_001, "节点信息不完整");
    ErrorCode NODE_CONFLICT = new ErrorCode(1_003_001_003, "节点冲突");
    ErrorCode RELATIONSHIP_NOT_COMPLETE = new ErrorCode(1_003_001_004, "节点信息不完整");
    ErrorCode RELATIONSHIP_ALREADY_EXISTS = new ErrorCode(1_003_001_005, "关系已存在");
}