package com.iris.blog.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.config.redis.SysParamsRedis;
import com.iris.blog.domain.search.SearchSysConfigDTO;
import com.iris.blog.utils.ExcelUtils;
import com.iris.blog.utils.LambdaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.SysConfigMapper;
import com.iris.blog.dao.entity.SysConfigEntity;
import com.iris.blog.domain.dto.SysConfigDTO;
import com.iris.blog.domain.vo.SysConfigVO;
import com.iris.blog.service.SysConfigService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 参数管理
 */
@Service("sysConfigService")
@Slf4j
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigEntity> implements SysConfigService {

    @Resource
    private SysParamsRedis sysParamsRedis;

    @Override
    public R selectSysConfigList(PageReq<SearchSysConfigDTO> req){

        SysConfigEntity entity = BeanUtil.newBean(req.getAction(), SysConfigEntity.class);
        Page<SysConfigEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .orderByDesc(SysConfigEntity::getId));

        PageBean<SysConfigVO> pageBean = PageUtil.pageBean(page, SysConfigVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectSysConfigById( Long id){

        SysConfigEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        SysConfigVO vo = BeanUtil.newBean(entity, SysConfigVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveSysConfig(SysConfigDTO dto){
        SysConfigEntity entity = BeanUtil.newBean(dto, SysConfigEntity.class);
        this.save(entity);
        sysParamsRedis.set(entity.getParamCode(), entity.getParamValue());
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateSysConfig(SysConfigDTO dto){
        this.checkExist(dto.getId());
        SysConfigEntity entity = BeanUtil.newBean(dto, SysConfigEntity.class);
        this.updateById(entity);
        sysParamsRedis.set(entity.getParamCode(), entity.getParamValue());
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeSysConfigByIds(Long id){
        SysConfigEntity sysConfig = checkExist(id);
        this.removeById(id);
        sysParamsRedis.delete(sysConfig.getParamCode());
        return R.ok();
    }

    @Override
    public R updateStatusById(Long id) {
        SysConfigEntity sysConfig = this.checkExist(id);
        sysConfig.setStatus(!sysConfig.getStatus());
        this.updateById(sysConfig);
        sysParamsRedis.set(sysConfig.getParamCode(), sysConfig.getParamValue());
        return R.ok();
    }

    @Override
    public void export(SearchSysConfigDTO paramCode, HttpServletResponse response) {

        try {
            List<SysConfigEntity> list = this.list(Wrappers.<SysConfigEntity>lambdaQuery().eq(SysConfigEntity::getStatus, Boolean.TRUE)
                    .eq(StringUtils.isNotBlank(paramCode.getParamCode()),SysConfigEntity::getParamCode, paramCode.getParamCode())
                    .eq(null != paramCode.getStatus(),SysConfigEntity::getStatus,paramCode.getStatus())
                    .eq(null != paramCode.getParamType() ,SysConfigEntity::getParamType,paramCode.getParamType()).orderByDesc(SysConfigEntity::getId));
            ExcelUtils.write(response, "系统参数报表", "系统参数报表", SysConfigVO.class, list.stream().map(item -> BeanUtil.newBean(item, SysConfigVO.class)).toList());
        } catch (IOException e) {
            throw new BusinessException(ResultCode.REPORT_EXPORT_ERROR);
        }
    }

    @Override
    public String getValueByCode(String paramCode) {

        SysConfigEntity sysConfig = getSysConfigByCode(paramCode);

        return Optional.ofNullable(sysConfig).orElse(new SysConfigEntity()).getParamValue();
    }
    @Override
    public String getValueByCodeByRedis(String paramCode) {
        String paramValue = sysParamsRedis.get(paramCode);
        if(paramValue == null){
            SysConfigEntity sysConfig = getSysConfigByCode(paramCode);
            sysParamsRedis.set(paramCode, sysConfig.getParamValue());
        }
        return paramValue;
    }


    @Override
    public R updateValueByCode(String paramCode, String paramValue) {
        SysConfigEntity config = getSysConfigByCode(paramCode);
        config.setParamValue(paramValue);
        this.updateById(config);
        sysParamsRedis.set(paramCode, paramValue);
        return R.ok();
    }

    public SysConfigEntity checkExist(Long id){
        SysConfigEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }

    private SysConfigEntity getSysConfigByCode(String paramCode) {
        SysConfigEntity sysConfig = this.getOne(Wrappers.<SysConfigEntity>lambdaQuery().eq(SysConfigEntity::getParamCode, paramCode).last("limit 1"));
        Optional.ofNullable(sysConfig).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return sysConfig;
    }
}