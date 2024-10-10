package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.domain.search.SearchDictTypeDTO;
import com.iris.blog.service.DictDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.DictTypeMapper;
import com.iris.blog.dao.entity.DictTypeEntity;
import com.iris.blog.domain.dto.DictTypeDTO;
import com.iris.blog.domain.vo.DictTypeVO;
import com.iris.blog.service.DictTypeService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典类型
 */
@Service("dictTypeService")
@Slf4j
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictTypeEntity> implements DictTypeService {

    @Resource
    private DictDataService dictDataService;
    @Override
    public R selectDictTypeList(PageReq<SearchDictTypeDTO> req){

        DictTypeEntity entity = BeanUtil.newBean(req.getAction(), DictTypeEntity.class);
        Page<DictTypeEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .orderByDesc(DictTypeEntity::getSort));

        PageBean<DictTypeVO> pageBean = PageUtil.pageBean(page, DictTypeVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectDictTypeById( Long id){

        DictTypeEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        DictTypeVO vo = BeanUtil.newBean(entity, DictTypeVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveDictType(DictTypeDTO dto){
        DictTypeEntity entity = BeanUtil.newBean(dto, DictTypeEntity.class);
        this.save(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateDictType(DictTypeDTO dto){
        DictTypeEntity type = this.checkExist(dto.getId());
        DictTypeEntity entity = BeanUtil.newBean(dto, DictTypeEntity.class);
        this.updateById(entity);
        //修改类型相关的字典值
        this.dictDataService.updateDictDataByType(type.getDictType(),dto.getDictType());
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeDictTypeByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    @Override
    public R<List<DictTypeVO>> selectList() {
        LambdaQueryWrapper<DictTypeEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(DictTypeEntity::getSort).select(DictTypeEntity::getId,DictTypeEntity::getDictName,DictTypeEntity::getDictType);
        List<DictTypeEntity> list = this.list(queryWrapper);
        return R.ok(BeanUtil.sourceToTarget(list, DictTypeVO.class));
    }

    public DictTypeEntity checkExist(Long id){
        DictTypeEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}