package com.iris.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.blog.common.ResultCode;
import com.iris.blog.common.constant.CommonConstant;
import com.iris.blog.common.constant.RedisKeyConstant;
import com.iris.blog.components.oss.dto.UploadDTO;
import com.iris.blog.config.redis.RedisUtil;
import com.iris.blog.dao.entity.UserInfoEntity;
import com.iris.blog.dao.entity.UserRoleEntity;
import com.iris.blog.domain.dto.UserUpdatePasswordDTO;
import com.iris.blog.domain.dto.app.AppRegisterDTO;
import com.iris.blog.domain.dto.app.AppUpdateEmailDTO;
import com.iris.blog.domain.dto.app.AppUserDTO;
import com.iris.blog.domain.search.SearchUserDTO;
import com.iris.blog.domain.vo.IPAddressVO;
import com.iris.blog.service.OssFileService;
import com.iris.blog.service.UserInfoService;
import com.iris.blog.service.UserRoleService;
import com.iris.blog.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iris.blog.common.R;
import com.iris.blog.common.PageBean;
import com.iris.blog.utils.PageUtil;
import com.iris.blog.utils.BeanUtil;
import com.iris.blog.common.PageReq;
import com.iris.blog.common.exception.BusinessException;
import com.iris.blog.dao.mapper.UserMapper;
import com.iris.blog.dao.entity.UserEntity;
import com.iris.blog.domain.dto.UserDTO;
import com.iris.blog.domain.vo.UserVO;
import com.iris.blog.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户表
 */
