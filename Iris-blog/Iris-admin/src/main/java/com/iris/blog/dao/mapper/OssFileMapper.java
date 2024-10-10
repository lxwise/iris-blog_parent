package com.iris.blog.dao.mapper;

import com.iris.blog.dao.entity.OssFileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lstar
 * @date: 2024-05
 * @description: 存储文件表
 */
@Mapper
public interface OssFileMapper extends BaseMapper<OssFileEntity> {
	
}
