package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.constant.SystemParamCodeConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.components.oss.cloud.CloudStorageConfig;
import com.iris.blog.components.oss.cloud.OssFactory;
import com.iris.blog.components.oss.dto.UploadDTO;
import com.iris.blog.service.SysConfigService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.OssFileMapper;
import com.iris.blog.dao.entity.OssFileEntity;
import com.iris.blog.domain.dto.OssFileDTO;
import com.iris.blog.domain.vo.OssFileVO;
import com.iris.blog.service.OssFileService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 存储文件表
 */
@Service("ossFileService")
@Slf4j
public class OssFileServiceImpl extends ServiceImpl<OssFileMapper, OssFileEntity> implements OssFileService {

    @Resource
    private SysConfigService sysConfigService;

    @Override
    public R selectOssFileList(PageReq<OssFileDTO> req){

        OssFileEntity entity = BeanUtil.newBean(req.getAction(), OssFileEntity.class);
        Page<OssFileEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .ge(null != req.getAction().getCreateTimeStart(), OssFileEntity::getCreateTime,req.getAction().getCreateTimeStart())
                        .le(null != req.getAction().getCreateTimeEnd(),OssFileEntity::getCreateTime,req.getAction().getCreateTimeEnd())
                        .orderByDesc(OssFileEntity::getId));

        PageBean<OssFileVO> pageBean = PageUtil.pageBean(page, OssFileVO.class);
        return R.ok(pageBean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeOssFileByIds(Long ids){

        OssFileEntity entity = checkExist(ids);
        this.removeById(ids);
        if(Objects.equals(BaseNumberEnum.FOUR.getCode(), entity.getConfigType()) || Objects.equals(BaseNumberEnum.FIVE.getCode(), entity.getConfigType()) ){
            Objects.requireNonNull(OssFactory.build()).deleteFile(entity.getUrl());
        }else {
            Objects.requireNonNull(OssFactory.build()).deleteFile(entity.getName());
        }
        return R.ok();
    }

    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UploadDTO upload(MultipartFile file){
        try {

            //上传文件
            String fileType= FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = FileUtil.getName(file.getOriginalFilename());
            String url = Objects.requireNonNull(OssFactory.build()).uploadSuffix(file.getInputStream(), fileType,"image",fileName);
            String filePath = Objects.requireNonNull(OssFactory.build()).getFilePath(fileType, "image", fileName);

            LambdaQueryWrapper<OssFileEntity> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(OssFileEntity::getUrl, url)
                    .eq(OssFileEntity::getFilePath, filePath)
                    .eq(OssFileEntity::getType, fileType);
            if(this.count(queryWrapper) > 0){
                return new UploadDTO(url, file.getSize());
            }
            //保存文件信息
            OssFileEntity entity = new OssFileEntity();
            entity.setName(fileName);
            entity.setUrl(url);
            entity.setFilePath(filePath);
            entity.setType(fileType);
            entity.setSize(file.getSize());
            String paramValue = sysConfigService.getValueByCode(SystemParamCodeConstant.SYS_OSS_CONFIG_KEY);
            CloudStorageConfig config = JSON.parseObject(paramValue, CloudStorageConfig.class);
            entity.setConfigType(config.getType());
            entity.setCreator(StpUtil.getLoginIdAsString());
            this.save(entity);
            //文件信息
            UploadDTO dto = new UploadDTO();
            dto.setUrl(url);
            dto.setSize(file.getSize());
            return dto;
        } catch (IOException e) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR);
        }
    }

    public OssFileEntity checkExist(Long id){
        OssFileEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}