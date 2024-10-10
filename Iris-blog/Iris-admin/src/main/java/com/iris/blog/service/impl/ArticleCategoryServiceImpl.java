package com.iris.blog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.search.app.AppSearchAppArticleDTO;
import com.iris.blog.domain.vo.app.AppArticleCategoryVO;
import com.iris.blog.domain.vo.app.AppArticleHomeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.ArticleCategoryMapper;
import com.iris.blog.dao.entity.ArticleCategoryEntity;
import com.iris.blog.domain.dto.ArticleCategoryDTO;
import com.iris.blog.domain.vo.ArticleCategoryVO;
import com.iris.blog.service.ArticleCategoryService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章分类表
 */
@Service("articleCategoryService")
@Slf4j
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategoryEntity> implements ArticleCategoryService {

    @Override
    public R selectArticleCategoryList(PageReq<SearchNameDTO> req){

        Page<ArticleCategoryVO> page= new Page<>(req.getPageNo(), req.getPageSize());
        page = this.baseMapper.selectArticleCategoryList(page,req.getAction());

        PageBean<ArticleCategoryVO> pageBean = PageUtil.pageBean(page, ArticleCategoryVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectArticleCategoryById( Long id){

        ArticleCategoryEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        ArticleCategoryVO vo = BeanUtil.newBean(entity, ArticleCategoryVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveArticleCategory(ArticleCategoryDTO dto){
        checkNameExist(dto,null);
        ArticleCategoryEntity entity = BeanUtil.newBean(dto, ArticleCategoryEntity.class);
        this.save(entity);
        return R.ok();
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateArticleCategory(ArticleCategoryDTO dto){
        checkNameExist(dto, dto.getId());
        ArticleCategoryEntity entity = BeanUtil.newBean(dto, ArticleCategoryEntity.class);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeArticleCategoryByIds(List<Long> ids){
        ids.forEach(this::validateCategoryHasExistArticle);
        this.removeByIds(ids);
        return R.ok();
    }

    @Override
    public R<List<ArticleCategoryVO>> selectList(String name) {
        LambdaQueryWrapper<ArticleCategoryEntity> queryWrapper = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like(ArticleCategoryEntity::getName,name);
        }
        queryWrapper.orderByDesc(ArticleCategoryEntity::getSort)
                .select(ArticleCategoryEntity::getId,ArticleCategoryEntity::getName);
        List<ArticleCategoryEntity> list = this.list(queryWrapper);

        return R.ok(BeanUtil.sourceToTarget(list, ArticleCategoryVO.class));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long savaCategoryByName(String categoryName) {
        LambdaQueryWrapper<ArticleCategoryEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ArticleCategoryEntity::getName, categoryName);
        ArticleCategoryEntity category = this.getOne(queryWrapper);
        if(ObjectUtil.isNull(category)){
            category = new ArticleCategoryEntity();
            category.setName(categoryName);
            category.setSort(0);
            this.save(category);
        }
        return category.getId();
    }

    @Override
    public List<ArticleCategoryVO> articleCategoryCount() {
        return this.baseMapper.articleCategoryCount();
    }

    @Override
    public R<List<AppArticleCategoryVO>> listCategory() {
        List<ArticleCategoryVO> list = this.baseMapper.articleCategoryCount();
        if(CollectionUtils.isEmpty(list)){
            return R.ok();
        }
        List<AppArticleCategoryVO> collect = list.stream().map(item -> {
            AppArticleCategoryVO vo = new AppArticleCategoryVO();
            vo.setId(item.getId());
            vo.setCategoryName(item.getName());
            vo.setArticleCount(item.getArticleCount());
            return vo;
        }).collect(Collectors.toList());
        return R.ok(collect);
    }

    @Override
    public R<PageBean<AppArticleHomeVO>> articleCategoryList(PageReq<AppSearchAppArticleDTO> req) {
        Page<AppArticleHomeVO> page= new Page<>(req.getPageNo(), req.getPageSize());
        page = this.baseMapper.articleCategoryList(page,req.getAction());
        PageBean<AppArticleHomeVO> pageBean = PageUtil.pageBean(page, AppArticleHomeVO.class);
        return R.ok(pageBean);
    }


    /**
     * 校验分类下是否存在资源
     * @param id
     */
    private void validateCategoryHasExistArticle(Long id) {
        int count = this.baseMapper.validateCategoryHasExistArticle(id);
        if (count > 0){
            throw new BusinessException(ResultCode.NAME_IS_EXIST_RESOURCE);
        }
    }

    /**
     * 校验名称是否存在
     * @param dto
     * @param id
     */
    private void checkNameExist(ArticleCategoryDTO dto,Long id) {
        LambdaQueryWrapper<ArticleCategoryEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ArticleCategoryEntity::getName, dto.getName());
        ArticleCategoryEntity category = this.getOne(queryWrapper);

        if(ObjectUtil.isNotNull(category)){
            if(null == id) {
                throw new BusinessException(ResultCode.NAME_IS_EXIST);
            }
            if(!Objects.equals(id, category.getId())){
                throw new BusinessException(ResultCode.NAME_IS_EXIST);
            }
        }


    }

    public ArticleCategoryEntity checkExist(Long id){
        ArticleCategoryEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}