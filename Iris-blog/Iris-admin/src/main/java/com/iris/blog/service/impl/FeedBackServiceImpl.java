package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.domain.dto.app.AppFeedBackDTO;
import com.iris.blog.domain.search.SearchFeedBackDTO;
import com.iris.blog.domain.vo.UserVO;
import com.iris.blog.service.EmailService;
import com.iris.blog.service.UserService;
import com.iris.blog.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.FeedBackMapper;
import com.iris.blog.dao.entity.FeedBackEntity;
import com.iris.blog.domain.dto.FeedBackDTO;
import com.iris.blog.domain.vo.FeedBackVO;
import com.iris.blog.service.FeedBackService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户反馈表
 */
@Service("feedBackService")
@Slf4j
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBackEntity> implements FeedBackService {

    @Resource
    private EmailService emailService;

    @Resource
    private UserService userService;

    @Override
    public R selectFeedBackList(PageReq<SearchFeedBackDTO> req) {

        FeedBackEntity entity = BeanUtil.newBean(req.getAction(), FeedBackEntity.class);
        Page<FeedBackEntity> page = this.page((new Page<>(req.getPageNo(), req.getPageSize())),
                Wrappers.lambdaQuery(entity)
                        .orderByDesc(FeedBackEntity::getId));
        PageBean<FeedBackVO> pageBean = PageUtil.pageBean(page, FeedBackVO.class);
        return R.ok(pageBean);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeFeedBackByIds(List<Long> ids) {
        this.removeByIds(ids);
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateStatus(Long id) {
        FeedBackEntity backEntity = this.checkExist(id);
        boolean flag = !backEntity.getStatus();
        backEntity.setStatus(flag);
        this.updateById(backEntity);
        if(flag){
            UserVO vo = userService.getUserInfoByUserId(backEntity.getUserId());
            if(null == vo || StringUtils.isBlank(vo.getEmail())){
             return R.ok();
            }
            this.emailService.emailNotice(vo.getEmail(),backEntity.getTitle());
        }
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveFeedback(AppFeedBackDTO dto) {
        long userId = StpUtil.getLoginIdAsLong();
        UserVO vo = userService.getUserInfoByUserId(userId);
        FeedBackEntity entity = BeanUtil.newBean(dto, FeedBackEntity.class);
        entity.setUserId(userId);
        if(null != vo){
         entity.setIp(vo.getIp());
         entity.setIpAddress(vo.getIpAddress());
         entity.setOs(vo.getOs());
        }
        this.save(entity);
        return R.ok();
    }

    public FeedBackEntity checkExist(Long id) {
        FeedBackEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }
}