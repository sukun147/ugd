package com.sukun.ugd.module.tcmc.service.qalog;

import com.sukun.ugd.module.tcmc.controller.app.qa.vo.QALogSaveReqVO;
import com.sukun.ugd.module.tcmc.service.clientuser.ClientUserService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.sukun.ugd.module.tcmc.dal.dataobject.qalog.QALogDO;

import com.sukun.ugd.module.tcmc.dal.mysql.qalog.QALogMapper;

import java.util.List;

/**
 * 问答日志 Service 实现类
 *
 * @author sukun
 */
@Service
@Validated
public class QALogServiceImpl implements QALogService {

    @Resource
    private QALogMapper qALogMapper;

    @Resource
    private ClientUserService clientUserService;

    @Override
    public List<QALogDO> getQALogList(Long sessionId) {
        return qALogMapper.selectList(sessionId);
    }

    @Override
    public QALogDO createQALog(QALogSaveReqVO reqVO) {
        // 检查是否有会话ID，如果无，创建新会话，如果有，检查会话是否存在
        Long sessionId = reqVO.getSessionId();
        if (sessionId == null) {
            // 生成会话标题
            String title = abstractQuestion(reqVO.getQuestion());
            sessionId = clientUserService.createSession(title);
        } else {
            clientUserService.validateSessionExists(sessionId);
        }
        // 创建问答，调用问答模型
        String answer = generateAnswer(reqVO.getQuestion());
        QALogDO qALog = new QALogDO();
        qALog.setSessionId(sessionId);
        qALog.setQuestion(reqVO.getQuestion());
        qALog.setAnswer(answer);
        qALogMapper.insert(qALog);
        return qALog;
    }

    private String abstractQuestion(String question) {
        return "新对话";
    }

    private String generateAnswer(String question) {
        return "测试回答";
    }

}