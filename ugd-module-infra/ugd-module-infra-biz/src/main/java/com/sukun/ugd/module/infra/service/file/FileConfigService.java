package com.sukun.ugd.module.infra.service.file;

import com.sukun.ugd.framework.common.pojo.PageResult;
import com.sukun.ugd.module.infra.framework.file.core.client.FileClient;
import com.sukun.ugd.module.infra.controller.admin.file.vo.config.FileConfigPageReqVO;
import com.sukun.ugd.module.infra.controller.admin.file.vo.config.FileConfigSaveReqVO;
import com.sukun.ugd.module.infra.dal.dataobject.file.FileConfigDO;

import jakarta.validation.Valid;

/**
 * 文件配置 Service 接口
 *
 * @author 芋道源码
 */
public interface FileConfigService {

    /**
     * 创建文件配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFileConfig(@Valid FileConfigSaveReqVO createReqVO);

    /**
     * 更新文件配置
     *
     * @param updateReqVO 更新信息
     */
    void updateFileConfig(@Valid FileConfigSaveReqVO updateReqVO);

    /**
     * 更新文件配置为 Master
     *
     * @param id 编号
     */
    void updateFileConfigMaster(Long id);

    /**
     * 删除文件配置
     *
     * @param id 编号
     */
    void deleteFileConfig(Long id);

    /**
     * 获得文件配置
     *
     * @param id 编号
     * @return 文件配置
     */
    FileConfigDO getFileConfig(Long id);

    /**
     * 获得文件配置分页
     *
     * @param pageReqVO 分页查询
     * @return 文件配置分页
     */
    PageResult<FileConfigDO> getFileConfigPage(FileConfigPageReqVO pageReqVO);

    /**
     * 测试文件配置是否正确，通过上传文件
     *
     * @param id 编号
     * @return 文件 URL
     */
    String testFileConfig(Long id) throws Exception;

    /**
     * 获得指定编号的文件客户端
     *
     * @param id 配置编号
     * @return 文件客户端
     */
    FileClient getFileClient(Long id);

    /**
     * 获得 Master 文件客户端
     *
     * @return 文件客户端
     */
    FileClient getMasterFileClient();

}
