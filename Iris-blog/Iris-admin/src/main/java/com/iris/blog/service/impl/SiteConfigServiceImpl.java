package com.iris.blog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.config.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.SiteConfigMapper;
import com.iris.blog.dao.entity.SiteConfigEntity;
import com.iris.blog.domain.dto.SiteConfigDTO;
import com.iris.blog.domain.vo.SiteConfigVO;
import com.iris.blog.service.SiteConfigService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-06
 * @description: 网站配置
 */
@Service("siteConfigService")
@Slf4j
public class SiteConfigServiceImpl extends ServiceImpl<SiteConfigMapper, SiteConfigEntity> implements SiteConfigService {

    @Resource
    private RedisUtil redisUtil;
    @Override
    public SiteConfigVO getSiteConfigInfo(){
        SiteConfigVO value = redisUtil.getKeyValue(RedisKeyConstant.SYS_SITE_SETTING, SiteConfigVO.class);
        if (ObjectUtil.isNotNull(value)){
            return value;
        }
        SiteConfigEntity entity = this.getById(BaseNumberEnum.ONE.getCode());
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        SiteConfigVO vo = BeanUtil.newBean(entity, SiteConfigVO.class);

        redisUtil.saveRedisValue(RedisKeyConstant.SYS_SITE_SETTING, vo,null);
        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveSiteConfig(SiteConfigDTO dto){
        checkExist(Long.valueOf(BaseNumberEnum.ONE.getCode()));
        SiteConfigEntity entity = BeanUtil.newBean(dto, SiteConfigEntity.class);
        this.saveOrUpdate(entity);
        redisUtil.delKey(RedisKeyConstant.SYS_SITE_SETTING);
        return R.ok();
    }

    public SiteConfigEntity checkExist(Long id){
        SiteConfigEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}