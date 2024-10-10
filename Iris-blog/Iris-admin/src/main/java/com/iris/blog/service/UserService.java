package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.UserEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.UserDTO;
import com.iris.blog.domain.dto.UserUpdatePasswordDTO;
import com.iris.blog.domain.dto.app.AppRegisterDTO;
import com.iris.blog.domain.dto.app.AppUpdateEmailDTO;
import com.iris.blog.domain.dto.app.AppUserDTO;
import com.iris.blog.domain.search.SearchUserDTO;
import com.iris.blog.domain.vo.IPAddressVO;
import com.iris.blog.domain.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lstar
 * @date: 2024-04
 * @description: 系统管理-用户表
 */
public interface UserService extends IService<UserEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectUserList(PageReq<SearchUserDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectUserById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveUser(UserDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateUser(UserDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeUserByIds(List<Long> ids);

    /**
     * 更新用户登录信息
     * @param loginId
     * @param ipLocation
     */
    void updateUserLoginInfo(Object loginId, IPAddressVO ipLocation);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    UserEntity selectUserByName(String username);

    /**
     * 启用/禁用
     * @param id
     * @return
     */
    R updateStatus(Long id);

    /**
     * 重置用户密码
     * @param dto
     * @return
     */
    R updateUserPassword(UserUpdatePasswordDTO dto);

    /**
     * 获取当前登录用户信息
     * @return
     */
    R getUserInfo();

    /**
     * 修改用户邮箱
     * @param dto
     */
    void updateUserEmail(AppUpdateEmailDTO dto);

    /**
     * 修改用户头像
     * @param file
     * @return
     */
    R updateUserAvatar(MultipartFile file);

    /**
     * 修改用户信息
     * @param dto
     */
    void updateUserInfo(AppUserDTO dto);

    /**
     * 修改用户密码
     * @param dto
     */
    void updatePassword(AppRegisterDTO dto);

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    UserVO getUserInfoByUserId(long userId);
}

