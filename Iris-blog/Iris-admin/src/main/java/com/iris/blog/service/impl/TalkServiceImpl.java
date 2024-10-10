package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.common.enums.CommentTypeEnum;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.domain.vo.app.AppCommentCountVO;
import com.iris.blog.service.CommentService;
import com.iris.blog.service.CommonService;
import com.iris.blog.utils.HTMLUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.TalkMapper;
import com.iris.blog.dao.entity.TalkEntity;
import com.iris.blog.domain.dto.TalkDTO;
import com.iris.blog.domain.vo.TalkVO;
import com.iris.blog.service.TalkService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author lstar
 * @date: 2024-08
 * @description: 说说
 */
@Service("talkService")
@Slf4j
public class TalkServiceImpl extends ServiceImpl<TalkMapper, TalkEntity> implements TalkService {

    @Resource
    private CommentService commentService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public R selectTalkList(PageReq req){

        Page<TalkEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.<TalkEntity>lambdaQuery()
                        .orderByDesc(TalkEntity::getId));
        PageBean<TalkVO> pageBean = PageUtil.pageBean(page, TalkVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectTalkById( Long id){

        TalkEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        TalkVO vo = BeanUtil.newBean(entity, TalkVO.class);
        return R.ok(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveTalk(TalkDTO dto){
        TalkEntity entity = BeanUtil.newBean(dto, TalkEntity.class);
        entity.setUserId(StpUtil.getLoginIdAsLong());
        this.save(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateTalk(TalkDTO dto){
        this.checkExist(dto.getId());
        TalkEntity entity = BeanUtil.newBean(dto, TalkEntity.class);
        this.updateById(entity);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeTalkByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    @Override
    public R<List<String>> listTalkHome() {

        // 查询最新5条说说
        List<TalkEntity> talkList = this.list(new LambdaQueryWrapper<TalkEntity>()
                .select(TalkEntity::getTalkContent)
                .eq(TalkEntity::getStatus, BaseNumberEnum.ONE.getCode())
                .orderByDesc(TalkEntity::getIsTop)
                .orderByDesc(TalkEntity::getId)
                .last("limit 5"));

        if (talkList == null || talkList.isEmpty()) {
            return R.ok(Collections.emptyList());
        }
        List<String> list = talkList.stream()
                .map(item -> item.getTalkContent().length() > 200
                        ? HTMLUtils.deleteHtmlTag(item.getTalkContent().substring(0, 200))
                        : HTMLUtils.deleteHtmlTag(item.getTalkContent()))
                .toList();
        return R.ok(list);
    }

    @Override
    public R<PageBean<TalkVO>> talkList(PageReq req) {
        Page<TalkVO> page = new Page<>(req.getPageNo(), req.getPageSize());


        List<TalkVO> talkRespList = this.baseMapper.talkList(page);
        if(CollectionUtils.isEmpty(talkRespList)){
            return R.ok(new PageBean<>());
        }
        // 查询说说评论量
        List<Long> talkIdList = talkRespList.stream()
                .map(TalkVO::getId)
                .collect(Collectors.toList());
        List<AppCommentCountVO> commentCountVOList = commentService.selectCommentCountByTypeId(talkIdList, CommentTypeEnum.TALK.getType());
        Map<Integer, Integer> commentCountMap = commentCountVOList.stream()
                .collect(Collectors.toMap(AppCommentCountVO::getId, AppCommentCountVO::getCommentCount));
        // 查询说说点赞量
        Map<String, Integer> likeCountMap = redisUtil.getHashKeyAll(RedisKeyConstant.TALK_LIKE_COUNT);
        // 封装说说
        page.setRecords(talkRespList);
        PageBean<TalkVO> pageBean = PageUtil.pageBean(page, TalkVO.class);
        pageBean.getRecords().forEach(item -> {
            TalkVO talkVO = BeanUtil.newBean(item, TalkVO.class);
            talkVO.setLikeCount(Optional.ofNullable(likeCountMap.get(item.getId().toString())).orElse(0));
            talkVO.setCommentCount(Optional.ofNullable(commentCountMap.get(item.getId())).orElse(0));
        });
        return R.ok(pageBean);
    }

    @Override
    public R<TalkVO> getTalkById(Integer talkId) {
        // 查询说说信息
        TalkVO talkVO = this.baseMapper.selectTalkById(talkId);
        if (Objects.isNull(talkVO)) {
            return null;
        }
        // 查询说说评论量
        List<Long> talkIdList = CollectionUtil.toList(talkVO.getId());
        List<AppCommentCountVO> commentCountVOList = commentService.selectCommentCountByTypeId(talkIdList, CommentTypeEnum.TALK.getType());

        // 查询说说点赞量
        Object likeCount = redisUtil.getHashKey(RedisKeyConstant.TALK_LIKE_COUNT, talkId.toString());
        talkVO.setLikeCount((Integer) Optional.ofNullable(likeCount).orElse(0));
        if(CollectionUtils.isNotEmpty(commentCountVOList)){
         talkVO.setCommentCount(commentCountVOList.get(0).getCommentCount());
        }else {
            talkVO.setCommentCount(0);
        }
        return R.ok(talkVO);
    }


    public TalkEntity checkExist(Long id){
        TalkEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}