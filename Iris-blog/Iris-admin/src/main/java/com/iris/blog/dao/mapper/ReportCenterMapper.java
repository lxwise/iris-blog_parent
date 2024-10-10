package com.iris.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.domain.dto.ReportCenterDTO;
import com.iris.blog.dao.entity.ReportCenterEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 报表下载中心 Mapper 接口
 * </p>
 *
 * @author liuzc
 * @since 2022-07-15
 */
@Mapper
public interface ReportCenterMapper extends BaseMapper<ReportCenterEntity> {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<ReportCenterEntity> getListByPage(Page page, @Param("dto") ReportCenterDTO dto);

    /**
     * 查询大于今天失败的list
     * @return
     */
    List<ReportCenterEntity> queryFailList();
}
