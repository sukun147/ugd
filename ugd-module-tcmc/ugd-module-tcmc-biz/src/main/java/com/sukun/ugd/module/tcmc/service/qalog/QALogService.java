package com.sukun.ugd.module.tcmc.service.qalog;

import com.sukun.ugd.module.tcmc.controller.app.qa.vo.QALogSaveReqVO;
import com.sukun.ugd.module.tcmc.dal.dataobject.qalog.QALogDO;

import java.util.List;

/**
 * 问答日志 Service 接口
 *
 * @author sukun
 */
public interface QALogService {

    /**
     * 获得问答日志列表
     *
     * @param sessionId 会话id
     * @return 问答日志列表
     */
    List<QALogDO> getQALogList(Long sessionId);

    /**
     * 创建问答日志
     *
     * @param reqVO 问答日志保存请求
     * @return 问答日志
     */
    QALogDO createQALog(QALogSaveReqVO reqVO);
}