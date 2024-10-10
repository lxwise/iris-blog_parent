package com.iris.blog.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.ArticleCategoryEntity;
import com.iris.blog.dao.entity.SiteConfigEntity;
import com.iris.blog.domain.search.SearchNameDTO;
import com.iris.blog.domain.vo.ArticleCategoryVO;
import com.iris.blog.domain.vo.SiteConfigVO;
import com.iris.blog.domain.vo.app.AppArticleTagVO;
import com.iris.blog.utils.LambdaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.ArticleTagMapper;
import com.iris.blog.dao.entity.ArticleTagEntity;
import com.iris.blog.domain.dto.ArticleTagDTO;
import com.iris.blog.domain.vo.ArticleTagVO;
import com.iris.blog.service.ArticleTagService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章标签表
 */
@Service("articleTagService")
@Slf4j
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTagEntity> implements ArticleTagService {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public R selectArticleTagList(PageReq<SearchNameDTO> req){

        Page<ArticleTagVO> page= new Page<>(req.getPageNo(), req.getPageSize());
        page = this.baseMapper.selectArticleTagList(page,req.getAction());

        PageBean<ArticleTagVO> pageBean = PageUtil.pageBean(page, ArticleTagVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectArticleTagById( Long id){

        ArticleTagEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        ArticleTagVO vo = BeanUtil.newBean(entity, ArticleTagVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveArticleTag(ArticleTagDTO dto){
        checkNameExist(dto,null);
        ArticleTagEntity entity = BeanUtil.newBean(dto, ArticleTagEntity.class);
        this.save(entity);
        return R.ok();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateArticleTag(ArticleTagDTO dto){
        this.checkExist(dto.getId());
        checkNameExist(dto, dto.getId());
        ArticleTagEntity entity = BeanUtil.newBean(dto, ArticleTagEntity.class);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeArticleTagByIds(List<Long> ids){

        ids.forEach(this::validateTagHasExistArticle);
        this.removeByIds(ids);
        return R.ok();
    }

    @Override
    public List<String> getTagsByIds(List<String> strings) {

        LambdaQueryWrapper<ArticleTagEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(ArticleTagEntity::getId, strings);
        List<ArticleTagEntity> list = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }

        return LambdaUtil.convertList(list, ArticleTagEntity::getName);
    }

    @Override
    public List<ArticleTagVO> selectList(String name) {
        LambdaQueryWrapper<ArticleTagEntity> queryWrapper = Wrappers.lambdaQuery();
        if(StringUtils.isNotBlank(name)){
            queryWrapper.like(ArticleTagEntity::getName,name);
        }
        queryWrapper.orderByDesc(ArticleTagEntity::getSort)
                .select(ArticleTagEntity::getId,ArticleTagEntity::getName);
        List<ArticleTagEntity> list = this.list(queryWrapper);
        return BeanUtil.sourceToTarget(list, ArticleTagVO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String savaTagByName(List<String> tagNameList) {
        List<Long> list = new ArrayList<>();
        tagNameList.forEach(item ->{
            ArticleTagEntity articleTag = this.getOne(Wrappers.lambdaQuery(ArticleTagEntity.class).eq(ArticleTagEntity::getName, item));
            if(ObjectUtil.isNull(articleTag)){
                articleTag = new ArticleTagEntity();
                articleTag.setName(item);
                this.save(articleTag);
            }
            list.add(articleTag.getId());
        });
        return LambdaUtil.listToStringComma(list, Function.identity());
    }

    @Override
    public R<List<AppArticleTagVO>> articleTagList() {

        Object value = redisUtil.getValue(RedisKeyConstant.SYS_TAG_INFO_KEY);
        if (ObjectUtil.isNotNull(value) && value instanceof List){
            return R.ok((List<AppArticleTagVO>) value);
        }
        List<AppArticleTagVO> vos = this.baseMapper.articleTagList();
        redisUtil.saveRedisValue(RedisKeyConstant.SYS_TAG_INFO_KEY, vos,5L, TimeUnit.MINUTES);
        return R.ok(vos);
    }


    /**
     * 校验标签下是否存在资源
     * @param id
     */
    private void validateTagHasExistArticle(Long id) {
        int count = baseMapper.validateTagHasExistArticle(id);
        if (count > 0){
            throw new BusinessException(ResultCode.NAME_IS_EXIST_RESOURCE);
        }
    }

    /**
     * 校验名称是否存在
     * @param dto
     * @param id
     */
    private void checkNameExist(ArticleTagDTO dto, Long id) {
        LambdaQueryWrapper<ArticleTagEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ArticleTagEntity::getName, dto.getName());
        ArticleTagEntity tag = this.getOne(queryWrapper);

        if(ObjectUtil.isNotNull(tag)){
            if(null == id) {
                throw new BusinessException(ResultCode.NAME_IS_EXIST);
            }
            if(!Objects.equals(id, tag.getId())){
                throw new BusinessException(ResultCode.NAME_IS_EXIST);
            }
        }
    }

    public ArticleTagEntity checkExist(Long id){
        ArticleTagEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}