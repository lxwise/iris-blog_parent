package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.ArticleCollectMapper;
import com.iris.blog.dao.entity.ArticleCollectEntity;
import com.iris.blog.domain.vo.ArticleCollectVO;
import com.iris.blog.service.ArticleCollectService;

import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章收藏表
 */
@Service("articleCollectService")
@Slf4j
public class ArticleCollectServiceImpl extends ServiceImpl<ArticleCollectMapper, ArticleCollectEntity> implements ArticleCollectService {

    @Override
    public R selectArticleCollectList(PageReq req){

        Page<ArticleCollectEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.<ArticleCollectEntity>lambdaQuery()
                        .eq(ArticleCollectEntity::getUserId, StpUtil.getLoginIdAsLong())
                        .orderByDesc(ArticleCollectEntity::getId));

        PageBean<ArticleCollectVO> pageBean = PageUtil.pageBean(page, ArticleCollectVO.class);
        return R.ok(pageBean);
    }

    @Override
    public R collect(Long id) {
        ArticleCollectEntity entity = new ArticleCollectEntity();
        entity.setUserId(StpUtil.getLoginIdAsLong());
        entity.setArticleId(id);
        this.save(entity);
        return R.ok();
    }

    @Override
    public R cancelCollect(Long id) {
        this.remove(new LambdaQueryWrapper<ArticleCollectEntity>().eq(ArticleCollectEntity::getUserId,StpUtil.getLoginIdAsString()).eq(ArticleCollectEntity::getArticleId,id));
        return R.ok();
    }


    public ArticleCollectEntity checkExist(Long id){
        ArticleCollectEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}