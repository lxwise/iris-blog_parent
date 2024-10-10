package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.SysConfigEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.SysConfigDTO;
import com.iris.blog.domain.search.SearchSysConfigDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 参数管理
 */
public interface SysConfigService extends IService<SysConfigEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectSysConfigList(PageReq<SearchSysConfigDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectSysConfigById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveSysConfig(SysConfigDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateSysConfig(SysConfigDTO dto);

    /**
     * 删除
     * @param id
     * @return
     */
    R removeSysConfigByIds(Long id);

    /**
     * 修改状态
     * @param id
     * @return
     */
    R updateStatusById(Long id);

    /**
     * 导出
     * @param paramCode
     * @param response
     */
    void export(SearchSysConfigDTO paramCode, HttpServletResponse response);

    /**
     * 根据参数编码获取参数值
     * @param paramCode
     * @return
     */
    String getValueByCode(String paramCode);
    /**
     * 根据参数编码获取参数值从Redis
     * @param paramCode
     * @return
     */
    String getValueByCodeByRedis(String paramCode);

    /**
     * 根据参数编码，更新参数值
     * @param paramCode
     * @param paramValue
     */

    R updateValueByCode(String paramCode, String paramValue);
}

