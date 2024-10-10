package com.iris.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.dao.entity.DictTypeEntity;
import com.iris.blog.domain.search.SearchDictDataDTO;
import com.iris.blog.domain.vo.DictTypeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import com.iris.blog.dao.mapper.DictDataMapper;
import com.iris.blog.dao.entity.DictDataEntity;
import com.iris.blog.domain.dto.DictDataDTO;
import com.iris.blog.domain.vo.DictDataVO;
import com.iris.blog.service.DictDataService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 字典数据
 */
@Service("dictDataService")
@Slf4j
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictDataEntity> implements DictDataService {

    @Override
    public R selectDictDataList(PageReq<SearchDictDataDTO> req){

        DictDataEntity entity = BeanUtil.newBean(req.getAction(), DictDataEntity.class);
        Page<DictDataEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .orderByDesc(DictDataEntity::getId));

        PageBean<DictDataVO> pageBean = PageUtil.pageBean(page, DictDataVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectDictDataById( Long id){

        DictDataEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        DictDataVO vo = BeanUtil.newBean(entity, DictDataVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveDictData(DictDataDTO dto){
        DictDataEntity entity = BeanUtil.newBean(dto, DictDataEntity.class);
        this.save(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateDictData(DictDataDTO dto){
        this.checkExist(dto.getId());
        DictDataEntity entity = BeanUtil.newBean(dto, DictDataEntity.class);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeDictDataByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    @Override
    public R<List<DictDataVO>> selectList(String dictType) {
        LambdaQueryWrapper<DictDataEntity> queryWrapper = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(dictType)){
            queryWrapper.eq(DictDataEntity::getDictType,dictType);
        }
        queryWrapper.orderByDesc(DictDataEntity::getSort)
                .select(DictDataEntity::getId,DictDataEntity::getDictType,DictDataEntity::getDictLabel,DictDataEntity::getDictValue);
        List<DictDataEntity> list = this.list(queryWrapper);

        return R.ok(BeanUtil.sourceToTarget(list, DictDataVO.class));
    }

    @Override
    public void updateDictDataByType(String oldDictType,String dictType) {
        LambdaUpdateWrapper<DictDataEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(DictDataEntity::getDictType,oldDictType)
                .set(DictDataEntity::getDictType,dictType);
        this.update(updateWrapper);
    }

    public DictDataEntity checkExist(Long id){
        DictDataEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}