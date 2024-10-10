package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.GatewayConfigMapper;
import com.iris.blog.dao.entity.GatewayConfigEntity;
import com.iris.blog.domain.dto.GatewayConfigDTO;
import com.iris.blog.domain.vo.GatewayConfigVO;
import com.iris.blog.service.GatewayConfigService;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 网关配置
 */
@Service("gatewayConfigService")
@Slf4j
public class GatewayConfigServiceImpl extends ServiceImpl<GatewayConfigMapper, GatewayConfigEntity> implements GatewayConfigService {

    @Override
    public R selectGatewayConfigList(PageReq<GatewayConfigDTO> req){

        GatewayConfigDTO action = req.getAction();
        GatewayConfigEntity entity = BeanUtil.newBean(action, GatewayConfigEntity.class);
        Page<GatewayConfigEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .orderByDesc(GatewayConfigEntity::getId));
        PageBean<GatewayConfigVO> pageBean = PageUtil.pageBean(page, GatewayConfigVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectGatewayConfigById( Long id){
        GatewayConfigEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        GatewayConfigVO vo = BeanUtil.newBean(entity, GatewayConfigVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveGatewayConfig(GatewayConfigDTO dto){
        GatewayConfigEntity entity = BeanUtil.newBean(dto, GatewayConfigEntity.class);
        this.save(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateGatewayConfig(GatewayConfigDTO dto){
        this.checkExist(dto.getId());
        GatewayConfigEntity entity = BeanUtil.newBean(dto, GatewayConfigEntity.class);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeGatewayConfigByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }


    @Override
    public void export(GatewayConfigDTO dto, HttpServletResponse response) {
        try {
            GatewayConfigEntity entity = BeanUtil.newBean(dto, GatewayConfigEntity.class);
            List<GatewayConfigEntity> list = this.list(Wrappers.lambdaQuery(entity)
                    .orderByDesc(GatewayConfigEntity::getId));
            ExcelUtils.write(response, "网关配置", "网关配置", GatewayConfigVO.class, list.stream().map(item -> BeanUtil.newBean(item, GatewayConfigVO.class)).toList());
        } catch (IOException e) {
            throw new BusinessException(ResultCode.REPORT_EXPORT_ERROR);
        }
    }

    public GatewayConfigEntity checkExist(Long id){
        GatewayConfigEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}