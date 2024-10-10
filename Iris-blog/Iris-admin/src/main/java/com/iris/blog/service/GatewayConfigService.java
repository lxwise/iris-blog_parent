package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.GatewayConfigEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.GatewayConfigDTO;
import javax.servlet.http.HttpServletResponse;
/**
 * @author lstar
 * @date: 2024-06
 * @description: 网关配置
 */
public interface GatewayConfigService extends IService<GatewayConfigEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectGatewayConfigList(PageReq<GatewayConfigDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectGatewayConfigById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveGatewayConfig(GatewayConfigDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateGatewayConfig(GatewayConfigDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeGatewayConfigByIds(List<Long> ids);


    /**
     * 导出
     * @param dto
     * @param response
     */
    void export(GatewayConfigDTO dto, HttpServletResponse response);
}

