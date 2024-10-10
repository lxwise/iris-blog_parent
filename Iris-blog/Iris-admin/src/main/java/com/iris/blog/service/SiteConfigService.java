package com.iris.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.SiteConfigEntity;
import com.iris.blog.common.R;
import com.iris.blog.domain.dto.SiteConfigDTO;
import com.iris.blog.domain.vo.SiteConfigVO;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 网站配置
 */
public interface SiteConfigService extends IService<SiteConfigEntity> {
    /**
     * 获取网站配置信息
     * @return
     */
    SiteConfigVO getSiteConfigInfo();

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveSiteConfig(SiteConfigDTO dto);
}

