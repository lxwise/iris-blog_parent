package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.common.enums.CommentTypeEnum;
import com.iris.blog.common.enums.NoticeTypeEnum;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.*;
import com.iris.blog.dao.mapper.TalkMapper;
import com.iris.blog.domain.dto.UpdateCommentStatusDTO;
import com.iris.blog.domain.dto.app.AppCommentDTO;
import com.iris.blog.domain.search.SearchCommentDTO;
import com.iris.blog.domain.search.app.AppSearchCommentDTO;
import com.iris.blog.domain.vo.IPAddressVO;
import com.iris.blog.domain.vo.SiteConfigVO;
import com.iris.blog.domain.vo.UserVO;
import com.iris.blog.domain.vo.app.*;
import com.iris.blog.service.*;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.utils.HTMLUtils;
import com.iris.blog.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.ArticleCommentMapper;
import com.iris.blog.domain.vo.CommentVO;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 文章评论表
 */
@Service("commentService")
@Slf4j
public class CommentServiceImpl extends ServiceImpl<ArticleCommentMapper, CommentEntity> implements CommentService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UserService userService;
    @Resource
    private ArticleService articleService;
    @Resource
    private TalkMapper talkMapper;

    @Resource
    private NoticeService noticeService;

    @Override
    public R selectCommentList(PageReq<SearchCommentDTO> req){

        Page<CommentVO> page = new Page<>(req.getPageNo(), req.getPageSize());
                     page = this.baseMapper.selectCommentList(page,req.getAction());

        PageBean<CommentVO> pageBean = PageUtil.pageBean(page, CommentVO.class);
        return R.ok(pageBean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeCommentByIds(List<Long> ids){
        this.removeByIds(ids);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateStatus(UpdateCommentStatusDTO dto) {
        LambdaUpdateWrapper<CommentEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.in(CommentEntity::getId,dto.getIds())
                .set(CommentEntity::getStatus,dto.getStatus());
        this.update(updateWrapper);


        // 添加通知
        List<CommentEntity> commentList = this.listByIds(dto.getIds());
        if(CollectionUtils.isNotEmpty(commentList)){
            this.noticeService.sendBathNotice(StpUtil.getLoginIdAsLong(),commentList);
        }
        return R.ok();
    }

    @Override
    public R<List<AppRecentCommentLatestVO>> latestRecentCommentList() {
        return R.ok(this.baseMapper.latestRecentCommentList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addComment(AppCommentDTO comment) {

        // 校验评论参数
        verifyComment(comment);
        // 过滤标签和敏感词
        comment.setCommentContent(HTMLUtils.filter(comment.getCommentContent()));

        IPAddressVO addressVO = IPUtils.getIpAddressByTencent(null);
        long userId = StpUtil.getLoginIdAsLong();
        CommentEntity newComment = CommentEntity.builder()
                .userId(userId)
                .commentType(comment.getCommentType())
                .typeId(comment.getTypeId())
                .content(comment.getCommentContent())
                .parentId(comment.getParentId())
                .replyId(comment.getReplyId())
                .replyUserId(comment.getToUid())
                .parentId(comment.getParentId())
                .ip(addressVO != null ? addressVO.getIp() : null)
                .ipAddress(addressVO != null ? addressVO.getFullLocation() : null)
                .os(addressVO != null ? addressVO.getDevice() : null)
                .build();
        // 保存评论
        this.save(newComment);

        // 发送通知
        this.noticeService.sendNotice(userId, newComment,true);
    }

    @Override
    public R<PageBean<AppCommentVO>> viewCommentList(PageReq<AppSearchCommentDTO> req) {
        Page<AppCommentVO> page= new Page<>(req.getPageNo(), req.getPageSize());
        page = this.baseMapper.selectParentComment(page,req.getAction());
        List<AppCommentVO> commentRespList = page.getRecords();
        if (CollectionUtils.isEmpty(commentRespList)) {
           return R.ok(new PageBean<>());
        }
        // 评论点赞
        Map likeCountMap = redisUtil.getHashKeyAll(RedisKeyConstant.COMMENT_LIKE_COUNT);
        // 父评论id集合
        List<Integer> parentCommentIdList = commentRespList.stream().map(AppCommentVO::getId).collect(Collectors.toList());
        // 分组查询每组父评论下的子评论前三条
        List<AppReplyCommentVO> replyRespList = this.baseMapper.selectReplyByParentIdListLimit3(parentCommentIdList);
        // 封装子评论点赞量
        replyRespList.forEach(item -> item.setLikeCount((Integer) Optional.ofNullable(likeCountMap.get(item.getId().toString())).orElse(0)));
        // 根据父评论id生成对应子评论的Map
        Map<Integer, List<AppReplyCommentVO>> replyMap = replyRespList.stream().collect(Collectors.groupingBy(AppReplyCommentVO::getParentId));
        // 父评论的回复数量
        List<AppReplyCommentCountVO> replyCountList = this.baseMapper.selectReplyCountByParentId(parentCommentIdList);
        // 转换Map
        Map<Integer, Integer> replyCountMap = replyCountList.stream().collect(Collectors.toMap(AppReplyCommentCountVO::getCommentId, AppReplyCommentCountVO::getReplyCount));
        // 封装评论数据
        commentRespList.forEach(item -> {
            item.setLikeCount((Integer) Optional.ofNullable(likeCountMap.get(item.getId().toString())).orElse(0));
            item.setReplyVOList(replyMap.get(item.getId()));
            item.setReplyCount(Optional.ofNullable(replyCountMap.get(item.getId())).orElse(0));
        });

        PageBean<AppCommentVO> pageBean = PageUtil.pageBean(page, AppCommentVO.class);
        return R.ok(pageBean);
    }

    @Override
    public R<PageBean<AppReplyCommentVO>> viewReplyCommentList(PageReq<AppSearchCommentDTO> req) {
        Page<AppReplyCommentVO> page= new Page<>(req.getPageNo(), req.getPageSize());
        // 分页查询子评论
        page = this.baseMapper.selectReplyByParentId(page, req.getAction().getCommentId());
        if (Objects.isNull(page.getRecords())) {
            return R.ok(new PageBean<>());
        }
        // 子评论点赞Map
        Map likeCountMap = redisUtil.getHashKeyAll(RedisKeyConstant.COMMENT_LIKE_COUNT);
        page.getRecords().forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        PageBean<AppReplyCommentVO> pageBean = PageUtil.pageBean(page, AppReplyCommentVO.class);
        return R.ok(pageBean);
    }

    @Override
    public List<AppCommentCountVO> selectCommentCountByTypeId(List<Long> talkIdList, Integer type) {
        return this.baseMapper.selectCommentCountByTypeId(talkIdList,type);
    }

    /**
     * 校验评论参数
     * @param comment
     */
    private void verifyComment(AppCommentDTO comment) {
        if (comment.getCommentType().equals(CommentTypeEnum.ARTICLE.getType())) {
            ArticleEntity article = articleService.getOne(new LambdaQueryWrapper<ArticleEntity>().select(ArticleEntity::getId).eq(ArticleEntity::getId, comment.getTypeId()));
            Assert.notNull(article, "文章不存在");
        }
        if (comment.getCommentType().equals(CommentTypeEnum.TALK.getType())) {
            TalkEntity talk = talkMapper.selectOne(new LambdaQueryWrapper<TalkEntity>().select(TalkEntity::getId).eq(TalkEntity::getId, comment.getTypeId()));
            Assert.notNull(talk, "说说不存在");
        }
        // 评论为子评论，判断回复的评论和用户是否存在
        Optional.ofNullable(comment.getParentId()).ifPresent(parentId -> {
            // 判断父评论是否存在
            CommentEntity parentComment = this.getOne(new LambdaQueryWrapper<CommentEntity>().select(CommentEntity::getId, CommentEntity::getParentId, CommentEntity::getCommentType).eq(CommentEntity::getId, parentId));
            Assert.notNull(parentComment, "父评论不存在");
            Assert.isNull(parentComment.getParentId(), "当前评论为子评论，不能作为父评论");
            Assert.isTrue(comment.getCommentType().equals(parentComment.getCommentType()), "只能以同类型的评论作为父评论");
            // 判断回复的评论和用户是否存在
            CommentEntity replyComment = this.getOne(new LambdaQueryWrapper<CommentEntity>().select(CommentEntity::getId, CommentEntity::getParentId, CommentEntity::getUserId, CommentEntity::getCommentType).eq(CommentEntity::getId, comment.getReplyId()));
            UserEntity toUser = userService.getOne(new LambdaQueryWrapper<UserEntity>().select(UserEntity::getId).eq(UserEntity::getId, comment.getToUid()));
            Assert.notNull(replyComment, "回复的评论不存在");
            Assert.notNull(toUser, "回复的用户不存在");
            Assert.isTrue(comment.getCommentType().equals(replyComment.getCommentType()), "只能回复同类型的下的评论");
            if (Objects.nonNull(replyComment.getParentId())) {
                Assert.isTrue(replyComment.getParentId().equals(parentId), "提交的评论parentId与当前回复评论parentId不一致");
            }
            Assert.isTrue(replyComment.getUserId().equals(comment.getToUid()), "提交的评论toUid与当前回复评论fromUid不一致");
            // 只能回复当前父评论及其子评论
            List<Long> replyIdList = this.baseMapper.selectCommentIdByParentId(parentId);
            replyIdList.add(parentId);
            Assert.isTrue(replyIdList.contains(parentId), "当前父评论下不存在该子评论");
        });
    }

    public CommentEntity checkExist(Long id){
        CommentEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}