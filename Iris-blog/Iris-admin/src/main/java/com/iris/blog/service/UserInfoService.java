package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.UserInfoEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.UserInfoDTO;
/**
 * @author lstar
 * @date: 2024-04
 * @description: 用户信息表
 */
public interface UserInfoService extends IService<UserInfoEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectUserInfoList(PageReq<UserInfoDTO> req);

    /**
     * 信息
     * @param id
     * @return
     */
    R selectUserInfoById( Long id);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveUserInfo(UserInfoDTO dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    R updateUserInfo(UserInfoDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeUserInfoByIds(List<Long> ids);

}

