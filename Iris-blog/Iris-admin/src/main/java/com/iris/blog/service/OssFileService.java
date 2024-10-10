package com.iris.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.OssFileEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.OssFileDTO;
import com.iris.blog.components.oss.dto.UploadDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 存储文件表
 */
public interface OssFileService extends IService<OssFileEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectOssFileList(PageReq<OssFileDTO> req);


    /**
     * 删除
     * @param ids
     * @return
     */
    R removeOssFileByIds(Long ids);

    /**
     * 文件上传
     * @param file
     * @return
     */
    UploadDTO upload(MultipartFile file);
}

