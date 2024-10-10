package com.iris.blog.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iris.blog.dao.entity.CarouselEntity;
import com.iris.blog.common.R;
import com.iris.blog.common.PageReq;
import com.iris.blog.domain.dto.CarouselDTO;
import com.iris.blog.domain.vo.app.AppCarouselVO;

/**
 * @author lstar
 * @date: 2024-08
 * @description: 首页轮播
 */
public interface CarouselService extends IService<CarouselEntity> {

    /**
    * 列表
     * @param req
     * @return
    */
    R selectCarouselPageList(PageReq req);

    /**
     * 保存
     * @param dto
     * @return
     */
    R saveCarousel(CarouselDTO dto);

    /**
     * 删除
     * @param ids
     * @return
     */
    R removeCarouselByIds(List<Integer> ids);

    /**
     * 修改状态
     * @param id
     * @return
     */
    R updateStatusById(Long id);


    /**
     * 首页轮播图列表
     * @return
     */
    List<AppCarouselVO> getCarouselList();
}

