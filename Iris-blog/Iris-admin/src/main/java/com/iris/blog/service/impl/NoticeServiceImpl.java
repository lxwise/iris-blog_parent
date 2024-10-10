package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iris.blog.common.PageBean;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.R;
import com.iris.blog.common.enums.BaseNumberEnum;
import com.iris.blog.common.enums.NoticeTypeEnum;
import com.iris.blog.dao.entity.ArticleEntity;
import com.iris.blog.dao.entity.CommentEntity;
import com.iris.blog.dao.entity.TalkEntity;
import com.iris.blog.dao.mapper.TalkMapper;
import com.iris.blog.domain.dto.NoticeDTO;
import com.iris.blog.domain.vo.NoticeVO;
import com.iris.blog.domain.vo.UserVO;
import com.iris.blog.domain.vo.app.AppNoticeCountVO;
import com.iris.blog.service.ArticleService;
import com.iris.blog.service.UserInfoService;
import com.iris.blog.service.UserService;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.dao.mapper.NoticeMapper;
import com.iris.blog.dao.entity.NoticeEntity;
import com.iris.blog.service.NoticeService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lstar
 * @date: 2024-09
 * @description: 系统通知
 */
@Service("noticeService")
@Slf4j
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeEntity> implements NoticeService {

    @Resource
    private ThreadPoolTaskExecutor executor;

    @Resource
    private UserService userService;
    @Resource
    private ArticleService articleService;
    @Resource
    private TalkMapper talkMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public R<PageBean<NoticeVO>> getNoticeList(PageReq<NoticeDTO> req) {
        long userId = StpUtil.getLoginIdAsLong();
        Page<NoticeEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.<NoticeEntity>lambdaQuery()
                        .eq(NoticeEntity::getFromUserId, userId)
                        .eq(NoticeEntity::getNoticeType, req.getAction().getNoticeType())
                        .orderByAsc(NoticeEntity::getStatus)
                        .orderByDesc(NoticeEntity::getId)
        );
        PageBean<NoticeVO> pageBean = PageUtil.pageBean(page, NoticeVO.class);
        if(CollectionUtils.isNotEmpty(page.getRecords())){
            List<Long> ids = page.getRecords().stream().map(NoticeEntity::getId).toList();
            LambdaUpdateWrapper<NoticeEntity> updateWrapper = Wrappers.lambdaUpdate();
            updateWrapper.set(NoticeEntity::getStatus, 1).in(NoticeEntity::getId, ids);
            this.update(updateWrapper);
        }
        return R.ok(pageBean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void clearAllNotice(Integer type) {
        long userId = StpUtil.getLoginIdAsLong();
        LambdaQueryWrapper<NoticeEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(NoticeEntity::getFromUserId, userId)
                .eq(NoticeEntity::getNoticeType, type);
        this.remove(queryWrapper);
    }

    @Override
    public R<AppNoticeCountVO> readAllNotice() {
        long userId = StpUtil.getLoginIdAsLong();
        AppNoticeCountVO vo = this.baseMapper.readAllNotice(userId);
        return R.ok(vo);
    }

    @Override
    public void sendNotice(long userId, CommentEntity newComment,Boolean isComment) {
        // 通知用户
        UserVO userVO =  userService.getUserInfoByUserId(userId);
        if(null == userVO){
            return;
        }
        this.sendNoticeAsync(userVO, newComment,isComment);
    }

    @Override
    public void sendBathNotice(long userId, List<CommentEntity> commentList) {
        // 通知用户
        UserVO userVO =  userService.getUserInfoByUserId(userId);
        if(null == userVO){
            return;
        }
        commentList.forEach(comment -> this.sendNoticeAsync(userVO, comment,true));
    }

    private void sendNoticeAsync(UserVO userVO, CommentEntity newComment,Boolean isComment) {
        executor.execute(() -> {
            try {
                NoticeEntity vo = BeanUtil.newBean(userVO, NoticeEntity.class);
                vo.setId(null);
                vo.setToUserId(userVO.getId());
                vo.setToUserNickname(userVO.getNickname());
                vo.setToUserAvatar(userVO.getAvatar());
                vo.setContent(getNoticeContentType(newComment.getCommentType(), newComment.getTypeId(),vo));

                if(isComment){
                    vo.setNoticeType(BaseNumberEnum.TWO.getCode());
                    vo.setTypeId(Long.valueOf(newComment.getTypeId()));
                    if(null != newComment.getReplyUserId()){
                        vo.setFromUserId(newComment.getReplyUserId());
                    }
                }else {
                    vo.setNoticeType(BaseNumberEnum.THREE.getCode());
                    vo.setTypeId(Long.valueOf(newComment.getTypeId()));
                    vo.setFromUserId(newComment.getUserId());
                }
                vo.setNoticeTypePath(getNoticeContentTypePath(newComment.getCommentType()));
                this.save(vo);
            } catch (Exception e) {
               log.error("发送通知失败,错误:{}",e.getMessage(),e);
            }

        });
    }


    /**
     * 获取通知类型内容描述
     * @param commentType
     * @param typeId
     * @return
     */
    private String getNoticeContentType(Integer commentType, Integer typeId,NoticeEntity vo) {
        String desc = "您有新的消息,请点击前往查看!";
        if(commentType == 1){
            ArticleEntity article = articleService.getById(typeId);
            if(article == null){
                desc = "您评论的文章有了新动态,请点击前往查看!";
            }else {
                vo.setFromUserId(article.getAuthorId());
                desc = "您评论的文章《"+article.getTitle()+"》有了新动态,请点击前往查看!";
            }
        }
        if(commentType == 2){
            desc = "您评论的友链有了新动态,请点击前往查看!";
        }
        if(commentType == 3){
            TalkEntity talkEntity = talkMapper.selectById(typeId);
            desc = "您评论的说说有了新动态,请点击前往查看!";
            if(talkEntity != null){
                vo.setFromUserId(talkEntity.getUserId());
            }
        }
        return desc;
    }

    /**
     * 获取通知类型路径
     * @param commentType
     * @return
     */
    private String getNoticeContentTypePath(Integer commentType) {
        if(commentType == 1){
            return NoticeTypeEnum.ARTICLE.getPath();
        }
        if(commentType == 2){
            return NoticeTypeEnum.FRIEND.getPath();
        }
        if(commentType == 3){
            return NoticeTypeEnum.TALK.getPath();
        }
        return NoticeTypeEnum.NOTICE.getPath();
    }
}