@Service("userService")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private OssFileService ossFileService;

    @Override
    public R selectUserList(PageReq<SearchUserDTO> req){

        SearchUserDTO action = req.getAction();
        Page<UserVO> page = new Page<>(req.getPageNo(), req.getPageSize());
        page = this.baseMapper.pageList(page,action);

        PageBean<UserVO> pageBean = PageUtil.pageBean(page, UserVO.class);
        return R.ok(pageBean);
    }


    @Override
    public R selectUserById( Long id){

        UserVO entity = this.baseMapper.getUserInfoById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return R.ok(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveUser(UserDTO dto){
        UserEntity entity = BeanUtil.newBean(dto, UserEntity.class);
        UserEntity userEntity = selectUserByName(entity.getUsername());
        if(ObjectUtils.isNotEmpty(userEntity)){
            throw new BusinessException(ResultCode.USER_IS_EXIST);
        }

        //保存用户
        if(StringUtils.isBlank(entity.getPassword())){
            entity.setPassword(EncryptUtil.encrypt(CommonConstant.DEFAULT_PASSWORD,CommonConstant.KEY));
        }else {
            entity.setPassword(EncryptUtil.encrypt(entity.getPassword(),CommonConstant.KEY));
        }
        this.save(entity);

        //保存用户信息
        UserInfoEntity userInfo = BeanUtil.newBean(dto, UserInfoEntity.class);
        userInfo.setId(entity.getId());
        userInfoService.save(userInfo);

        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R updateUser(UserDTO dto){
        this.checkExist(dto.getId());
        UserEntity entity = BeanUtil.toBean(dto, UserEntity.class);
        this.updateById(entity);
        //保存用户信息
        UserInfoEntity userInfo = BeanUtil.newBean(dto, UserInfoEntity.class);
        userInfo.setId(entity.getId());
        userInfoService.updateById(userInfo);
        return R.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R removeUserByIds(List<Long> ids){
        this.removeByIds(ids);
        //删除用户信息
        this.userInfoService.removeByIds(ids);
        //删除角色信息
        LambdaQueryWrapper<UserRoleEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(UserRoleEntity::getUserId, ids);
        this.userRoleService.remove(queryWrapper);
        return R.ok();
    }

    @Override
    public void updateUserLoginInfo(Object loginId, IPAddressVO ipLocation) {

        LambdaUpdateWrapper<UserEntity> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(UserEntity::getLastLoginTime, LocalDateTime.now() )
                .set((!CommonConstant.UNKNOWN.equals(ipLocation.getIp())),UserEntity::getIp, ipLocation.getIp())
                .set((!CommonConstant.UNKNOWN.equals(ipLocation.getDevice())),UserEntity::getOs, ipLocation.getDevice())
                .set((!CommonConstant.UNKNOWN.equals(ipLocation.getLatitude())),UserEntity::getLatitude, ipLocation.getLatitude())
                .set((!CommonConstant.UNKNOWN.equals(ipLocation.getLongitude())),UserEntity::getLongitude, ipLocation.getLongitude())
                .set((!CommonConstant.UNKNOWN.equals(ipLocation.getFullLocation())),UserEntity::getIpAddress, ipLocation.getFullLocation())
                .eq(UserEntity::getId,loginId);
        this.update(updateWrapper);
    }

    @Override
    public UserEntity selectUserByName(String username) {
        UserEntity user = this.getOne(Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getUsername, username)
                .last("limit 1")
        );
        return user;
    }

    @Override
    public R updateStatus(Long id) {
        UserEntity entity = checkExist(id);
        entity.setStatus(!entity.getStatus());
        this.updateById(entity);
        return R.ok();
    }

    @Override
    public R updateUserPassword(UserUpdatePasswordDTO dto) {
        UserEntity userEntity = checkExist(dto.getId());
        userEntity.setPassword(EncryptUtil.encrypt(dto.getPassword(),CommonConstant.KEY));
        this.updateById(userEntity);
        return R.ok();
    }

    @Override
    public R getUserInfo() {

        long userId = StpUtil.getLoginIdAsLong();
        UserVO entity = this.baseMapper.getUserInfoById(userId);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        Set<Object> articleLikeSet = redisUtil.getAllSet(RedisKeyConstant.USER_ARTICLE_LIKE + userId);
        Set<Object> commentLikeSet = redisUtil.getAllSet(RedisKeyConstant.USER_COMMENT_LIKE + userId);
        Set<Object> talkLikeSet = redisUtil.getAllSet(RedisKeyConstant.USER_TALK_LIKE + userId);
        entity.setArticleLikeSet(articleLikeSet);
        entity.setCommentLikeSet(commentLikeSet);
        entity.setTalkLikeSet(talkLikeSet);
        return R.ok(entity);
    }

    @Override
    public void updateUserEmail(AppUpdateEmailDTO dto) {
        verifyCode(dto.getEmail(), dto.getCode());
        UserInfoEntity newUser = new UserInfoEntity();
        newUser.setId(StpUtil.getLoginIdAsLong());
        newUser.setEmail(dto.getEmail());
        userInfoService.updateById(newUser);
    }

    @Override
    public R updateUserAvatar(MultipartFile file) {
        UploadDTO upload = ossFileService.upload(file);
        UserInfoEntity newUser = new UserInfoEntity();
        newUser.setId(StpUtil.getLoginIdAsLong());
        newUser.setAvatar(upload.getUrl());
        userInfoService.updateById(newUser);
        return R.ok(upload);
    }

    @Override
    public void updateUserInfo(AppUserDTO dto) {

        UserInfoEntity userInfo = BeanUtil.newBean(dto, UserInfoEntity.class);
        userInfo.setId(StpUtil.getLoginIdAsLong());
        userInfoService.updateById(userInfo);
    }

    @Override
    public void updatePassword(AppRegisterDTO dto) {

        verifyCode(dto.getUsername(), dto.getCode());
        UserEntity existUser = this.getOne(new LambdaQueryWrapper<UserEntity>()
                .select(UserEntity::getUsername)
                .eq(UserEntity::getUsername, dto.getUsername()));
        Assert.notNull(existUser, "邮箱尚未注册！");
        // 根据用户名修改密码
        this.update(Wrappers.<UserEntity>lambdaUpdate()
                .set(UserEntity::getPassword, EncryptUtil.encrypt(dto.getPassword(),CommonConstant.KEY))
                .eq(UserEntity::getUsername, dto.getUsername()));
    }

    @Override
    public UserVO getUserInfoByUserId(long userId) {
        return this.baseMapper.getUserInfoByUserId(userId);
    }

    public UserEntity checkExist(Long id){
        UserEntity entity = this.getById(id);
        Optional.ofNullable(entity).orElseThrow(() -> new BusinessException(ResultCode.NO_DATA_ERROR));
        return entity;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     */
    public void verifyCode(String username, String code) {
        String sysCode = redisUtil.getKeyValue(RedisKeyConstant.USER_LOGIN_CODE + username,String.class);
        Assert.notBlank(sysCode, "验证码未发送或已过期！");
        Assert.isTrue(sysCode.equals(code), "验证码错误，请重新输入！");
    }
}